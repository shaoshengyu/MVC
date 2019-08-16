package com.dao;

import java.util.List;

import com.pojo.Userinfo;

public interface UserinfoMapper {
	//�������� .xml�ļ��е�id��ͬ
	public List<Userinfo> selectUser(Userinfo userinfo);
	public int insertUser(Userinfo userinfo);
	public int updateUser(Userinfo userinfo);
	public int deleteUser(Userinfo userinfo);
}