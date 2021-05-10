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
import com.takeit.model.biz.OrderBiz;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Order;
import com.takeit.model.dto.OrderDetail;
import com.takeit.model.dto.Shipping;

/**
 * 주문 요청 컨트롤러
 * @author 김태경
 */
@WebServlet("/order/orderController")
public class FrontOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletContext application;
	public String CONTEXT_PATH;
	
	public void init() {
		application = getServletContext();
		CONTEXT_PATH = application.getContextPath();
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		switch (action) {
		case "order":
			order(request, response);
			break;
		case "sellerOrderList":
			sellerOrderList(request, response);
			break;
		case "memberOrderList":
			memberOrderList(request, response);
			break;
		case "orderCancelRequest":
			orderCancelRequest(request, response);
			break;
		case "orderCancel":
			orderCancel(request, response);
			break;
		case "updateShipStatusForm":
			updateShipStatusForm(request, response);
			break;
		case "updateShipStatus":
			updateShipStatus(request, response);
			break;
		case "orderForm":
			orderForm(request, response);
			break;
		default:
			response.sendRedirect(CONTEXT_PATH + "/index");
			break;
		}
	}
	
	/** 주문 등록 화면 요청 서비스 */
	protected void orderForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String memberId = (String)session.getAttribute("memberId");
		
		String _orderPrice = request.getParameter("cartTotalPrice");
		String[] itemNos = request.getParameterValues("itemNo");
		String[] itemQtys = request.getParameterValues("itemQty");
		String[] itemPayPrices = request.getParameterValues("itemPrice");
		
		Order order = new Order();
		ArrayList<OrderDetail> orderDetails = new ArrayList<>();
		OrderDetail orderDetail = null;
		for (int index = 0; index < itemNos.length; index++) {
			orderDetail = new OrderDetail();
			orderDetail.setItemNo(itemNos[index]);
			orderDetail.setItemQty(Integer.valueOf(itemQtys[index]));
			orderDetail.setItemPayPrice(Integer.valueOf(itemPayPrices[index]));
			orderDetails.add(orderDetail);
		}
		int orderPrice = Integer.valueOf(_orderPrice);
		
		order.setOrderDetails(orderDetails);
		order.setOrderPrice(orderPrice);
		order.setMemberId(memberId);
		
		OrderBiz biz = new OrderBiz();
		try {
			biz.getOrderItem(order);
			
			request.setAttribute("order", order);
			request.getRequestDispatcher("/order/order.jsp").forward(request, response);
		} 
		catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}
	
	/** 일반회원 주문등록 요청 서비스 */
	protected void order(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if (session == null || session.getAttribute("memberId") == null) {
			MessageEntity message = new MessageEntity("message", 1);
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		String memberId = (String)session.getAttribute("memberId"); 
		String receiveMethod = request.getParameter("receiveMethod");
		String shipStatusCode = null;
		if (receiveMethod.equals("배송")) {
			shipStatusCode = "O-GET";
		}
		
		String[] itemNos= request.getParameterValues("itemNo");
		String[] itemQtys = request.getParameterValues("itemQty");
		String[] itemPayPrices = request.getParameterValues("itemPayPrice");
		String[] itemTakeits = request.getParameterValues("itemTakeit");
		String[] sellerIds = request.getParameterValues("sellerId");
		
		String _orderPrice = request.getParameter("orderPrice");
		int orderPrice = Integer.valueOf(_orderPrice);
		
		String recipientName = request.getParameter("recipientName");
		String recipientMobile = request.getParameter("recipientMobile");
		String recipientAddr = request.getParameter("recipientAddr");
		String recipientAddrDetail = request.getParameter("recipientAddrDetail");
		String recipientPostNo = request.getParameter("recipientPostNo");
		String shipRequest = request.getParameter("shipRequest");
		
		ArrayList<Order> orderList = new ArrayList<>();
		Order order = null;
		ArrayList<OrderDetail> orderDetails = null;
		OrderDetail orderDetail = null;
		for (int index = 0; index < itemNos.length; index++) {
			System.out.println(itemNos[index]);
			System.out.println(sellerIds[index]);
			System.out.println(itemPayPrices[index]);
			System.out.println(itemQtys[index]);
			
			order = new Order();
			orderDetails = new ArrayList<>();
			orderDetail = new OrderDetail();
			for (Order dto : orderList) {
				if (dto.getOrderDetails().get(0).getSellerId().equals(sellerIds[index])) {
					orderDetails = dto.getOrderDetails();
					orderDetail.setSellerId(sellerIds[index]);
					orderDetail.setItemNo(itemNos[index]);
					orderDetail.setItemQty(Integer.valueOf(itemQtys[index]));
					orderDetail.setItemPayPrice(Integer.valueOf(itemPayPrices[index]));
					orderDetail.setItemTakeit(itemTakeits[index]);
					orderDetails.add(orderDetail);
					break;
				}
			}
			if (orderDetails.isEmpty()) {
				orderDetail.setSellerId(sellerIds[index]);
				orderDetail.setItemNo(itemNos[index]);
				orderDetail.setItemQty(Integer.valueOf(itemQtys[index]));
				orderDetail.setItemPayPrice(Integer.valueOf(itemPayPrices[index]));
				orderDetail.setItemTakeit(itemTakeits[index]);
				orderDetails.add(orderDetail);
				
				order.setOrderDetails(orderDetails);
				
				order.setOrderPrice(orderPrice);
				order.setMemberId(memberId);
				order.setReceiveMethod(receiveMethod);
				order.setRecipientName(recipientName);
				order.setRecipientPostNo(recipientPostNo);
				order.setRecipientAddr(recipientAddr);
				order.setRecipientAddrDetail(recipientAddrDetail);
				
				order.setRecipientMobile(recipientMobile);
				order.setShipRequest(shipRequest);
				order.setShipStatusCode(shipStatusCode);
				
				orderList.add(order);
			} 
		}
		OrderBiz biz = new OrderBiz();
		try {
			biz.addOrder(orderList);
			response.sendRedirect(CONTEXT_PATH + "/member/myPage.jsp");
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}
	
	/** 배송상태변경 요청 서비스 */
	protected void updateShipStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String orderNo = request.getParameter("orderNo");
		String shipStatusCode = request.getParameter("shipStatusCode");
		
		orderNo = orderNo.trim();
		shipStatusCode = shipStatusCode.trim();
		
		OrderBiz biz = new OrderBiz();
		
		try {
			biz.updateShipStatusCode(orderNo,shipStatusCode);
			response.getWriter().write("success");
			
		} catch (CommonException e) {
			response.getWriter().write("failed");
			return;
		}
	}
	
	/**	배송상태변경 화면 요청 서비스 */
	protected void updateShipStatusForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		ArrayList<Shipping> shippingList = new ArrayList<Shipping>();
		OrderBiz biz = new OrderBiz();
		
		try {
			biz.getShippingList(shippingList);
			
			request.setAttribute("shippingList", shippingList);
			request.getRequestDispatcher("/order/shipStatusUpdateForm.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}	
	}
	
	/** 판매자회원의 주문취소 요청 서비스 */
	protected void orderCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String orderNo = request.getParameter("orderNo");
		
		if (orderNo == null || orderNo.trim().length() == 0) {
			MessageEntity message = new MessageEntity("error", 12);
			message.setLinkTitle("판매목록");
			message.setUrl(CONTEXT_PATH + "/order/orderController?action=sellerOrderList");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		orderNo = orderNo.trim();
		
		OrderBiz biz = new OrderBiz();
		try {
			biz.orderCancel(orderNo);
			response.getWriter().write("success");
			
		} catch (CommonException e) {
			response.getWriter().write("failed");
			return;
		}
	}
	
	/** 일반회원의 주문취소요청 요청 서비스 */
	protected void orderCancelRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String orderNo = request.getParameter("orderNo");
		
		if (orderNo == null || orderNo.trim().length() == 0) {
			MessageEntity message = new MessageEntity("error", 12);
			message.setLinkTitle("마이페이지");
			message.setUrl(CONTEXT_PATH + "/member/myPage.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		orderNo = orderNo.trim();
		
		OrderBiz biz = new OrderBiz();
		try {
			biz.orderCancelRequest(orderNo);
			response.getWriter().write("success");
		} catch (CommonException e) {
			response.getWriter().write("failed");
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("주문내역");
			message.setUrl(CONTEXT_PATH + "/order/orderController?action=memberOrderList");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}

	/** 일반회원의 주문목록 요청 서비스 */
	protected void memberOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String memberId = (String)session.getAttribute("memberId");
		
		OrderBiz biz = new OrderBiz();
		ArrayList<Order> orderList = new ArrayList<>();
		
		try {
			biz.getMemberOrderList(memberId, orderList);
			
			if(!orderList.isEmpty()){
				request.setAttribute("orderList", orderList);
				request.getRequestDispatcher("/order/memberOrderList.jsp").forward(request, response);
			}else {
				response.sendRedirect("/takeit/order/noOrderList.jsp");
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	
	/** 판매자회원의 주문 목록 요청 서비스 */
	protected void sellerOrderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("dto") == null) {
			MessageEntity message = new MessageEntity("message", 0);
			message.setLinkTitle("로그인");
			message.setUrl(CONTEXT_PATH + "/member/memberLogin.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		String sellerId = (String)session.getAttribute("sellerId");
		
		OrderBiz biz = new OrderBiz();
		ArrayList<Order> orderList = new ArrayList<>();
		
		try {
			biz.getSellerOrderList(sellerId, orderList);
			
			request.setAttribute("orderList", orderList);
			request.getRequestDispatcher("/order/sellerOrderList.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
}
