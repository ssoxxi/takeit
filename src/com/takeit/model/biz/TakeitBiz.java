package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.TakeitDao;
import com.takeit.model.dto.Member;
import com.takeit.model.dto.MessageEntity;
import com.takeit.model.dto.ShopLoc;
import com.takeit.model.dto.Takeit;
import com.takeit.model.dto.TakeitItem;
import com.takeit.util.Utility;

/**
 * 잇거래 업무처리 위한 TakeitBiz 클래스
 * @author 김태경
 * @since jdk1.8
 * @version v2.0 2021/05/10
 */
public class TakeitBiz {
	
	/**
	 * 잇거래 등록
	 * @param takeit 잇거래 객체
	 */
	public void addTakeit(Takeit takeit) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		boolean result = existTakeit(takeit.getShopLocCode());
		if (result) {
			MessageEntity message = new MessageEntity("error", 11);
			throw new CommonException(message);
		}
		
		try {
			dao.insertTakeit(conn, takeit);
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/**
	 * 잇거래 존재여부 확인 메서드
	 * @param shopLocCode 
	 * @return 이미 존재시 true, 미존재시 false
	 */
	public boolean existTakeit(String shopLocCode) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			int row = dao.searchExistTakeit(conn, shopLocCode);
			if(row == 1) {
				return true;
			}
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		return false;
	}
	
