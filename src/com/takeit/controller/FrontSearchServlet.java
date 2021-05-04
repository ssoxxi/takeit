package com.takeit.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.takeit.common.CommonException;
import com.takeit.model.biz.BoardBiz;
import com.takeit.model.biz.SearchBiz;
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Category;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;

/**
 * 게시판 관리 컨트롤러
 */
@WebServlet("/searchController")
public class FrontSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	//서버 구동시에 해당 어플리케이션당 한 개의 환경설정, 모든 서블릿(jsp)공유객체, 서버 종료시까지 사용
	public ServletContext application;
	public String CONTEXT_PATH;
	
	public void init() {
		application = getServletContext();
		CONTEXT_PATH = (String) application.getAttribute("CONTEXT_PATH");	
	}	   

	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		request.setCharacterEncoding("utf-8");
		switch(action) {
		case "searchList":
			searchList(request,response);
			break;
//		case "":
//			(request,response);
//			break;
//		case "":
//			(request,response);
//			break;
//		case "":
//			(request,response);
//			break;
			
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	/**검색 결과 전체 목록*/
	protected void searchList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[debug]검색결과 조회 요청");
		String searchInput = request.getParameter("searchInput");
		searchInput = searchInput.trim();
		
		System.out.println("[debug] " + searchInput);
		
		ArrayList<Item> searchList = new ArrayList<Item>();
		SearchBiz sbiz = new SearchBiz();
		
		try {
			sbiz.getSearchList(searchList, searchInput);
			request.setAttribute("searchList", searchList);
			request.getRequestDispatcher("/searchList.jsp").forward(request, response);
		} catch (CommonException e) {
			// TODO: handle exception
		}
	}
	
	
}
