package com.cheese.service.impl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.chainsaw.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.MainModelClause;
import com.cheese.dao.UserMapper;
import com.cheese.pojo.Department;
import com.cheese.pojo.PageBean;
import com.cheese.pojo.User;
import com.cheese.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	 @Autowired
	 private UserMapper userMapper;

	 @Override
	 public int insert(User user) {
	    	return userMapper.insert(user);
	 }
	  
	 @Override
	  public User selectUser(User user) {
		  return userMapper.selectUser(user);
	  }
	  
	 @Override
	  public User selectO(String userName) {
		  return userMapper.selectO(userName);
	  }
	  
	 @Override
		public int selectCount() {
			return userMapper.selectCount();
		}

		
	 @Override
	 public PageBean<User> findByPage(int currentPage) {	 
			Map<String,Object> map = new HashMap<String,Object>();
			PageBean<User> pageBean = new PageBean<User>();
			
		    //��װ��ǰҳ��
	        pageBean.setCurrPage(currentPage);
	        
			//ÿҳ��ʾ������
			int pageSize=5;
			pageBean.setPageSize(pageSize);
			
			//��װ�ܼ�¼��
			int totalCount = userMapper.selectCount();
			pageBean.setTotalCount(totalCount);

			//��װ��ҳ��
			double tc = totalCount;
	        Double num =Math.ceil(tc/pageSize);//����ȡ��
	        pageBean.setTotalPage(num.intValue());
	      
			map.put("start",(currentPage-1)*pageSize);
			map.put("size", pageBean.getPageSize());
			//��װÿҳ��ʾ������
			List<User> lists = userMapper.findByPage(map);
			pageBean.setLists(lists);
			
			return pageBean;
		}
	 
	 @Override
	 public List<Department> selectDept(int departmentId){
		 return userMapper.selectDept(departmentId);
	 }
}
