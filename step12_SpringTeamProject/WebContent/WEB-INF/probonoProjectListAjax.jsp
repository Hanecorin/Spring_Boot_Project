<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>모든 Probono Project list 화면 : probonoProjectListAjax.jsp</title>
</head>
<body>
<br><br><br>
<center> 
<h3>재능 기부 프로젝트 list(Ajax)</h3>
<hr><p>

<table border="1">
	<tr>   
		<th>프로젝트 id</th><th>프로젝트명</th><th>재능기부자 id</th>
		<th>재능 수혜자  id</th><th>재능기부 내용</th>
	</tr>

	 <c:forEach items="${requestScope.probonoProjectAll}" var="data">
		 <tr>
		 	<td>${data.probonoProjectId}</td>
		 	<td>${data.probonoProjectName}</td>
		 	
		 	<%-- 
		 		<td><a href="/step08_refectoring_s/probono/activist?activistId=giver1">giver1</a></td> 
		 		${pageContext.request.contextPath}
		 			- project명까지 정확하게 인식하게 되는 EL tag의 표현법
		 	--%>
		 	<td><a href="${pageContext.request.contextPath}/probono/activist?activistId=${data.activistId}">
		 		${data.activistId}
		 	</a></td>
		 	<td><a href="${pageContext.request.contextPath}/probono/recipient?recipientId=${data.receiveId}">${data.receiveId}</a></td>
		 	<td>${data.projectContent}</td>
		 </tr>
	 </c:forEach>
	
	
	
	
 
</table>

<br><br><br>
<font color="blue">재능 기부자 id 및 재능 수혜자 id를 클릭하면 상세 정보 확인이 가능합니다</font>
&nbsp;&nbsp;&nbsp;<a href="index.html">메인 화면 이동</a>

</center>
</body>
</html>