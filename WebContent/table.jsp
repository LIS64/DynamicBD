<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<% ArrayList<String> columns = new ArrayList<>();
   ArrayList<String> rows = new ArrayList<>();
   columns = (ArrayList<String>) request.getAttribute("columns");
    if (columns!=null && columns.size()>0){ %>
<table border="1">
<tr>
<% for (int i=0; i<columns.size(); i++){ %>
<td><center><b><%=columns.get(i) %></b></center></td>
<%}%>
</tr>

 <% rows = (ArrayList<String>) request.getAttribute("rows");
     if (rows!=null && rows.size()>0){  
      String[] row = null;
       for (int i=0; i<rows.size(); i++){
        row = rows.get(i).split("'"); %>
 <tr>
 	  <% for (int j=0; j< row.length; j++) { %>
 	     <td><%=row[j] %></td>
 	   <% } %>
 </tr>
    <% } %>
 <% } %>
 </table>
<% } %>
</body>
</html>