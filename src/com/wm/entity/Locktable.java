package com.wm.entity;

public class Locktable {
	
	String tablename;
	
	String tablekey;
	
	String locktype;
	
	String lockid;
	
	String transaction;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTablekey() {
		return tablekey;
	}

	public void setTablekey(String tablekey) {
		this.tablekey = tablekey;
	}

	public String getLocktype() {
		return locktype;
	}

	public void setLocktype(String locktype) {
		this.locktype = locktype;
	}

	public String getLockid() {
		return lockid;
	}

	public void setLockid(String lockid) {
		this.lockid = lockid;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
		
}
