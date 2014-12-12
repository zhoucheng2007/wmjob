package com.wm.webservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.dao.BaseDao;
import com.wm.dao.LockDao;
import com.wm.entity.Locktable;

@WebService(serviceName="WmService",portName="WmServicePort",targetNamespace="http://blogzhou.com/jaxws/wm")
public class SynService {
	

	@WebMethod
	public String doService(String json){
		System.out.println("客户端过来的json为："+json);
		//json={"cmd":"add",
		//"tablename":"order1",
		//"sqllist":["insert into order1(id,num,price,createtime,money,goodid,goodname) values('01a7bf20-af33-44aa-944a-10fe62519e16','1','8','null','8','nd01','book')",
		//"insert into order1(id,num,price,createtime,money,goodid,goodname) values('9f180fa5-3d34-4ef4-8473-184573e7600c','1','10','null','10','nd02','pen')"]}
		JSONObject jsonObject=new JSONObject(json);
		String cmd=jsonObject.getString("cmd");
		String tablename=jsonObject.getString("tablename");
		String transaction=jsonObject.getString("transaction");
		JSONArray jsonArray=jsonObject.getJSONArray("sqllist");
		LockDao lockDao=new LockDao();
		
		JSONObject jsonObjectReturn=new JSONObject();
		
		if("getlock".equals(cmd)){
			String tablekey=jsonObject.getString("tablekey");
			boolean flag=false;
			try {
				flag=lockDao.islock(tablename, tablekey);
				String code="0";
				if(flag){
					
				}else{
					code="1";
				}
				jsonObjectReturn.put("code", code);
				jsonObjectReturn.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				jsonObjectReturn.put("code", 1);
				jsonObjectReturn.put("message", "error");
			}
			return jsonObjectReturn.toString();
		}else if("addlock".equals(cmd)){
				Locktable locktable=new Locktable();
				locktable.setLockid(jsonObject.getString("lockid"));
				locktable.setLocktype("write");
				locktable.setTablekey(jsonObject.getString("tablekey"));
				locktable.setTablename(jsonObject.getString("tablename"));
				locktable.setTransaction(jsonObject.getString("transaction"));
				try {
					String result=lockDao.addlock(locktable);
					String code="0";
					if(null!=result){
						
					}else{
						code="1";
					}
					jsonObjectReturn.put("code", code);
					jsonObjectReturn.put("message", "success");
				} catch (Exception e) {
					e.printStackTrace();
					jsonObjectReturn.put("code", 1);
					jsonObjectReturn.put("message", "error");
				}
				return jsonObjectReturn.toString();
		}else if("deletelock".equals(cmd)){
			String tablekey=jsonObject.getString("tablekey");
			boolean flag=false;
			try {
				flag=lockDao.deletelock(tablename,tablekey);
				String code="0";
				if(flag){
					
				}else{
					code="1";
				}
				jsonObjectReturn.put("code", code);
				jsonObjectReturn.put("message", "success");
			} catch (Exception e) {
				e.printStackTrace();
				jsonObjectReturn.put("code", 1);
				jsonObjectReturn.put("message", "error");
			}
			return jsonObjectReturn.toString();
		}else{//其他操作
			List<String> sqlList=new ArrayList<String>();
			for(int i=0;i<jsonArray.length();i++){
				sqlList.add(jsonArray.get(i).toString());
			}
			BaseDao baseDao=new BaseDao();
			try {
				baseDao.addResult(sqlList);
				jsonObjectReturn.put("code", 0);
				jsonObjectReturn.put("message", "success");
			} catch (SQLException e) {
				e.printStackTrace();
				jsonObjectReturn.put("code", 1);
				jsonObjectReturn.put("message", "error");
			}		
			return jsonObjectReturn.toString();
		}
		

	}
	
	
}
