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
		//设置管理节点
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
	        // 指定调用WebService的URL 
	        EndpointReference targetEPR = new EndpointReference("http://10.204.1.108:8080/macketManager/webservice/Chat?wsdl"); 
	        options.setTo(targetEPR); 
	        // 指定方法的参数值 
	        Object[] requestParam = new Object[] {json}; 
	        // 指定方法返回值的数据类型的Class对象 
			Class[] responseParam = new Class[] {String.class}; 
	        // 指定要调用的getGreeting方法及WSDL文件的命名空间 
	        QName requestMethod = new QName("http://webservice/", "makeCaller"); 
	        //System.out.println(configNode.getWebserviceadd() +"  "+configNode.getNamespace()+" "+configNode.getMethod());
	        // 调用方法并输出该方法的返回值 
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
