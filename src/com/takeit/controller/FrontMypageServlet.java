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
import com.takeit.model.biz.MypageBiz;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Seller;

/**
 * 마이페이지 관리 컨트롤러
 * @author 심선경
 * @since jdk1.8
 * @version v2.0
 */
@WebServlet("/member/mypageController")
public class FrontMypageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	public ServletContext application;
	public String CONTEXT_PATH;
	public String imgPath;
	public void init() {
		application = getServletContext();
		CONTEXT_PATH = (String) application.getAttribute("CONTEXT_PATH");
		String imgPath = "C:/student_ucamp33/apps_down/05.tomcat/apache-tomcat-8.5.64/webapps";
		application.setAttribute("imgPath", imgPath);
	}	
	
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		request.setCharacterEncoding("utf-8");
		switch(action) {
		case "memberInfoForm":
			memberInfoForm(request, response);
			break;
		case "setMemberInfo":
			setMemberInfo(request, response);
			break;
		case "sellerInfoForm":
			sellerInfoForm(request, response);
			break;
		case "setSellerInfo":
			setSellerInfo(request, response);
			break;
		case "removeMember":
			removeMember(request,response);
			break;
		case "removeMemberForm":
			removeMemberForm(request,response);
			break;
		case "removeSeller":
			removeSeller(request,response);
			break;
		case "memberPwUpdateForm":
			memberPwUpdateForm(request,response);
			break;
		case "setMemberPw":
			setMemberPw(request,response);
			break;
		case "setSellerPw":
			setSellerPw(request,response);
			break;
		case "memberList":
			memberList(request,response);
			break;
		case "sellerList":
			sellerList(request,response);
			break;
		case "AceRemoveMember":
			AceRemoveMember(request,response);
			break;	
		case "AceRemoveSeller":
			AceRemoveSeller(request,response);
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
	
	/**
	 * 판매자 회원 전체 조회 서비스
	 */
	protected void sellerList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		ArrayList<Seller> sellerList = new ArrayList<Seller>();
		MypageBiz biz = new MypageBiz();
		
		
		try {
			biz.getSellerList(sellerList);
			
			System.out.println(sellerList);
			
			session.setAttribute("sellerList", sellerList);
			request.getRequestDispatcher("/member/sellerList.jsp").forward(request, response);
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}
	}
	
	/**
	 * 일반회원 전체 조회 서비스
	 */
	protected void memberList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		ArrayList<Member> memberList = new ArrayList<Member>();
		MypageBiz biz = new MypageBiz();
		
		try {
			biz.getMemberList(memberList);
			
			System.out.println(memberList);
			
			session.setAttribute("memberList", memberList);
			request.getRequestDispatcher("/member/memberList.jsp").forward(request, response);
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}
	}
	
	
	
	/**
	 * 회원 탈퇴 요청 페이지 서비스
	 */
	protected void removeMemberForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.getRequestDispatcher("/member/memberRemove.jsp").forward(request, response);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	} 
		
		
	/**
	 * 회원 탈퇴 판매자  서비스
	 */
	protected void removeSeller (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		String sellerId = request.getParameter("sellerId");
		String sellerPw = request.getParameter("sellerPw");
		
		if(sellerId == null || sellerId.trim().length()  == 0 ) {

			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(sellerPw == null || sellerPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		sellerId = sellerId.trim();
		sellerPw = sellerPw.trim();
		
		MypageBiz biz = new MypageBiz();
		
		
		try {
			biz.removeSeller(sellerId,sellerPw);
			
			message = new MessageEntity("success", 4);
			message.setLinkTitle("메인화면으로");
			message.setUrl("/takeit/index");
			
			if(session != null) {
				if(session.getAttribute("seller") != null) {
					session.removeAttribute("seller");
				}
				if(session.getAttribute("sellerId") != null) {
					session.removeAttribute("sellerId");
				}
				if(session.getAttribute("dto") != null) {
					session.removeAttribute("dto");
				}
			
				session.invalidate();
			
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	/**
	 * 비밀번호 변경 요청 페이지 서비스
	 */
	protected void memberPwUpdateForm (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);

		try {
			session.getAttribute("member");
			session.getAttribute("seller");
			request.getRequestDispatcher("/member/memberPwUpdate.jsp").forward(request, response);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	} 
	
	
	/**
	 * 비밀번호 변경 판매자 서비스
	 */
	protected void setSellerPw (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		String sellerId = (String)session.getAttribute("sellerId");
		String sellerPw = request.getParameter("sellerPw");
		String sellerPw2 = request.getParameter("sellerPw2");
		
		MessageEntity message = null;
		
		if(sellerId == null || sellerId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(sellerPw == null || sellerPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(sellerPw2 == null || sellerPw2.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		sellerId = sellerId.trim();
		sellerPw = sellerPw.trim();
		sellerPw2= sellerPw2.trim();
		
		MypageBiz biz = new MypageBiz();
		
		Seller dto = new Seller();
		dto.setSellerId(sellerId);
		dto.setSellerPw(sellerPw);
		
		
		try {
			biz.setSellerPw(sellerPw2, dto);
			message = new MessageEntity("success", 3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
			
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	/**
	 * 판매자 정보 수정 서비스
	 */
	protected void setSellerInfo (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		String sellerId = (String)session.getAttribute("sellerId");
		String sellerPw = request.getParameter("sellerPw");
		String name = request.getParameter("name");
		String sellerNo = request.getParameter("sellerNo");
		String shopName = request.getParameter("shopName");
		String shopMobile = request.getParameter("shopMobile");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String shopCategoryNo = request.getParameter("shopCategoryNo");
		String shopKakaoId = request.getParameter("shopKakaoId");
		String postNo = request.getParameter("postNo");
		String address = request.getParameter("address");
		String addressDetail = request.getParameter("addressDetail");
		
		
		if(sellerId == null || sellerId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(sellerPw == null || sellerPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(name == null || name.trim().length() ==0) {


			message = new MessageEntity("validation",2);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		
		if(postNo == null || postNo.trim().length() == 0) {

			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기 우편번호");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(address == null || address.trim().length() ==0) {

			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기 주소");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(addressDetail == null || addressDetail.trim().length() ==0) {
				

			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기 상세주소");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(mobile == null || mobile.trim().length() == 10) {


			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(email == null || email.trim().length() ==0) {


			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		
		if(sellerNo == null || sellerNo.trim().length() ==0) {

			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(shopName == null || shopName.trim().length() ==0) {

			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(shopMobile == null || shopMobile.trim().length() ==0) {

			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(shopCategoryNo == null || shopCategoryNo.trim().length() ==0) {

			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		
		sellerId = sellerId.trim();
		sellerPw = sellerPw.trim();
		name = name.trim();
		mobile = mobile.trim();
		email = email.trim();
		postNo = postNo.trim();
		address = address.trim();
		addressDetail = addressDetail.trim();
		shopCategoryNo = shopCategoryNo.trim();
		shopMobile = shopMobile.trim();
		shopName = shopName.trim();
		sellerNo = sellerNo.trim();
		
		
		MypageBiz biz = new MypageBiz();
		
		Seller dto = new Seller();
		dto.setSellerId(sellerId);
		dto.setSellerPw(sellerPw);
		dto.setName(name);
		dto.setSellerNo(sellerNo);
		dto.setShopName(shopName);
		dto.setShopMobile(shopMobile);
		dto.setMobile(mobile);
		dto.setEmail(email);
		dto.setShopCategoryNo(shopCategoryNo);
		dto.setShopKakaoId(shopKakaoId);
		dto.setPostNo(postNo);
		dto.setAddress(address);
		dto.setAddressDetail(addressDetail);
		
		try {
			
			biz.sellerInfoUpdate(dto);
			
			session.getAttribute("seller");
			message = new MessageEntity("success", 3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=sellerInfoForm");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	
	
	/**
	 * 판매자 정보 조회 서비스
	 */
	protected void sellerInfoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		String sellerId = (String)session.getAttribute("sellerId");
		
		if(sellerId == null || sellerId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		sellerId = sellerId.trim();
		
 		Seller dto = new Seller();
		dto.setSellerId(sellerId);
		
		MypageBiz biz = new MypageBiz();
		
		
		try {
			biz.sellerDetail(dto);	
			session.setAttribute("seller", dto);
			request.getRequestDispatcher("/member/sellerInfo.jsp").forward(request, response);
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	}
	
	/**
	 * 괸리자 > 회원 탈퇴 일반회원서비스
	 */
	protected void AceRemoveMember (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MessageEntity message = null;
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		if(memberId == null || memberId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("회원 목록으로 ");
			message.setUrl("/takeit/member/mypageController?action=memberList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(memberPw == null || memberPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("회원 목록으로 ");
			message.setUrl("/takeit/member/mypageController?action=memberList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		memberId = memberId.trim();
		memberPw = memberPw.trim();
		
		MypageBiz mybiz = new MypageBiz();
		
		try {
			mybiz.removeMember(memberId,memberPw);
			
			message = new MessageEntity("success", 4);
			message.setLinkTitle("회원목록으로");
			message.setUrl("/takeit/member/mypageController?action=memberList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	/**
	 * 괸리자 > 회원 탈퇴 판매자 서비스
	 */
	protected void AceRemoveSeller (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MessageEntity message = null;
		
		String sellerId = request.getParameter("sellerId");
		String sellerPw = request.getParameter("sellerPw");
		
		if(sellerId == null || sellerId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("회원 목록으로 ");
			message.setUrl("/takeit/member/mypageController?action=memberList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(sellerPw == null || sellerPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("회원 목록으로 ");
			message.setUrl("/takeit/member/mypageController?action=memberList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		sellerId = sellerId.trim();
		sellerPw = sellerPw.trim();
		
		MypageBiz mybiz = new MypageBiz();
		
		try {
			mybiz.removeSeller(sellerId,sellerPw);
			
			message = new MessageEntity("success", 4);
			message.setLinkTitle("회원목록으로");
			message.setUrl("/takeit/member/mypageController?action=sellerList");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	
	/**
	 * 회원 탈퇴 일반회원 서비스
	 */
	protected void removeMember (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		MessageEntity message = null;
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		if(memberId == null || memberId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(memberPw == null || memberPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		memberId = memberId.trim();
		memberPw = memberPw.trim();
		
		MypageBiz mybiz = new MypageBiz();
		
		
		try {
			mybiz.removeMember(memberId,memberPw);
			
			message = new MessageEntity("success", 4);
			message.setLinkTitle("메인화면으로");
			message.setUrl("/takeit/index");
			
			if(session != null) {
				if(session.getAttribute("member") != null) {
					session.removeAttribute("member");
				}
				if(session.getAttribute("memberId") != null) {
					session.removeAttribute("memberId");
				}
				if(session.getAttribute("dto") != null) {
					session.removeAttribute("dto");
				}
				
				session.invalidate();
			
			}
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
	
	/**
	 * 비밀번호 변경 일반회원 서비스
	 */
	protected void setMemberPw (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		MessageEntity message = null;
		
		String memberId = (String)session.getAttribute("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberPw2 = request.getParameter("memberPw2");
		
		
		if(memberId == null || memberId.trim().length()  == 0 ) {

			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(memberPw == null || memberPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(memberPw2 == null || memberPw2.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		memberId = memberId.trim();
		memberPw = memberPw.trim();
		memberPw2 = memberPw2.trim();
		
		
		MypageBiz biz = new MypageBiz();
		
		
		Member dto = new Member();
		dto.setMemberId(memberId);
		dto.setMemberPw(memberPw);
		
		try {
			biz.setMemberPw(memberPw2, dto);
			
			message = new MessageEntity("success", 3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
	} 
	
		
		
	/** 
	 * 일반회원 내 정보 수정   서비스
	 */
	protected void setMemberInfo (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		String memberId = (String)session.getAttribute("memberId");
		String memberPw = request.getParameter("memberPw");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String postNo = request.getParameter("postNo");
		String address = request.getParameter("address");
		String addressDetail = request.getParameter("addressDetail");
		String birth = request.getParameter("birth");
		String memberLocNo = request.getParameter("memberLocNo");
		String shopLocCode = request.getParameter("shopLocCode");
		
		
		if(memberId == null || memberId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(memberPw == null || memberPw.trim().length() == 0  ) {


			message = new MessageEntity("validation",1);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(name == null || name.trim().length() ==0) {

			message = new MessageEntity("validation",2);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(postNo == null || postNo.trim().length() == 0) {


			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(address == null || address.trim().length() == 0) {
			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(addressDetail == null || addressDetail.trim().length() == 0) {

			message = new MessageEntity("validation",3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		if(mobile == null || mobile.trim().length() == 0) {


			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(email == null || email.trim().length() == 0) {
			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		if(birth == null || birth.trim().length() == 0) {
			message = new MessageEntity("validation",4);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		memberId = memberId.trim();
		memberPw = memberPw.trim();
		name = name.trim();
		mobile = mobile.trim();
		email = email.trim();
		postNo = postNo.trim();
		address = address.trim();
		addressDetail = addressDetail.trim();
		birth = birth.trim();
		memberLocNo = memberLocNo.trim();
		shopLocCode = shopLocCode.trim();
	
		MypageBiz biz = new MypageBiz();
		
		Member dto = new Member();
		dto.setMemberId(memberId);
		dto.setMemberPw(memberPw);
		dto.setName(name);
		dto.setMobile(mobile);
		dto.setEmail(email);
		dto.setPostNo(postNo);
		dto.setAddress(address);
		dto.setAddressDetail(addressDetail);
		dto.setBirth(birth);
		dto.setMemberLocNo(memberLocNo);
		dto.setShopLocCode(shopLocCode);
		
		try {
			biz.memberInfoUpdate(dto);
			
			session.setAttribute("dto", dto);
			message = new MessageEntity("success", 3);
			message.setLinkTitle("내 정보 보러가기");
			message.setUrl("/takeit/member/mypageController?action=memberInfoForm");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
		
		
		
	/**
	 * 일반회원 내 정보 조회 서비스
	 */
	protected void memberInfoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		MessageEntity message = null;
		
		String memberId = (String)session.getAttribute("memberId");
		
		if(memberId == null || memberId.trim().length()  == 0 ) {


			message = new MessageEntity("validation",0);
			message.setLinkTitle("마이페이지로 이동");
			message.setUrl("/takeit/member/myPage.jsp");
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		
		memberId = memberId.trim();
		
		MypageBiz biz = new MypageBiz();
		
		Member dto = new Member();
		dto.setMemberId(memberId);
		
		try {
			biz.memberDetail(dto);
			session.setAttribute("member", dto);
			request.getRequestDispatcher("/member/memberInfo.jsp").forward(request, response);
		}catch (CommonException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			message = e.getMessageEntity();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			
		}
	}
		
	
}
