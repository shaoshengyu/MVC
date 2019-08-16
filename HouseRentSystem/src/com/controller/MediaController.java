package com.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/media")
public class MediaController {

	@Autowired
	TransactioninfoService transservice;
	
	@Autowired
	SellhouseinfoService sellhouseinfoService;
	
	@Autowired
	MessageinfoService messageinfoService;
	
	@Autowired
	UserinfoService userinfoService;
	
	@Autowired
	TransactioninfoService transactioninfoService;
	
	
	@RequestMapping("/mediaaddhouse")
	public String addhouse() {
		return "mediaAddhouse";
	}
	
	//�н�鿴������Ϣ
	@RequestMapping(value="/mediatransaction")
	public String mediatransaction(Transactioninfo transactioninfo,HttpSession session,HttpServletRequest request){
		int mediaid=(int) request.getSession().getAttribute("userid");
		transactioninfo.setOwnerid(mediaid);
		 List<Transactioninfo> t=transactioninfoService.getTransaction(transactioninfo);
		 if(t.size()==0){
			    session.setAttribute("mediatranmsg", "�޽�����Ϣ");
				return "mediatransinfo";
			}else{
				session.setAttribute("mediatranmsg", "");
				request.setAttribute("transList",t);
				return "mediatransinfo";
			}
	}
	//�н��ϴ���Դ��Ϣ
	@RequestMapping(value="/addHouse")
	public String addHouse(Sellhouseinfo sellhouseinfo,FilePo filePo,HttpSession session,HttpServletRequest request){
		 Userinfo ui=new Userinfo();
		 ui.setUserid(sellhouseinfo.getOwnerid());
		 ui.setUserrole("��ͨ�û�");
		 ui.setUserstatus("����");
		 List<Userinfo> u=userinfoService.getUser(ui);
		if(u.size()==0){
		    session.setAttribute("addhousemsg", "���û��Ų����ڻ��ѱ��������������");
			return "mediaAddhouse";
		}else{	
			Date date=new Date(System.currentTimeMillis());
			int ownerid=(int) request.getSession().getAttribute("userid");
			sellhouseinfo.setOwnerid(u.get(0).getUserid());
			sellhouseinfo.setMediaid(ownerid);
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
	          sellhouseinfo.setRentlength("����˽��");;
	          sellhouseinfoService.insertSell(sellhouseinfo);   
	          request.setAttribute("sellhouseinfo", sellhouseinfo);
	          session.setAttribute("addhousemsg", "");
	          //��ȡ��ҳ��Ϣ
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
		  }
		}
	//�н�鿴���ϴ���Դ��Ϣ
		@RequestMapping("/ownhouse")
		public String queryhouse(Userinfo userinfo,Sellhouseinfo sellinfo,HttpServletRequest request,HttpSession session) {
			 
				int ownerid=(int) request.getSession().getAttribute("userid");
				sellinfo.setMediaid(ownerid);
				List<Sellhouseinfo> house = sellhouseinfoService.getSell(sellinfo);
				if(house.size()==0) 
				{
					session.setAttribute("ownhousemsg", "δ�ϴ���Դ��Ϣ");
				}else
				{
					session.setAttribute("ownhousemsg", "");
					request.setAttribute("ownhouse",house);
				}

				return "mediaownhouse";
			}
	//�н�鿴������Ϣ		
	@RequestMapping(value="/seemessage")
	public String message(Messageinfo messageinfo,HttpSession session,HttpServletRequest request){
		Messageinfo me=new Messageinfo();
		int mediaid=(int) request.getSession().getAttribute("userid");
		me.setMediaid(mediaid);
		me.setStatus("δ����");
		List<Messageinfo> m = messageinfoService.getMessage(me);
		if(m.size()==0){
			session.setAttribute("liuyanmsg", "��������Ϣ");
			return "mediamessage";
		}else{
			session.setAttribute("liuyanmsg", "");
			request.setAttribute("messageList",m);
			return "mediamessage";
		}
	}
	
	//������Ϣ
	@RequestMapping(value="/updatemessage")
	public String updatemessage(Messageinfo messageinfo,HttpSession session,HttpServletRequest request){
		messageinfo.getMessageid();
		Messageinfo mi=new Messageinfo();
		mi.setMessageid(messageinfo.getMessageid());
		mi.setStatus("�Ѵ���");
		messageinfoService.updateMessage(mi);
		//��ȡ����ҳ����Ϣ
		Messageinfo me=new Messageinfo();
		int mediaid=(int) request.getSession().getAttribute("userid");
		me.setMediaid(mediaid);
		me.setStatus("δ����");
		List<Messageinfo> m = messageinfoService.getMessage(me);
		if(m.size()==0){
			session.setAttribute("liuyanmsg", "��������Ϣ");
			return "mediamessage";
		}else{
			session.setAttribute("liuyanmsg", "");
			request.setAttribute("messageList",m);
			return "mediamessage";
		}
	}
	//�н��ϴ����׼�¼--��ȡ������Ϣ		
	@RequestMapping("/transaction")
	public String uptransaction(Sellhouseinfo sellinfo,HttpSession session,HttpServletRequest request) {
		sellinfo.getHouseid();
		List<Sellhouseinfo> hou = sellhouseinfoService.getSell(sellinfo);
		request.setAttribute("trans",hou.get(0));
		return "mediauptrans";
	}
	//�н��ϴ����׼�¼--����������Ϣ		
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
		//Ϊ��תҳ���ṩ����
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
	}


}
