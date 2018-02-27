/**
 * *********公用工具方法***********
 */

/**
 * 把后台数据直接转成生成图表数据,用来生成一个series的数据，比如说一条折线数据
 * xField 横坐标字段名称
 * yfield 纵坐标字段名称
 * data 后台传来的数据
 * categoryField 可选 类目字段，针对于一个坐标中多个图，这个方法是生成一个曲线柱图饼图的，如果只要现在只要悠扬瑜伽的店铺数据生成一条曲线，那就要设置这个值shopType
 * categoryValue 可选 类目值,如果只要现在只要悠扬瑜伽的店铺数据生成一条曲线，那就要设置这个值1
 */
function generateChartdata(xField,yfield,data,categoryField,categoryValue){
	var chartData=new Array();
	for(var i=0;data&&i<data.length;i++){
		var oneData=data[i];
		//如果没有设置类目那就直接按坐标轴取值，如果设置了，那这条数据必须要要属于这个类目才可以
		if(!categoryField||oneData[categoryField]==categoryValue){
			chartData.push({name:oneData[xField],value:oneData[yfield]});
		}
	}
	return chartData;
}