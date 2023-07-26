package cash.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;

public class CashbookService {
//	cashbook 데이터 입력과 hashtag 입력을 같이하는 매서드
	public int insertCashbookHashtag(Cashbook cashbook) {
		int checkRow = 0;
		Connection conn = null;
		String driver = "org.mariadb.jdbc.Driver";
		String dbUrl = "jdbc:mariadb://43.201.156.144:3306/cash";
		String dbUser = "root";
		String dbPw = "java1234";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
//			cashbook data 입력
			CashbookDao cashbookDao = new CashbookDao();
			int cashbookNo = cashbookDao.insertCashbook(conn, cashbook);
			System.out.println("check service");
			
//			hashtag 입력
			HashtagDao hashtagDao = new HashtagDao();
			List<Hashtag> list = new ArrayList<Hashtag>();
			list = hashtagDao.splitHashtag(cashbook.getMemo(), cashbookNo);
			for (Hashtag h : list) {
				int row = hashtagDao.insertHashTag(conn, h);
			}
			
			conn.commit();
			System.out.println("입력 성공 commit");
			checkRow = 1;
		} catch (Exception e) {
			try {
				conn.rollback();
				checkRow = 0;
				System.out.println("입력 실패 rollback");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		입력성공시 1, 실패시 0
		return checkRow;
	}
}
