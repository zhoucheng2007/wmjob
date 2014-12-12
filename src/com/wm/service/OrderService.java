package com.wm.service;

import java.util.List;

import org.json.JSONObject;

import com.wm.dao.OrderDao;
import com.wm.entity.Order;

public class OrderService {

	OrderDao orderDao=new OrderDao();
	
	public List<Order> getOrder() throws Exception{
		List<Order> orderList=orderDao.getOrder();
		return orderList;
	}	
	
	
	public JSONObject addOrder(List<Order> orderList) throws Exception{		
		return orderDao.addOrder(orderList);
	}
	
	public JSONObject updateOrder(List<Order> orderList) throws Exception{
		return orderDao.updateOrder(orderList);
	}
}
