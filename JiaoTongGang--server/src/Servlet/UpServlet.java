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
  		out.println("�����ڽ���ͼƬ��������");
  		
  		
  		
  		 
		file=request.getParameter("file");
		message=request.getParameter("message");
		
		 JSONObject upmsg = JSONObject.fromObject(message);//��ȡ�ͻ��˴����JSON���ݲ�����

		 System.out.println(file);
		 System.out.println(message);
         if(file!=null){
        	 //����Ƭ��ȡ������
                 byte[] b;			
					b =Base64Coder.decodeLines(file);	
                 String filepath=request.getSession().getServletContext().getRealPath("/files");
                 File file=new File(filepath);
                 if(!file.exists())
                         file.mkdirs();
                 String time=upmsg.getString("time");//��ȡJSON�е�ʱ��
                 String regEx="[^0-9]";     //�����ļ����������ƣ�����������ʽȡ��ʱ���з����ַ���
                 Pattern p = Pattern.compile(regEx);   //��������ʽ���벢����Patern��  
                 Matcher m = p.matcher(time);     //����һ��������macher����
                 time=m.replaceAll("").trim();		//���ַ�����ƥ��ķ����滻��"".�൱��ɾ�������ַ���
                 System.out.println(time);//ȥ�����
                 
                 String imagpath=file.getPath()+"/upload"+time+".png";
                 FileOutputStream fos=new FileOutputStream(imagpath);
                 System.out.println(file.getPath());
                 
                 //�����ݿ��������Ϣ
                 if(message!=null){
                	// JSONObject upmsg = JSONObject.fromObject(message);Ϊ�˷����ȡʱ����ΪͼƬ���� �ҵ���������
     				UpMsg up=new UpMsg();
     				up.setLng(upmsg.getString("lng"));
     				up.setLat(upmsg.getString("lat"));
     				up.setLocation(upmsg.getString("desc"));
     				up.setTime(upmsg.getString("time"));
     				up.setText(upmsg.getString("text"));
     				up.setPhotopathString(imagpath);//��ͼƬ��·����ȡ����
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


