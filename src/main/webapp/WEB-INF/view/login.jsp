<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
</head>
<body>
	<h1>로그인</h1>
	<!-- 중복되도 상관없는 이유 : get으로 실행되면 컨트롤러에서 doGet으로 받고, post방식으로 오면 getPost로 받아서 이렇게 표현해도 괜찮다. -->
	<form action="${pageContext.request.contextPath}/login" method="post">
		<table border="1">
			<tr>
				<td>Member Id</td>
				<td>	<input type ="text" name="memberId"></td>
			</tr>
			<tr>
				<td>Member Pw</td>
				<td>	<input type ="password" name="memberPw"></td>
			</tr>
		</table>
		<button type="submit">로그인</button>
		<a href="${pageContext.request.contextPath}/addMember">회원가입</a>
	</form>
</body>
</html>