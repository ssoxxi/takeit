/**
 * 
 */
package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.BoardDao;
import com.takeit.model.dao.IndexDao;
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Category;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.Review;

/**
 * 시작페이지 서비스
 * @author 한소희
 *
 */
public class IndexBiz {
	private IndexDao dao = IndexDao.getInstance(); 
	
	/**신상품 리스트*/
	public void getNewItemList(ArrayList<Item> itemList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getNewItemList(con, itemList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**후기 리스트*/
	public void getBestReviewList(ArrayList<Review> reviewList) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getBestReviewList(con, reviewList);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}
	

	

}
