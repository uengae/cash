<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbook.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
	<h1>${word}태그 캐쉬북</h1>
	<!-- 컨트롤러 매핑 이름 : logout -->
	<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/calendar">달력</a>
	<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/memberOne">회원정보</a>
	<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/logout">로그아웃</a>
	
	<table class="table table-bordered">
		<tr>
			<th>수입/지출</th>
			<th>금액</th>
			<th>메모</th>
			<th>작성날짜</th>
		</tr>
		<c:set var="c" value="${list}"></c:set>
		<c:forEach var="c" items="${list}">
		<tr>
			<td>${c.category}</td>
			<td>${c.price}</td>
			<td>${c.memo}</td>
			<td>${fn:substring(c.createdate, 0, 10)}</td>
		</tr>
		</c:forEach>
	</table>
	<table>
		<tr>
			<c:if test="${beginPage > 1}">
				<td>
					<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${beginPage + pagePerPage}">
						이전
					</a>
				</td>
			</c:if>
			<c:forEach var="i" begin="${beginPage}" end="${endPage}">
				<td>
					<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${i}">
						${i}
					</a>
				</td>
			</c:forEach>
			<c:if test="${endPage < tatalTagPage}">
				<td>
					<a href="${pageContext.request.contextPath}/cashbookListByTag?word=${word}&currentPage=${beginPage + pagePerPage}">
						다음
					</a>
				</td>
			</c:if>
		</tr>
	</table>
</body>
</html>