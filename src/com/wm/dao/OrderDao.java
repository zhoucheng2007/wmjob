package com.wm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.Order;


public class OrderDao extends BaseDao {

	public List<Order> getOrder() throws Exception{
		List<Order> orderList=new ArrayList<Order>();
		StringBuffer stringBuffer=new StringBuffer();		
		stringBuffer.append("select * from order1 o,goods g  where o.goodid=g.id");
		JSONArray jsonArray=getResult(stringBuffer.toString());
		if(jsonArray.length()>0){
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);	
				Order order=new Order();
				order.setId(jsonObject.getString("id"));
				order.setGoodname(jsonObject.getString("goodname"));
				order.setPrice(jsonObject.getString("price"));	
				order.setCreatetime(jsonObject.getString("createtime"));	
				order.setNum(jsonObject.getString("num"));
				order.setMoney(jsonObject.getString("money"));		
				orderList.add(order);
			}			
		}else{			
		}
		return orderList;
	}
	
	public JSONObject addOrder(List<Order> orderList) throws Exception{
		
		List<String> sqlList=new ArrayList<String>();
		
		//主键列表
		List<String> idList=new ArrayList<String>();
		for(Order order:orderList){
			UUID uuid = UUID.randomUUID(); 
			String id=uuid.toString() ;
			String num=order.getNum();
			String price=order.getPrice();
			String createtime=order.getCreatetime();
			String money=order.getMoney();
			String goodid=order.getGoodid();
			String goodname=order.getGoodname();
			String sql="insert into order1(id,num,price,createtime,money,goodid,goodname)"
					+ " values('"+id+"','"+num+"','"+price+"','"+createtime+"','"+money+"','"+goodid+"','"+goodname+"')";
			sqlList.add(sql);
			idList.add(id);
		}
		
		JSONObject jsonObject=addResult(sqlList,idList,"order1");
		
		return jsonObject;
		
	}
	
	public JSONObject updateOrder(List<Order> orderList) throws Exception{
		
		List<String> sqlList=new ArrayList<String>();
		//主键列表
		List<String> idList=new ArrayList<String>();
		for(Order order:orderList){
			String id=order.getId();
			String num=order.getNum();
			String price=order.getPrice();
			String createtime=order.getCreatetime();
			String money=order.getMoney();
			String goodid=order.getGoodid();
			String goodname=order.getGoodname();
			String sql="update order1 set num='"+num+"',money='"+money+"' where id='"+id+"'";
			sqlList.add(sql);
			idList.add(id);
		}
		
		JSONObject jsonObject=addResult(sqlList,idList,"order1");
		
		return jsonObject;
		
	}
	
	
	public static void main(String[] args) throws Exception {

	}
	
}