	/** 판매자회원의 잇거래 상품 목록 조회 */
	public void getTakeitItemList(String shopLocCode, ArrayList<TakeitItem> takeitItemList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitItemList(conn, shopLocCode, takeitItemList);
			
		} catch(CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	/** 비회원 잇거래 상품목록 조회  */
	public void getTakeitItemList(ArrayList<TakeitItem> takeitItemList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitItemList(conn, takeitItemList);
			
		} catch(CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 일반회원의 잇거래 상품 목록 조회 */
	public void getTakeitItemList(Member member, ArrayList<TakeitItem> takeitItemList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitItemList(conn, member, takeitItemList);
			
		} catch(CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 잇거래 상품 상세조회 */
	public void getTakeitItem(String shopLocCode, TakeitItem takeitItem) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitItem(conn, takeitItem);
			dao.searchshopLocName(conn, takeitItem);
			if (takeitItem.getShopLocCode().equals(shopLocCode)) {
				dao.selectTakeitNo(conn, takeitItem);
				dao.searchTakeitCurrPrice(conn, takeitItem);
			}
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/**
	 * 회원구역 등록 
	 * @param member 회원객체
	 */
	public void addMemberLocNo(Member member) throws CommonException {
		member.setMemberLocNo(null);
		member.setShopLocCode(null);
		
		HashMap<String, String> latLng = Utility.getLatlng(member.getAddress());
		String _lat = latLng.get("lat");
		String _lng = latLng.get("lng");
		if ( _lat == null || _lng == null) {
			MessageEntity message = new MessageEntity("validation", 3);
			throw new CommonException(message);
		}
		Double lat = Double.valueOf(_lat);
		Double lng = Double.valueOf(_lng);
		
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		ArrayList<ShopLoc> shopLocList = new ArrayList<ShopLoc>();
		try {
			dao.searchShopLocList(conn, shopLocList);
		} catch (CommonException e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		
		double locLat = 0;
		double locLng = 0;
		
		for (ShopLoc shopLoc : shopLocList) {
			Double shopLat = Double.valueOf(shopLoc.getShopLocLat());
			Double shopLng = Double.valueOf(shopLoc.getShopLocLng());
			
			locLat = shopLat-lat;
			locLng = shopLng-lng;
			
			if (Math.abs(locLat) <= 0.05 && Math.abs(locLng) <= 0.05) {
				member.setShopLocCode(shopLoc.getShopLocCode());
				break;
			}
		}
		
		if (member.getShopLocCode() == null) {
			System.out.println("근처 지역상점이 없습니다");
			return ;
		}
		String memberLocNo = "";
		locLat += 0.05;
		memberLocNo += (int)(locLat*100);
		
		locLng += 0.05;
		memberLocNo += (int)(locLat*100);
		member.setMemberLocNo(memberLocNo);
		conn = JdbcTemplate.getConnection();
		try {
			boolean result = dao.isValidMemberLocNo(conn, member);
			if (!result) {
				dao.addMemberLocNo(conn, member);
				JdbcTemplate.commit(conn);
			}
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/**
	 * 상점구역 등록
	 * @param address 주소
	 * @param shopLoc 상점구역 객체
	 */
	public void addShopLoc(String address, ShopLoc shopLoc) throws CommonException {
		HashMap<String, String> latLng = new HashMap<String, String>();
		latLng = Utility.getLatlng(address);
		
		String _lat = latLng.get("lat");
		String _lng = latLng.get("lng");
		
		if ( _lat == null || _lng == null) { 
			MessageEntity message = new MessageEntity("validation", 3);
			throw new CommonException(message);
		}
		
		Double lat = Double.valueOf(_lat);
		Double lng = Double.valueOf(_lng);
		
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		ArrayList<ShopLoc> shopLocList = new ArrayList<ShopLoc>();
		try {
			dao.searchShopLocList(conn, shopLocList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		
		double locLat = 0;
		double locLng = 0;
		
		for (ShopLoc shopLocaion : shopLocList) {
			Double shopLat = Double.valueOf(shopLocaion.getShopLocLat());
			Double shopLng = Double.valueOf(shopLocaion.getShopLocLng());
			
			locLat = shopLat-lat;
			locLng = shopLng-lng;
			
			if (Math.abs(locLat) <= 0.05 && Math.abs(locLng) <= 0.05) {
				MessageEntity message = new MessageEntity("error", 43);
				throw new CommonException(message);
			}
		}
		shopLoc.setShopLocLat(_lat);
		shopLoc.setShopLocLng(_lng);
		
		conn = JdbcTemplate.getConnection();
		try {
			dao.addShopLoc(conn, shopLoc);
			dao.addMemberLoc(conn, shopLoc);
			
			JdbcTemplate.commit(conn);
		} catch(CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 상점구역목록 조회
	 * @param shopLocList 상점구역목록
	 */
	public void getShopLocList(ArrayList<ShopLoc> shopLocList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();

		try {
			dao.searchShopLocList(conn, shopLocList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/**
	 * 상점구역 삭제
	 * @param shopLocList 상점구역목록
	 */
	public void deleteShopLoc(Takeit takeit) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();

		try {
			dao.updateMemberLocNull(conn, takeit);
			dao.deleteShopLoc(conn, takeit);
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 만료된 잇거래 목ㅁ록 조회 */
	public void getTakeitExpiredList(ArrayList<Takeit> takeitList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitExpiredList(conn, takeitList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 진행중인 잇거래 목록 조회 */
	public void getTakeitLiveList(ArrayList<Takeit> takeitList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitLiveList(conn, takeitList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/** 종료된 잇거래 목록 조회 */
	public void getTakeitDeadList(ArrayList<Takeit> takeitList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitDeadList(conn, takeitList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
		
	}

	/** 잇거래 목록 전체 조회*/
	public void getTakeitAllList(ArrayList<Takeit> takeitList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			dao.searchTakeitAllList(conn, takeitList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/** 잇거래 삭제 */
	public void deleteTakeit(ArrayList<String> takeitNoList) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			for (String takeitNo : takeitNoList) {
				dao.deleteTakeit(conn, takeitNo);
			}
			JdbcTemplate.commit(conn);
		} catch (CommonException e) {
			JdbcTemplate.rollback(conn);
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}

	/** 비회원 잇거래 상품개수 조회*/
	public int takeitItemListCount() throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			return dao.selectTakeitItemListCount(conn);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 회원 잇거래상품개수 조회*/
	public int takeitItemListCount(Member member) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			return dao.selectTakeitItemListCount(conn, member);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
	
	/** 판매자 잇거래 상품개수 조회*/
	public int takeitItemListCount(String shopLocCode) throws CommonException {
		TakeitDao dao = TakeitDao.getInstance();
		Connection conn = JdbcTemplate.getConnection();
		
		try {
			return dao.selectTakeitItemListCount(conn, shopLocCode);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(conn);
		}
	}
}
