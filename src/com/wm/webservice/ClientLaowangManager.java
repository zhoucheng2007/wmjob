package com.wm.webservice;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.wm.constant.SessionConstant;
import com.wm.entity.ConfigNode;
import com.wm.service.ConfigNodeService;

public class ClientLaowangManager {

	
	public static ConfigNode getManagerConfigNode() throws Exception{
		ConfigNodeService configNodeService=new ConfigNodeService();
		List<ConfigNode> configNodeList=configNodeService.getConfigNode();
		ConfigNode cfn = null;
		//���ù���ڵ�
		for(ConfigNode configNode:configNodeList){
			if("1".equals(configNode.getIsmanager())){
				cfn= configNode;
			}
		}
		return cfn;
	}
	
	@SuppressWarnings({ "finally", "rawtypes" })
	public static String doService(String json) throws Exception{
		 	RPCServiceClient serviceClient;
		 	String returnString="";		 	
			//ConfigNode configNode=getManagerConfigNode();			
			try {
			serviceClient = new RPCServiceClient();					 	
	        Options options = serviceClient.getOptions(); 
	        // ָ������WebService��URL 
	        EndpointReference targetEPR = new EndpointReference("http://10.204.1.108:8080/macketManager/webservice/Chat?wsdl"); 
	        options.setTo(targetEPR); 
	        // ָ�������Ĳ���ֵ 
	        Object[] requestParam = new Object[] {json}; 
	        // ָ����������ֵ���������͵�Class���� 
			Class[] responseParam = new Class[] {String.class}; 
	        // ָ��Ҫ���õ�getGreeting������WSDL�ļ��������ռ� 
	        QName requestMethod = new QName("http://webservice/", "makeCaller"); 
	        //System.out.println(configNode.getWebserviceadd() +"  "+configNode.getNamespace()+" "+configNode.getMethod());
	        // ���÷���������÷����ķ���ֵ 
			returnString=(String) serviceClient.invokeBlocking(requestMethod, requestParam, responseParam)[0];
			} catch (AxisFault e) {
				e.printStackTrace();
			}finally{
				return returnString;
			} 
	        
		}
	
	public static void main(String[] args) throws Exception {
		ClientLaowangManager serviceCallManager=new ClientLaowangManager();
		System.out.println(serviceCallManager.doService("lao wang "));
	}
	
}
