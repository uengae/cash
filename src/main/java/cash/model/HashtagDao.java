package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import cash.vo.Hashtag;

public class HashtagDao {
	public int insertHashTag(Hashtag hashtag) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt =null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			String sql = "INSERT INTO hashtag"
					+ " (cashbook_no, word, updatedate, createdate)"
					+ " VALUES(?, ?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, hashtag.getCashbookNo());
			stmt.setString(2, hashtag.getWord());
			row = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
}
