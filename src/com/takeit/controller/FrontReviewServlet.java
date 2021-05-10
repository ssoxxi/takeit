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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.takeit.common.CommonException;
import com.takeit.model.biz.ReviewBiz;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Review;

/**
 * 후기 관리 controller
 */
@WebServlet("/item/reviewController")
public class FrontReviewServlet extends HttpServlet {
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
		String action = request.getParameter("action");
		request.setCharacterEncoding("utf-8");

		switch(action) {
		case "reviewList":
			reviewList(request, response);
			break;
		case "enrollReviewForm":
			enrollReviewForm(request, response);
			break;
		case "enrollReview":
			enrollReview(request, response);
			break;
		case "updateReviewForm":
			updateReviewForm(request,response);
			break;
		case "myReviewList":
			myReviewList(request,response);
			break;
		case "setReviewInfo":
			setReviewInfo(request,response);
			break;
		case "deleteReview":
			deleteReview(request, response);
			break;
		case "reviewDetail":
			reviewDetail(request, response);
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
	 * 후기전체조회
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void reviewList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("reviewList");
		ArrayList<Review> reviewList = new ArrayList<Review>();
		ReviewBiz bbiz = new ReviewBiz();
		try {
			bbiz.getReviewList(reviewList);
			System.out.println("reviewList"+reviewList);
			if(reviewList != null) {
				request.setAttribute("reviewList", reviewList);
				request.getRequestDispatcher("/review/reviewList.jsp").forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 25);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}

	/**
	 * 후기등록요청서비스
	 */
	protected void enrollReviewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String itemNo = request.getParameter("itemNo");
		
		request.setAttribute("itemNo", itemNo);
		request.getRequestDispatcher("/review/review.jsp").forward(request, response);
	}

	/**후기등록*/
	private void enrollReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String directory = 	imgPath+"/takeit/img/review";
	
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		MultipartRequest multi
		= new MultipartRequest(request, directory, maxSize, encoding,
				new DefaultFileRenamePolicy());

		String memberId = multi.getParameter("memberId");
		String itemNo = multi.getParameter("itemNo");
		String reviewTitle = multi.getParameter("reviewTitle");
		String reviewContents = multi.getParameter("reviewContents");
		String reviewScore_ = multi.getParameter("reviewScore");


		int reviewScore = 0;
		if (reviewScore_!= null) {
			reviewScore = Integer.parseInt(reviewScore_);
		}
		String reviewImg = multi.getFilesystemName("reviewImg");


		System.out.println("memberId"+memberId);
		System.out.println("itemNo"+itemNo);
		System.out.println("reviewTitle"+reviewTitle);
		System.out.println("reviewContents"+reviewContents);
		System.out.println("reviewScore"+reviewScore);


		if (memberId == null || memberId.trim().length() == 0){
			MessageEntity message = new MessageEntity("validation", 5);
			message.setUrl("takeit/review/review.jsp");
			message.setLinkTitle("후기등록");

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;   
		}

		if (itemNo == null || itemNo.trim().length() == 0){
			MessageEntity message = new MessageEntity("validation", 5);
			message.setUrl("takeit/review/review.jsp");
			message.setLinkTitle("후기등록");

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;  

		}
		if (reviewTitle   == null || reviewTitle.trim().length() == 0){
			MessageEntity message = new MessageEntity("validation", 5);
			message.setUrl("takeit/review/review.jsp");
			message.setLinkTitle("후기등록");

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;  
		}

		if (reviewContents == null || reviewContents.trim().length() == 0){
			MessageEntity message = new MessageEntity("validation", 5);
			message.setUrl("takeit/review/review.jsp");
			message.setLinkTitle("후기등록");

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;  
		}

		if (reviewScore == 0 ){
			MessageEntity message = new MessageEntity("validation", 5);
			message.setUrl("takeit/review/review.jsp");
			message.setLinkTitle("후기등록");

			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;  
		}

		ReviewBiz rbiz = new ReviewBiz();

		Review dto = new Review(itemNo, memberId, reviewTitle, reviewContents,reviewScore,reviewImg);

