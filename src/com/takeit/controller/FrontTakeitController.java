package com.takeit.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.takeit.model.biz.TakeitBiz;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.TakeitItem;

/**
 * 테이크잇
 */
@WebServlet("/takeit/takeitController")
public class FrontTakeitController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		case "takeitItemList":
			takeitItemList(request, response);
			break;
		case "takeitItemDetail":
			takeitItemDetail(request, response);
			break;
		case "test":
			test(request, response);
			break;
		}
	}
	
	protected void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = new Member();
		member.setAddress("인천광역시 부평구 부평5동 대정로 롯데시네마 부평");
		new TakeitBiz().getMemberLocNo(member);
		System.out.println("[debug]회원구역:"+member.getShopLocCode()+member.getMemberLocNo());
	}
	
	protected void takeitItemDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemNo = request.getParameter("itemNo");
		System.out.println("itemNo : " + itemNo);
		
		TakeitBiz biz = new TakeitBiz();
		
		TakeitItem takeitItem = new TakeitItem();
		takeitItem.setItemNo(itemNo);
		
		biz.getTakeitItem(takeitItem);
		
		request.setAttribute("takeitItem", takeitItem);
		request.getRequestDispatcher("/takeit/takeitItemDetail.jsp").forward(request, response);
		
	}
	
	/**
	 * 테이크잇 상품목록 요청 서비스 
	 * 0. 상품클릭으로 부터 get요청으로 
	 * 1. 사용자의 아이디를 세션으로부터 받아옵니다
	 * 2. 테이크잇상품엔티티에는 구매자아이디, 회원구역번호, 상점구역코드, 상품목록,
	 * 3. 테이크잇 비즈 클래스사용 
	 * 4. 테이크잇 DAO 사용
	 * 5. 회원의 구역과 , 테이크잇의 구역이 일치하는곳이 있다면, 그잇거래가 alive인지 확인후,
	 * 5-1. 상품번호와 아이디를 전부 가져와서, 그 상품 중 잇거래여부를 
	 * 
	 * N. 상품클릭시 이미 Alive=F 일 가능성 여부 체크 > 알림창표시
	 * N. 만약 다른구역의 상품을 조회하거나, 
	 * N. 구매버튼 클릭시 장바구니페이지로 가게되고, 장바구니리스트를 받아오는게 아닌 이 상품만 담아서 결제로 진행한다
	 * */
	protected void takeitItemList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//그렇다면, 셀러일때는 어떻게해야하나?
		//세션으로 받아온 유저아이디, 구역번호, 상점구역번호  // 구역번호 상점번호 디폴트값 :필요할듯!
		String memberId = "user01";
		String memberLocNo = "29";
		String shopLocCode = "AA";

		Member member = new Member();
		
		member.setMemberId(memberId);
		member.setMemberLocNo(memberLocNo);
		member.setShopLocCode(shopLocCode);
		
		ArrayList<TakeitItem> takeitItemList = new ArrayList<TakeitItem>();
		
		TakeitBiz biz = new TakeitBiz();
		biz.getTakeitItemList(member, takeitItemList);
		
		request.setAttribute("takeitItemList", takeitItemList);
		request.getRequestDispatcher("/takeit/takeitList.jsp").forward(request, response);
	}
}









