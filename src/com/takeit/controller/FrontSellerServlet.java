package com.takeit.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.takeit.common.CommonException;
import com.takeit.model.biz.SellerBiz;
import com.takeit.model.biz.TakeitBiz;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Seller;
import com.takeit.model.dto.ShopLoc;

/**
 * 판매자 회원관리 컨트롤러
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
@WebServlet(urlPatterns = {"/seller/controller"})
public class FrontSellerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ServletContext application;
	public String CONTEXT_PATH;
	public String imgPath;
	
	public void init() {
		application = getServletContext();
		CONTEXT_PATH = (String) application.getAttribute("CONTEXT_PATH");	
		imgPath = (String)application.getAttribute("imgPath");
	}	 
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getParameter("action");
		
		switch(action) {
		case "sellerInputForm":
			sellerInputForm(request, response);
			break;
		case "sellerInput":
			sellerInput(request, response);
			break;
		case "sellerLogin":
			sellerLogin(request, response);
			break;
		case "sellerLogout":
			sellerLogout(request, response);
			break;
		case "sellerFindId":
			sellerFindId(request, response);
			break;
		case "sellerFindPw":
			sellerFindPw(request, response);
			break;
		case "sellerIdChk":
			sellerIdChk(request, response);
			break;
		case "sellerEmailChk":
			sellerEmailChk(request, response);
			break;
		case "shopNameChk":
			shopNameChk(request, response);
			break;
		case "sellerNoChk":
			sellerNoChk(request, response);
			break;
		default:
			response.sendRedirect(CONTEXT_PATH + "/index");
			break;
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * 회원가입 폼
	 */
	protected void sellerInputForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TakeitBiz takeitBiz = new TakeitBiz();
		ArrayList<ShopLoc> shopLocList = new ArrayList<>();
		
		SellerBiz sellerBiz = new SellerBiz();
		ArrayList<Seller> shopCategoryList = new ArrayList<Seller>();
		
		try {
			takeitBiz.getShopLocList(shopLocList);
			sellerBiz.shopCategoryList(shopCategoryList);
			
			request.setAttribute("shopLocList", shopLocList);
			request.setAttribute("shopCategoryList", shopCategoryList);
			request.getRequestDispatcher("/seller/sellerInput.jsp").forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			message.setLinkTitle("메인으로");
			message.setUrl(CONTEXT_PATH + "/index");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		

	
	}
	
	/**
	 * 회원가입
	 */
	protected void sellerInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동확인 : sellerInput");
		
		String directory = imgPath+"/takeit/img/seller";
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		System.out.println(directory);
		MultipartRequest multi
		= new MultipartRequest(request, directory, maxSize, encoding,
				new DefaultFileRenamePolicy());
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");

		String sellerId = multi.getParameter("sellerId");
		String sellerPw = multi.getParameter("sellerPw");
		String name = multi.getParameter("name");
		String mobile = multi.getParameter("mobile");
		String email = multi.getParameter("email");
		String postNo = multi.getParameter("postNo");
		String address = multi.getParameter("address");
		String addressDetail = multi.getParameter("addressDetail");
		String shopMobile = multi.getParameter("shopMobile");
		String sellerNo = multi.getParameter("sellerNo");
		String shopName = multi.getParameter("shopName");
		String shopKakaoId = multi.getParameter("shopKakaoId");
		String shopImg = multi.getFilesystemName("shopImg");
		String shopCategoryNo = multi.getParameter("shopCategoryNo");
		String shopLocCode = multi.getParameter("shopLocCode");

		System.out.println(sellerId + sellerPw + name + mobile + email + postNo + address + addressDetail
				+ shopMobile + sellerNo + shopName + shopKakaoId + shopImg + shopCategoryNo + shopLocCode);
		
		Seller dto = new Seller(sellerId, sellerPw, name, mobile, email, postNo, address, addressDetail, sellerNo, shopMobile, shopName, shopKakaoId, shopImg, shopCategoryNo, shopLocCode);
		
		SellerBiz biz = new SellerBiz();
		
		try {
			biz.addSeller(dto);
			MessageEntity message = new MessageEntity("success", 0);
			message.setUrl("/takeit/seller/sellerLogin.jsp");
			message.setLinkTitle("로그인");
			request.setAttribute("message", message);
			rd.forward(request, response);
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	/**
	 * 로그인
	 */
	protected void sellerLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동확인 : sellerLogin");
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String sellerId = request.getParameter("sellerId");
		String sellerPw = request.getParameter("sellerPw");
		
		System.out.println(sellerId);
		System.out.println(sellerPw);
		
		Seller dto = new Seller();
		
		SellerBiz biz = new SellerBiz();
		
		dto.setSellerId(sellerId);
		dto.setSellerPw(sellerPw);
		
		try {
			biz.login(dto);
			
			if(dto.getAddress() != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("sellerId", sellerId); 
				session.setAttribute("dto", dto);
				response.sendRedirect("/takeit/index");
			}else {
				MessageEntity message = new MessageEntity("error", 34);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/seller/sellerLogin.jsp");
				request.setAttribute("message", message);
				rd.forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}

	/**
	 * 로그아웃
	 */
	protected void sellerLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("작동확인 : sellerLogout");
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		HttpSession session = request.getSession(false);
		
		session.removeAttribute("dto");
		
		session.invalidate();
	
		MessageEntity message = new MessageEntity("success", 2);
		message.setUrl("/takeit/index");
		message.setLinkTitle("처음으로");
		request.setAttribute("message", message);
		rd.forward(request, response);
	}
	
	/**
	 * 아이디 찾기
	 */
	protected void sellerFindId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동 확인 : sellerFindId");

		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		System.out.println(name + email);
		
		SellerBiz biz = new SellerBiz();
		Seller dto = new Seller();

		dto.setName(name);
		dto.setEmail(email);
		
		try {
			biz.idFind(dto);
			if(dto.getSellerId() != null){
				request.setAttribute("idInfo", dto.getSellerId());
				request.setAttribute("entryDate", dto.getEntryDate());
				request.getRequestDispatcher("/member/idFindMessage.jsp").forward(request, response);;
			}else {
				MessageEntity message = new MessageEntity("error", 35);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/seller/sellerFindId.jsp");
				request.setAttribute("message", message);
				rd.forward(request, response);
			}
		}catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	/**
	 * 비밀번호 찾기
	 */
	protected void sellerFindPw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("작동 확인 : sellerFindPw");

		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String sellerId = request.getParameter("sellerId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		System.out.println(sellerId);
		System.out.println(name);
		System.out.println(email);

		SellerBiz biz = new SellerBiz();
		Seller dto = new Seller();

		dto.setSellerId(sellerId);
		dto.setName(name);
		dto.setEmail(email);

		try {
			biz.pwFind("dto", dto);
			if(dto.getSellerPw() != null) {
				request.setAttribute("pwInfo", dto.getSellerPw());
				request.getRequestDispatcher("/member/pwFindMessage.jsp").forward(request, response);
			}else {
				MessageEntity message = new MessageEntity("error", 36);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/seller/sellerFindPw.jsp");
				request.setAttribute("message", message);
				rd.forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	/**
	 * 아이디 중복체크
	 */
	protected void sellerIdChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String sellerId = request.getParameter("sellerId");
		
		SellerBiz biz = new SellerBiz();

		try {
			int result = biz.idCheck(sellerId);
			if(result == 1) {
				response.getWriter().write("1");
			}else {
				response.getWriter().write("0");
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	/**
	 * 이메일 중복체크
	 */
	protected void sellerEmailChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		SellerBiz biz = new SellerBiz();
	
		try {
			int result = biz.emailCheck(email);
			if(result == 1) {
				response.getWriter().write("1");
			}else {
				response.getWriter().write("0");
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}	
	}

	/**
	 * 사업자등록번호 중복체크
	 */
	protected void sellerNoChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sellerNo = request.getParameter("sellerNo");
		
		SellerBiz biz = new SellerBiz();
		
		try {
			int result = biz.sellerNoChk(sellerNo);
			if(result == 1) {
				response.getWriter().write("1");
			}else {
				response.getWriter().write("0");
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}	
	}

	/**
	 * 상점명 중복체크
	 */
	protected void shopNameChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String shopName = request.getParameter("shopName");
		
		SellerBiz biz = new SellerBiz();
		
		try {
			int result = biz.shopNameChk(shopName);
			if(result == 1) {
				response.getWriter().write("1");
			}else {
				response.getWriter().write("0");
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}	
	}
}
