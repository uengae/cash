<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import = "cash.vo.*" %>
<%

	Member member = (Member) request.getAttribute("member");
	System.out.println(request.getAttribute("member") +"<--getAttribute member-- modifyMember.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyMember.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<h1>멤버 정보 수정</h1>
	<form action="${pageContext.request.contextPath}/modifyMember" method="post">
		<p>
			id : <%=member.getMemberId() %>
			<input type="hidden" name="memberId" value="<%=member.getMemberId()%>">
		</p>
		<div>
			<p>
				기존 비밀번호 : <input type="password" name="beforePw">
			</p>
			<p>
				새로운 비밀번호 :	<input type="password" name="newPw" id="newPw">
			</p>
			<p>
				비밀번호 확인 :	<input type="password" id="pwCk">
			</p><button type="button" id="ckBtn">비밀번호 확인</button>
		</div>
		<p>가입일자 : <%=member.getCreatedate() %></p>
		<p>수정일자 : <%=member.getUpdatedate() %></p>
		<button type="submit" id="modifyBtn" disabled>수정</button>
	</form>
</body>
<script>
	$('#ckBtn').click(function(){
		//alert('ckbtn click');		
		//공백일 경우 메시지
		if ($('#newPw').val() === undefined || $('#newPw').val() === '' 
		|| $('#pwCk').val() === undefined || $('#pwCk').val() === '') {
			alert('비밀번호 칸이 비었습니다.');
			$('#modifyBtn').prop('disabled', true); // 수정 버튼 비활성화
		} else if ($('#newPw').val() != $('#pwCk').val()) {
			alert('비밀번호가 일치하지 않습니다.');
			$('#modifyBtn').prop('disabled', true); // 수정 버튼 비활성화
		} else {
			alert('비밀번호가 일치합니다.');
			$('#modifyBtn').prop('disabled', false); // 수정 버튼 활성화
		}
	})
</script>
</html>