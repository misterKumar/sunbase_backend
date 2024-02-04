<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css">
    <title>Add Customer</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
    <div class="container">
        <h2>Add Customer</h2>
        <form action="${pageContext.request.contextPath}/add-customer" method="post">
            <!-- Add your form fields for customer details here -->
            <button type="submit" class="btn">Add Customer</button>
        </form>
    </div>
</body>
</html>
