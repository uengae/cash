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
</head>
<body>
	<!-- 중복되도 상관없는 이유 : get으로 실행되면 컨트롤러에서 doGet으로 받고, post방식으로 오면 getPost로 받아서 이렇게 표현해도 괜찮다. -->
	<form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
		<div class="container-sm" style="width: 50%">
			<table class="table caption-top table-bordered">
				<caption>
					<h3>캐시북 로그인</h3>
				</caption>
				<tr>
					<th>아이디</th>
					<td>
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
						 ID저장 <input type="checkbox" name="idSave">
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
</body>
</html>