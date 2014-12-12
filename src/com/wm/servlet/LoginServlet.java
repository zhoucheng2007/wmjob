package com.wm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wm.constant.SessionConstant;
import com.wm.entity.ConfigNode;
import com.wm.entity.User;
import com.wm.service.ConfigNodeService;
import com.wm.service.LoginService;

@WebServlet(name="LoginServlet",urlPatterns="/LoginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
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

		String username=request.getParameter("username");
		String password=request.getParameter("password");
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

		String username=request.getParameter("username");
		String password=request.getParameter("password");
		User user;
		List<ConfigNode> configNodeList;
		LoginService loginService=new LoginService();	
		ConfigNodeService configNodeService=new ConfigNodeService();
		try {
			user=loginService.LoginByUsernamePassword(username, password);
			if(null!=user.getId()){
				//设置用户
				configNodeList=configNodeService.getConfigNode();
				request.getSession().setAttribute(SessionConstant.SESSION_USER, user);
				request.getSession().setAttribute(SessionConstant.CONFIG_NODE, configNodeList);
				//设置管理节点
				for(ConfigNode configNode:configNodeList){
					if("1".equals(configNode.getIsmanager())){
						request.getSession().setAttribute(SessionConstant.CONFIG_MANAGER_NODE, configNode);
					}
				}
				RequestDispatcher rd=request.getRequestDispatcher("/GoodsServlet");
			    rd.forward(request,response);
			}else{
				RequestDispatcher rd=request.getRequestDispatcher("/jsp/loginError.jsp");
			    rd.forward(request,response);			
			}
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
