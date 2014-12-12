package com.wm.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.Goods;


public class GoodsDao extends BaseDao {

	public List<Goods> getGoods() throws Exception{
		List<Goods> goodsList=new ArrayList<Goods>();
		StringBuffer stringBuffer=new StringBuffer();		
		stringBuffer.append("select * from goods g,shangjia s  where g.shangjiaid=s.shangjiaid");
		JSONArray jsonArray=getResult(stringBuffer.toString());
		if(jsonArray.length()>0){
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);	
				Goods Goods=new Goods();
				Goods.setId(jsonObject.getString("id"));
				Goods.setGoodname(jsonObject.getString("goodname"));
				Goods.setPrice(jsonObject.getString("price"));	
				Goods.setCreatetime(jsonObject.getString("createtime"));	
				Goods.setShangjiaid(jsonObject.getString("shangjiaid"));
				Goods.setShangjianame(jsonObject.getString("shangjianame"));		
				goodsList.add(Goods);
			}			
		}else{			
		}
		return goodsList;
	}
	
	public static void main(String[] args) throws Exception {
		GoodsDao GoodsDao=new GoodsDao();
		GoodsDao.getGoods();
	}
	
}
