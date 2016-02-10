<!DOCTYPE html>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Spettacolo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
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
  <%@ include file="navbar.jsp" %>

  <div class="container">
    <div class="row">

      <div class="col-sm-offset-4 col-sm-4">
        <div class="page-header">
          <h2>Nuovo Utente</h2>
        </div>
        <form action="RegistrazioneServlet" method="POST">
          <div class="form-group">
            <label for="user">Indirizzo Email</label>
            <input id="user" type="text" class="form-control" name="username" placeholder="Username" aria-describedby="basic-addon1">
          </div>
          <div class="form-group">
            <label for="passwd">Password</label>
            <input id="passwd" type="password" class="form-control" name="password" placeholder="Password" aria-describedby="basic-addon1">
          </div>
          <div class="form-group">
            <label for="passwd">Ripeti password</label>
            <input id="passwd2" type="password" onkeyup="contpass(); return false;" class="form-control" placeholder="Ripeti password" aria-describedby="basic-addon1">
            <span id="confirmMessage" class="confirmMessage"></span>
          </div>
          <button type="submit" class="btn btn-default">Iscriviti</button>
        </form>
      </div>

    </div>
  </div>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="js/bootstrap.min.js"></script>
  <!-- Controllo password se combacia -->
  <script type="text/javascript" src="js/passwdcheck.js"></script>

</body>

</html>
