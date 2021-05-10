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
import com.takeit.model.biz.SearchBiz;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Search;

/**
 * 게시판 관리 컨트롤러
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
@WebServlet("/searchController")
public class FrontSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
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
		default:
			response.sendRedirect(CONTEXT_PATH + "/index");
			break;
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
		
		ArrayList<Search> searchList = new ArrayList<Search>();
		SearchBiz sbiz = new SearchBiz();
		
		try {
			sbiz.getSearchList(searchList, "%"+searchInput+"%");
			request.setAttribute("searchList", searchList);
			request.setAttribute("searchInput", searchInput);
			request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 9);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	
	
}
