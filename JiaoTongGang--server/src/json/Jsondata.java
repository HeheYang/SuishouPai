package json;

import java.util.ArrayList;

import data.XiangData;
import data.Xiangdao;



public class Jsondata {

	//��ȡҪ��ӵ�JSON������
	public ArrayList<XiangData> getdata(){
		Xiangdao xiangdao=new Xiangdao();
		ArrayList<XiangData> list=xiangdao.getlocation();
		
		
		return list;
		
	}
	
	
//	//������
//	public static void main(String args[]) {
//		String msg="";
//		Jsondata json=new Jsondata();
//		ArrayList<XiangData>list=json.getdata();
//		msg=Json.creatJsonString("list", list);
//		System.out.println(msg);
		
	//}
}
