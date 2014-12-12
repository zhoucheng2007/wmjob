package com.wm.service;

import java.util.List;

import com.wm.dao.GoodsDao;
import com.wm.entity.Goods;

public class GoodsService {

	GoodsDao GoodsDao=new GoodsDao();
	
	public List<Goods> getGoods() throws Exception{
		List<Goods> listGoods=GoodsDao.getGoods();
		return listGoods;
	}
}
