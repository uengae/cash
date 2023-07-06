package cash.controller;

import java.io.IOException;

import cash.model.*;
import cash.vo.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login.jsp");

//		쿠키에 저장된 아이디가 있다면 request속성에 저장
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(Cookie c : cookies) {
				System.out.println(c.getName() + " <- cookie");
				if(c.getName().equals("loginId") == true) {
					request.setAttribute("loginId", c.getValue());
				}
			}
		}
		rd.forward(request, response);
		
//		System.out.println("check");
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member(memberId, memberPw, null, null);
		
		MemberDao memberDao = new MemberDao();
		Member loginMember = memberDao.selectMemberById(member);
		//null이면 로그인 실패
		
		//로그인 실패
		if(loginMember==null) {
			System.out.println("로그인 실패");
			response.sendRedirect(request.getContextPath()+"/login");		//get방식으로 오기 떄문에 jsp페이지로 간다
			return;		
		}
		
//		idSave체크값이 넘어 왔다면
		if(request.getParameter("idSave") != null) {
			System.out.println(request.getParameter("idSave") + " <- idSave");
			Cookie loginIdCookie = new Cookie("loginId", memberId);
//			loginIdCookie.setMaxAge(60*60*24); // 초단위
			response.addCookie(loginIdCookie);
		}
		
		//로그인 성공시 세션 사용
		HttpSession session = request.getSession();
//		System.out.println("로그인 성공");
		session.setAttribute("loginMember", loginMember);
		response.sendRedirect(request.getContextPath()+"/cashbook");		//get방식으로 오기 떄문에 jsp페이지로 간다
	}

}
