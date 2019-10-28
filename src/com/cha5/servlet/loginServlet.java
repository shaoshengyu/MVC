package com.cha5.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cha5.pojo.User;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//request.setCharacterEncoding("UTF-8");
		//String name=new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		//����ȡ�Ĵ����ʽUTF-8תΪISO-8859-1
		//String name=new String(request.getParameter("name").getBytes("UTF-8"),"ISO-8859-1");
		//��ISO-88591���б��룬֮����URLDecoder����
		//name = URLEncoder.encode(name,"ISO-8859-1");
		ServletContext context=super.getServletContext();
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		StringBuffer errorMsg=new StringBuffer();
		//��Ϣ��֤
		if("".equals(name)) {
			errorMsg.append("�û�������Ϊ��!<br>");
		}
		if("".equals(password)) {
			errorMsg.append("���벻��Ϊ��!<br>");
		}
		else if(password.length()<6||password.length()>12) {
			errorMsg.append("���볤�ȱ���Ϊ6-12λ!<br>");
		}
		
		request.setAttribute("errorMsg",errorMsg.toString());
		
		  Integer count =(Integer)context.getAttribute("count");
		    if(count==null) {
		    	count=1;
		    }else {
		    count+=1;
		    }
		    context.setAttribute("count", count);
		
		if(errorMsg.toString().equals("")) {
			//response.sendRedirect("main.jsp?name="+name);
			//��ȡ�Ự����
			
			HttpSession session=request.getSession();
			//session�����û���Ϣ
			StringBuffer url=request.getRequestURL();			
			User user=new User();
			user.setName(name);
			user.setUrl(url);
			session.setAttribute("xinxi",user);
			session.setAttribute("name",name);
			session.removeAttribute("xinxi");
			response.sendRedirect("main.jsp");	
		}else {
			RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
	}
}
