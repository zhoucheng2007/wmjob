package com.wm.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.User;

 
public class UserDao extends BaseDao{
	
	
	
	public User getUser(String username,String password) throws Exception{
				   		
		StringBuffer stringBuffer=new StringBuffer();
		User user=new User();
		stringBuffer.append("select * from user where name='").append(username).append("' and password='").append(password).append("'");
		JSONArray jsonArray=getResult(stringBuffer.toString());
		if(jsonArray.length()>0){
			JSONObject jsonObject=jsonArray.getJSONObject(0);			
			user.setCreattime(jsonObject.getString("creattime"));
			user.setId(jsonObject.getString("id"));
			user.setName(jsonObject.getString("name"));
			user.setPassword(jsonObject.getString("password"));			
		}else{
			
		}
		return user;
	}
	
	

	
}
