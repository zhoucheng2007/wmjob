package com.wm.entity;

import java.io.Serializable;

public class ConfigNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4526196064298107907L;

	String id;
	
	String nodename;
	
	String ismanager;
	
	String webserviceadd;
	
	String namespace;
	
	String method;
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(String ismanager) {
		this.ismanager = ismanager;
	}

	public String getWebserviceadd() {
		return webserviceadd;
	}

	public void setWebserviceadd(String webserviceadd) {
		this.webserviceadd = webserviceadd;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
