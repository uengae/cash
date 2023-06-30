<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>removeMember.jsp</title>
</head>
<body>
<div>
	<h1>회원 탈퇴 페이지</h1>
	<form action="${pageContext.request.contextPath}/removeMember" method="post">
		비밀번호 확인 : <input name="memberPw" type="password">
		<button type="submit">탈퇴</button>
	</form>
</div>
</body>
</html>