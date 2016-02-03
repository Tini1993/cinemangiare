<!DOCTYPE html>
<html lang="it">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
          <h2>Prezzi spettacoli</h2>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Intero</h3>
          </div>
          <div class="panel-body">
            Prezzo intero
            <span class="badge">14£</span>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Ridotto</h3>
          </div>
          <div class="panel-body">
            Studenti e bambini under 14
            <span class="badge">7£</span>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Militari</h3>
          </div>
          <div class="panel-body">
            Militari e forze armate
            <span class="badge">25£</span>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Disabili</h3>
          </div>
          <div class="panel-body">
            Affetti da disabilità 
            <span class="badge">5£</span>
          </div>
        </div>

      </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>

</html>
