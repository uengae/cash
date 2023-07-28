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

@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {
	//회원 수정폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//수정을 위한 기존 정보 불러오기
		HttpSession session = request.getSession();
		Member loginMember = new Member();
		
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
		MemberDao memberDao = new MemberDao();
		loginMember = memberDao.selectMemberOne(loginMember.getMemberId());
		
		//jsp에서 출력해주기 위해 세션에 담는다
		request.setAttribute("member", loginMember);
		System.out.println(request.getAttribute("member") +"<--getAttribute member-- modifyMemberController");

		//modifyMember.jsp를 보여줄거다 - 포워딩
		request.getRequestDispatcher("WEB-INF/view/modifyMember.jsp").forward(request, response);
	}

	
	//액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//param 받기
		String memberId = request.getParameter("memberId");
		String newPw = request.getParameter("newPw");		
		String beforePw = request.getParameter("beforePw");	
		System.out.println(memberId+"<--parm-- memberId modifyMemberController");
		System.out.println(newPw+"<--parm-- newPw modifyMemberController");
		System.out.println(beforePw+"<--parm-- beforePw modifyMemberController");

		Member modifyMember = new Member();
		modifyMember.setMemberId(memberId);
		modifyMember.setMemberpw(newPw);

		
		
		//dao
		MemberDao memberDao = new MemberDao();
		int result = memberDao.modifyMember(modifyMember, beforePw);
		
		if(result==1) {
			//성공
			System.out.println("수정성공<-- modifyMemberController");
			response.sendRedirect(request.getContextPath()+"/memberOne");
		}else {
			//실패
			System.out.println("수정실패 <-- modifyMemberController");
			request.getRequestDispatcher("WEB-INF/view/memberOne.jsp").forward(request, response);
		}
		
	}

}
