package com.zee.ordering.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 微信公众号工具类
 * 
 * @author houyong
 *
 */
public class WeixinUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	// 企业ID
	public static final String CORPID = "";
	// 管理组的凭证密钥
	private static final String SECRET = "";
	// 企业助手APPID
	public static final String QIYE_APPID = "0";
	// 点餐系统应用APPID
	public static final String DIANCAN_APPID = "1";
	// 顶级部门ID
	public static final String ZEE_PARTID = "1";
	// 餐饮部门ID
	public static final String ZEE_CANTEEN_PARTID = "2";
	// 接口调用凭证，这个是我调微信的token
	private static String ACCESSTOKEN = "";
	// 公众平台上，开发者设置的回调token，这个是微信调我的程序的token
	private static final String CALLBACK_TOKEN = "";
	// 公众平台上，开发者设置的回调EncodingAESKey，微信调我的程序用的
	private static final String ENCODING_AESKEY = "";

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 发送请求
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            参数，微信接受json参数
	 * @param repeatNum
	 *            错误请求后尝试的次数，至少1次
	 * @return 请求结果
	 */
	private static String sendPost(String url, String param, int repeatNum) {

		String resultString = "";

		// 自动加上access_token
		String sendUrl = url.indexOf("?") == -1 ? (url + "?access_token=" + ACCESSTOKEN)
				: (url + "&access_token=" + ACCESSTOKEN);

		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(sendUrl);
			logger.info(sendUrl);
			URLConnection conn = realUrl.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			logger.error("发送http请求出错！", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}

		resultString = result.toString();

		// 如果access_token出错，则重新获取access_token然后请求
		if (resultString.indexOf("\"errcode\":0") == -1) {
			repeatNum = repeatNum - 1;
			if (repeatNum >= 0) {
				getAccessToken(repeatNum);
				resultString = sendPost(url, param, repeatNum);
			}
		}

		return resultString;
	}

	/**
	 * 发送请求并默认请求失败尝试的次数为1次
	 * 
	 * @param url
	 *            请求的地址
	 * @param param
	 *            参数
	 * @return 请求结果
	 */
	public static String sendPost(String url, String param) {
		return sendPost(url, param, 1);
	}

	/**
	 * 获取请求接口凭证密钥
	 * 
	 * @param repeatNum
	 *            失败重复的次数
	 * @return 请求接口凭证密钥
	 */
	private static String getAccessToken(int repeatNum) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORPID + "&corpsecret=" + SECRET;
		String json = sendPost(url, "", repeatNum);

		try {
			Map<String, Object> map = mapper.readValue(json, Map.class);
			ACCESSTOKEN = map.get("access_token").toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return ACCESSTOKEN;
	}

	/**
	 * 发送文本信息
	 * 
	 * @param topartyId
	 *            发送给哪个部门
	 * @param appId
	 *            用哪个app发送
	 * @param content
	 *            发送的文本信息
	 */
	public static void sendTextMessage(String touser, String topartyId, String appId, String content) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("msgtype", "text");
		message.put("toparty", topartyId);
		message.put("touser", touser);
		message.put("agentid", appId);

		Map<String, Object> text = new HashMap<String, Object>();
		text.put("content", content);
		message.put("text", text);

		try {
			sendPost(url, mapper.writeValueAsString(message));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 验证请求
	 * 
	 * @param request
	 * @return
	 */
	public static String verifyURL(HttpServletRequest request) {
		String verifyContent = "";
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(CALLBACK_TOKEN, ENCODING_AESKEY, CORPID);
			String sVerifyMsgSig = request.getParameter("msg_signature");
			String sVerifyTimeStamp = request.getParameter("timestamp");
			String sVerifyNonce = request.getParameter("nonce");
			String sVerifyEchoStr = request.getParameter("echostr");
			
			if (StringUtils.isNotBlank(sVerifyMsgSig) && StringUtils.isNotBlank(sVerifyTimeStamp)
					&& StringUtils.isNotBlank(sVerifyNonce) && StringUtils.isNotBlank(sVerifyEchoStr)) {
				verifyContent = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
			}
		} catch (AesException e) {
			logger.error(e.getMessage(), e);
		}

		return verifyContent;
	}

	/**
	 * 解析微信传来的xml
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> parseXml(HttpServletRequest request) {
		Map<String, String> returnMap = new HashMap<String, String>();

		try {
			// 读取xml
			InputStream inputStream = request.getInputStream();
			StringBuilder xmlContent = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				xmlContent.append(System.getProperty("line.separator"));
				xmlContent.append(line);
			}
			bufferedReader.close();
			inputStream.close();

			// 解析xml
			if (!StringUtils.isBlank(xmlContent.toString())) {
				WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(CALLBACK_TOKEN, ENCODING_AESKEY, CORPID);

				String sReqMsgSig = request.getParameter("msg_signature");
				String sReqTimeStamp = request.getParameter("timestamp");
				String sReqNonce = request.getParameter("nonce");
				String message = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, xmlContent.toString());

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				StringReader sr = new StringReader(message);
				InputSource is = new InputSource(sr);
				Document document = db.parse(is);

				Element root = document.getDocumentElement();

				NodeList nodeList = root.getChildNodes();

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node oneNode = nodeList.item(i);
					returnMap.put(oneNode.getNodeName(), oneNode.getTextContent());
				}

				sr.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AesException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		}

		return returnMap;
	}

	/**
	 * 根据userId获取单个用户详情
	 * 
	 * @param userId
	 * @return {
		   "errcode": 0,
		   "errmsg": "ok",
		   "userid": "zhangsan",
		   "name": "李四",
		   "department": [1, 2],
		   "position": "后台工程师",
		   "mobile": "15913215421",
		   "gender": "1",
		   "email": "zhangsan@gzdev.com",
		   "weixinid": "lisifordev",  
		   "avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
		   "status": 1,
		   "extattr": {"attrs":[{"name":"爱好","value":"旅游"},{"name":"卡号","value":"1234567234"}]}
		}
	 */
	public static Map<String, Object> getUserInfo(String userId) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?userid=" + userId;
		String json = sendPost(url, "");
		
		
		System.out.println(json);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			returnMap = mapper.readValue(json, returnMap.getClass());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return returnMap;

	}
	
	/**
	 * 根据微信给的code获取单个用户详情
	 * 
	 * @param code 微信端提供
	 * @return {
		   "errcode": 0,
		   "errmsg": "ok",
		   "userid": "zhangsan",
		   "name": "李四",
		   "department": [1, 2],
		   "position": "后台工程师",
		   "mobile": "15913215421",
		   "gender": "1",
		   "email": "zhangsan@gzdev.com",
		   "weixinid": "lisifordev",  
		   "avatar": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
		   "status": 1,
		   "extattr": {"attrs":[{"name":"爱好","value":"旅游"},{"name":"卡号","value":"1234567234"}]}
		}
	 */
	public static Map<String, Object> getUserInfoByCode(String code) {
		String url="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?code="+code;
		String json = sendPost(url, "");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			returnMap = mapper.readValue(json, returnMap.getClass());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return returnMap;

	}

	/**
	 * 获取所有部门列表
	 * @return   "department": [
	       {
	           "id": 2,
	           "name": "广州研发中心",
	           "parentid": 1,
	           "order": 10
	       },
	       {
	           "id": 3
	           "name": "邮箱产品部",
	           "parentid": 2,
	           "order": 40
	       }
	   ]
	 */
	public static List<Map<String, Object>> getDepartmentList() {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list";
		String json = sendPost(url, "");

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		try {
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap = mapper.readValue(json, messageMap.getClass());
			
			if ("0".equals(messageMap.get("errcode")+"")) {
				returnList = (List<Map<String, Object>>) messageMap.get("department");
				logger.error("获取到部门列表>>"+returnList.size()+returnList.toString());
			} else {
				logger.error("获取部门列表失败：" + messageMap.get("errcode") + messageMap.get("errmsg"));
			}

		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return returnList;

	}
	
	
	
	/**
	 * 修改用户
	 * @param userInfo
	 * {
		   "userid": "zhangsan",
		   "name": "李四",
		   "department": [1],
		   "position": "后台工程师",
		   "mobile": "15913215421",
		   "gender": "1",
		   "email": "zhangsan@gzdev.com",
		   "weixinid": "lisifordev",
		   "enable": 1,
		   "avatar_mediaid": "2-G6nrLmr5EC3MNb_-zL1dDdzkd0p7cNliYu9V5w7o8K0",
		   "extattr": {"attrs":[{"name":"爱好","value":"旅游"},{"name":"卡号","value":"1234567234"}]}
		}
	 * @return
	 */
	public static boolean modifyUser(Map<String,Object> userInfo) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/update";
		
		try {
			String json=sendPost(url, mapper.writeValueAsString(userInfo));
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap = mapper.readValue(json, messageMap.getClass());

			if ("0".equals(messageMap.get("errcode")+"")) {
				return true;
			}else if("60111".equals(messageMap.get("errcode")+"")){
				logger.info("微信端用户已经取消关注不存在！"+userInfo.toString());
				return true;
			} else {
				logger.error("修改成员失败：" + messageMap.get("errcode") + messageMap.get("errmsg"));
				return false;
			}
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}
	
	
	
	
	/**
	 * 获取某个部门及子部门递归所有的人员
	 * @param departmentId 部门id
	 * @return 部门所有人员"userlist": [
           {
                  "userid": "zhangsan",
                  "name": "李四",
                  "department": [1, 2],
                  "position": "后台工程师",
                  "mobile": "15913215421",
                  "gender": "1",
                  "email": "zhangsan@gzdev.com",
                  "weixinid": "lisifordev",  
                  "avatar":           "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
                  "status": 1,
                  "extattr": {"attrs":[{"name":"爱好","value":"旅游"},{"name":"卡号","value":"1234567234"}]}
           }
     ]
	 */
	public static List<Map<String, Object>> getUserListByDepartment(String departmentId){
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?department_id="+departmentId+"&fetch_child=1";
		String json = sendPost(url, "");

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		try {
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap = mapper.readValue(json, messageMap.getClass());

			if ("0".equals(messageMap.get("errcode")+"")) {
				returnList = (List<Map<String, Object>>) messageMap.get("userlist");
				logger.error("获取到成员列表>>"+returnList.size()+returnList.toString());
			} else {
				logger.error("获取成员列表失败：" + messageMap.get("errcode") + messageMap.get("errmsg"));
			}

		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return returnList;
	}
	
	
	public static List<Map<String, Object>> getAllUserList(){
		return getUserListByDepartment(ZEE_PARTID);
	}

	

}
