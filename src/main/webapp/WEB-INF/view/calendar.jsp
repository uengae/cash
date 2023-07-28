<%@page import="cash.controller.ModifyMemberController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- JSP 컴파일시 자바코드로 변환되는 c:... (제어문법코드) 커스텀 태크 사용가능 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script>
		$(document).ready(function(){
			$('#preMonthBtn').click(function(){
					$('#targetMonth').val(${targetMonth} - 1);
					$('#changeMonthForm').submit();
			})
			$('#nextMonthBtn').click(function(){
				$('#targetMonth').val(${targetMonth} + 1);
				$('#changeMonthForm').submit();
			})
		})
	</script>
</head>
<body>
	<div class="container">
		<!-- 변수값 or 반환값 : EL사용 $ {표현식} -->
		<!-- 
			 속성값 대신 EL 사용
			 ex)
			 request.getAttribute("targetYear") -- $ {requestScope.targetYear} 보통 requestScope 는 생략
			 형변환연산이 필요없다.(EL이 자동으로 처리)
		 -->
		<!-- 자바코드(제어문) : JSTL 사용 하여 java코드 없어짐 -->
		<form action="${pageContext.request.contextPath}/calendar" method="get" id="changeMonthForm">
			&nbsp;
			<h3 style="text-align : center">
				<input type="hidden" name="targetMonth" id="targetMonth">
				<input type="hidden" name="targetYear" value="${targetYear}">
				<button class="btn btn-outline-primary" type="button" id="preMonthBtn">이전달</button>
				${targetYear}년 ${targetMonth + 1}월 달력
				<button class="btn btn-outline-primary" type="button" id="nextMonthBtn">다음달</button>
			</h3>
		</form>
		<div style="text-align : right">
			<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/memberOne">회원정보</a>
			<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/logout">로그아웃</a>
		</div>
		<div>
			<h4>이달의 해시테그</h4>
			<div>
				<c:forEach var="m" items="${htList}">
					<a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/cashbookListByTag?word=${m.word}">
						${m.word}(${m.cnt})
					</a>
				</c:forEach>
			</div>
		</div>
		<table class="table table-bordered caption-top">
		<caption>
			<h5>현재 접속자 : ${currentCounter}</h5>
		</caption>
			<thead class="table-dark">
				<tr>
					<th style="width: 14%;">일</th>
					<th style="width: 14%;">월</th>
					<th style="width: 14%;">화</th>
					<th style="width: 14%;">수</th>
					<th style="width: 14%;">목</th>
					<th style="width: 14%;">금</th>
					<th style="width: 14%;">토</th>
				</tr>
			</thead>
			<tr>
				<c:forEach var="i" begin="0" end="${totalCell - 1}" step="1">
					<c:set var="d" value="${i - beginBlank + 1}"></c:set>
					
					<c:if test="${i != 0 && i % 7 == 0}">
						</tr>
						<tr>
					</c:if>
					
					<c:if test="${d < 1 || d > lastDate}">
						<td></td>
					</c:if>
	
					<c:if test="${!(d < 1 || d > lastDate)}">
					
						<td style="height: 200px;">
							<div>
								<a href="${pageContext.request.contextPath}/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&day=${d}">
									${d}
								</a>
							</div>
							<c:forEach var="c" items="${list}">
								<c:if test="${d == fn:substring(c.cashbookDate, 8, 10)}">
									<div>
										<c:if test="${c.category == '총 수입'}">
											<span>총 수입 : ${c.price}</span>
										</c:if>
										<c:if test="${c.category == '총 지출'}">
											<span style="color:red;">총 지출 : ${c.price}</span>
										</c:if>
										<c:if test="${c.category == '수입'}">
											<span>+${c.price}</span>
										</c:if>
										<c:if test="${c.category == '지출'}">
											<span style="color:red;">-${c.price}</span>
										</c:if>
									</div>
								</c:if>
							</c:forEach>
						</td>
					</c:if>
				</c:forEach>
			</tr>
		</table>
	</div>
</body>
</html>