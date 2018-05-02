<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta content="text/html;charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link th:href="@{/webjars/bootstrap/css/bootstrap.min.css}" rel="stylesheet"/>
    <link th:href="@{/webjars/bootstrap/css/bootstrap-theme.min.css}" rel="stylesheet"/>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">访问model</h3>
    </div>
    <h1>Welcome ${singlePerson.name}!</h1>

</div>

<script th:src="@{/webjars/jquery/jquery.min.js}"></script>
<script th:src="@{/webjars/bootstrap/js/bootstrap.min.js}"></script>
<script type="application/javascript" th:src="@{/js/index.js}"></script>
</body>
</html>