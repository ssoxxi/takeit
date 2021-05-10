package com.takeit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.takeit.common.CommonException;
import com.takeit.model.biz.IndexBiz;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Review;

/**
 * index 시작페이지 요청 서블릿
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[debug] takeit 시작 페이지 요청");
		request.setCharacterEncoding("utf-8");
		
		/**신상품 리스트*/
		ArrayList<Item> itemList = new ArrayList<Item>();
		/**후기리스트*/
		ArrayList<Review> reviewList = new ArrayList<Review>();
		
		IndexBiz ibiz = new IndexBiz();
		
		try {
			ibiz.getNewItemList(itemList);
			ibiz.getBestReviewList(reviewList);
			
			request.setAttribute("itemList",itemList);
			request.setAttribute("reviewList",reviewList);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 24);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}


}
