package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class Xiangdao {
	String url = "jdbc:mysql://127.0.0.1:3306/e";//port：3306 database:masterdatabase
	//String url = "jdbc:mysql://127.0.0.1:3306/store";
	String username = "root";//user
	String password = "root";//password
			static int count=0;
			XiangData xiangqing;
			Connection connection=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
		ArrayList<XiangData> list=new ArrayList<XiangData>();

				public ArrayList<XiangData> getlocation(){
					try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}//加载驱动，连接数据库
					try {
						connection = DriverManager.getConnection(url,username, password );
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} 
					
					try {
						String sql="select id,lng,lat,deliver_car_driver,store_name,linkman_certification ,owner_certification,owner_name,personal_car,store_car_driver,store_simpleName,telephone,use_car_driver,linkman_name from store";
						ps = connection.prepareStatement(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						rs = ps.executeQuery();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				try{
						
					
					while(rs.next())
						{
						xiangqing=new XiangData();
					xiangqing.setId(rs.getInt("id"));
					xiangqing.setLng(rs.getDouble("lng"));
					xiangqing.setLat(rs.getDouble("lat"));
					xiangqing.setDeliver_car_driver(rs.getString("deliver_car_driver"));
					xiangqing.setStore_name(rs.getString("store_name"));
					xiangqing.setLinkman_certification(rs.getString("linkman_certification"));
					xiangqing.setOwner_certification(rs.getString("owner_certification"));
					xiangqing.setOwner_name(rs.getString("owner_name"));
					xiangqing.setPersonal_car(rs.getString("personal_car"));
					xiangqing.setStore_car_driver(rs.getString("store_car_driver"));
					xiangqing.setStore_Simplename(rs.getString("store_simpleName"));
					xiangqing.setTelephone(rs.getString("telephone"));
					xiangqing.setUse_car_driver(rs.getString("use_car_driver"));
					xiangqing.setLinkman_name(rs.getString("linkman_name"));
					count++;
					list.add(xiangqing);
						
						}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				return list;
			}
				
				
				public void upload(UpMsg upmsg){
					try {
						Class.forName("com.mysql.jdbc.Driver" );
					} catch (ClassNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}//加载驱动，连接数据库
					try {
						connection = DriverManager.getConnection(url,username, password );
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} 
					
						try{
							
							String sql="insert into message(lng,lat,time,location,text,photopath) values(?,?,?,?,?,?)";
							ps=connection.prepareStatement(sql);
							ps.setString(1, upmsg.getLng());
							ps.setString(2, upmsg.getLat());
							ps.setString(3, upmsg.getTime());
							ps.setString(4, upmsg.getLocation());
							ps.setString(5, upmsg.getText());
							ps.setString(6, upmsg.getPhotopathString());//将路径保存到数据库中，比保存图片写得快
							ps.execute();
						}catch(Exception e){
							e.printStackTrace();
						}finally{
							
							try {
								ps.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								connection.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
					
					
				}
				
				
				

//测试

//				public static void main(String args[]) {
//
//					Xiangdao xiangdao=new Xiangdao();
//					ArrayList<XiangData> list=xiangdao.getlocation();
//					for(int i=0;i<list.size();i++)
//					{
//						System.out.println(list.get(i).getLat()+"\t"+list.get(i).getLng());
//						
//					}
//					System.out.println(count);
//						
//				}


}
