package cash.controller;

import java.io.IOException;

import cash.model.*;
import cash.vo.*;
import cash.service.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	private CounterService counterService = null;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.counterService = new CounterService();
		
		int counter = counterService.getCounter();
		int totalCounter = counterService.getTotalCounter();
		
		request.setAttribute("counter", counter);
		request.setAttribute("totalCounter", totalCounter);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login.jsp");

//		쿠키에 저장된 아이디가 있다면 request속성에 저장
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for(Cookie c : cookies) {
				System.out.println(c.getName() + " <- cookie");
				if(c.getName().equals("loginId") == true) {
					request.setAttribute("loginId", c.getValue());
					request.setAttribute("idCheck", "checked");
				}
				System.out.println(c.getValue() + " <- cookie Value");
			}
		}
		
//		System.out.println("check");
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
		rd.forward(request, response);
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
			response.sendRedirect(request.getContextPath()+"/login");
			return;		
		}
		
//		idSave체크값이 넘어 왔다면
		if(request.getParameter("idSave") != null) {
			System.out.println(request.getParameter("idSave") + " <- idSave");
			Cookie loginIdCookie = new Cookie("loginId", memberId);
//			loginIdCookie.setMaxAge(60*60*24); // 초단위
			response.addCookie(loginIdCookie);
		} else {
			Cookie loginIdCookie = new Cookie("loginId", null);
			loginIdCookie.setMaxAge(0); // 초단위
			response.addCookie(loginIdCookie);
		}
		
		//로그인 성공시 세션 사용
		HttpSession session = request.getSession();
//		System.out.println("로그인 성공");
		session.setAttribute("loginMember", loginMember);
		response.sendRedirect(request.getContextPath()+"/cashbook");
	}

}
