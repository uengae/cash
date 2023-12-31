package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.service.CashbookService;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	
//	입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		session 유의성 검사
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginMember") == null) {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		
//		request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int day = Integer.parseInt(request.getParameter("day"));
		System.out.println(targetYear + " <- targetYear");
		System.out.println(targetMonth + " <- targetMonth");
		System.out.println(day + " <- day");
//		나머지 데이터는 입력폼에서 사용자가 입력
		
//		setAttrbute
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("day", day);
		request.getRequestDispatcher("/WEB-INF/view/addCashbook.jsp").forward(request, response);
	}
	
//	입력액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
//		session 유의성 검사
		HttpSession session = request.getSession();
		Member loginMember = new Member();
		
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		String memberId = loginMember.getMemberId();
		System.out.println(memberId + " <- memberId");
		
//		request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int day = Integer.parseInt(request.getParameter("day"));
		int price = Integer.parseInt(request.getParameter("price"));
		String category = request.getParameter("category");
		String memo = request.getParameter("memo");
		String cashbookDate = targetYear + "-" + (targetMonth + 1) + "-" + day;
		System.out.println(targetYear + " <- targetYear");
		System.out.println(targetMonth + " <- targetMonth");
		System.out.println(day + " <- day");
		System.out.println(price + " <- price");
		System.out.println(category + " <- category");
		System.out.println(memo + " <- memo");
		System.out.println(cashbookDate + " <- cashbookDate");
		
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(memberId);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setCategory(category);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		CashbookService cashbookService = new CashbookService();
		int checkRow = cashbookService.insertCashbookHashtag(cashbook); // 키값 반환
		if (checkRow == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath() + "/addCashbook?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&day=" + day);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/cashbook?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&day=" + day);
	}

}
