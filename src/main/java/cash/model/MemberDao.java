package cash.model;

import cash.vo.*;
import java.sql.*;

public class MemberDao {

//로그인 메서드
		public Member selectMemberById(Member paramMember) {
			Member returnMember = null;
			
			Connection conn = null;
			PreparedStatement stmt =null;
			ResultSet rs = null;
			String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, paramMember.getMemberId());
				stmt.setString(2, paramMember.getMemberpw());
				rs = stmt.executeQuery();
				if(rs.next()) {
					returnMember = new Member();
					returnMember.setMemberId(rs.getString("MemberId"));
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
			return returnMember;
		}
		
		
//회원가입 메서드
		public int insertMember(Member member) {
			int result = 0;			
			
			Connection conn = null;
			PreparedStatement stmt =null;
			String sql = "INSERT INTO member VALUES (?, PASSWORD(?), now(), NOW())";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, member.getMemberId());
				stmt.setString(2, member.getMemberpw());
				result = stmt.executeUpdate();
				
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
			return result;
		}
//id 중복검사
		public int idCheck(String id) {
			int result = 0 ;
			
			Connection conn = null;
			PreparedStatement stmt =null;
		    ResultSet rs = null;
			String sql = "SELECT member_id memberId FROM member WHERE member_id=?";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, id);
		        rs = stmt.executeQuery();
		        
		        if (rs.next()) {
		            result = 1; // 중복되는 아이디가 존재하는 경우
		        }
				System.out.println(result + "<--result-- idcheck MemberDao");
				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}			
			return result;
		}
		
//pw 검사
//		override
		public int idCheck(String id, String pw) {
			int result = 0 ;
			
			Connection conn = null;
			PreparedStatement stmt =null;
			ResultSet rs = null;
			String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw = PASSWORD(?)";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, id);
				stmt.setString(2, pw);
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					result = 1; // 로그인 성공
				}
				System.out.println(result + "<--result-- idcheck MemberDao");
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}			
			return result;
		}
		
		
//회원 상세 정보
		
		public Member selectMemberOne(String memberId) {
			Member member = new Member();
			
			Connection conn = null;
			PreparedStatement stmt =null;
			ResultSet rs = null;
			String sql = "SELECT member_id memberId, member_pw memberPw, createdate, updatedate "
					+ "FROM member WHERE member_id=?";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, memberId);
				rs = stmt.executeQuery();
				if(rs.next()) {
					member = new Member();
					member.setMemberId(rs.getString("MemberId"));
					member.setMemberpw(rs.getString("memberPw"));
					member.setCreatedate(rs.getString("createdate"));
					member.setUpdatedate(rs.getString("updatedate"));
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
			return member;
		}
		
//회원정보 수정
		public int modifyMember(Member modifyMember, String beforePw) {
			int result = 0;
			
			Connection conn = null;
			PreparedStatement stmt =null;
			String sql = "UPDATE member SET member_pw = PASSWORD(?), updatedate = NOW() "
					+ "WHERE member_id = ? AND member_pw = PASSWORD(?)";
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, modifyMember.getMemberpw());
				stmt.setString(2, modifyMember.getMemberId());
				stmt.setString(3, beforePw);
				result = stmt.executeUpdate();
				
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
			return result;
		}
		
		
		
		
		
//회원탈퇴
		public int deleteMemberInfo(Member member){
			int result = 0;
			
			Connection conn = null;
			PreparedStatement stmt =null;
			String sql = "DELETE FROM member WHERE member_id=? AND member_pw= PASSWORD(?)";

			System.out.println(member.getMemberId() + "<--id-- deleteMemberInfo MemberDao");
			System.out.println(member.getMemberpw() + "<--pw-- deleteMemberInfo MemberDao");
			
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://43.201.156.144:3306/cash","root","java1234");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, member.getMemberId());
				stmt.setString(2, member.getMemberpw());
				result = stmt.executeUpdate();
				System.out.println(result + "<--result-- deleteMemberInfo MemberDao");
				
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
			return result;
		}
		


}
