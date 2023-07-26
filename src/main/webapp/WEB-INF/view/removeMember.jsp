<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>removeMember.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div>
	<div class="container">
		&nbsp;
		<h1>회원 탈퇴 페이지</h1>
		<form action="${pageContext.request.contextPath}/removeMember" method="post">
			비밀번호 확인 : <input name="memberPw" type="password">
			<button class="btn btn-outline-secondary" type="submit">탈퇴</button>
		</form>
	</div>
</div>
</body>
</html>