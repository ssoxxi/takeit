package com.takeit.model.biz;

import java.sql.Connection;

import com.takeit.common.CommonException;
import com.takeit.common.JdbcTemplate;
import com.takeit.model.dao.MemberDao;
import com.takeit.model.dao.TakeitDao;
import com.takeit.model.dto.Member;

/**
 * 일반 회원관리 서비스
 * @author  임우진
 * @since   jdk1.8
 * @version v2.0
 */
public class MemberBiz {

	/**
	 * dao 객체 생성
	 */
	private MemberDao dao = new MemberDao();
	
	/**
	 * 회원가입 
	 */
	public void addMember(Member member) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		TakeitBiz takeitBiz = new TakeitBiz();
		 try {
			dao.addMember(con, member);
			takeitBiz.addMemberLocNo(member); 
			TakeitDao.getInstance().updateMemberLoc(con, member);
			JdbcTemplate.commit(con);
		} catch (CommonException e) {
			e.printStackTrace();
			JdbcTemplate.rollback(con);
			throw e;
		}finally {
			JdbcTemplate.close(con);
		}
	}
	
	/**
	 * 로그인
	 */
	public void login(Member member) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try{
			dao.login(con, member);
		} catch(CommonException e) {
			throw e; 
		} finally {
			JdbcTemplate.close(con);
		}
	}
	
	/**
	 * 아이디 찾기
	 */
	public void idFind(Member member) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try{
			dao.idFind(con, member);
		} catch(CommonException e) {
			throw e; 
		} finally {
			JdbcTemplate.close(con);
		}
	}
	
	/**
	 * 비밀번호 찾기
	 */
	public void pwFind(String temporaryPw, Member member) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try{
			dao.pwFind(con, temporaryPw, member);
			JdbcTemplate.commit(con);
		} catch(CommonException e) {
			JdbcTemplate.rollback(con);
			throw e; 
		} finally {
			JdbcTemplate.close(con);
		}
	}

	/**
	 * 아이디 중복체크
	 * @throws CommonException 
	 */
	public int idCheck(String memberId) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		boolean result = dao.memberIdChk(con, memberId);
		JdbcTemplate.close(con);
		if (result) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 이메일 중복체크
	 * @throws CommonException 
	 */
	public int emailCheck(String email) throws CommonException{
		Connection con = JdbcTemplate.getConnection();
		boolean result = dao.memberEmailChk(con, email);
		JdbcTemplate.close(con);
		if (result) {
			return 1;
		}
		return 0;
	}

	public void kakaoLogin(Member dto) throws CommonException {
		Connection con = JdbcTemplate.getConnection();
		try{
			dao.kakaoLogin(con, dto);
		} catch(CommonException e) {
			throw e; 
		} finally {
			JdbcTemplate.close(con);
		}
		
	}
}
