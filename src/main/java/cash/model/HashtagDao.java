package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cash.vo.Hashtag;

public class HashtagDao {
	
	
//	hashtag 갯수별로 출력
	public List<Map<String,Object>> selectWordCountByMonth(String memberId, int targetYear, int targetMonth) {
	      List<Map<String,Object>> list = new ArrayList<>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      try {
	         String driver = "org.mariadb.jdbc.Driver";
	         String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
	         String dbUser = "root";
	         String dbPw = "java1234";
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
	         String sql = "SELECT word, COUNT(*) cnt"
	                  + " FROM hashtag h INNER JOIN cashbook c"
	                  + " ON h.cashbook_no = c.cashbook_no"
	                  + " WHERE c.member_id = ?"
	                  + " AND YEAR(c.cashbook_date) = ?"
	                  + " AND MONTH(c.cashbook_date) = ?"
	                  + " GROUP BY word"
	                  + " ORDER BY COUNT(*) DESC";
	         stmt = conn.prepareStatement(sql);
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, memberId);
	         stmt.setInt(2, targetYear);
	         stmt.setInt(3, targetMonth + 1);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("word", rs.getString("word"));
	            m.put("cnt", rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         }catch(Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      return list;
	   }
	
//	hashtag 분류
	public void splitHashtag(String memo, int cashbookNo){
//		set은 데이터 중복값을 허용하지 않는다.
		Set<String> set = new HashSet<String>();
		memo = memo.replace("#", " #");
		for(String s : memo.split(" ")) {
			if (s.startsWith("#")) {
				String s2 = s.replace("#", "");
				if(s2.length() > 0) {
					System.out.println(s2 + " <- splitHashtag s2");
					set.add(s2);
				}
			}
		}
//		set데이터를 읽기위해서는 iterator()를 사용해야한다
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			Hashtag h = new Hashtag();
			h.setCashbookNo(cashbookNo);
			h.setWord(it.next());
			System.out.println(h.getWord() + " <- splitHashtag word");
			int row = insertHashTag(h);
			System.out.println(row + " <- splitHashtag row");
		}
	}
	
//	hashtag입력
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
