<%@page import="cash.controller.modifyMemberController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- JSP 컴파일시 자바코드로 변환되는 c:... (제어문법코드) 커스텀 태크 사용가능 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		table{
			width: 100%;
		}
	</style>
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
	<!-- 변수값 or 반환값 : EL사용 $ {표현식} -->
	<!-- 
		 속성값 대신 EL 사용
		 ex)
		 request.getAttribute("targetYear") -- $ {requestScope.targetYear} 보통 requestScope 는 생략
		 형변환연산이 필요없다.(EL이 자동으로 처리)
	 -->
	<!-- 자바코드(제어문) : JSTL 사용 하여 java코드 없어짐 -->
	<h1>${targetYear}년 ${targetMonth + 1}월 달력</h1>
	<form action="${pageContext.request.contextPath}/calendar" method="get" id="changeMonthForm">
		<input type="hidden" name="targetMonth" id="targetMonth">
		<input type="hidden" name="targetYear" value="${targetYear}">
		<button type="button" id="preMonthBtn">이전달</button>
		<button type="button" id="nextMonthBtn">다음달</button>
	</form>
	<div>
		<h2>이달의 해시테그</h2>
		<div>
			<c:forEach var="m" items="${htList}">
				<a href="">
					${m.word}(${m.cnt})
				</a>
			</c:forEach>
		</div>
	</div>
	<table>
		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>
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
					<td>
						<div>
							<a href="${pageContext.request.contextPath}/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&day=${d}">
								${d}
							</a>
						</div>
						<c:forEach var="c" items="${list}">
							<c:if test="${d == fn:substring(c.cashbookDate, 8, 10)}">
								<div>
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
	
	<%-- <table>
		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>
		<tr>
		<%
			for(int i = 0; i < totalCell; i++){
		%>
		<%
				if (i < beginBlank || i >= (beginBlank + lastDate)){
		%>
					<td>
						&nbsp;
					</td>
		<%
				} else {
		%>
					<td>
						<%=i - beginBlank + 1%>
					</td>
		<%
				}
		
				if ((i % 7) == 6){
		%>
				</tr>
				<tr>
		<%
				}
			}
		%>
		</tr>
	</table> --%>
</body>
</html>