		try {
			rbiz.enrollReview(dto);

			MessageEntity message = new MessageEntity("success", 12);
			message.setUrl("/takeit/item/reviewController?action=reviewList");
			message.setLinkTitle("후기조회");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} catch (CommonException e) {
			e.printStackTrace();
			MessageEntity message = e.getMessageEntity();
			System.out.println(message);
		}
	}


	/**
	 * 내가 작성한 후기 목록보기
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void myReviewList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		MessageEntity message = null;
		
		System.out.println("updateReviewForm");
		String memberId = (String)session.getAttribute("memberId"); // String
		
		ArrayList<Review> reviewList = new ArrayList<Review>();
		ReviewBiz bbiz = new ReviewBiz();
		try {
			bbiz.getMyReviewList(reviewList,memberId);
		
			if(reviewList != null) {
				request.setAttribute("reviewList", reviewList);
				request.getRequestDispatcher("/review/myReviewList.jsp").forward(request, response);
			}
		} catch (CommonException e) {
			 message = new MessageEntity("error", 25);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		
		
	}
	/**
	 * 내가 작성한 후기조회요청
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void updateReviewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String reviewNo = request.getParameter("reviewNo");
		System.out.println("reviewNo = " +reviewNo);
		ReviewBiz biz = new ReviewBiz();

		Review dto = new Review();
		dto.setReviewNo(reviewNo);

		try {
			biz.searchReview(dto,reviewNo);
			if(dto.getReviewTitle() != null) {
				request.setAttribute("review", dto);
				request.getRequestDispatcher("/review/reviewInfo.jsp").forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 25);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}
		

	/**  
	 * 작성후기 수정
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void setReviewInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

        String memberId = (String)session.getAttribute("memberId");
		memberId = memberId.trim();
		
    	String itemNo = request.getParameter("itemNo");
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContents = request.getParameter("reviewContents");

		int reviewViews = 0;
		String reviewViews_ = request.getParameter("reviewViews");

		if (reviewViews_ != null) {
			reviewViews = Integer.parseInt(reviewViews_);
		}

		int reviewScore = 0;
		String reviewScore_ = request.getParameter("reviewScore");
		
		if (reviewScore_ != null) {
			reviewScore = Integer.parseInt(reviewScore_);
		}
		
		String reviewImg = request.getParameter("reviewImg");
		String reviewNo = request.getParameter("reviewNo");
		String reviewDate = request.getParameter("reviewDate");

		
		ReviewBiz biz = new ReviewBiz();
		MessageEntity message = null;

		try {
			biz.setReview(new Review( itemNo,memberId, reviewTitle,  reviewContents, reviewScore, reviewImg, reviewNo, reviewViews, reviewDate));
			
			message = new MessageEntity("success", 13); 
			message.setLinkTitle("내 후기보기");
			message.setUrl("/takeit/item/reviewController?action=myReviewList");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		} catch(CommonException e) {
			message = e.getMessageEntity();
			message.setLinkTitle("작성 후기보기");
			message.setUrl("/takeit/review/reviewInfo.jsp");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}
	/**
	 * 후기삭제
	 * @param conn
	 * @param dto
	 */
	protected void deleteReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String reviewNo = request.getParameter("reviewNo");
	
    	reviewNo = reviewNo.trim();
		
		HttpSession session = request.getSession(false);
		String memberId = (String)session.getAttribute("memberId");
		memberId = memberId.trim();


		ReviewBiz bbiz = new ReviewBiz();
		MessageEntity message = null;

		try {
			bbiz.reviewDelete(reviewNo, memberId);
			message = new MessageEntity("success", 15); 
			message.setLinkTitle("후기목록보기");
			message.setUrl("/takeit/item/reviewController?action=myReviewList");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);

		} catch (CommonException e) {
			 message = new MessageEntity("error",27);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}


	/**
	 * 후기상세조회
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void reviewDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reviewNo = request.getParameter("reviewNo");
		System.out.println("reviewNo = " +reviewNo);
		ReviewBiz biz = new ReviewBiz();

		Review dto = new Review();
		dto.setReviewNo(reviewNo);

		try {
			biz.searchReview(dto,reviewNo);
			if(dto.getReviewTitle() != null) {
				request.setAttribute("review", dto);
				request.getRequestDispatcher("/review/reviewDetail.jsp").forward(request, response);
			}
		} catch (CommonException e) {
			MessageEntity message = new MessageEntity("error", 32);
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}

	}
}
