package cash.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/cashbook")
public class CashbookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		session 유의성 검사
		HttpSession session = request.getSession();
		Member loginMember = new Member();
		
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member)(session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		String memberId = loginMember.getMemberId();
		
//		파라미터값 받아오기
//		view에 넘겨줄 달력정보(모델값)
		Calendar firstDay = Calendar.getInstance(); // 오늘날짜
		
//		출력하고자하는 년도, 월, 일의 기본값
		int targetYear = firstDay.get(Calendar.YEAR);
		int targetMonth = firstDay.get(Calendar.MONTH);
		int day = firstDay.get(Calendar.DATE);
		
		
//		출력하고자 하는 년도와 월이 매개값으로 넘어왓다면
		if(request.getParameter("targetYear") != null
				&& request.getParameter("targetMonth") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			day = Integer.parseInt(request.getParameter("day"));
		}
		
//		paging
		CashbookDao cashbookDao = new CashbookDao();
		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("check");
		}
		int rowPerPage = 10;
		int pagePerPage = 3;
		int beginRow = (currentPage - 1) * rowPerPage;
		int beginPage = (currentPage - 1) / pagePerPage * pagePerPage + 1;
		int endPage = beginPage + pagePerPage;
		int totalCnt = cashbookDao.totalCnt(memberId, targetYear, targetMonth, day);
		int totalPage = (int)Math.ceil(totalCnt/(double)rowPerPage);
		if (endPage > totalPage) {
			endPage = totalPage;
		}
//		list 생성
		List<Cashbook> list = new CashbookDao().selectCashbookListByMonth(memberId, targetYear, targetMonth, day, beginRow, rowPerPage);
		
//		속성값 저장
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("day", day);
		request.setAttribute("list", list);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pagePerPage", pagePerPage);
		request.setAttribute("totalPage", totalPage);
		request.getRequestDispatcher("/WEB-INF/view/cashbook.jsp").forward(request, response);
	}

}
