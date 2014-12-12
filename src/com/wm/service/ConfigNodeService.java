package com.wm.service;

import java.util.List;

import com.wm.dao.ConfigNodeDao;
import com.wm.entity.ConfigNode;

public class ConfigNodeService {
	ConfigNodeDao configNodeDao=new ConfigNodeDao();
	
	public List<ConfigNode> getConfigNode() throws Exception{
		List<ConfigNode> configNodeList=configNodeDao.getConfigNode();
		return configNodeList;
	}
}
