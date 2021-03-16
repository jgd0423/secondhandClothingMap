<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

${allRowsCount }개의 레코드가 있습니다.

<form name="listForm">
	<table border="1" align="center" width="95%">
		<tr>
			<td colspan="10" align="center">
				<h2>가게 리스트</h2>
			</td>
		</tr>
		<tr>
			<td colspan="10" align="center">
				<select name="search_option" id="search_option">
					<c:choose>
						<c:when test="${search_option == 'name' }">
							<option value="">- 선택 -</option>
							<option value="name" selected>이름</option>
							<option value="instagram">인스타</option>
							<option value="shopUrl">shopUrl</option>
							<option value="name_instagram_shopUrl">이름+인스타+shopUrl</option>
						</c:when>
						<c:when test="${search_option == 'instagram' }">
							<option value="">- 선택 -</option>
							<option value="name">이름</option>
							<option value="instagram" selected>인스타</option>
							<option value="shopUrl">shopUrl</option>
							<option value="name_instagram_shopUrl">이름+인스타+shopUrl</option>
						</c:when>
						<c:when test="${search_option == 'shopUrl' }">
							<option value="">- 선택 -</option>
							<option value="name">이름</option>
							<option value="instagram">인스타</option>
							<option value="shopUrl" selected>shopUrl</option>
							<option value="name_instagram_shopUrl">이름+인스타+shopUrl</option>
						</c:when>
						<c:when test="${search_option == 'name_instagram_shopUrl' }">
							<option value="">- 선택 -</option>
							<option value="name">이름</option>
							<option value="instagram">인스타</option>
							<option value="shopUrl">shopUrl</option>
							<option value="name_instagram_shopUrl" selected>이름+인스타+shopUrl</option>
						</c:when>
						<c:otherwise>
							<option value="">- 선택 -</option>
							<option value="name">이름</option>
							<option value="instagram">인스타</option>
							<option value="shopUrl">shopUrl</option>
							<option value="name_instagram_shopUrl">이름+인스타+shopUrl</option>
						</c:otherwise>
					</c:choose>
				</select>
				
				<input type="text" name="search_data" id="search_data" value="${search_data }" style="width: 150px;" />
				&nbsp;
				<input type="button" value="검색" onclick="search();" />		
			</td>
		</tr>
		<tr>
			<td>순번</td>
			<td>id</td>
			<td>가게이름</td>
			<td>위도(latitude)</td>
			<td>경도(longitude)</td>
			<td>인스타그램</td>
			<td>주소</td>
			<td>shop Url</td>
			<td>등록일</td>
		</tr>
		<c:if test="${list.size() == 0 }">
			<tr>
				<td colspan="10" height="200" align="center">
					등록된 가게가 없습니다.
				</td>
			</tr>
		</c:if>
		<c:if test="${list.size() > 0 }">
			<c:forEach var="dto" items="${list }">
				<tr>
					<td>${tableRowNum }</td>
					<td>${dto.id }</td>
					<td>
						<a href="#" onclick="goView('${dto.no }')"> ${dto.shopName }</a>
					</td>
					<td>${dto.latitude }</td>
					<td>${dto.longitude }</td>
					<td>${dto.instagram }</td>
					<td>${dto.address }</td>
					<td>${dto.shopUrl }</td>
					<td>${dto.regiDate }</td>
				</tr>
				<c:set var="tableRowNum" value="${tableRowNum = tableRowNum - 1 }"/>
			</c:forEach>
		</c:if>
		<tr>
			<td colspan="10" height="50" align="center">
				<a href="#" onclick="choosePage(1, '${search_option }', '${search_data }')"><<</a>
				<c:choose>
					<c:when test="${pageNum - 1 <= 0 }">
						<a href="#" onclick="choosePage(${pageNum}, '${search_option }', '${search_data }')"><</a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="choosePage(${pageNum - 1 }, '${search_option }', '${search_data }')"><</a>
					</c:otherwise>
				</c:choose>
				<c:forEach var="i" begin="${pagingStartNum }" end="${pagingEndNum }" step="1" >
					<c:choose>
						<c:when test="${pageNum == i }">
							<b>[${i }]</b>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="choosePage(${i }, '${search_option }', '${search_data }')">${i }</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${pageNum + 1 >= maxPagesCount }">
						<a href="#" onclick="choosePage(${maxPagesCount }, '${search_option }', '${search_data }')">></a>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="choosePage(${pageNum + 1 }, '${search_option }', '${search_data }')">></a>
					</c:otherwise>
				</c:choose>
				<a href="#" onclick="choosePage(${maxPagesCount }, '${search_option }', '${search_data }')">>></a>
			</td>
		</tr>
		<tr>
			<td colspan="10" align="right">
				<button type="button" onclick="chooseAll()">전체목록</button>&nbsp;
				<button type="button" onclick="goWrite()">가게추가</button>&nbsp;
			</td>
		</tr>
	</table>
</form>
<br>

<script>

function choosePage(pageNumber, search_option, search_data) {
	let url = '';
	url += `${path}/shop_servlet/list.do?pageNumber=\${pageNumber}`;
	url += `&search_option=\${search_option}`;
	url += `&search_data=\${search_data}`;
	location.href = url;
}

function search() {
	document.listForm.method = 'post';
	document.listForm.action = '${path}/shop_servlet/list.do';
	document.listForm.submit();
}

function chooseAll() {
	location.href = '${path}/shop_servlet/list.do';
}

function goView(no) {
	location.href = `${path}/shop_servlet/view.do?no=\${no}`;
}

function goWrite() {
	location.href = '${path}/shop_servlet/write.do';
}

</script>