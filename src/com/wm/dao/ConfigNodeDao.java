package com.wm.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.ConfigNode;

public class ConfigNodeDao extends BaseDao{

	public List<ConfigNode> getConfigNode() throws Exception{
		List<ConfigNode> configNodeList=new ArrayList<ConfigNode>();
		StringBuffer stringBuffer=new StringBuffer();		
		stringBuffer.append("select * from config_node n,config_webservice w where n.id=w.nodeid");
		JSONArray jsonArray=getResult(stringBuffer.toString());
		if(jsonArray.length()>0){
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);	
				ConfigNode configNode=new ConfigNode();
				configNode.setId(jsonObject.getString("id"));
				configNode.setNodename(jsonObject.getString("nodename"));
				configNode.setIsmanager(jsonObject.getString("ismanager"));	
				configNode.setWebserviceadd(jsonObject.getString("webserviceadd"));	
				configNode.setNamespace(jsonObject.getString("namespace"));	
				configNode.setMethod(jsonObject.getString("method"));	
				configNodeList.add(configNode);
			}			
		}else{			
		}
		return configNodeList;
	}
	
	public static void main(String[] args) throws Exception {
		ConfigNodeDao configNodeDao=new ConfigNodeDao();
		configNodeDao.getConfigNode();
	}
	
}
