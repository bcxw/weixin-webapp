package com.zee.ordering.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zee.ordering.common.AjaxResult;
import com.zee.ordering.context.ConfigContext;
import com.zee.ordering.dao.AdminDao;
import com.zee.ordering.dao.ClockHistoryDao;
import com.zee.ordering.dao.ClockUserDao;
import com.zee.ordering.dao.MealAccountDao;
import com.zee.ordering.dao.MealHistoryDao;
import com.zee.ordering.dao.UserDao;
import com.zee.ordering.entity.ClockHistory;
import com.zee.ordering.entity.MealAccount;
import com.zee.ordering.entity.MealHistory;
import com.zee.ordering.service.MealManageService;

@Service
public class MealManageServiceImpl implements MealManageService {
	
	private static final Logger logger = LoggerFactory.getLogger(MealManageServiceImpl.class);
	
	
	@Resource
	private AdminDao adminDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private MealAccountDao mealAccountDao;
	
	@Resource
	private ClockHistoryDao clockHistoryDao;
	
	@Resource
	private ClockUserDao clockUserDao;
	
	@Resource
	private MealHistoryDao mealHistoryDao;
	
	@Resource
	private ConfigContext configContext;
	
	@Override
	public AjaxResult importClockHistory(InputStream inputStream) {
		HSSFWorkbook hssfWorkbook = null; 
		
		//本次导入的所有员工包括编号和名称
		List<Map<String,Object>> employeeList=new ArrayList<Map<String,Object>>();
		String clockMonth="";
		try {
			
			//要存储的打卡记录
			List<ClockHistory> clockHistoryList=new ArrayList<ClockHistory>();
			
			POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);  
	        hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);  
	        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0); 
	        
	        //打卡年月
	        HSSFRow monthRow = hssfSheet.getRow(2); 
	        HSSFCell monthCell = monthRow.getCell(25);  
	        clockMonth=monthCell.getStringCellValue();
	        clockMonth=clockMonth.replaceAll("考勤日期", "").replaceAll("：", "").replaceAll("：", "");
	        clockMonth=clockMonth.split("～")[0];
	        String[] tmpStrArr=clockMonth.split("-");
	        clockMonth=tmpStrArr[0]+"-"+tmpStrArr[1];
	        
	        if(mealAccountDao.existData(clockMonth)>0){
	        	return AjaxResult.error(clockMonth+"错误：此月份的结算数据已经生成！！！");
	        }
	        
	        //从第4行是考勤记录，上面是表头
	        int currentRowIndex = 4;
	        int rowEnd = hssfSheet.getLastRowNum();
	        while(currentRowIndex<=rowEnd){
	        	HSSFRow row = hssfSheet.getRow(currentRowIndex);  
	            if(null == row) continue;  
	            
	            //这行这个单元格肯定是工号文字，但还是判断一下
	            HSSFCell codeTextCell = row.getCell(1);
	            String codeText=codeTextCell.getStringCellValue();
	            if("工号：".equals(codeText)){
	            	HSSFCell codeCell = row.getCell(3);
	            	String code=codeCell.getStringCellValue();
	            	HSSFCell nameCell=row.getCell(11);
	            	String name=nameCell.getStringCellValue();
	            	
	            	Map<String,Object> emp=new HashMap<String,Object>();
	            	emp.put("code", code);emp.put("name", name);
	            	employeeList.add(emp);
	            	
	            	//读取此人的打卡记录，代码位置都不能变，是根据currentRowIndex来读取行的
	            	currentRowIndex++;
	            	HSSFRow dateRow=hssfSheet.getRow(currentRowIndex);
	            	
	            	//找下一个工号单元格的位置
	            	currentRowIndex++;
	            	//这个人这天日期开始的行数
	            	int timeRowStart=currentRowIndex;
	            	HSSFRow tempRow=hssfSheet.getRow(currentRowIndex);
	            	HSSFCell tempCell=tempRow.getCell(1);
	            	String tempText=tempCell.getStringCellValue();
	            	int timeRowNum=0;
	            	while(!"工号：".equals(tempText)){
	            		currentRowIndex++;
	            		timeRowNum++;
		            	tempRow=hssfSheet.getRow(currentRowIndex);
		            	if(tempRow!=null){
		            		tempCell=tempRow.getCell(1);
			            	tempText=tempCell.getStringCellValue();
		            	}else{
		            		break;
		            	}
		            	
	            	}
	            	
	            	//拿取当前人的所有打卡记录
	            	int cellStart = dateRow.getFirstCellNum();  
		            int cellEnd = dateRow.getLastCellNum();  
		            for(int k=cellStart;k<=cellEnd;k++)  {  
		                HSSFCell cell = dateRow.getCell(k); 
		                
		                if(null!=cell&&cell.getCellTypeEnum().equals(CellType.NUMERIC)){
		                	String clockDay = String.format("%02d", (int)cell.getNumericCellValue());
		                	String clockDate=clockMonth+"-"+clockDay;
		                	String clockDetail="";
		                	for(int d=0;d<timeRowNum;d++){
		                		HSSFRow timeRow=hssfSheet.getRow(timeRowStart+d);
		                		HSSFCell timeCell = timeRow.getCell(k); 
		                		String time=timeCell.getStringCellValue();
		                		if(StringUtils.isNotEmpty(time)){
		                			time=time.replaceAll(" ", "").replaceAll("\\r", "");
		                			String[] tmpArr=time.split("\\n");
		                			time=StringUtils.join(tmpArr,"|");
			                		clockDetail+=time+"|";
		                		}
		                		
		                		
		                	}
		                	
		                	//已经取完某一天的打卡记录，存入数据库
		                	ClockHistory clockHistory=new ClockHistory();
		                	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		                	clockHistory.setClockCode(code);
		                	clockHistory.setClockName(name);
		                	clockHistory.setClockDate(sdf.parse(clockDate));
		                	clockHistory.setClockDetail(clockDetail);
			                
		                	clockHistoryList.add(clockHistory);
		                	
		                }
		                 
		            } 
	            	
	            	
	            }
	           
	        } 
	        
	        clockHistoryDao.save(clockHistoryList);
	        
			
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage());
		}  finally{
			try {
				if(hssfWorkbook!=null)hssfWorkbook.close();
				if(inputStream!=null)inputStream.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
			
		}
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("clockMonth", clockMonth);
		data.put("employeeList", employeeList);
		data.put("weixinUserList", userDao.getAllUser());
		data.put("matchUserList", clockUserDao.getAllMatchUser());
		
		return AjaxResult.success(data);
	}


	@Override
	public AjaxResult mealClockAccount(String clockMonth, List<Map<String, Object>> empList) {
		
		/**判断是否已经有这月的结算数据**/
		if(mealAccountDao.existData(clockMonth)>0){
        	return AjaxResult.error(clockMonth+"此月份的结算数据已经生成！！！");
        }
		
		
		/**存储更新匹配人员,并生成一个临时全部微信对应打卡机的键值对应匹配方便查找**/
		Map<String,Map<String,Object>> empMatchMap=new HashMap<String,Map<String,Object>>();
		for(int i=0;i<empList.size();i++){
			Map<String, Object> oneEmp=empList.get(i);
			
			String weixinId=oneEmp.get("weixin_id")+"";
			String weixinName=oneEmp.get("weixin_name")+"";
			String clockId=oneEmp.get("clock_id")+"";
			String clockName=oneEmp.get("clock_name")+"";
			
			empMatchMap.put(weixinId+"|"+weixinName, oneEmp);
			
			int matchStatus=Integer.parseInt(oneEmp.get("match_status")+"");
			
			if(matchStatus==1){
				if(clockUserDao.existMatchUser(clockId,clockName)>0){
					clockUserDao.update(weixinId, weixinName, clockId, clockName);
				}else{
					clockUserDao.save(weixinId, weixinName, clockId, clockName);
				}
			}
			
		}
		
		/**生成此月的结算数据**/
		List<MealAccount> allMealAccountList=new ArrayList<MealAccount>();
		
		//存发生错误的就餐记录
		List<MealHistory> errMealHistoryList=new ArrayList<MealHistory>();
		
		List<ClockHistory> clockHistoryList=clockHistoryDao.getClockHistoryByMonth(clockMonth);
		List<MealHistory> mealHistotyList=mealHistoryDao.getMealHistoryByMonth(clockMonth);
		for(int h=0;h<mealHistotyList.size();h++){
			
			MealHistory mealHistory=mealHistotyList.get(h);
			
			String weixinId=mealHistory.getWeixinid();
			String weixinName=mealHistory.getUserName();
			Date mealDate=mealHistory.getMealDate();
			Format dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String mealDateStr=dateFormat.format(mealDate);
			
			//找匹配
			Map<String, Object> oneEmpMatch=empMatchMap.get(weixinId+"|"+weixinName);
			
			if(oneEmpMatch!=null){
				String clockId=oneEmpMatch.get("clock_id")+"";
				String clockName=oneEmpMatch.get("clock_name")+"";
				
				//找打卡记录
				ClockHistory oneClockHistory=null;
				for(int c=0;c<clockHistoryList.size();c++){
					ClockHistory oneClockHistoryTmp=clockHistoryList.get(c);
					String importClockId=oneClockHistoryTmp.getClockCode();
					String importClockName=oneClockHistoryTmp.getClockName();
					String clockDateStr=dateFormat.format(oneClockHistoryTmp.getClockDate());
					
					if(clockId.equals(importClockId)&&clockName.equals(importClockName)&&mealDateStr.equals(clockDateStr)){
						oneClockHistory=oneClockHistoryTmp;
						break;
					}
					
				}
				
				if(oneClockHistory!=null){
					MealAccount mealAccount=new MealAccount();
					
					mealAccount.setUserId(mealHistory.getUserId());
					mealAccount.setUserName(mealHistory.getUserName());
					mealAccount.setWeixinid(mealHistory.getWeixinid());
					mealAccount.setRestaurantId(mealHistory.getRestaurantId());
					mealAccount.setDepartmentId(mealHistory.getDepartmentId());
					mealAccount.setGender(mealHistory.getGender());
					mealAccount.setMealDate(mealHistory.getMealDate());
					mealAccount.setMealWeek(mealHistory.getMealWeek());
					mealAccount.setBreakfast(mealHistory.getBreakfast());
					mealAccount.setLunch(mealHistory.getLunch());
					mealAccount.setDinner(mealHistory.getDinner());
					
					int breakfastClock=0;
					int lunchClock=0;
					int dinnerClock=0;
					String clockDetail=oneClockHistory.getClockDetail();
					String[] detailArr=clockDetail.split("\\|");
					for(int d=0;d<detailArr.length;d++){
						String oneClockTime=detailArr[d];
						if(StringUtils.isNotEmpty(oneClockTime)){
							if(oneClockTime.compareTo(configContext.getBreakfastClockStart())>=0&&oneClockTime.compareTo(configContext.getBreakfastClockEnd())<=0){
								breakfastClock=1;
							}else if(oneClockTime.compareTo(configContext.getLunchClockStart())>=0&&oneClockTime.compareTo(configContext.getLunchClockEnd())<=0){
								lunchClock=1;
							}else if(oneClockTime.compareTo(configContext.getDinnerClockStart())>=0&&oneClockTime.compareTo(configContext.getDinnerClockEnd())<=0){
								dinnerClock=1;
							}
						}
						
					}
					
					mealAccount.setBreakfastClock(breakfastClock);
					mealAccount.setLunchClock(lunchClock);;
					mealAccount.setDinnerClock(dinnerClock);
					mealAccount.setClockDetail(oneClockHistory.getClockDetail());
					mealAccount.setClockCode(oneClockHistory.getClockCode());
					mealAccount.setClockName(oneClockHistory.getClockName());
					
					allMealAccountList.add(mealAccount);
				}else{
					errMealHistoryList.add(mealHistory);
				}
			}else{
				errMealHistoryList.add(mealHistory);
			}
			
		}
		
		
		/**结算数据存到数据库**/
		//只差保存了
		if(allMealAccountList.size()>0)
		mealAccountDao.save(allMealAccountList);
		
		//只返回有错误的数据
		return AjaxResult.success(errMealHistoryList);
	}


	@Override
	public int getAllMealAccountTotal(String month,String name) {
		return mealAccountDao.getAllMealAccountTotal( month, name);
	}


	@Override
	public List<MealAccount> getAllMealAccount(String month,String name,int page, int size) {
		return mealAccountDao.getAllMealAccount(month, name,page, size);
	}


	@Override
	public HSSFWorkbook exportMealAccount(String month) {
		HSSFWorkbook wb = new HSSFWorkbook();
		
		HSSFSheet sheet=wb.createSheet(month+"报餐乐捐汇总");
		
		/*******表头*********/
		HSSFRow row0=sheet.createRow(0);
		row0.createCell(0).setCellValue("姓名/日期");
		for(int i=1;i<=31;i++){
			String headerCellValue=i<10?"0"+i:i+"";
			row0.createCell(i).setCellValue(headerCellValue);
		}
		
		/**********内容***********/
		//写入的行数
		int writeRowNum=0;
		//记录人员所在的行数,键是name|weixinid
		Map<String,Integer> empRowIndexMap=new HashMap<String,Integer>();
		List<MealAccount> mealAccountList=mealAccountDao.getMealAccountByMonth(month);
		
		for(int i=0;i<mealAccountList.size();i++){
			MealAccount oneMealAccount=mealAccountList.get(i);
			
			HSSFRow row=null;
			
			int currentWriteRowIndex=-1;
			String rowIndexKey=oneMealAccount.getUserName()+"|"+oneMealAccount.getWeixinid();
			//已经存在行数的人就写入原来的行，不存在的新增行写入
			if(empRowIndexMap.containsKey(rowIndexKey)){
				currentWriteRowIndex=empRowIndexMap.get(rowIndexKey);
				row=sheet.getRow(currentWriteRowIndex);
			}else{
				writeRowNum++;
				currentWriteRowIndex=writeRowNum;
				empRowIndexMap.put(rowIndexKey, currentWriteRowIndex);
				row=sheet.createRow(currentWriteRowIndex);
				row.createCell(0).setCellValue(rowIndexKey);
			}
			
			//计算报餐和打卡不匹配的值
			Date mealDate=oneMealAccount.getMealDate();
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(mealDate);
			int cellIndex=calendar.get(Calendar.DAY_OF_MONTH);
			
			int donationMoneyOfDay=0;
			
			int donationMoney=Integer.parseInt(configContext.getDonationMoney());
			
			if(oneMealAccount.getBreakfast()!=oneMealAccount.getBreakfastClock()){
				donationMoneyOfDay+=donationMoney;
			}
			
			if(oneMealAccount.getLunch()!=oneMealAccount.getLunchClock()){
				donationMoneyOfDay+=donationMoney;
			}
			
			if(oneMealAccount.getDinner()!=oneMealAccount.getDinnerClock()){
				donationMoneyOfDay+=donationMoney;
			}
			if(donationMoneyOfDay>0)
			row.createCell(cellIndex).setCellValue(donationMoneyOfDay);
		}
		
		return wb;
	}

}
