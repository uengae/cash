package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CounterDao {
//	오늘 날짜 첫번째 접속 -> insert
	public void insertCounter(Connection conn) throws Exception {
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO counter VALUES (CURDATE(), 1)";
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
//			예외를 던져야한다.
			throw new Exception(); // 강제로 예외를 발생
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
	}
//	오늘 날짜 첫번째 아니면 -> update
	public void updateCounter(Connection conn) throws Exception {
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE counter SET count_num = count_num + 1 WHERE count_date = CURDATE()";
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
//			예외를 던져야한다.
			throw new Exception(); // 강제로 예외를 발생
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
	}
	
//	오늘 날짜 카운터
	public int selectCounterCurdate(Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int counterNum = 0;
		try {
			String sql = "SELECT count_num countNum FROM counter WHERE count_date = CURDATE()";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				counterNum = rs.getInt("countNum");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			예외를 던져야한다.
			throw new Exception(); // 강제로 예외를 발생
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
		return counterNum;
	}
	
//	누적 카운터
	public int selectTotalCounter (Connection conn) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalCounter = 0;
		try {
			String sql = "SELECT SUM(count_num) totalCounter FROM counter";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalCounter = rs.getInt("totalCounter");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			예외를 던져야한다.
			throw new Exception(); // 강제로 예외를 발생
		} finally {
			try {
				stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} 
		return totalCounter;
	}
}
