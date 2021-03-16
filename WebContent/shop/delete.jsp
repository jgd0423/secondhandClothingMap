<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<form name="deleteForm">
	<input type="hidden" name="no" id="no" value="${dto.no }" />
	<table border="1" align="center" width="80%">
		<tr>
			<td colspan="2">
				<h2>가게정보삭제</h2>
			</td>
		</tr>
		<tr>
			<td style="align: center;">id</td>
			<td>${dto.id }</td>
		</tr>
		<tr>
			<td style="align: center;">가게이름</td>
			<td>${dto.shopName }</td>
		</tr>
		<tr>
			<td style="align: center;">주소</td>
			<td>${dto.address }</td>
		</tr>
		<tr>
			<td style="align: center;">위도(latitude)</td>
			<td>${dto.latitude }</td>
		</tr>
		<tr>
			<td style="align: center;">경도(longitude)</td>
			<td>${dto.longitude }</td>
		</tr>
		<tr>
			<td style="align: center;">인스타그램</td>
			<td>${dto.instagram }</td>
		</tr>
		<tr>
			<td style="align: center;">shop URL</td>
			<td>${dto.shopUrl }</td>
		</tr>
		<tr>
			<td style="align: center;">등록일</td>
			<td>${dto.regiDate }</td>
		</tr>
		<tr>
			<td align="center" colspan="2" height="50px">
				<button type="button" id="btnDelete">삭제하기</button>
				<button type="button" id="btnList">목록으로</button>
			</td>
		</tr>
	</table>
</form>

<script>

$(document).ready(() => {
	$("#btnDelete").click(() => {
		if (confirm('삭제하시겠습니까?')) {
			goDelete();
		}
	});
	
	$("#btnList").click(() => {
		goList();
	});
});

function goDelete() {
	document.deleteForm.method = 'post';
	document.deleteForm.action = '${path}/shop_servlet/deleteProc.do';
	document.deleteForm.submit();
}

function goList() {
	location.href = '${path}/shop_servlet/list.do';
}

</script>