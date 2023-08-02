package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cash.vo.Cashbook;

public class CashbookDao {
	
//	tag 별 cashbook 총갯수 출력
	public int totalTagCnt(String memberId, String word) {
		int totalTagCnt = 0;
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) cnt"
				+ " FROM cashbook c INNER JOIN hashtag h"
				+ " ON c.cashbook_no = h.cashbook_no"
				+ " WHERE c.member_id = ?"
				+ " AND h.word = ?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalTagCnt = rs.getInt("cnt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		return totalTagCnt;
	}
	
//	날짜별 cashbook 총갯수 출력
	public int totalCnt(String memberId, int targetYear, int targetMonth, int day) {
		int totalTagCnt = 0;
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) cnt"
				+ " FROM cashbook"
				+ " WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			stmt.setInt(4, day);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalTagCnt = rs.getInt("cnt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		return totalTagCnt;
	}
//	수입, 지출 입력
//	반환값 : cashbook_no 키값
//	트랜잭션
	public int insertCashbook(Connection conn, Cashbook cashbook) throws SQLException {
		int cashbookNo = 0;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO cashbook"
					+ " (member_id, category, cashbook_date, price, memo, updatedate, createdate)"
					+ " VALUES(?, ?, ?, ?, ?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			int row = stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbookNo;
	}
	
//	hashtag 데이터 출력
	public List<Cashbook> selectCashbookListByTag(String memberId, String word, int beginRow, int rowPerPage){
		List<Cashbook> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		String sql = "SELECT c.cashbook_no cashbookNo, c.category category, c.price price, c.cashbook_date cashbookDate, c.memo memo, c.createdate createdate"
				+ " FROM cashbook c INNER JOIN hashtag h"
				+ " ON c.cashbook_no = h.cashbook_no"
				+ " WHERE c.member_id = ?"
				+ " AND h.word = ?"
				+ " ORDER BY c.cashbook_date DESC"
				+ " LIMIT ?, ?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, word);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCreatedate(rs.getString("createdate"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}		
		return list;
	}
	
//	cashbook 데이터 출력
//	override
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth){
		List<Cashbook> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		
		String sql = "SELECT case when category='지출' then '총 지출'\r\n"
				+ "				ELSE '총 수입' END AS category\r\n"
				+ "		, SUM(case when category='지출' then -price\r\n"
				+ "				ELSE price END) AS price\r\n"
				+ "		, cashbook_date AS cashbookDate\r\n"
				+ "FROM cashbook\r\n"
				+ "WHERE member_id = ? and YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ?\r\n"
				+ "GROUP BY category, cashbook_date\r\n"
				+ "UNION ALL\r\n"
				+ "SELECT category, price, cashbook_date cashbookDate\r\n"
				+ "FROM cashbook\r\n"
				+ "WHERE member_id = ? and YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ?"
				;
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			stmt.setString(4, memberId);
			stmt.setInt(5, targetYear);
			stmt.setInt(6, targetMonth + 1);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCategory(rs.getString("category"));
//				System.out.println(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}					
		
		return list;
	}
	
	
//	원하는 날짜의 cashbook data 출력
//	override
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth, int day, int beginRow, int rowPerPage){
		List<Cashbook> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		
		String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate, memo, createdate\r\n"
				+ "FROM cashbook\r\n"
				+ "WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ?\r\n"
				+ "ORDER BY cashbook_date\r\n"
				+ "LIMIT ?, ?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			stmt.setInt(4, day);
			stmt.setInt(5, beginRow);
			stmt.setInt(6, rowPerPage);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCreatedate(rs.getString("createdate"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}					
		
		return list;
	}
	
//	cashbook data 삭제
	public void removeCashbook (int cashbookNo) {
		Connection conn = null;
		PreparedStatement stmt =null;
		
		String sql = "DELETE FROM cashbook WHERE cashbook_no = ?";
		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			System.out.println(stmt);
			int row = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
	}

//	cashbook data 하나 불러오기
	public Cashbook selectOneCashbook (int cashbookNo, String memberId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Cashbook c = new Cashbook();
		String sql = "SELECT category, price, memo, cashbook_date cashbookDate FROM cashbook WHERE cashbook_no = ? AND member_id = ?";

		try {
			conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			stmt.setString(2, memberId);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			if(rs.next()) {
				c.setCashbookDate(rs.getString("cashbookDate"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		return c;
	}
}
