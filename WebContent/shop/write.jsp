<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<form name="writeForm" id="writeForm">
	<table border="1" align="center" width="50%">
		<tr>
			<td colspan="2"><h2>가게정보 입력</h2></td>
		</tr>
		<tr>
			<td>가게이름</td>
			<td><input style="width: 100%;" type="text" name="name" id="name" /></td>
		</tr>
		<tr>
			<td>위도(latitude)</td>
			<td><input style="width: 100%;" type="text" name="latitude" id="latitude" /></td>
		</tr>
		<tr>
			<td>경도(longitude)</td>
			<td><input style="width: 100%;" type="text" name="longitude" id="longitude" /></td>
		</tr>
		<tr>
			<td>인스타그램</td>
			<td><input style="width: 100%;" type="text" name="instagram" id="instagram" /></td>
		</tr>
		<tr>
			<td>주소</td>
			<td><input style="width: 100%;" type="text" name="address" id="address" /></td>
		</tr>
		<tr>
			<td>shopUrl</td>
			<td><input style="width: 100%;" type="text" name="shopUrl" id="shopUrl" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="height: 50px;">
				<button type="button" onclick="goWrite()">저장하기</button>
				<button type="button" onclick="goList()">목록으로</button>
				<button type="button" onclick="goMap()">지도로</button>
			</td>
		</tr>
	</table>
</form>

<script>

function goWrite() {
	document.writeForm.method = 'post';
	document.writeForm.action = '${path}/shop_servlet/writeProc.do';
	document.writeForm.submit();
}

function goList() {
	location.href = '${path}/shop_servlet/list.do';	
}

function goMap() {
	location.href = '${path}/shop_servlet/map.do';	
}

</script>