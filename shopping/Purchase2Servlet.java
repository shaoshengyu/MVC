package com.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Purchase2Servlet
 */
@WebServlet("/Purchase2Servlet")
public class Purchase2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Purchase2Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//��Ӧ��Ϣͷ�����������
		response.setContentType("text/html;charset=UTF-8");
				
		HttpSession session=request.getSession();
		Map<String,Integer> pur =(Map<String,Integer>)session.getAttribute("shopping");
		
		String id=null;
		Cookie cookies[]=request.getCookies();
		for(int i=0;i<cookies.length;i++){
		if("sessionId".equals(cookies[i].getName())){
			  id=cookies[i].getValue();
			}
		}
		response.getWriter().print("<p>sessionIdΪ:"+id+"</p>");
		
		if(pur!=null&&pur.size()!=0) {
			response.getWriter().print("<p>���ﳵ�е���:</p>");
			//����������ʾ���ﳵ����Ϣ
			for(String e:pur.keySet()){
				response.getWriter().print("<p>"+e+","+pur.get(e)+"̨</p>");		
			}
		}else {
			response.getWriter().print("<p>δ�����κ���Ʒ</p>");	
		}
		response.getWriter().print("<p><a href='purchase.jsp'>��������</a></p>");	
	}

}
