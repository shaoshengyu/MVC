package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInteceptor extends HandlerInterceptorAdapter{
	//��ͼ֮ǰ����
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		HttpSession session =request.getSession();
		String userRole=(String) request.getSession().getAttribute("userrole");//��session��ȡ������ֵ
		if(userRole.equals("����Ա"))
		{
			session.setAttribute("interMsg", "��Ȩ��Ϊ����ԱȨ��");
			request.getRequestDispatcher("/WEB-INF/views/UserLogin.jsp").forward(request, response);
			return false;
		}
		session.setAttribute("interMsg", "");
		return true;
	}	
}
