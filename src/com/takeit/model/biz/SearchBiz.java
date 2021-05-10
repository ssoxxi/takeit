package com.takeit.model.biz;

import java.sql.Connection;
import java.util.ArrayList;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.SearchDao;
import com.takeit.model.dto.Board;
import com.takeit.model.dto.Item;
import com.takeit.model.dto.Search;

/**
 * 상품 검색 서비스
 * @author 한소희
 */
public class SearchBiz {
	private SearchDao dao = SearchDao.getInstance();

	/**검색결과 조회*/
	public void getSearchList(ArrayList<Search> searchList, String searchInput) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try {
			dao.getSearchList(con, searchList, searchInput);
		} catch (CommonException e) {
			throw e;
		} finally {
			JdbcTemplate.close(con);
		}
	}
	
}
