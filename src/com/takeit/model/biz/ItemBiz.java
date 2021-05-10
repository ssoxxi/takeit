
package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.ItemDao;
import com.takeit.model.dto.Item;


public class ItemBiz {
	
	private ItemDao dao = ItemDao.getInstance(); 

	
	/** 
	 * 등록상품전체 조회
	 * @return ArrayList<Item>
	 */

	public void getItemList(ArrayList<Item> ItemList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.ItemList(con, ItemList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**
	 * 판매자 등록상품 전체조회
     * @param dto review
	 * @return 성공시 등록 미입력시 오류처리
	 */
	public void getMySellList(ArrayList<Item> itemList ,String sellerId) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getMyReviewList(con, itemList,sellerId);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	  /**
     * <pre>
	 * 상품등록
	 * -- 상품등록 입력 데이터 : 상품번호,상품카테고리이름,판매가,할인율,판매단위,재고량,원산지,포장타입,판매자,이미지,안내사항,유통기한,잇거래여부	
	 * -- 시스템 추가 데이터 : 상품 등록일
	 * </pre>
	 * @param dto 상품객체
     * @return 성공시 등록 미입력시 오류처리
	 */

	public void enrollItem(Item dto) throws CommonException{
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.addItem(conn, dto);
			JdbcTemplate.commit(conn);
		}catch (Exception e) {
			e.printStackTrace();
			JdbcTemplate.rollback(conn);
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/**
	 * 이미지 파일 이름 변경
	 * @param dto 상품 객체
	 * @throws CommonException
	 */
	public void getItemImgName(Item dto) throws CommonException{
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchItemImgName(conn, dto);
		}catch (Exception e) {
			e.printStackTrace();
			
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
		
	}
	
	
	/**
	 * 카테고리 목록 조회
	 * @param categoryList 카테고리 목록
	 * @throws CommonException
	 */
	public void getCategoryList(ArrayList<Item> categoryList) throws CommonException{
		
		Connection conn = JdbcTemplate.getConnection(); 
		try {
			dao.getCategotyList(conn, categoryList);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
		
	} 
	
	
	/**상품삭제*/
	public void deleteItem(String sellerId, String itemNo) throws CommonException{

		Connection conn = JdbcTemplate.getConnection();
		try {
			dao.deleteItem(conn, sellerId, itemNo);
			JdbcTemplate.commit(conn);
		}catch (Exception e) {
			e.printStackTrace();
			JdbcTemplate.rollback(conn);
		}finally {
			JdbcTemplate.close(conn);
		}

	}

	/**상품상세조회*/
	public void getItem(Item dto) throws CommonException {
		ItemDao dao = ItemDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		System.out.println("dto = "+ dto.getItemNo());
		try {
			dao.searchItem(conn, dto);
		} catch (CommonException e) {
			throw e;
		}


		JdbcTemplate.close(conn);
	}



	/**판매자등록상품 보기*/
	public void getSellItem(Item dto) throws CommonException {

		Connection conn = JdbcTemplate.getConnection();

		try {
			dao.searchItem(conn, dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			JdbcTemplate.close(conn);
		}
	}

	/**판매자상품상세조회*/
	public void searchSell(Item dto,String itemNo) throws CommonException {
		ItemDao dao = ItemDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		System.out.println("dto = "+ dto.getItemNo());
		try {
			dao.searchItem(conn, dto);
		} catch (CommonException e) {
			throw e;
		}
		JdbcTemplate.close(conn);
	}

	/**
	 * 판매상품변경
	 * @param dto item
	 */
	public void setSellItem(Item dto) throws CommonException{
		Connection conn = JdbcTemplate.getConnection();
		try {
			dao.updatePacking(conn,dto);
			dao.updateItemCategory(conn,dto);
			dao.updateItem(conn,dto);
			JdbcTemplate.commit(conn);

		} catch (Exception e) {
			e.printStackTrace();
			JdbcTemplate.rollback(conn);
			throw e;
		}finally {
			JdbcTemplate.close(conn);

		}
	}

	/**카테고리별 상품 목록*/
	public void getCategoryItemList(ArrayList<Item> categoryItemList, String categoryNo, String categoryName) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getCategoryItemList(con, categoryItemList, categoryNo, categoryName);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	public int itemListCount() throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			return dao.selectItemListCount(con);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

}
	