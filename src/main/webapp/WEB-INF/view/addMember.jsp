<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addMember.jsp</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.0/dist/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<script>
	$(document).ready(function() {
		let checkId = false;
	  $("#ckbtn").click(function() {
	    // AJAX 요청 보내기
	    $.ajax({
	      url: '${pageContext.request.contextPath}/checkMember', // 중복 체크를 수행하는 서블릿 주소
	      type: 'post',
	      data: { 'memberId': $('#memberId').val()}, // 서버로 보낼 데이터
	      dataType: 'json',
	      success: function(response) {
	        // 중복 여부에 따라 처리
	        if (response == 1) {
	          alert("이미 사용 중인 아이디입니다.");
	        } else {
	          alert("사용 가능한 아이디입니다.");
	          checkId = true;
	        }
	      },
	      error: function(response) {
	        // 에러 처리
	        console.log(error);
	        alert("중복 체크 요청을 실패했습니다.");
	      }
	    });
	  });
	  $('#addBtn').click(function(){
		  if ($('#memberId').val() == ''){
	        alert("아이디 입력해주세요");
	        return false;
		  } 
		  if (!checkId){
	        alert("아이디 중복검사해주세요");
	        return false;
		  } 
		  if ($('#memberPw').val() == ''){
	        alert("비밀번호 입력해주세요");
	        return false;
		  } 
		  if ($('#checkPw').val() == ''
				  || $('#checkPw').val() != $('#memberPw').val()){
	        alert("비밀번호가 서로 다릅니다.");
	        return false;
		  }
		  $('#addForm').submit();
	  })
	});
	</script>

</head>
<body>
	<!-- 중복되도 상관없는 이유 : get으로 실행되면 컨트롤러에서 doGet으로 받고, post방식으로 오면 getPost로 받아서 이렇게 표현해도 괜찮다. -->
	<form action="${pageContext.request.contextPath}/addMember" method="post" id="addForm">
		<div class="container-sm" style="width: 50%">
			<table class="table caption-top table-bordered">
				<caption>
					<h3>회원가입</h3>
				</caption>
				<tr>
					<td>아이디</td>
					<td>
						<input type ="text" name="memberId" id="memberId">
					</td>
					<td>
						<button class="btn btn-outline-secondary" type="button" id="ckbtn">중복체크</button>
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td colspan="2">
						<input type ="password" name="memberPw" id="memberPw">
					</td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td colspan="2">
						<input type ="password" id="checkPw">
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<button class="btn btn-primary" type="button" id="addBtn">회원가입</button>
						<a class="btn btn-secondary" href="${pageContext.request.contextPath}/login">취소</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>