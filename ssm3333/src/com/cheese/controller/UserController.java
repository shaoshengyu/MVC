package com.cheese.controller;

import java.awt.Color;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cheese.pojo.Department;
import com.cheese.pojo.FilePo;
import com.cheese.pojo.PageBean;
import com.cheese.pojo.User;
import com.cheese.service.UserService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	// ��ȡָ����Χ�������ɫ
			private Color getRandColor(int fc, int bc) {
				Random random = new Random();
				if (fc > 255)
					fc = 255;
				if (fc < 0)
					fc = 0;
				if (bc > 255)
					bc = 255;
				if (bc < 0)
					bc = 0;
				int r = fc + random.nextInt(bc - fc);
				int g = fc + random.nextInt(bc - fc);
				int b = fc + random.nextInt(bc - fc);
				return new Color(r, g, b);
			}
	
	@RequestMapping("/toregUser")
	public String toregUser(){
		return "regUser";
	}
	
	@RequestMapping("/tologin")
	public String tologin(){
		return "login";
	}
	
	@RequestMapping("/tofunction")
	public String tofunction(){
		return "function";
	}
	
	@RequestMapping("/tomain")
	public String tomain(){
		return "main";
	}
	
	@RequestMapping("/tomessage")
	public String tomessage(){
		return "message";
	}
	
	@RequestMapping("/todepartment")
	public String todepartment(){
		return "department";
	}
	
	@RequestMapping("/regUser")
	public String regUser(User user,HttpServletRequest request,MultipartFile userPicture,FilePo filePo ){	  
	 user.setUserState(1);
	//�ļ��ϴ�
     String fileName = filePo.getMyfile().getOriginalFilename();
     String newFileName = System.currentTimeMillis()+"-"+ fileName;
     File targetFile = new File("F:/sm3333",newFileName);
     if(!targetFile.exists()){
     	  targetFile.mkdirs();
       }
       
       try{
     	  filePo.getMyfile().transferTo(targetFile);
       }catch(Exception e){
     	  e.printStackTrace();
       }	
    user.setUserPicture(newFileName);
	userService.insert(user);		
	return "main";
   }

	@RequestMapping("/login")
	 public void login(User user,String userName,String userPassword,String validateCode,HttpServletResponse response,HttpSession session) throws IOException {
		response.setContentType("text/javascript;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		ObjectMapper mapper=new ObjectMapper();
		if(userService.selectO(userName)==null) {
			jsonObject.put("r", 1);
		}else {
			jsonObject.put("r", 2);
		}
		if(userService.selectUser(user)==null) {
			jsonObject.put("re", 1);
		}else {
			jsonObject.put("re", 2);
		}
		String sessionValidateCode = (String)session.getAttribute("SESSION_VALIDATECODE");		  
		if (!sessionValidateCode.equals(validateCode)) {	  
			jsonObject.put("res", 1);
		}else {
			jsonObject.put("res", 2);
		}
		jsonObject.put("userN", userService.selectUser(user).getUserName());
		mapper.writeValue(response.getWriter(),jsonObject);
	}
	
	@RequestMapping("/us")
	public void us(String userN,HttpSession session) {
		System.out.println(userN);
		session.setAttribute("userName", userN);
		System.out.println(session.getAttribute("userName"));	
	}
	
	@RequestMapping(value="/ajaxOne")
	protected void ajaxOne(HttpServletResponse response,String userName,User user) throws JsonGenerationException, JsonMappingException, IOException {
		response.setContentType("text/javascript;charset=utf-8"); 
		user=userService.selectO(userName);
		//����Jackson�����ObjectMapper����
		ObjectMapper mapper=new ObjectMapper();
		//��һ��Java����ת����JSON��������������Ӧ���
		mapper.writeValue(response.getWriter(),user);		 
	}
		
	@RequestMapping(value="/validateCode")
	protected void validateCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     
	       // ������Ӧͷ Content-type����
			response.setContentType("image/jpeg");
			// ��ȡ�������������������
			ServletOutputStream out = response.getOutputStream();
			// ��������ͼ��
			int width = 60;
			int height = 20;
			BufferedImage imgbuf = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = imgbuf.createGraphics();
			// �趨����ɫ
			g.setColor(getRandColor(200, 250));
			// �趨ͼ����״�����
			g.fillRect(0, 0, width, height); 
			// �������100�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
			Random r = new Random();
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 100; i++) {
				int x = r.nextInt(width);
				int y = r.nextInt(height);
				int xl = r.nextInt(12);
				int yl = r.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			// �������100�����ŵ㣬ʹͼ���е���֤�벻�ױ�������������̽�⵽
			g.setColor(getRandColor(120, 240));
			for (int i = 0; i < 100; i++) {
				int x = r.nextInt(width);
				int y = r.nextInt(height);
				g.drawOval(x, y, 0, 0);
			}
			// �������0-9֮���4λ������֤��
			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			String code = "";
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(r.nextInt(10));
				code += rand;
				g.setColor(new Color(20 + r.nextInt(110), 20 + r.nextInt(110),
						20 + r.nextInt(110)));
				g.drawString(rand, 13 * i + 6, 16);
			}
			// ����֤�뱣�浽session��
			request.getSession().setAttribute("SESSION_VALIDATECODE", code);
			// ���ͼ��
			ImageIO.write(imgbuf, "JPEG", out);
			out.close();
			
		}
	
	
	@RequestMapping(value="/list")
	public void main(@RequestParam(value="currentPage",defaultValue="1",required=false)int currentPage,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		response.setContentType("text/javascript;charset=utf-8");
		PageBean<User> page=userService.findByPage(currentPage);//���Է�ҳ����		
		//����Jackson�����ObjectMapper����
		ObjectMapper mapper=new ObjectMapper();
		//��һ��Java����ת����JSON��������������Ӧ���
		mapper.writeValue(response.getWriter(),page);		
	}
	
	@RequestMapping(value="/department")
	public String department(int departmentId,HttpServletRequest request){
		List<Department> department = userService.selectDept(departmentId);
		request.setAttribute("department", department);
		return "dumessage";	
	}
}
