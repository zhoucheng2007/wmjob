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

public class ServiceCallManager {

	
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
			ConfigNode configNode=getManagerConfigNode();			
			try {
			serviceClient = new RPCServiceClient();					 	
	        Options options = serviceClient.getOptions(); 
	        // ָ������WebService��URL 
	        EndpointReference targetEPR = new EndpointReference(configNode.getWebserviceadd()); 
	        options.setTo(targetEPR); 
	        // ָ�������Ĳ���ֵ 
	        Object[] requestParam = new Object[] {json}; 
	        // ָ����������ֵ���������͵�Class���� 
			Class[] responseParam = new Class[] {String.class}; 
	        // ָ��Ҫ���õ�getGreeting������WSDL�ļ��������ռ� 
	        QName requestMethod = new QName(configNode.getNamespace(), configNode.getMethod()); 
	        System.out.println(configNode.getWebserviceadd() +"  "+configNode.getNamespace()+" "+configNode.getMethod());
	        // ���÷���������÷����ķ���ֵ 
			returnString=(String) serviceClient.invokeBlocking(requestMethod, requestParam, responseParam)[0];
			} catch (AxisFault e) {
				e.printStackTrace();
			}finally{
				return returnString;
			} 
	        
		}
	
	public static void main(String[] args) throws Exception {
		ServiceCallManager serviceCallManager=new ServiceCallManager();
		System.out.println(serviceCallManager.doService("start"));
	}
	
}
