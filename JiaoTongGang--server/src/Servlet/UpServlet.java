package Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.Json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.apache.commons.lang.SystemUtils;
import org.apache.jasper.tagplugins.jstl.core.Out;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import data.Base64Coder;
import data.UpMsg;
import data.Xiangdao;

/**
 * Servlet implementation class UpServlet
 */
@WebServlet("/UpServlet")

public class UpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private  String file;
       private String message;
       Xiangdao insert=new Xiangdao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html;charset=utf-8");
  		request.setCharacterEncoding("utf-8");
  		request.setCharacterEncoding("utf-8");
  		PrintWriter out=response.getWriter();
  		out.println("我正在接收图片。。。。");
  		
  		
  		
  		 
		file=request.getParameter("file");
		message=request.getParameter("message");
		
		 JSONObject upmsg = JSONObject.fromObject(message);//获取客户端传输的JSON数据并解析

		 System.out.println(file);
		 System.out.println(message);
         if(file!=null){
        	 //将照片获取并保存
                 byte[] b;			
					b =Base64Coder.decodeLines(file);	
                 String filepath=request.getSession().getServletContext().getRealPath("/files");
                 File file=new File(filepath);
                 if(!file.exists())
                         file.mkdirs();
                 String time=upmsg.getString("time");//获取JSON中的时间
                 String regEx="[^0-9]";     //由于文件命名的限制，利用正则表达式取出时间中非数字符号
                 Pattern p = Pattern.compile(regEx);   //将正则表达式编译并赋予Patern类  
                 Matcher m = p.matcher(time);     //生成一个给定的macher对象
                 time=m.replaceAll("").trim();		//将字符串中匹配的符号替换成"".相当于删除非数字符号
                 System.out.println(time);//去除完成
                 
                 String imagpath=file.getPath()+"/upload"+time+".png";
                 FileOutputStream fos=new FileOutputStream(imagpath);
                 System.out.println(file.getPath());
                 
                 //向数据库中添加信息
                 if(message!=null){
                	// JSONObject upmsg = JSONObject.fromObject(message);为了方便获取时间作为图片名字 我调到了上面
     				UpMsg up=new UpMsg();
     				up.setLng(upmsg.getString("lng"));
     				up.setLat(upmsg.getString("lat"));
     				up.setLocation(upmsg.getString("desc"));
     				up.setTime(upmsg.getString("time"));
     				up.setText(upmsg.getString("text"));
     				up.setPhotopathString(imagpath);//将图片的路径提取出来
     				insert.upload(up);
                 }
                 fos.write(b);
                 fos.flush();
                 fos.close();

         		out.flush();
         		out.close(); 
				}
 }
		
	
		
	
	
	}


