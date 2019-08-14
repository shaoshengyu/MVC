package com.cha5.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class CheckFilter
 */
public class LoginFilter implements Filter {

	private FilterConfig config;
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		this.config=null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		//��ȡ��ʼ������
		String check1=config.getInitParameter("login");
		String check2=config.getInitParameter("loginServlet");
		//��ȡ�Ự����
		HttpSession session =((HttpServletRequest) request).getSession();
		//��ȡ��ַ·��
		String path=((HttpServletRequest) request).getServletPath();
		
		if(session.getAttribute("name")!=null||path.endsWith(check1)||path.endsWith(check2)) {
			//��������
			chain.doFilter(request, response);
		}else {
			request.setAttribute("tip", "���ȵ�¼!");
			//ת���ص�¼ҳ��
			request.getRequestDispatcher(check1).forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.config =fConfig;
	}

}
