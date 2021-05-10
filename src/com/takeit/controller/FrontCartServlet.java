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
import com.takeit.model.biz.CartBiz;
import com.takeit.model.dto.Cart;
import com.takeit.model.dto.MessageEntity;

/**
 * 장바구니 관리 컨트롤러
 * @author 	한소희
 * @since	jdk1.8
 * @version v2.0
 */
@WebServlet("/cartController")
public class FrontCartServlet extends HttpServlet {
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
			case "cartList":
				cartList(request,response);
				break;
			case "addCart":
				addCart(request,response);
				break;
			case "removeCart":
				removeCart(request,response);
				break;
			case "removeAllCart":
				removeAllCart(request,response);
				break;
			case "changeCartQty":
				changeCartQty(request,response);
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
		
		/**장바구니 전체 목록 조회*/
		protected void cartList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[dubug]장바구니 전체 목록 요청");
			HttpSession session = request.getSession(false);
			if(session == null || session.getAttribute("memberId") == null) {
				MessageEntity message = new MessageEntity("message" , 0);
				message.setLinkTitle("로그인");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			
			String memberId = (String)session.getAttribute("memberId");
			memberId = memberId.trim();
			System.out.println("[debug]" + memberId);
			
			
			CartBiz cbiz = new CartBiz();
			ArrayList<Cart> cartList = new ArrayList<Cart>(); 
			
			try {
				int cartTotalPrice =  0;
				cbiz.getCartList(memberId, cartTotalPrice, cartList);
				session.setAttribute("cartList", cartList);
				boolean isNotTakeit = false;
				for(Cart dto : cartList) {
					cartTotalPrice += dto.getTotalPrice();
					System.out.println("isTakeit:"+dto.getItemTakeit());
					if (dto.getItemTakeit().equals("F")) {
						isNotTakeit = true;
					}
				}
				
				if(cartTotalPrice < 50000 && cartTotalPrice > 0 && isNotTakeit) {
					session.setAttribute("cartTotalPrice", cartTotalPrice + 3500);
				} else if(cartTotalPrice == 0){
					session.setAttribute("cartTotalPrice", 0);
				} else {
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				}
				System.out.println("[debug] servlet 누적 총 결제 금액= " + (int)session.getAttribute("cartTotalPrice"));
				request.getRequestDispatcher("/item/cartList.jsp").forward(request, response);
			} catch (CommonException e) {
				MessageEntity message = new MessageEntity("error", 21);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
			
		}
		
		/**장바구니 등록*/
		protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[dubug]장바구니 등록 요청");
			HttpSession session  = request.getSession(false);

			if(session.getAttribute("memberId") == null) {
				MessageEntity message = new MessageEntity("message" , 0);

				message.setLinkTitle("로그인");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}

			String memberId = (String)session.getAttribute("memberId");
			String itemNo = request.getParameter("itemNo");
			int cartItemQty = Integer.parseInt(request.getParameter("cart-itemQty"));
			
			memberId = memberId.trim();
			itemNo = itemNo.trim();
			System.out.println("[debug]" + memberId + ", " + itemNo + ", " + cartItemQty );
			
			Cart cart = new Cart(memberId, itemNo, cartItemQty);
			CartBiz cbiz = new CartBiz();
			ArrayList<Cart> cartList = new ArrayList<Cart>();
			
			try {
				int result = cbiz.searchCartItem(itemNo, memberId);
				int cartTotalPrice =  0;
				if(result == 1) {
					cbiz.cartUpdate(cart);
				} else {
					cbiz.addCart(cart);
				}
				cbiz.getCartList(memberId, cartTotalPrice, cartList);
				boolean isNotTakeit = false;
				session.setAttribute("cartList", cartList);
				for(Cart dto : cartList) {
					cartTotalPrice += dto.getTotalPrice();
					if (dto.getItemTakeit().equals("F")) {
						isNotTakeit = true;
					}
				}
				
				if(cartTotalPrice < 50000 && cartTotalPrice > 0 && isNotTakeit) {
					session.setAttribute("cartTotalPrice", cartTotalPrice+3500);
				} else if(cartTotalPrice == 0){
					session.setAttribute("cartTotalPrice", 0);
				} else {
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				}
				request.getRequestDispatcher("/item/cartList.jsp").forward(request, response);
			} catch (CommonException e) {
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}
		
		/**장바구니 삭제*/
		protected void removeCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[dubug]장바구니 삭제 요청");
			HttpSession session  = request.getSession(false);
			String memberId = (String)session.getAttribute("memberId");
			String itemNo = request.getParameter("itemNo");
			memberId = memberId.trim();
			itemNo = itemNo.trim();
			
			System.out.println("[debug]" + memberId + ", " + itemNo );
			if(session.getAttribute("memberId") == null) {
				MessageEntity message = new MessageEntity("message", 0);
				message.setLinkTitle("로그인");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			
			CartBiz cbiz = new CartBiz();
			ArrayList<Cart> cartList = new ArrayList<Cart>();
			
			try {
				int cartTotalPrice =  0;
				cbiz.removeCart(memberId, itemNo); 
				cbiz.getCartList(memberId, cartTotalPrice, cartList); 
				session.setAttribute("cartList", cartList);
				for(Cart dto : cartList) {
					cartTotalPrice += dto.getTotalPrice();
				}
				if(cartTotalPrice < 50000 && cartTotalPrice > 0) {
					session.setAttribute("cartTotalPrice", cartTotalPrice+3500);
				} else if(cartTotalPrice == 0){
					session.setAttribute("cartTotalPrice", 0);
				} else {
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				}
				request.getRequestDispatcher("/item/cartList.jsp").forward(request, response);
			} catch (CommonException e) {
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
			
		}
		
		/**장바구니  전체 삭제*/
		protected void removeAllCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[dubug]장바구니 전체 삭제 요청");
			HttpSession session  = request.getSession(false);
			String memberId = (String)session.getAttribute("memberId");
			memberId = memberId.trim();
			
			System.out.println("[debug]" + memberId );
			if(session.getAttribute("memberId") == null) {
				MessageEntity message = new MessageEntity("message", 0);
				message.setLinkTitle("로그인");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}
			
			CartBiz cbiz = new CartBiz();
			ArrayList<Cart> cartList = new ArrayList<Cart>();
			try {
				int cartTotalPrice =  0;
				cbiz.removeAllCart(memberId); 
				cbiz.getCartList(memberId, cartTotalPrice, cartList); 
				session.setAttribute("cartList", cartList);
				for(Cart dto : cartList) {
					cartTotalPrice += dto.getTotalPrice();
				}
				if(cartTotalPrice < 50000 && cartTotalPrice > 0) {
					session.setAttribute("cartTotalPrice", cartTotalPrice+3500);
				} else if(cartTotalPrice == 0){
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				} else {
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				}
				
				request.getRequestDispatcher("/item/cartList.jsp").forward(request, response);
			} catch (CommonException e) {
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
			
		}
		
		/**장바구니 변경*/
		protected void changeCartQty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[dubug]장바구니 수량 변경 요청");
			HttpSession session  = request.getSession(false);

			if(session.getAttribute("memberId") == null) {
				MessageEntity message = new MessageEntity("message" , 0);

				message.setLinkTitle("로그인");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}

			String memberId = (String)session.getAttribute("memberId");
			String itemNo = request.getParameter("itemNo");
			int changeCartItemQty = Integer.parseInt(request.getParameter("change-cart-itemQty"));
			
			memberId = memberId.trim();
			itemNo = itemNo.trim();
			System.out.println("[debug]" + memberId + ", " + itemNo + ", " + changeCartItemQty );
			
			Cart cart = new Cart(memberId, itemNo, changeCartItemQty);
			CartBiz cbiz = new CartBiz();
			ArrayList<Cart> cartList = new ArrayList<Cart>();
			
			try {
				int cartTotalPrice =  0;
				cbiz.changeCartQty(cart);
				cbiz.getCartList(memberId, cartTotalPrice, cartList);

				session.setAttribute("cartList", cartList);
				for(Cart dto : cartList) {
					cartTotalPrice += dto.getTotalPrice();
				}
				if(cartTotalPrice < 50000 && cartTotalPrice > 0) {
					session.setAttribute("cartTotalPrice", cartTotalPrice+3500);
				} else if(cartTotalPrice == 0){
					session.setAttribute("cartTotalPrice", 0);
				} else {
					session.setAttribute("cartTotalPrice", cartTotalPrice);
				}
				request.getRequestDispatcher("/item/cartList.jsp").forward(request, response);
			} catch (CommonException e) {
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}

}
