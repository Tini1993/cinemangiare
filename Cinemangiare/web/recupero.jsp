<%-- 
    Document   : recupero
    Created on : 28-gen-2016, 22.14.11
    Author     : colom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
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
<%@ include file="navbar.jsp" %>

  <div class="container">
    <div class="row">

      <div class="col-sm-offset-4 col-sm-4">
        <div class="page-header">
          <h2>Recupero Email</h2>
        </div>
                                    <form class="form-horizontal" method="post" action=RecuperoPasswordServlet>
                            <fieldset>
                                <div class="form-group">
                                    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
                                    <div class="col-sm-8">
                                        <input type="email" name="utenteEmail" class="form-control" id="utenteEmail" placeholder="Email" required>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <div class="col-lg-10 col-lg-offset-2">
                                        <button type="submit" class="btn btn-success">Richiedi</button>
                                    </div>
                                </div>
                            </fieldset>
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
