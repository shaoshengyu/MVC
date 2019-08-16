package com.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		//��ȡ�Ự����
		HttpSession session=request.getSession();
		
		//�ӻỰ��ȡshopping(���ﳵ)���Զ���
		Map<String,Integer> pur =(Map<String,Integer>)session.getAttribute("shopping");
		//����shopping���Զ�����ʵ����һ��
		
		String sessionId=request.getSession().getId();
		Cookie cookie=new Cookie("sessionId",request.getSession().getId());
		cookie.setMaxAge(365*24*60*60);
		response.addCookie(cookie);
		
		if(pur==null) {
			pur = new HashMap<String,Integer>();
		}
		//��ȡҪ�������
		String[] equipments=request.getParameterValues("equipment");
		if(equipments!=null&&equipments.length!=0) {
			for(String e:equipments) {
				//�жϵ����Ƿ��Ѿ���shopping��
				if(pur.get(e)!=null) {
					//�õ����ѷ��빺�ﳵ����
					int count=pur.get(e);
					pur.put(e, count+1);
				}else {
					//֮ǰδ����õ���ʱ
					pur.put(e,1);
				}
			}
		}
		//�����º�shopping�洢�ڻỰ��
		session.setAttribute("shopping", pur);
		response.sendRedirect("Purchase2Servlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
