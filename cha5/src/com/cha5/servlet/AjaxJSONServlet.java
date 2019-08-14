package com.cha5.servlet;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cha5.pojo.GameBean;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class AjaxJSONServlet
 */
@WebServlet("/AjaxJSONServlet")
public class AjaxJSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxJSONServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//JavaBean������Ϣ
		GameBean g1=new GameBean("11111111", "ios","����","ʥ��ʹ");
		GameBean g2=new GameBean("22222222", "ios","СС½","Ԫ��ʹ");
		GameBean g3=new GameBean("12345678", "��׿","����","��սʿ");
		GameBean g4=new GameBean("aaaaaaaa", "��׿","���","������");
		// ʹ��Mapģ�����ݿ�
		Map<String, GameBean> datas = new HashMap<String, GameBean>();
		datas.put(g1.getZhanghao(), g1);
		datas.put(g2.getZhanghao(), g2);
		datas.put(g3.getZhanghao(), g3);
		datas.put(g4.getZhanghao(), g4);

		// ��ȡAjax��������
		String zhanghao = request.getParameter("zhanghao");
		// �����˺Ŵ�ģ�����ݿ����Ϣ
		GameBean data = datas.get(zhanghao);
		if (data == null) {
			data =new GameBean("","δ��½������Ϸ","δ��½������Ϸ","δ��½������Ϸ");
		}
		//����Jackson�����ObjectMapper����
		ObjectMapper mapper=new ObjectMapper();
		//��һ��Java����ת����JSON��������������Ӧ���
		mapper.writeValue(response.getWriter(),data);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
