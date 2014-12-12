package com.wm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.Locktable;

public class LockDao extends BaseDao {
	//查询事物是否枷锁
	public boolean islock(String tablename,String tablekey) throws Exception{
		String sql="select * from locktable where tablename='"+tablename+"' and  tablekey='"+tablekey+"'";
		System.out.println(sql);
		JSONArray jsonArray=getResult(sql);
		if(jsonArray.length()==0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据表名和主键获取事物锁
	 * @param tablename
	 * @param tablekey
	 * @return
	 * @throws Exception
	 */
	public Locktable getlock(String tablename,String tablekey) throws Exception{
		String sql="select * from locktable where tablename='"+tablename+"' and  tablekey='"+tablekey+"'";
		System.out.println(sql);
		JSONArray jsonArray=getResult(sql);
		if(jsonArray.length()==0){
			return new Locktable();
		}else{
			JSONObject jsonObject=jsonArray.getJSONObject(0);
			Locktable locktable=new Locktable();
			locktable.setLockid(jsonObject.getString("lockid"));
			locktable.setLocktype(jsonObject.getString("locktype"));
			locktable.setTablekey(jsonObject.getString("tablekey"));
			locktable.setTablename(jsonObject.getString("tablename"));
			locktable.setTransaction(jsonObject.getString("transaction"));
			return locktable;
		}
	}
	
	//查询事物是否枷锁
	public String addlock(Locktable locktable){
		String lockid=UUID.randomUUID().toString() ;
		String tablename=locktable.getTablename();
		String tablekey=locktable.getTablekey();
		String locktype=locktable.getLocktype();
		String transaction=locktable.getTransaction();
		String sql="insert into locktable(lockid,tablename,tablekey,locktype,transaction)"
				+ " values('"+lockid+"','"+tablename+"','"+tablekey+"','"+locktype+"','"+transaction+"')";
		List<String> sqlList=new ArrayList<String>();
		sqlList.add(sql);
		boolean flag=false;
		try {
			flag = addResult(sqlList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(flag){
			return lockid;
		}else{
			return null;
		}		
	}
	
	//查询事物是否枷锁
	public boolean deletelock(String tablename,String tablekey){
		String sql="delete from locktable where tablename='"+tablename+"' and tablekey='"+tablekey+"'";
		List<String> sqlList=new ArrayList<String>();
		sqlList.add(sql);
		boolean flag=false;
		try {
			flag = addResult(sqlList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	
	public static void main(String[] args) throws Exception {
		LockDao lockDao=new LockDao();
		System.out.println(lockDao.islock("order1", "6f3b06d0-6a66-4146-9be3-9268a30c2a0c"));
	}
}
