package com.wm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wm.entity.Goods;
import com.wm.entity.Order;
import com.wm.service.GoodsService;
import com.wm.service.OrderService;

@WebServlet(name="GoodsServlet",urlPatterns="/GoodsServlet")
public class GoodsServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GoodsServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("get请求");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Goods> goodsList;
		String cmd=request.getParameter("cmd");
		System.out.println("处理请求为"+cmd);
		if(null==cmd){
			//默认查询
			cmd="list";
		}
		GoodsService goodsService=new GoodsService();
		OrderService orderService=new OrderService();
		try {
				//查询所有操作
				if("list".equals(cmd)){
						goodsList=goodsService.getGoods();
						request.setAttribute("goodsList", goodsList);
						RequestDispatcher rd=request.getRequestDispatcher("/jsp/index.jsp");
					    rd.forward(request,response); 
				//增加操作
				 }else if("add".equals(cmd)){
					 String orderString=request.getParameter("order");
					 List<Order> orderList=new ArrayList<Order>();
					 JSONArray jsonArray=new JSONArray(orderString);
					 if(jsonArray.length()>0){
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject=jsonArray.getJSONObject(i);	
								Order order=new Order();
								order.setGoodid(jsonObject.getString("id"));
								order.setPrice(jsonObject.getString("price"));	
								order.setNum(jsonObject.getString("num"));
								order.setGoodname(jsonObject.getString("goodname"));
								order.setMoney(String.valueOf(jsonObject.getInt("money")));		
								orderList.add(order);
							}
							//todo保存键盘
							JSONObject jsonObjectReturn=orderService.addOrder(orderList);
							
							boolean codeReturn=jsonObjectReturn.getBoolean("code");
							String messageReturn=jsonObjectReturn.getString("message");
							
							String code="";
							
							if(codeReturn){
								code="1";
							}else if(!codeReturn){
								code="0";
							}
							
							response.setCharacterEncoding("UTF-8");  
						    response.setContentType("application/json; charset=utf-8");  
						    PrintWriter out = null;  
						    JSONObject jsonObject = new JSONObject();
						    jsonObject.put("code", code);
						    jsonObject.put("message", messageReturn);
						    try {  
						        out = response.getWriter();  
						        out.append(jsonObject.toString()); 
						    } catch (IOException e) {  
						        e.printStackTrace();  
						    } finally {  
						        if (out != null) {  
						            out.close();  
						        }  
						    }  

						}
				 }else if("query".equals(cmd)){
					 	List<Order> orderList=orderService.getOrder();
						request.setAttribute("orderList", orderList);
						RequestDispatcher rd=request.getRequestDispatcher("/jsp/order.jsp");
					    rd.forward(request,response); 
				 }else if("update".equals(cmd)){
					 String orderString=request.getParameter("order");
					 List<Order> orderList=new ArrayList<Order>();
					 JSONArray jsonArray=new JSONArray(orderString);
					 if(jsonArray.length()>0){
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObject=jsonArray.getJSONObject(i);	
								Order order=new Order();
								order.setId(jsonObject.getString("id"));
								order.setPrice(jsonObject.getString("price"));	
								order.setNum(jsonObject.getString("num"));
								order.setGoodname(jsonObject.getString("goodname"));
								order.setMoney(String.valueOf(jsonObject.getInt("money")));		
								orderList.add(order);
							}
							//todo保存键盘
							JSONObject jsonObjectReturn=orderService.updateOrder(orderList);
							boolean codeReturn=jsonObjectReturn.getBoolean("code");
							String messageReturn=jsonObjectReturn.getString("message");
							
							String code="";
							
							if(codeReturn){
								code="1";
							}else if(!codeReturn){
								code="0";
							}
							response.setCharacterEncoding("UTF-8");  
						    response.setContentType("application/json; charset=utf-8");  
						    PrintWriter out = null;  
						    JSONObject jsonObject = new JSONObject();
						    jsonObject.put("code", code);
						    jsonObject.put("message", messageReturn);
						    try {  
						        out = response.getWriter();  
						        out.append(jsonObject.toString()); 
						    } catch (IOException e) {  
						        e.printStackTrace();  
						    } finally {  
						        if (out != null) {  
						            out.close();  
						        }  
						    }  

						}
				 }
				
				//设置用户
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e.getMessage());
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
