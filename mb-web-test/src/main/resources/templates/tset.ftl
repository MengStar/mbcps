<#-- @ftlvariable name="urls" type="org.springframework.web.servlet.resource.ResourceUrlProvider" -->
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html;charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <link href="${urls.getForLookupPath('/webjars/bootstrap/css/bootstrap.min.css')}" rel="stylesheet"/>
    <link href="${urls.getForLookupPath('/webjars/bootstrap/css/bootstrap-theme.min.css')}" rel="stylesheet"/>
    <script src="${urls.getForLookupPath('/webjars/bootstrap/js/bootstrap.min.js')}"></script>
    <script src="${urls.getForLookupPath('/webjars/bootstrap/js/bootstrap.min.js')}"></script>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">访问model</h3>
    </div>
    <h1>Welcome ${singlePerson.name}!</h1>
    <script src="${urls.getForLookupPath('/js/index.js')}"></script>
</body>
</html>