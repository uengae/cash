package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.vo.*;
import cash.model.*;

@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {

	//addMember.jsp 회원가입 폼으로 이동하기 위해 필요하다
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효검사 (null일 때만 통과)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request, response);
			return;
		}else {
			//login.jsp view를 이동하는 controller를 리다이렉트
			//response.sendRedirect(request.getContextPath()+"/login");
			//return;
		}
	}
	
	//회원가입 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("dopost 들어왔니 <--addMemberController");
		//session 유효검사 (null일 때만 통과)
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			System.out.println("세션에서 튕기니 <--addMemberController");
			//login.jsp view를 이동하는 controller를 리다이렉트
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}

		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member(memberId, memberPw, null, null);
		

		//회원가입 DAO 호출
		MemberDao memberDao = new MemberDao();
		//아이디 중복검사
		int idCk = memberDao.idCheck(memberId);
		if (idCk == 1) {
			//아이디 중복시 다시 addMember로
			response.sendRedirect(request.getContextPath()+"/addMember");
			return;
		}
		
		//회원가입 db 삽입 퀘리
		int row = memberDao.insertMember(member);
		if(row==0) {
			//회원가입 실패시 겟방식으로 다시 addMember
			response.sendRedirect(request.getContextPath()+"/addMember");
		}else if (row==1) {
			//회원가입 성공시 
			request.getRequestDispatcher("/login").forward(request, response);
		}else {
			System.out.println("add member Error!");
		}
		return;
	}

}
