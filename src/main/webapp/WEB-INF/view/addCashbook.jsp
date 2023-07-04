<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>${targetYear}년 ${targetMonth + 1}월 ${day}일 수입/지출 추가</h1>
	<form action="${pageContext.request.contextPath}/addCashbook" method="post">
		<input type="hidden" value="${targetYear}" name="targetYear">
		<input type="hidden" value="${targetMonth}" name="targetMonth">
		<input type="hidden" value="${day}" name="day">
		<table>
			<tr>
				<th>수입/지출</th>
				<td>
					<select name="category">
						<option value="수입">수입</option>
						<option value="지출">지출</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>금액</th>
				<td>
					<input type="number" name="price" required="required">
				</td>
			</tr>
			<tr>
				<th>메모</th>
				<td>
					<textarea name="memo" required="required"></textarea>
				</td>
			</tr>
		</table>
		<button type="submit">입력</button>
	</form>
</body>
</html>