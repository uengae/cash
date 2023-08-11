<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<title>login.jsp</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.0/dist/jquery.min.js"></script>
	<script>
	$(document).ready(function() {
		  $("#loginBtn").click(function() {
	
		    // AJAX 요청 보내기
		    $.ajax({
		      url: '${pageContext.request.contextPath}/checkMember', // 중복 체크를 수행하는 서블릿 주소
		      type: 'post',
		      data: { 'memberId': $('#memberId').val(),
		    	  'memberPw': $('#memberPw').val()}, // 서버로 보낼 데이터
		      dataType: 'json',
		      success: function(response) {
		        // 중복 여부에 따라 처리
		        if (response == 1) {
		          $('#loginForm').submit();
		        } else {
		          alert("없는 아이디 이거나 틀린 비밀번호 입니다.");
		        }
		      },
		      error: function(response) {
		        // 에러 처리
		        console.log(error);
		        alert("로그인에 실패하였습니다.");
		      }
		    });
		  });
		});
	</script>
	<!--  -->
</head>
<body>
	<!-- 중복되도 상관없는 이유 : get으로 실행되면 컨트롤러에서 doGet으로 받고, post방식으로 오면 getPost로 받아서 이렇게 표현해도 괜찮다. -->
	<form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
		<div class="container-sm" style="width: 50%">
			<table class="table caption-top table-bordered">
				<caption>
					<h3>캐시북 로그인</h3>
					<h4>
						test id : user1, 1234
					</h4>
				</caption>
				<tr>
					<th>아이디</th>
					<td>
					<!-- 로그인 되게 아이디 비번 넣어두기 -->
						<input type ="text" name="memberId" id="memberId" value="${loginId}">
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type ="password" name="memberPw" id="memberPw">
					</td>
				</tr>
				<tr>
					<th>
						 ID저장 <input type="checkbox" name="idSave" ${idCheck}>
					</th>
					<td>
						<button class="btn btn-secondary" type="button" id="loginBtn">로그인</button>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/addMember">회원가입</a>
					</td>
				</tr>
			</table>
			<div>
				 오늘 방문자 : ${counter}
			</div>
			<div>
				 총 방문자 : ${totalCounter}
			</div>
		</div>
	</form>
	<hr>
	<!-- 프로젝트 관련 내용 -->
	<div class="container">
		<h3>cashbook프로젝트</h3>
		프로젝트 내용 : 가계부 작성<br>
		<br>
		사용 툴: Eclipse, HeidiSQL<br>
		사용언어: java, SQL, jsp, jQuery, ajax, EL, JSTL<br>
		사용 DB: MariaDB(10.5)<br>
		사용 WAS: Tomcat (9.0)<br>
		<br>
		Model 2 방식으로 만든 개인 프로젝트<br>
		Java의 Calendar API를 이용하여 달력을 만들어 가계부를 구성<br>
		세션을 통해 로그인 기능을 구현<br>
		cookie를 이용하여 id 저장 기능 구현<br>
		ajax를 이용하여 비동기식으로 id, pw 체크 기능을 구현<br>
		listener를 이용하여 방분자 수 체크와 현재 접속자를 체크할 수 있도록 구현<br>
		filter를 이용하여 모든페이지가 utf-8로 인코딩되게 구현<br>
		service를 이용하여 트랜잭션을 구현<br>
		<br>
		기간 2023.06.30 ~ 2023.07.14<br>
	</div>
</body>
</html>