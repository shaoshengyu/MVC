package com.controller;


import java.io.File;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pojo.Blackuser;
import com.pojo.FilePo;
import com.pojo.Messageinfo;
import com.pojo.Reportinfo;
import com.pojo.Sellhouseinfo;
import com.pojo.Transactioninfo;
import com.pojo.Userinfo;
import com.service.BlackuserService;
import com.service.MessageinfoService;
import com.service.ReportinfoService;
import com.service.SellhouseinfoService;
import com.service.TransactioninfoService;
import com.service.UserinfoService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	SellhouseinfoService sellhouseinfoService;
	
	@Autowired
	UserinfoService userinfoService;
	
	@Autowired
	TransactioninfoService transactioninfoService;
	
	@Autowired
	BlackuserService blackuserService;

	@Autowired
	TransactioninfoService transservice;
	
	@Autowired
	ReportinfoService reportinfoService;
	
	@Autowired
	MessageinfoService messageinfoService;
	
	@RequestMapping("/useraddhouse")
	public String addhouse() {
		return "userAddhouse";
	}
	
	
	//�û��ϴ���Դ
	@RequestMapping(value="/addHouse")
	public String addHouse(Sellhouseinfo sellhouseinfo,FilePo filePo,HttpSession session,HttpServletRequest request){
		 Userinfo ui=new Userinfo();
		 ui.setUserid(sellhouseinfo.getMediaid());
		 ui.setUserrole("�н�");
		 ui.setUserstatus("����");
		 List<Userinfo> u=userinfoService.getUser(ui);
		if(u.size()==0){
		    session.setAttribute("addhousemsg", "���н�Ų����ڻ��ѱ��������������");
			return "userAddhouse";
		}else{	
			Date date=new Date(System.currentTimeMillis());
			int ownerid=(int) request.getSession().getAttribute("userid");
			sellhouseinfo.setOwnerid(ownerid);
			sellhouseinfo.setMediaid(u.get(0).getUserid());
			//�ļ��ϴ�
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
	          sellhouseinfo.setHouseimage(fileName);
	          sellhouseinfo.setCreatetime(date);
	          sellhouseinfo.setHousestatus("������");
	          sellhouseinfo.setRentlength("����˽��");
	          sellhouseinfoService.insertSell(sellhouseinfo);   
	          request.setAttribute("sellhouseinfo", sellhouseinfo);
	          session.setAttribute("addhousemsg", "");
	          //Ϊuser�����ṩ��Դ��Ϣ����
	          Sellhouseinfo shinfo=new Sellhouseinfo();
	          List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
	          request.setAttribute("sellinfo", sellinfo);
	          return "user";
		  }
		}
	//�û�������Ϣ��ѯ
	@RequestMapping(value="/usertransaction")
	public String usertransaction(Transactioninfo transactioninfo,HttpSession session,HttpServletRequest request){
			int ownerid=(int) request.getSession().getAttribute("userid");
			transactioninfo.setOwnerid(ownerid);
			 List<Transactioninfo> t=transactioninfoService.getTransaction(transactioninfo);
			 if(t.size()==0){
				    session.setAttribute("tranmsg", "�޽�����Ϣ");
					return "usertransinfo";
			 }else{
					session.setAttribute("tranmsg", "");
					request.setAttribute("transList",t);
					return "usertransinfo";}
			 }
	//�û�������Ϣ��ѯ--��ȷ��ѯ
	@RequestMapping(value="/usertransaction2")
	public String usertransaction2(Transactioninfo transactioninfo,HttpSession session,HttpServletRequest request){
			if(transactioninfo.getTranstatus().equals(""))
			{
				transactioninfo.setTranstatus(null);
			}
			int ownerid=(int) request.getSession().getAttribute("userid");
			transactioninfo.setOwnerid(ownerid);
			 List<Transactioninfo> t=transactioninfoService.getTransaction(transactioninfo);
			 if(t.size()==0){
				    session.setAttribute("tranmsg", "�޽�����Ϣ");
					return "usertransinfo";
			 }else{
					session.setAttribute("tranmsg", "");
					request.setAttribute("transList",t);
					return "usertransinfo";}
			 }
	//�û��鿴���ϴ���Դ��Ϣ
	@RequestMapping("/ownhouse")
	public String queryhouse(Userinfo userinfo,Sellhouseinfo sellinfo,HttpServletRequest request,HttpSession session) {
			int ownerid=(int) request.getSession().getAttribute("userid");
			sellinfo.setOwnerid(ownerid);
			List<Sellhouseinfo> house = sellhouseinfoService.getSell(sellinfo);
			if(house.size()==0) 
			{
				session.setAttribute("ownhousemsg", "δ�ϴ���Դ��Ϣ");
			}else
			{
				session.setAttribute("ownhousemsg", "");
				request.setAttribute("ownhouse",house);
			}

			return "userownhouse";
		}
	//�ٱ�����--�ӽ��׽����ȡֵ
	@RequestMapping("/juBaovalue")
	public String juBao(Userinfo userinfo,HttpSession session,HttpServletRequest request) {
			userinfo.setUserid(userinfo.getUserid());
			request.setAttribute("userid", userinfo.getUserid());	
			return "userjubao";			
		}
	//�ٱ�����---����д�ľٱ���Ϣ���浽���ݿ�
	@RequestMapping(value="/jubao")
	public String black(Blackuser blackuser,Userinfo userinfo,HttpSession session,HttpServletRequest request) throws ParseException{
		  Date date = new Date(System.currentTimeMillis());
		 /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		  String da=dateFormat.format(date);
		  Date date2=(Date) dateFormat.parse(da);
	      System.out.println(date2);
	      System.out.println(new Date(System.currentTimeMillis()));*/
	      blackuser.setUserid(userinfo.getUserid());
		  blackuser.setCreatetime(date);
		  blackuserService.insertBlack(blackuser);
		  //Ϊuser�����ṩ��Դ��Ϣ����
		  Sellhouseinfo shinfo=new Sellhouseinfo();
		  List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
		  request.setAttribute("sellinfo", sellinfo);
				
		  return "user";
			
		}
	
	//�û���ӽ��׼�¼--��ȡ�����Ϣ
	@RequestMapping("/transaction")
	public String uptransaction(Sellhouseinfo sellinfo,HttpSession session,HttpServletRequest request) {
			sellinfo.getHouseid();
			List<Sellhouseinfo> hou = sellhouseinfoService.getSell(sellinfo);
			request.setAttribute("trans",hou.get(0));
			return "useruptrans";
		}
	//�û���ӽ��׼�¼--��ӽ�����Ϣ
	@RequestMapping("/transaction2")
	public String addtransaction(Transactioninfo transinfo,HttpSession session,HttpServletRequest request) {
			Date date=new Date(System.currentTimeMillis());
			transinfo.setCreatetime(date);
			transservice.addTransaction(transinfo);
			//���ķ��ӵ�״̬
			Sellhouseinfo sh=new Sellhouseinfo();
			sh.setHouseid(transinfo.getHouseid());
			sh.setHousestatus("�ѳ���");
			sellhouseinfoService.updateSell(sh);
			//Ϊuser�����ṩ��Դ��Ϣ����
			 Sellhouseinfo shinfo=new Sellhouseinfo();
			 List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
			 request.setAttribute("sellinfo", sellinfo);
			 return "user";
		}
		
	//�û����޹���
	@RequestMapping("/usercredit")
	public String addcredit(Userinfo userinfo,HttpSession session,HttpServletRequest request) {
			List<Userinfo> us = userinfoService.getUser(userinfo);
			Userinfo user = new Userinfo();
			user.setUserid(userinfo.getUserid());
			user.setUsercredit(us.get(0).getUsercredit()+1);
			userinfoService.updateUser(user);
			//����ҳ������
			Transactioninfo transactioninfo=new Transactioninfo();
			int ownerid=(int) request.getSession().getAttribute("userid");
			transactioninfo.setOwnerid(ownerid);
			List<Transactioninfo> t=transactioninfoService.getTransaction(transactioninfo);
			request.setAttribute("transList",t);
			return"usertransinfo";
	}
	
	//�û����۹���--��ȡ��Ϣ	
	@RequestMapping(value="/comment")
	public String showcomment(Transactioninfo ts ,HttpServletRequest request){
			Transactioninfo trans = new Transactioninfo();
			trans.setCustomid(ts.getCustomid());
			trans.setMediaid(ts.getMediaid());
			trans.setOwnerid(ts.getOwnerid());
			request.setAttribute("trans", trans);
			return "usercomment";
		}
	//�û����۹���--�ϴ�������Ϣ		
	@RequestMapping("/savecommentinfo")
	public String comment(Reportinfo reportinfo,HttpSession session,HttpServletRequest request) {
			reportinfoService.addReport(reportinfo);
			//Ϊuser�����ṩ��Դ��Ϣ����
			 Sellhouseinfo shinfo=new Sellhouseinfo();
			 List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(shinfo);
			 request.setAttribute("sellinfo", sellinfo);
			return "user";
			
		}
	//��ʾ���з�Դ��Ϣ
	@RequestMapping("/showhouseinfo")
	public String showhouseinfo(Sellhouseinfo sellhouseinfo,HttpServletRequest request,HttpSession session){
			session.setAttribute("msg", "");
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(sellhouseinfo);
			request.setAttribute("sellinfo", sellinfo);
			return "userselecthouse";
	}
	//��ʾ���з�Դ��Ϣ--��ȷ��ʾ��Դ��Ϣ
		@RequestMapping("/selhouse")
		public String selhouse(Sellhouseinfo sellhouseinfo,HttpServletRequest request,HttpSession session){
			//��Ϊɸѡ��ԭ�򣬵�û������ɸѡ������ʱ��Ҫ�Ѵ����ֵ���лָ�
			if(sellhouseinfo.getLocationprovince().equals("0"))
			{
				sellhouseinfo.setLocationprovince(null);
			}
			if(sellhouseinfo.getLocationcity().equals("0"))
			{
				sellhouseinfo.setLocationcity(null);;
			}
			if(sellhouseinfo.getLocationqu().equals("0"))
			{
				sellhouseinfo.setLocationqu(null);
			}
			if(sellhouseinfo.getSpace().equals("0")) {
				sellhouseinfo.setSpace(null);
			}
				
			List<Sellhouseinfo> sellinfo = sellhouseinfoService.getSell(sellhouseinfo);
			if(sellinfo.size()==0){
				session.setAttribute("housemsg", "�Բ���δ�ҵ����������ķ�Դ");
				return "userselecthouse";
			}else{
				session.setAttribute("housemsg", "");
				request.setAttribute("sellinfo", sellinfo);
				return "userselecthouse";
			}
		}
	//���н�
	@RequestMapping("/medialist")
	public String medialist( Userinfo userinf,HttpServletRequest request) {
		    userinf.setUserrole("�н�");
			userinf.setUserstatus("����");
			List<Userinfo> list = userinfoService.getUser(userinf);
			request.setAttribute("userinf", list);
			return "usermedialist";
		}
	
	//��ȡ˽����Ϣ
	@RequestMapping("/getmessageinfo")
	public String getmessageinfo(Messageinfo messageinfo,HttpSession session,HttpServletRequest request){
		int ownerid=(int) request.getSession().getAttribute("userid");
		messageinfo.setUserid(ownerid);
		request.setAttribute("messageinfo", messageinfo);
		return "useraddmessage";
	}
	
	//����˽������
	@RequestMapping("/savemessageinfo")
	public String savemessageinfo(Messageinfo messageinfo,HttpSession session,HttpServletRequest request){
		messageinfo.setStatus("δ����");
		Date date=new Date(System.currentTimeMillis());
		messageinfo.setCreatetime(date);
		messageinfoService.addMessage(messageinfo);
		//��ȡ����ҳ�������
		Userinfo userinf=new Userinfo();
		userinf.setUserrole("�н�");
		userinf.setUserstatus("����");
		List<Userinfo> list = userinfoService.getUser(userinf);
		request.setAttribute("userinf", list);
		return "usermedialist";
	}
}