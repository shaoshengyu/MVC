package com.cha5.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxServlet
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ʹ��Mapģ�����ݿ�
		Map<String,String> datas=new HashMap<String,String>();
		datas.put("11111111","ios,����,ʥ��ʹ");
		datas.put("22222222","ios,СС½,Ԫ��ʹ");
		datas.put("12345678","��׿,����,��սʿ");
		datas.put("aaaaaaaa","��׿,���,������");
		
		//��ȡAjax��������
		String zhanghao=request.getParameter("zhanghao");
		//�����˺Ŵ�ģ�����ݿ����Ϣ
		String data=datas.get(zhanghao);
		if(data==null) {
			data="δ��½������Ϸ,δ��½������Ϸ,δ��½������Ϸ";					
		}
		//��������������Ӧ���
		response.getWriter().print(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
