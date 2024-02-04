<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/scripts.js"></script>
    <title>Customer List</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Customer List</h2>
        <button class="btn btn-sync">Sync</button>
        <button class="btn btn-logout">Logout</button>
        <div class="table-container">
            <!-- Customer list table will be displayed here -->
        </div>
    </div>
</body>
</html>
