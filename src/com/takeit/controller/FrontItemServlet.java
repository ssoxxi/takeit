package com.takeit.controller;

import java.io.File;
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
import com.takeit.model.biz.ItemBiz;
import com.takeit.model.biz.MypageBiz;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.Paging;


/**
 * 상품관리를 위한 FrontController servlet
 * @author 김효원
 */
@WebServlet("/item/itemController")
public class FrontItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//서버구동시에 해당 어플리케이션당 한개의 한개의 환경설정 
		public ServletContext application;
		public String CONTEXT_PATH;
		public String imgPath;
		public void init() {
			application = getServletContext();
			CONTEXT_PATH = application.getContextPath();
			System.out.println("[loadOnStartup]CONTEXT_PATH : " + CONTEXT_PATH);
			application.setAttribute("CONTEXT_PATH", CONTEXT_PATH);
			imgPath = "C:/student_ucamp33/apps_down/05.tomcat/apache-tomcat-8.5.64/webapps";
			application.setAttribute("imgPath", imgPath);
		}
		

		protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String action = request.getParameter("action");
			request.setCharacterEncoding("utf-8");
			switch(action) {
			case "itemEnrollForm":
				itemEnrollForm(request, response);
				break;
			case "itemEnroll":
				itemEnroll(request, response);
				break;
			case "itemList":
				itemList(request, response);
				break;
			case "itemDetail":
				itemDetail(request, response);
				break;
			case "deleteItem":
				deleteItem(request, response);
		    	break;
			case "sellerItemForm":
				sellerItemForm(request,response);
				break;
			case "myitemList":
				myitemList(request,response);
				break;
			case "setSellItem":
				setSellItem(request,response);
				break;
			default:
				response.sendRedirect(CONTEXT_PATH + "/index");
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}
	
	/**
	 * 상품 등록 요청 서비스
	 */
	protected void itemEnroll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String directory = imgPath+"/takeit/img/item";
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		System.out.println(directory);
		MultipartRequest multi
		= new MultipartRequest(request, directory, maxSize, encoding,
				new DefaultFileRenamePolicy());
		
		
		String sellerId = multi.getParameter("sellerId");
		String itemName = multi.getParameter("itemName");
		int itemPrice = Integer.parseInt(multi.getParameter("itemPrice"));
		String salesUnit = multi.getParameter("salesUnit");
		
		String itemOrigin = multi.getParameter("itemOrigin");
		int itemStock = Integer.parseInt(multi.getParameter("itemStock"));
		String itemCategoryNo = multi.getParameter("itemCategoryNo");
		String itemImg = multi.getFilesystemName(("itemImg"));
		String itemTakeit = multi.getParameter("itemTakeit");
		
		MessageEntity message = null;
		ItemBiz biz = new ItemBiz();
		
		
		
		Item dto = new Item();
		dto.setSellerId(sellerId);
		dto.setItemName(itemName);
		dto.setItemPrice(itemPrice);
		dto.setSalesUnit(salesUnit);
		dto.setItemOrigin(itemOrigin);
		dto.setItemStock(itemStock);
		dto.setItemImg(itemImg);
		dto.setItemTakeit(itemTakeit);
		dto.setItemCategoryNo(itemCategoryNo);
		
		Item dto2 = new Item();
		
		try {
			
			File file = new File(imgPath+"/takeit/img/item/"+dto.getItemImg());
			if(file.exists() == false) {
				System.out.println("등록된  이미지가 없습니다.");
				return;
			}
			
			biz.enrollItem(dto);
			biz.getItemImgName(dto2);
			
			File newFile = new File(imgPath+"/takeit/img/item/"+dto2.getItemImg());
			file.renameTo(newFile);
			
			if(newFile.exists() ==true) {
				System.out.println("파일명이 성공적으로 변경되었습니다. 변경전 : " + dto.getItemImg() + "변경 후 : " + dto2.getItemImg() );
			} else {
				System.out.println("파일명 변경이 실패되었습니다.");
			}
			
			if(dto.getItemName() != null) {
				
				message = new MessageEntity("success", 14);
				message.setUrl("/takeit/item/itemController?action=itemList");
				message.setLinkTitle("상품 리스트로");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);;
			} else {
				
				message = new MessageEntity("error", 7);
				message.setLinkTitle("뒤로가기");
				message.setUrl("/takeit/item/itemController?action=itemEnrollForm");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}catch (Exception e) {
			message = new MessageEntity("error", 7);
			message.setLinkTitle("뒤로가기");
			message.setUrl("/takeit/item/itemController?action=itemEnrollForm");
			request.setAttribute("message", message);
			request.getRequestDispatcher("/message.jsp").forward(request, response);;
			
		}
	          
	}
	/**
	 * 상품 등록 요청 페이지
	 */
		protected void itemEnrollForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			HttpSession session = request.getSession();
			
			MessageEntity message = null;
			
			ArrayList<Item> categoryList = new ArrayList<Item>();
			ItemBiz biz = new ItemBiz();
			
			
			try {
				biz.getCategoryList(categoryList);
				
				System.out.println(categoryList);
				
				session.getAttribute("seller");
				session.setAttribute("categoryList", categoryList);
				request.getRequestDispatcher("/item/itemEnroll.jsp").forward(request, response);
			}catch (CommonException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				message = e.getMessageEntity();
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
				
			}
		}

		/**
		 * 상품전체조회
		 * @param request
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 */
		protected void itemList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			ArrayList<Item> itemList = new ArrayList<Item>();
			ItemBiz biz = new ItemBiz();
			
			Paging paging = new Paging();
			paging.setMaxRows(8);
			int totalCnt = 0;
			
			String go = request.getParameter("go");
			String goGroup = request.getParameter("goGroup");
			
			if(go != null) {
				paging.setGo(Integer.parseInt(go));
			} 
			if(goGroup != null) {
				paging.setGoGroup(Integer.parseInt(goGroup));
			}
			
			try {
				totalCnt = biz.itemListCount();
				paging.setTotalCount(totalCnt);
				
				int startRow = paging.getStartRowNo(); 	//페이지 시작 라인
				int endRow = paging.getEndRowNo();		//페이직 끝 라인
				
				request.setAttribute("startRow", startRow);
				request.setAttribute("endRow", endRow);
				request.setAttribute("startPageNo", paging.getStartPageNo());
				request.setAttribute("endPageNo", paging.getEndPageNo());
				request.setAttribute("whereGroup", paging.getWhereGroup());
				request.setAttribute("totalGroup", paging.getTotalGroup());
				request.setAttribute("nextGroup", paging.getNextGroup());
				request.setAttribute("priorGroup", paging.getPriorGroup());
				
				biz.getItemList(itemList);
				
				request.setAttribute("itemList",itemList);
				request.getRequestDispatcher("/item/newItem.jsp").forward(request, response);
			} catch (CommonException e) {
				MessageEntity message = new MessageEntity("error", 24);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}

		}
	
		/**
		 * 상품삭제기능
		 * @param conn
		 * @param dto
		 */
		protected void deleteItem (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("[debug]판매자 등록 상품 삭제 요청");
		
			HttpSession session = request.getSession(false);
			String sellerId = (String)session.getAttribute("sellerId");
			String itemNo = request.getParameter("itemNo");
			sellerId = sellerId.trim();
			itemNo = itemNo.trim();
			System.out.println("[deubg] "+ sellerId + ", " + itemNo);
		
			ItemBiz ibiz = new ItemBiz();
			ArrayList<Item> itemList = new ArrayList<Item>();
			ItemBiz abiz = new ItemBiz();
			
			try {
				ibiz.deleteItem( sellerId, itemNo);
				abiz.getMySellList(itemList, sellerId);
				System.out.println("itemList = "+itemList);
		
				if(itemList != null) {
					request.setAttribute("itemList",itemList);
					request.getRequestDispatcher("/item/mySellList.jsp").forward(request, response);
				}
			} catch (CommonException e) {
				MessageEntity message = new MessageEntity("error", 10);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}
		}	


		/**
		 * 상품상세조회
		 * @param request
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 */
		protected void itemDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String itemNo = request.getParameter("itemNo");
			System.out.println("itemNo = "+itemNo);
			ItemBiz biz = new ItemBiz();

			Item dto = new Item();
			dto.setItemNo(itemNo);
			try {
				biz.getItem(dto);
				System.out.println("dto.판매자 = "+ dto.getSellerName());
				System.out.println("dto= "+ dto);

				request.setAttribute("item", dto);
				request.getRequestDispatcher("/item/itemDetail.jsp").forward(request, response);
			} catch (CommonException e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 판매자 등록상품전체조회
		 * @param request
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 */
		protected void myitemList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			MessageEntity message = null;
			
			String sellerId = (String)session.getAttribute("sellerId"); 
		
			ArrayList<Item> itemList = new ArrayList<Item>();
			ItemBiz abiz = new ItemBiz();
			try {
				abiz.getMySellList(itemList, sellerId);
		
				if(itemList != null) {
					request.setAttribute("itemList",itemList);
					request.getRequestDispatcher("/item/mySellList.jsp").forward(request, response);
				}
			} catch (CommonException e) {
				 message = new MessageEntity("error", 24);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}

}
		
