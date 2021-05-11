package com.takeit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.takeit.common.CommonException;
import com.takeit.model.biz.ItemBiz;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;


/**
 * 카테고리에 따른 상품관리를 위한 FrontController servlet
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
@WebServlet("/categoryController")
public class FrontCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
		public ServletContext application;
		public String CONTEXT_PATH;
		
		public void init() {
			application = getServletContext();
			CONTEXT_PATH = application.getContextPath();
			System.out.println("[loadOnStartup]CONTEXT_PATH : " + CONTEXT_PATH);
			application.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
		}
		

		protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
			request.setCharacterEncoding("utf-8");
			switch(action) {
			case "categoryList":
				categoryList(request,response);
				break;
			default:
				response.sendRedirect(CONTEXT_PATH + "/index");
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}
	

	protected void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[debug]카테고리별 상품 목록 요청");
		String categoryNo = request.getParameter("categoryNo");
		categoryNo = categoryNo.trim();
		System.out.println("[debug] 카테고리: "+categoryNo);
		
		ArrayList<Item> categoryItemList = new ArrayList<Item>();
		ItemBiz ibiz = new ItemBiz();
		
		String categoryName = null;
		try {
			ibiz.getCategoryItemList(categoryItemList, categoryNo, categoryName);
			request.setAttribute("categoryItemList",categoryItemList);
			request.getRequestDispatcher("/item/itemList.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 8);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	
	}
	
		
}
