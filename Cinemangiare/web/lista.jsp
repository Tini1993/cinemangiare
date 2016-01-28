<%-- 
    Document   : lista
    Created on : 13-ago-2015, 19.27.31
    Author     : Mattia
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="it">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Cinema Limoni</title>

  <!-- Bootstrap -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="style.css">

</head>

<body>
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand" href="http://www.google.com">
          <img alt="Brand" src="img/icona.png">
        </a>
      </div>

      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li><a href="index.html">Home <span class="sr-only">(current)</span></a></li>
          <li  class="active"><a href="films.html">I Film </a></li>
          <li><a href="prices.html">Prezzi </a></li>
          <li><a href="contact.html">Dove Siamo </a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
          <li><a href="register.html">Registrati</a></li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><b>Login</b> <span class="caret"></span></a>
            <ul id="login-dp" class="dropdown-menu">
              <li>
                <div class="row">
                  <div class="col-md-12">
                    <form class="form" role="form" method="post" action="LoginServlet" accept-charset="UTF-8" id="login-nav">
                      <div class="form-group">
                        <label class="sr-only" for="exampleInputEmail2">Indirizzo Email</label>
                        <input type="text" name="username" class="form-control" id="exampleInputEmail2" placeholder="Email address" required>
                      </div>
                      <div class="form-group">
                        <label class="sr-only" for="exampleInputPassword2">Password</label>
                        <input type="password" name="password" class="form-control" id="exampleInputPassword2" placeholder="Password" required>
                        <div class="help-block text-right"><a href="">Password dimenticata ?</a></div>
                      </div>
                      <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                      </div>
                      <div class="checkbox">
                        <label>
                          <input type="checkbox"> tienimi loggato
                        </label>
                      </div>
                    </form>
                  </div>
                  <div class="bottom text-center">
                    Nuovo ? <a href="register.html"><b>Registrati</b></a>
                  </div>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
      <!-- /.navbar-collapse -->

    </div>

  </nav>

    
  <div class="container">
   <table class="table table-striped">
   <thead>
      <tr>
         <th>titolo</th>
         <th>durata</th>
      </tr>
   </thead>
   <tbody>
      
      <c:forEach items="${Films}" var="film">
      <tr>
          <td> ${film.titolo}</td>
          <td> <a href="film.jsp"><button class="btn btn-success">Prenota</button></a>
      </tr>
       </c:forEach>
   </tbody>
</table>
   
      
  </div>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="js/bootstrap.min.js"></script>
</body>

</html>
