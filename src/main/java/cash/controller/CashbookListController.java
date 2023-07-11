package cash.controller;

import java.io.IOException;
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

@WebServlet("/cashbookListByTag")
public class CashbookListController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		session 구현
		HttpSession session = request.getSession();
		Member loginMember = new Member();
		
		if(session.getAttribute("loginMember") != null) {
			loginMember = (Member) (session.getAttribute("loginMember"));
		}else {
			request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
			return;
		}
		String memberId = loginMember.getMemberId();
		
		String word = request.getParameter("word");
		
		CashbookDao cashbookDao = new CashbookDao();
//		paging
		int currentPage = 1;
		if (request.getParameter("currnetPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int rowPerPage = 10;
		int pagePerPage = 3;
		int beginRow = (currentPage - 1) * rowPerPage;
		int beginPage = (currentPage - 1) / pagePerPage * pagePerPage + 1;
		int endPage = beginPage + pagePerPage;
		int totalTagCnt = cashbookDao.totalTagCnt(memberId, word);
		int totalTagPage = (int)Math.ceil(totalTagCnt/(double)rowPerPage);
		if (endPage > totalTagPage) {
			endPage = totalTagPage;
		}
		List<Cashbook> list = cashbookDao.selectCashbookListByTag(memberId, word, beginRow, rowPerPage);
		
		request.setAttribute("list", list);
		request.setAttribute("word", word);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("pagePerPage", pagePerPage);
		request.setAttribute("totalTagPage", totalTagPage);
		
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
	}


}
