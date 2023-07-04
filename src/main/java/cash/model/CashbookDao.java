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
//	수입, 지출 입력
//	반환값 : cashbook_no 키값
	public int insertCashbook(Cashbook cashbook) {
		int cashbookNo = 0;
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
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
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbookNo;
	}
	
//	cashbook 데이터 출력
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth){
		List<Cashbook> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		
		String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate\r\n"
				+ "FROM cashbook\r\n"
				+ "WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ?\r\n"
				+ "ORDER BY cashbook_date";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			System.out.println(stmt);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
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
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}					
		
		return list;
	}
	
	
//	원하는 날짜의 cashbook data 출력
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth, int day){
		List<Cashbook> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt =null;
		ResultSet rs = null;
		
		String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate, memo, createdate\r\n"
				+ "FROM cashbook\r\n"
				+ "WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ?\r\n"
				+ "ORDER BY cashbook_date";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			stmt.setInt(4, day);
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
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}					
		
		return list;
	}
}