/**
 * 판매자 등록 상품 조회요청		
 */
		protected void sellerItemForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String itemNo = request.getParameter("itemNo");
			System.out.println("itemNo = " +itemNo);
			ItemBiz  biz = new ItemBiz ();

			Item dto = new Item();
			dto.setItemNo(itemNo);

			try {
				biz.searchSell(dto, itemNo);
				if(dto.getItemName() != null) {
					request.setAttribute("item", dto);
					request.getRequestDispatcher("/item/sellInfo.jsp").forward(request, response);
				}
			} catch (CommonException e) {
				MessageEntity message = new MessageEntity("error", 8);
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}

		}
			
		/**
		 *등록 상품수정
		 * @param request
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 */
		protected void setSellItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			HttpSession session = request.getSession();

			String sellerId = (String)session.getAttribute("sellerId");
			sellerId = sellerId.trim();

			String packTypeNo = request.getParameter("packTypeNo");
			String packTypeName = request.getParameter("packTypeName");


			String expirationDate = request.getParameter("expirationDate");
			String notice = request.getParameter("notice");
			int freshPercent =Integer.parseInt( request.getParameter("freshPercent"));

			String itemNo = request.getParameter("itemNo");
			String itemName = request.getParameter("itemName");
			int itemPrice = Integer.parseInt(request.getParameter("itemPrice"));
			String salesUnit = request.getParameter("salesUnit");
			String itemOrigin = request.getParameter("itemOrigin");
			int itemStock = Integer.parseInt(request.getParameter("itemStock"));
			String itemImg = request.getParameter("itemImg");

			String itemInputDate = request.getParameter("itemInputDate");
			int discRate = Integer.parseInt(request.getParameter("discRate"));
			String itemTakeit = request.getParameter("itemTakeit");

			String sellerName = request.getParameter("sellerName");
			String shopName = request.getParameter("shopName");

			String itemCategoryNo = request.getParameter("itemCategoryNo");
			String itemCategoryName =request.getParameter("itemCategoryName");
			double itemCustScore = Double.parseDouble(request.getParameter("itemCustScore"));
			
		
            ItemBiz biz = new ItemBiz();
			MessageEntity message = null;
			Item dto = new Item(packTypeNo, packTypeName, itemCategoryNo,  itemCategoryName,
					expirationDate, notice, freshPercent, itemNo, sellerId,itemName,
					 itemPrice,  salesUnit, itemOrigin,itemStock, itemImg,  itemCustScore,
					itemInputDate,  discRate, itemTakeit,sellerName,shopName);
			
			dto.setPackTypeNo(packTypeNo);
			try {
				biz.setSellItem(dto);
				biz.getSellItem(dto);
				message = new MessageEntity("success", 5); 
				message.setLinkTitle("내 상품보기");
				message.setUrl("/takeit/item/itemController?action=myitemList");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);

			//	request.setAttribute("item", dto);
			//	request.getRequestDispatcher("/item/itemController?action=sellerItemForm&itemNo="+itemNo).forward(request, response);

			} catch(CommonException e) {
				message = e.getMessageEntity();
				message.setLinkTitle("상품보기");
				message.setUrl("/takeit/item/sellInfo.jsp");
				request.setAttribute("message", message);
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}

		}
				
}