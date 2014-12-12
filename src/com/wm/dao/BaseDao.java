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
		
		//3.ͨ��Connection���󴴽�Statement����
		Statement sta=(Statement) conn.createStatement();
		//4.ʹ��Statementִ��SQL���
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
			//3.ͨ��Connection���󴴽�Statement����	
			conn.setAutoCommit(false);
			for(String sql:sqlList){
				System.out.println("ִ�и��£�"+sql);
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
	 * �����������ӷ���,����webservice����
	 * @param sqlList
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public JSONObject addResult(List<String> sqlList,List<String> idList,String tableName) throws SQLException{
		boolean flag=true;
		JSONObject jsonObject=new JSONObject();
		//���Ӳ���
		jsonObject.put("cmd", "add");
		//�����ı����
		jsonObject.put("tablename", tableName);
		//SQL����
		jsonObject.put("sqllist", sqlList);
		//����ɣ�,UUIDģ�ⲻ���ظ�
		jsonObject.put("transaction", UUID.randomUUID());
		
		Statement sta=conn.createStatement();;
		JSONObject jsonObjectReturn = new JSONObject();
		
		try {
			//��ѯ�Ƿ�����
			for(String id:idList){
				System.out.println("ִ�и��£�"+id);
				jsonObject.put("cmd", "getlock");
				jsonObject.put("tablekey", id);
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
						//���ﾭ�¼��������������ܼ�����
						flag=false;
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "�����Ѿ������������Ժ�������");
						System.out.println("�����Ѿ������������Ժ�������");
						return jsonObjectReturn;
						
						
					}else{
						//û����������ִ��
					}
				} catch (Exception e) {
					flag=false;
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "����������������쳣");
					return jsonObjectReturn;
				}
			}
			
			
			//��������
			for(String id:idList){
				System.out.println("ִ��������"+id);
				jsonObject.put("cmd", "addlock");
				jsonObject.put("tablekey", id);
				jsonObject.put("lockid", UUID.randomUUID());
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
						//���ﾭ�¼��������������ܼ�����												
						
					}else{
						flag=false;
						System.out.println("��������ʧ�ܣ��ύʧ��");
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "��������ʧ�ܣ��ύʧ��");
						return jsonObjectReturn;
						
						//û����������ִ��
					}
				} catch (Exception e) {
					flag=false;
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("�������������쳣");
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "�������������쳣");
					return jsonObjectReturn;
				}
			}
			
			//3.ͨ��Connection���󴴽�Statement����	
			conn.setAutoCommit(false);
			for(String sql:sqlList){
				System.out.println("ִ�и��£�"+sql);
				sta.executeUpdate(sql);
			}
			
			//���¹���ڵ㿪ʼ
			try {
				jsonObject.put("cmd", "add");
				ServiceCallManager.doService(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				System.out.println("���¹���ڵ����"+e.getMessage());
				jsonObjectReturn.put("code", flag);
				jsonObjectReturn.put("message", "���¹���ڵ��������ع������нڵ����ʧ��");
				return jsonObjectReturn;
			}
			conn.commit();
			
			//�ͷ���
			for(String id:idList){
				jsonObject.put("cmd", "deletelock");
				jsonObject.put("tablekey", id);
				try {
					String returnString=ServiceCallManager.doService(jsonObject.toString());
					JSONObject jsonObject2=new JSONObject(returnString);
					String code=jsonObject2.getString("code");
					if("0".equals(code)){
												
					}else{
						//���ﾭ�¼��������������ܼ�����							
						System.out.println("�����ύ�ɹ������ͷ���ʧ��");
						jsonObjectReturn.put("code", flag);
						jsonObjectReturn.put("message", "�����ύ�ɹ���������ڵ��ͷ���ʧ��");
						return jsonObjectReturn;
					}
				} catch (Exception e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					jsonObjectReturn.put("code", flag);
					jsonObjectReturn.put("message", "����ڵ��ͷ��������쳣");
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
		jsonObjectReturn.put("message", "���¹���ڵ�ͷ�Ƭ�ɹ�");
		return jsonObjectReturn;		
	}
	
}
