package com.wm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.connection.ConnectionManager;
import com.wm.webservice.ServiceCallManager;

public class BaseDao {
	Connection conn=ConnectionManager.getConnection();
	
	public JSONArray getResult(String sql) throws Exception{
		
		//3.通过Connection对象创建Statement对象
		Statement sta=(Statement) conn.createStatement();
		//4.使用Statement执行SQL语句
		ResultSet rs=(ResultSet) sta.executeQuery(sql);	
		ResultSetMetaData data = (ResultSetMetaData) rs.getMetaData();
		List<String> columnNameList=new ArrayList<String>();
		for (int i = 1; i <= data.getColumnCount(); i++) 
		{
		String columnName = data.getColumnName(i);
		columnNameList.add(columnName);
		}
		JSONArray jsonArray=new JSONArray();	
		String record="";
		while(rs.next())
		{			
			JSONObject jsonObject=new JSONObject();
			for(String columnName:columnNameList){
				record=rs.getString(columnName)==null?" ":rs.getString(columnName);
				jsonObject.put(columnName, record);
			}
			jsonArray.put(jsonObject);
		}
		rs.close();
		sta.close();
		conn.close();
		return jsonArray;		
	}
	
	public boolean addResult(List<String> sqlList) throws SQLException{
		boolean flag=true;
		Statement sta=conn.createStatement();
		try {
			//3.通过Connection对象创建Statement对象	
			conn.setAutoCommit(false);
			for(String sql:sqlList){
				System.out.println("执行更新："+sql);
				sta.executeUpdate(sql);
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			flag=false;
		}finally{
			sta.close();
			conn.close();
		}				
		return flag;		
	}
	
	/**
	 * 带表名的增加方法,用于webservice调用
	 * @param sqlList
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public JSONObject addResult(List<String> sqlList,List<String> idList,String tableName) throws SQLException{
		boolean flag=true;
		JSONObject jsonObject=new JSONObject();
		//增加操作
		jsonObject.put("cmd", "add");
		//操作的表对象
		jsonObject.put("tablename", tableName);
		//SQL集合
		jsonObject.put("sqllist", sqlList);
		//事物ＩＤ,UUID模拟不会重负
		jsonObject.put("transaction", UUID.randomUUID());
		
		Statement sta=conn.createStatement();;
		JSONObject jsonObjectReturn = new JSONObject();
		
		try {
			//查询是否上锁
			for(String id:idList){
				System.out.println("执行更新："+id);
				jsonObject.put("cmd", "getlock");
				jsonObject.put("tablekey", id);
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
						//事物经呗加锁。跳出。不能继续做
						flag=false;
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "事物已经被加锁，请稍后再试试");
						System.out.println("事物已经被加锁，请稍后再试试");
						return jsonObjectReturn;
						
						
					}else{
						//没加锁，继续执行
					}
				} catch (Exception e) {
					flag=false;
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "检查事物上锁出现异常");
					return jsonObjectReturn;
				}
			}
			
			
			//事物上锁
			for(String id:idList){
				System.out.println("执行上锁："+id);
				jsonObject.put("cmd", "addlock");
				jsonObject.put("tablekey", id);
				jsonObject.put("lockid", UUID.randomUUID());
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
						//事物经呗加锁。跳出。不能继续做												
						
					}else{
						flag=false;
						System.out.println("事物上锁失败，提交失败");
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "事物上锁失败，提交失败");
						return jsonObjectReturn;
						
						//没加锁，继续执行
					}
				} catch (Exception e) {
					flag=false;
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("事物上锁出现异常");
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "事物上锁出现异常");
					return jsonObjectReturn;
				}
			}
			
			//3.通过Connection对象创建Statement对象	
			conn.setAutoCommit(false);
			for(String sql:sqlList){
				System.out.println("执行更新："+sql);
				sta.executeUpdate(sql);
			}
			
			//更新管理节点开始
			try {
				jsonObject.put("cmd", "add");
				ServiceCallManager.doService(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				System.out.println("更新管理节点出错"+e.getMessage());
				jsonObjectReturn.put("code", flag);
				jsonObjectReturn.put("message", "更新管理节点出错，事物回滚，所有节点更新失败");
				return jsonObjectReturn;
			}
			conn.commit();
			
			//释放锁
			for(String id:idList){
				jsonObject.put("cmd", "deletelock");
				jsonObject.put("tablekey", id);
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
												
					}else{
						//事物经呗加锁。跳出。不能继续做							
						System.out.println("事物提交成功，但释放锁失败");
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "事物提交成功，但管理节点释放锁失败");
						return jsonObjectReturn;
					}
				} catch (Exception e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "管理节点释放锁出现异常");
					return jsonObjectReturn;
				}
			}					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			flag=false;
		}finally{
			sta.close();
			conn.close();
		}
		jsonObjectReturn.put("code", flag);
		jsonObjectReturn.put("message", "更新管理节点和分片成功");
		return jsonObjectReturn;		
	}
	
}
