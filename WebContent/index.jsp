<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Тестовое задание</title>
</head>
<body>
<center>Таблица из БД</center>
<br/>
<form action="processingDB" method="post">
<br/>
Имя таблицы: <input type="text" name="tableName" />
<input type="submit" value="Загрузить" />
</form>
<br/>
<br/>
<jsp:include page="error.jsp" flush="true" />
<jsp:include page="table.jsp" flush="true" />
</body>
</html>