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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
	<div class="container">
	<h3>멤버 상세 정보</h3>
	<p>id : <%=member.getMemberId()  %></p>
	<p>pw :<%=member.getMemberpw() %> </p>
	<p>가입일자 : <%=member.getCreatedate() %></p>
	<p>수정일자 : <%=member.getUpdatedate() %></p>
	<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/modifyMember">정보수정</a>
	<a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
	</div>
</body>
</html>