package com.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.FilePo;
import com.pojo.Messageinfo;
import com.pojo.Sellhouseinfo;
import com.pojo.Transactioninfo;
import com.pojo.Userinfo;
import com.service.MessageinfoService;
import com.service.SellhouseinfoService;
import com.service.TransactioninfoService;
import com.service.UserinfoService;
@Controller
@RequestMapping("/origin")
public class OriginController {
	@Autowired
	SellhouseinfoService sellhouseinfoService;
	
	@Autowired
	UserinfoService userinfoService;
	
	@Autowired
	MessageinfoService messageinfoService;
	
	@Autowired
	TransactioninfoService transservice;
	
	@RequestMapping("/showforgetpwd")
	public String showupdatepwd() {
		return "originforgetpwd";
	}
	//�û���½����֤
	@RequestMapping(value="/validateUser")
	public String login(Userinfo userinfo,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		List<Userinfo>  user= userinfoService.getUser(userinfo);//
		if(user.size()==0) {
			session.setAttribute("loginmsg", "�û������û����������˶ԣ���");
			//��תҳ�������
			Sellhouseinfo sellhouseinfo=new Sellhouseinfo();
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(sellhouseinfo);
			request.setAttribute("sellinfo", sellinfo);
			return "origin";
			
		}else if(user.get(0).getUserstatus().equals("����")){
			session.setAttribute("loginmsg", "���˻��Ѿ������ã�����");
			//��תҳ�������
			Sellhouseinfo sellhouseinfo=new Sellhouseinfo();
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(sellhouseinfo);
			request.setAttribute("sellinfo", sellinfo);
			session.setAttribute("username", user.get(0).getUsername());
			
			return "origin";
		}else if(user.get(0).getUserrole().equals("����Ա")) {
			session.setAttribute("loginmsg", "");
		    session.setAttribute("userrole", user.get(0).getUserrole());
		    session.setAttribute("username", user.get(0).getUsername());
		    session.setAttribute("userid", user.get(0).getUserid());
		    //�ṩ��תҳ������
		    Transactioninfo ti=new Transactioninfo();
		    List<Transactioninfo> trans = transservice.getTransaction(ti);
		    if(trans.size()==0){
				session.setAttribute("tranmsg", "�Բ���δ�ҵ����������Ľ��׼�¼");
				return "adminchakanjiaoyi";
			}else{
				session.setAttribute("tranmsg", "");
				request.setAttribute("transList", trans);
				return "adminchakanjiaoyi";
				}
		}else if(user.get(0).getUserrole().equals("��ͨ�û�")) {
			 session.setAttribute("loginmsg", "");
			 session.setAttribute("userrole", user.get(0).getUserrole());
			 session.setAttribute("username", user.get(0).getUsername());
			 session.setAttribute("userid", user.get(0).getUserid());
			 //Ϊuser�����ṩ��Դ��Ϣ����
			 Sellhouseinfo shinfo=new Sellhouseinfo();
			 List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
			 request.setAttribute("sellinfo", sellinfo);
			return "user";
		}else if(user.get(0).getUserrole().equals("�н�")) {
			session.setAttribute("loginmsg", "");
			session.setAttribute("userrole", user.get(0).getUserrole());
			session.setAttribute("username", user.get(0).getUsername());
			session.setAttribute("userid", user.get(0).getUserid());
			//����н��ʼҳ����Ϣ
			Messageinfo me=new Messageinfo();
			int mediaid=(int) request.getSession().getAttribute("userid");
			me.setMediaid(mediaid);
			List<Messageinfo> m = messageinfoService.getMessage(me);
			if(m.size()==0){
				session.setAttribute("liuyanmsg", "��������Ϣ");
				return "mediamessage";
			}else{
				session.setAttribute("liuyanmsg", "");
				request.setAttribute("messageList",m);
				return "mediamessage";
			}
		}else {
			return "UserLogin";
		}
		
	}
	//����û��˺�
	@RequestMapping("/addUser")
	public String addUser(@ModelAttribute("userinfo") Userinfo userinfo,HttpSession session,FilePo filePo, HttpServletRequest request) {
		Userinfo us=new Userinfo();
		us.setUsername(userinfo.getUsername());
		List<Userinfo> user=userinfoService.getUser(us);
		if(user.size()>0){
			session.setAttribute("addusermsg", "�û����Ѵ��ڣ���");
			//����Ϊoriginҳ����ʾ��Դ�ṩ��Ϣ
	        Sellhouseinfo shinfo=new Sellhouseinfo();
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
			request.setAttribute("sellinfo", sellinfo);
			return "origin";
		}else{
			String fileName = filePo.getMyfile().getOriginalFilename();
		    File targetFile = new File("D:/image",fileName);
		    if(!targetFile.exists()){
		        targetFile.mkdirs();
		     }
		     try{
		        filePo.getMyfile().transferTo(targetFile);
		     }catch(Exception e){
		        	 e.printStackTrace();
		     }
	    userinfo.setUserimage(fileName);
		userinfo.setUserstatus("����");
		userinfo.setUsercredit(0);
		userinfoService.addUser(userinfo);
        request.setAttribute("userinfo", userinfo);
        session.setAttribute("addusermsg", "");
        //����Ϊoriginҳ����ʾ��Դ�ṩ��Ϣ
        Sellhouseinfo shinfo=new Sellhouseinfo();
		List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
		request.setAttribute("sellinfo", sellinfo);
        return "origin";
	  }
	}
	//��������--�һ�����
	@RequestMapping("/forget")
	public String forgetUser(Userinfo userinfo,HttpServletRequest request,HttpSession session){
		Userinfo us=new Userinfo();
		us.setUseremail(userinfo.getUseremail());
		us.setUsername(userinfo.getUsername());
		List<Userinfo> user = userinfoService.getUser(us);
		if(user.size()==0) {
			session.setAttribute("forgetpasswordmsg", "��֤��Ϣ���󣡣�");
			return "originforgetpwd";
		}else{
			userinfoService.updateUser(userinfo);
			session.setAttribute("forgetpasswordmsg", "");
			request.setAttribute("userinfo", userinfo);
			 //����Ϊoriginҳ����ʾ��Դ�ṩ��Ϣ
	        Sellhouseinfo shinfo=new Sellhouseinfo();
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
			request.setAttribute("sellinfo", sellinfo);
			return "origin";		
	  }
	}
	
	//��ʾ���з�Դ��Ϣ
		@RequestMapping("/showhouseinfo")
		public String showhouseinfo(Sellhouseinfo sellhouseinfo,HttpServletRequest request,HttpSession session){
				session.setAttribute("msg", "");
				List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(sellhouseinfo);
				request.setAttribute("sellinfo", sellinfo);
				return "origin";
		}
	
	//�����ȡ�û���Ϣ	
		@RequestMapping(value="/showuserinfo")
		public String getuserid(Userinfo userinfo,HttpServletRequest request){
				userinfo.setUserid(userinfo.getUserid());
				userinfo.setUserrole("��ͨ�û�");
				List<Userinfo> use = userinfoService.getUser(userinfo);
				request.setAttribute("us", use.get(0));
				return "userinfo";	
	}
	//�����ȡ�н���Ϣ	
	@RequestMapping(value="/mediainfo")
	public String mediainfo(Userinfo userinfo,HttpSession session,HttpServletRequest request){
				userinfo.setUserid(userinfo.getUserid());
				userinfo.setUserrole("�н�");
				List<Userinfo> uu=userinfoService.getUser(userinfo);			
				request.setAttribute("media",uu.get(0));
				return "mediainfo";
		}
	
}
