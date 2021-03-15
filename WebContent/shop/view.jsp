<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<form name="viewForm">
	<table border="1" align="center" width="80%">
		<tr>
			<td colspan="2">
				<h1>가게 상세보기</h1>
				<input type="hidden" name="no" value="${dto.no }" />
			</td>
		</tr>
		<tr>
			<td width="150px">id : </td>
			<td>${dto.id}</td>
		</tr>
		<tr>
			<td>가게이름 : </td>
			<td>${dto.name}</td>
		</tr>
		<tr>
			<td>위도 : </td>
			<td>${dto.latitude}</td>
		</tr>
		<tr>
			<td>경도 : </td>
			<td>${dto.longitude}</td>
		</tr>
		<tr>
			<td>인스타그램 : </td>
			<td>${dto.instagram}</td>
		</tr>
		<tr>
			<td>주소 : </td>
			<td>${dto.address}</td>
		</tr>
		<tr>
			<td>shop URL : </td>
			<td>${dto.shopUrl}</td>
		</tr>
		<tr>
			<td>등록일 : </td>
			<td>${dto.regiDate}</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="goWrite()">글쓰기</button>
				<button type="button" onclick="goModify(${dto.no})">수정하기</button>
				<button type="button" onclick="goDelete(${dto.no})">삭제하기</button>
				<button type="button" onclick="goList()">목록으로</button>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="50px">
				<table border="1" width="100%" align="center">
					<tr>
						<td width="100px">이전글 : </td>
						<td>
							<c:if test="${dto.preName == null }">
								이전글이 없습니다.
							</c:if>
							<c:if test="${dto.preName != null }">
								<a href="#" onclick="goNearPage(${dto.preNo})">${dto.preName }</a>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="100px">다음글 : </td>
						<td>
							<c:if test="${dto.nxtName == null }">
								다음글이 없습니다.
							</c:if>
							<c:if test="${dto.nxtName != null }">
								<a href="#" onclick="goNearPage(${dto.nxtNo})">${dto.nxtName }</a>
							</c:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>


<script>

function goNearPage(no) {
	location.href = `${path}/board2_servlet/view.do?no=\${no}`;
}

function goList() {
	location.href = '${path}/board2_servlet/list.do';
}

function goWrite() {
	location.href = '${path}/board2_servlet/write.do';
}

function goView(no) {
	document.viewForm.method = 'post';
	document.viewForm.action = `${path}/board2_servlet/view.do?no=\${no}`;
	document.viewForm.submit();
}

function goReply(no) {
	location.href = `${path}/board2_servlet/reply.do?no=\${no}`;
}

function goModify(no) {
	location.href = `${path}/board2_servlet/modify.do?no=\${no}`;
}

function goDelete(no) {
	location.href = `${path}/board2_servlet/delete.do?no=\${no}`;
}

</script>