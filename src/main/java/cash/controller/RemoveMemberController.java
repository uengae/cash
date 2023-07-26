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


@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {
	//탈퇴 전 비밀번호 입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/view/removeMember.jsp").forward(request, response);
		
	}

	//탈퇴
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션 정보 가져오기
		HttpSession session = request.getSession();
		System.out.println("doPost 들어왔다 <-- RemoveMemberController");
		
		Member loginMember = new Member();
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
			System.out.println(loginMember.getMemberId()+"<--새로 들어온 아이디-- RemoveMemberController");
		}else {
			System.out.println("세션검사 리턴 <-- RemoveMemberController");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		String memberPwParam = request.getParameter("memberPw");
		System.out.println(memberPwParam + "<--param PW-- RemoveMemberController");

		loginMember.setMemberpw(memberPwParam) ;
		
		MemberDao memberDao = new MemberDao();
		int result = memberDao.deleteMemberInfo(loginMember);
		
		if(result==1) {
			//탈퇴성공
			System.out.println("탈퇴성공<-- RemoveMemberController");
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/login");
		}else {
			//탈퇴실패
			System.out.println("탈퇴실패 <-- RemoveMemberController");
			request.getRequestDispatcher("WEB-INF/view/removeMember.jsp").forward(request, response);
		}
	}		

}
