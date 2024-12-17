<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
</head>
<body>
<div class="container">
  <form class="form-horizontal" role="form" method="post" action="/login">
    <div class="form-group">
      <label class="control-label col-sm-2" for="username">User:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="username" name="username" value="">
      </div>
      <label class="control-label col-sm-2" for="password">Password:</label>
      <div class="col-sm-10">
        <input type="password" class="form-control" id="password" name="password" value="">
      </div>
    </div>

    <% String error = (String) request.getAttribute("error");
      if (error != null && error != "") { %>
    <div class="alert alert-danger">Error: <% out.print(error); %></div>
    <% } %>

    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button class="btn btn-primary">Login</button>
      </div>
    </div>
  </form>
</div>

</body>
</html>
