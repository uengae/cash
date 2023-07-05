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
import com.google.gson.Gson;
import com.google.gson.JsonParser;

@WebServlet("/checkMember")
public class CheckMember extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		MemberDao dao = new MemberDao();
		int row = 0;
		if(request.getParameter("memberPw") == null) {
			row = dao.idCheck(memberId);
		} else {
			row = dao.idCheck(memberId, memberPw);
		}
		System.out.println(row);
		
		//json 변환 라이브러리
		Gson gson = new Gson();	
		
		//자바객체 변수값을  json 문자열로 변경
		String jsonStr = gson.toJson(row);
		response.getWriter().print(jsonStr);
	}

}
