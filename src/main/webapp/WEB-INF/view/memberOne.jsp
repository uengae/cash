<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import = "cash.vo.*" %>
<%

	Member member = (Member) request.getAttribute("member");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberOne.jsp</title>
</head>
<body>
	<h1>멤버 상세 정보</h1>
	<p>id : <%=member.getMemberId()  %></p>
	<p>pw :<%=member.getMemberpw() %> </p>
	<p>가입일자 : <%=member.getCreatedate() %></p>
	<p>수정일자 : <%=member.getUpdatedate() %></p>
	<a href="${pageContext.request.contextPath}/modifyMember">정보수정</a>
	<a href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
</body>
</html>