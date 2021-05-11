package com.takeit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.takeit.common.CommonException;
import com.takeit.model.biz.MemberBiz;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.MessageEntity;
import com.takeit.util.Utility;

/**
 * 일반 회원관리 컨트롤러
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
@WebServlet(urlPatterns = {"/member/controller"}, loadOnStartup = 1)
public class FrontMemberServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ServletContext application;
	public String CONTEXT_PATH;
	
	public void init() {
		application = getServletContext();
		CONTEXT_PATH = application.getContextPath();
		System.out.println("[loadOnStartup]CONTEXT_PATH : " + CONTEXT_PATH);
		application.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
		application.setAttribute("takeitScope", "all");
		String imgPath="C:/student_ucamp33/apps_down/05.tomcat/apache-tomcat-8.5.64/webapps";
		application.setAttribute("imgPath", imgPath);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getParameter("action");
		
		switch(action) {
		case "memberInputForm":
			memberInputForm(request, response);
			break;
		case "memberInput":
			memberInput(request, response);
			break;
		case "memberLogin":
			memberLogin(request, response);
			break;
		case "memberLogout":
			memberLogout(request, response);
			break;
		case "memberFindId":
			memberFindId(request, response);
			break;
		case "memberFindPw":
			memberFindPw(request, response);
			break;
		case "memberIdChk":
			memberIdChk(request, response);
			break;
		case "memberEmailChk":
			memberEmailChk(request, response);
			break;
		case "kakaoLogin":
			kakaoLogin(request, response);
			break;
		case "kakaoMemberInputForm":
			kakaoMemberInputForm(request, response);
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
	protected void memberInputForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("작동확인 : memberInputForm");
		
		request.getRequestDispatcher("/member/memberInput.jsp").forward(request, response);
	}
	
	/**
	 * 회원가입 
	 */
	protected void memberInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동확인 : memberInput");
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");

		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String postNo = request.getParameter("postNo");
		String address = request.getParameter("address");
		String addressDetail = request.getParameter("addressDetail");
		String birth = request.getParameter("birth");
				
		System.out.println(memberId + "," + memberId + "," + name + "," + mobile + "," + email + "," + postNo + "," + address + "," + addressDetail + "," + birth);

		memberId = memberId.trim();
		memberPw = memberPw.trim();
		name = name.trim();
		mobile = mobile.trim();
		email = email.trim();
		postNo = postNo.trim();
		address = address.trim();
		addressDetail = addressDetail.trim();
		birth = birth.trim();				
		
		if(memberId == null || memberId.trim().length() == 0 || memberId.length() < 6 || memberId.length() > 20 ) {
			MessageEntity message = new MessageEntity("error",33);
			message.setLinkTitle("뒤로가기");
			message.setUrl("/takeit/member/memberInput.jsp");
			request.setAttribute("message", message);
			rd.forward(request, response);
			return;
		}
		
		Member dto = new Member(memberId, memberPw, name, mobile, email, postNo, address, addressDetail, birth);
		
		MemberBiz biz = new MemberBiz();
				
		try {
			biz.addMember(dto);
			MessageEntity message = new MessageEntity("success", 0);
			message.setUrl("/takeit/member/memberLogin.jsp");
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
	protected void memberLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동확인 : memberLogin");
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		System.out.println(memberId + "," + memberPw);
		
		memberId = memberId.trim();
		memberPw = memberPw.trim();
		
		Member dto = new Member();
		
		MemberBiz biz = new MemberBiz();
		
		dto.setMemberId(memberId);
		dto.setMemberPw(memberPw);
		
		try {
			biz.login(dto);
			
			if(dto.getAddress() != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("memberId", memberId); 
				session.setAttribute("dto", dto);
				response.sendRedirect("/takeit/index");
			}else {
				MessageEntity message = new MessageEntity("error", 34);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/member/memberLogin.jsp");
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
	protected void memberLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("작동확인 : memberLogout");
		
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
	protected void memberFindId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("작동 확인 : memberFindId");

		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		System.out.println(name + "," + email);
		
		name = name.trim();
		email = email.trim();
		
		MemberBiz biz = new MemberBiz();
		Member dto = new Member();

		dto.setName(name);
		dto.setEmail(email);
		
		try {
			biz.idFind(dto);
			if(dto.getMemberId() != null){
				request.setAttribute("idInfo", dto.getMemberId());
				request.setAttribute("entryDate", dto.getEntryDate());
				request.getRequestDispatcher("/member/idFindMessage.jsp").forward(request, response);;
			}else {
				MessageEntity message = new MessageEntity("error", 35);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/member/memberFindId.jsp");
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
	protected void memberFindPw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("작동 확인 : memberFindPw");

		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		String memberId = request.getParameter("memberId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		System.out.println(memberId + "," + name + "," + email );

		memberId = memberId.trim();
		name = name.trim();
		email = email.trim();
		
		MemberBiz biz = new MemberBiz();
		Member dto = new Member();

		dto.setMemberId(memberId);
		dto.setName(name);
		dto.setEmail(email);
		
		try {
			biz.pwFind("dto", dto);
			if(dto.getMemberPw() != null) {
				request.setAttribute("pwInfo", dto.getMemberPw());
				request.getRequestDispatcher("/member/pwFindMessage.jsp").forward(request, response);
			}else {
				MessageEntity message = new MessageEntity("error", 36);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/member/memberFindPw.jsp");
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
	protected void memberIdChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String memberId = request.getParameter("memberId");
		System.out.println("chk"+memberId);
		memberId = memberId.trim();
		
		MemberBiz biz = new MemberBiz();

		try {
			int result = biz.idCheck(memberId);
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
	protected void memberEmailChk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String email = request.getParameter("email");
		
		email = email.trim();
		
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		MemberBiz biz = new MemberBiz();
		
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
			rd.forward(request, response);
		}
	}
	
	/** api 로그인 요청 서비스 */
	protected void kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("kakaoId");
		
		System.out.println("작동확인 : kakaoLogin");
		RequestDispatcher rd = request.getRequestDispatcher("/message.jsp");
		
		Member dto = new Member();
		MemberBiz biz = new MemberBiz();
		dto.setMemberId(memberId);
		
		try {
			biz.kakaoLogin(dto);
			if(dto.getAddress() != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("memberId", memberId); 
				session.setAttribute("dto", dto);
				response.sendRedirect("/takeit/index");
			}else {
				MessageEntity message = new MessageEntity("error", 34);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/member/memberLogin.jsp");
				request.setAttribute("message", message);
				rd.forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = e.getMessageEntity();
			request.setAttribute("message", message);
			rd.forward(request, response);
		}
	}
	
	/** 회원가입 화면 요청 서비스 */
	protected void kakaoMemberInputForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String kakaoId = request.getParameter("kakaoIdInput");
		
		String kakaoEmail = request.getParameter("kakaoEmail");
		request.setAttribute("kakaoId", kakaoId);
		request.setAttribute("kakaoEmail", kakaoEmail);
		request.setAttribute("kakaoPass", Utility.getSecureNumberAndString(10, true));
		
		request.getRequestDispatcher("/loginapi/kakaoMemberInput.jsp").forward(request, response);
	}
}
