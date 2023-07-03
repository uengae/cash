package cash.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		session 인증검사
		
//		view에 넘겨줄 달력정보(모델값)
		Calendar firstDay = Calendar.getInstance(); // 오늘날짜
		
//		출력하고자하는 년도, 월, 일의 기본값
		int targetYear = firstDay.get(Calendar.YEAR);
		int targetMonth = firstDay.get(Calendar.MONTH);
		firstDay.set(Calendar.DATE, 1);
		
//		출력하고자 하는 년도와 월이 매개값으로 넘어왓다면
		if(request.getParameter("targetYear") != null
				&& request.getParameter("targetMonth") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			firstDay.set(Calendar.YEAR, targetYear);
//			API에서 자동으로 Calendar.MONTH: 12가 입력되면 월1, 년 + 1  
//			API에서 자동으로 Calendar.MONTH: -1가 입력되면 월12, 년 - 1  
			firstDay.set(Calendar.MONTH, targetMonth);
			
//			year과 month 값 재정의
			targetYear = firstDay.get(Calendar.YEAR);
			targetMonth = firstDay.get(Calendar.MONTH);
		}
		System.out.println(targetYear + " <- targetYear");
		System.out.println(targetMonth + " <- targetMonth");
		
//		달력출력시 시작공백
//		1일 날짜의 요일(일1, 월2, ... 토6) - 1
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println(beginBlank + " <- beginBlank");
		
//		출력되는 월의 마지막 날짜
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		System.out.println(lastDate + " <- lastDate");
		
//		달력출력시 마지막 날짜 출력 후 공백 -> 전체 출력 셀의 개수가 7로 나누어 떨어져야 한다.
		int endBlank = 0;
		if((beginBlank + lastDate) % 7 != 0) {
			endBlank = 7 - ((beginBlank + lastDate) % 7);
		}
		int totalCell = beginBlank + lastDate + endBlank;
		System.out.println(endBlank + " <- endBlank");
		System.out.println(totalCell + " <- totalCell");
		
//		뷰에 값넘기기(request 속성)
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("totalCell", totalCell);
		request.setAttribute("beginBlank", beginBlank);
		request.setAttribute("endBlank", endBlank);
//		달력을 출력하는 뷰
		request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
	}
}
