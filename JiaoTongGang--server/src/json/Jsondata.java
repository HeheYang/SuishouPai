package json;

import java.util.ArrayList;

import data.XiangData;
import data.Xiangdao;



public class Jsondata {

	//获取要添加到JSON的数据
	public ArrayList<XiangData> getdata(){
		Xiangdao xiangdao=new Xiangdao();
		ArrayList<XiangData> list=xiangdao.getlocation();
		
		
		return list;
		
	}
	
	
//	//测试用
//	public static void main(String args[]) {
//		String msg="";
//		Jsondata json=new Jsondata();
//		ArrayList<XiangData>list=json.getdata();
//		msg=Json.creatJsonString("list", list);
//		System.out.println(msg);
		
	//}
}
