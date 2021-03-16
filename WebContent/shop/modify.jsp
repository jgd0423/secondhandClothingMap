<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<form name="modifyForm">
	<input type="hidden" name="no" id="no" value="${dto.no }" />
	<table border="1" align="center" width="80%">
		<tr>
			<td colspan="2">
				<h2>가게정보수정</h2>
			</td>
		</tr>
		<tr>
			<td style="align: center;">id</td>
			<td>${dto.id }</td>
		</tr>
		<tr>
			<td style="align: center;">가게이름</td>
			<td><input style="width: 100%;" type="text" name="shopName" id="shopName" value="${dto.shopName }" /></td>
		</tr>
		<tr>
			<td style="align: center;">주소</td>
			<td><input style="width: 100%;" type="text" name="address" id="address" value="${dto.address }" /></td>
		</tr>
		<tr>
			<td style="align: center;">위도(latitude)</td>
			<td><input style="width: 100%;" type="text" name="latitude" id="latitude" value="${dto.latitude }" /></td>
		</tr>
		<tr>
			<td style="align: center;">경도(longitude)</td>
			<td><input style="width: 100%;" type="text" name="longitude" id="longitude" value="${dto.longitude }" /></td>
		</tr>
		<tr>
			<td style="align: center;">인스타그램</td>
			<td><input style="width: 100%;" type="text" name="instagram" id="instagram" value="${dto.instagram }" /></td>
		</tr>
		<tr>
			<td style="align: center;">shop URL</td>
			<td><input style="width: 100%;" type="text" name="shopUrl" id="shopUrl" value="${dto.shopUrl }" /></td>
		</tr>
		<tr>
			<td style="align: center;">등록일</td>
			<td>${dto.regiDate }</td>
		</tr>
		<tr>
			<td align="center" colspan="2" height="50px">
				<button type="button" id="btnModify">수정하기</button>
				<button type="button" id="btnList">목록으로</button>
			</td>
		</tr>
	</table>
</form>

<script>

$(document).ready(() => {
	$("#btnModify").click(() => {
		goModify();
	});
	
	$("#btnList").click(() => {
		goList();
	});
});

function goModify() {
	document.modifyForm.method = 'post';
	document.modifyForm.action = '${path}/shop_servlet/modifyProc.do';
	document.modifyForm.submit();
}

function goList() {
	location.href = '${path}/shop_servlet/list.do';
}

</script>