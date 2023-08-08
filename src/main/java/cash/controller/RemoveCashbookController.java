package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//세션 정보 가져오기
		HttpSession session = request.getSession();
		System.out.println("doPost 들어왔다 <-- RemoveMemberController");
		
		if(session.getAttribute("loginMember") == null) {
//			System.out.println("세션검사 리턴 <-- RemoveCashbookController");
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		String memberId = ((Member)session.getAttribute("loginMember")).getMemberId();
		CashbookDao cashbookDao = new CashbookDao();
		
//		cashbook 날짜 분할
		Cashbook cashbook = cashbookDao.selectOneCashbook(cashbookNo, memberId);
		String targetYear = cashbook.getCashbookDate().substring(0,4);
		System.out.println(targetYear + "targetYear");
		String targetMonth = Integer.parseInt(cashbook.getCashbookDate().substring(5,7)) - 1 +"";
		System.out.println(targetMonth + "targetMonth");
		String day = cashbook.getCashbookDate().substring(8,10);
		System.out.println(day + "day");
		
//		cashbook 삭제
		cashbookDao.removeCashbook(cashbookNo);
		response.sendRedirect(request.getContextPath() + "/cashbook?targetYear=" + targetYear + "&targetMonth=" + targetMonth + "&day=" + day);
	}

}
