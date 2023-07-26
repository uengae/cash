package cash.service;

import java.sql.Connection;
import java.sql.DriverManager;

import dao.CounterDao;

public class CounterService {
	private CounterDao counterDao;
	
	public void addCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
//			conn.setAutoCommit(false);
			String dbUrl = "jdbc:mariadb://43.201.156.144:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			counterDao.insertCounter(conn);
		} catch (Exception e) {
//			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		conn.commit();
	}
	
	public void modifyCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
//			conn.setAutoCommit(false);
			String dbUrl = "jdbc:mariadb://43.201.156.144:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			counterDao.updateCounter(conn);
		} catch (Exception e) {
//			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		conn.commit();
	}

	public int getCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		int counter = 0;
		try {
			String dbUrl = "jdbc:mariadb://43.201.156.144:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			counter = counterDao.selectCounterCurdate(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return counter;
	}
	
	public int getTotalCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		int counter = 0;
		try {
			String dbUrl = "jdbc:mariadb://43.201.156.144:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			counter = counterDao.selectTotalCounter(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return counter;
		
	}
	

}
