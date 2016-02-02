<%-- 
    Document   : film
    Created on : 4-set-2015, 4-set-2015 20.21.16
    Author     : Andrea
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="Bean.Film"%>
<%@page import="Bean.Spettacolo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cinema Limoni</title>

        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="style.css">
    </head>
            
        <%@ include file="navbar.jsp" %>
 <div class="container">        
<div class="col-xs-12" style="height:50px;"></div>
 
<div class="row">
      <div class="col-xs-6 col-md-4"><img  class="img-responsive center-block" src="${FilmSel.url_locandina}"/> </div>

  <div class="col-xs-12 col-md-8">

         
      <p><h2 class="text-uppercase">${FilmSel.titolo}   <span class="label label-default">New</span></h2> </p>

      <p> DURATA: ${FilmSel.durata}min </p>

      <p><h4>${FilmSel.trama}</h4> </p>
      <p><a class="btn btn-primary btn-lg" href="#" role="button">prenota</a></p>

  </div>
</div>

       <div class="row"> 
           <div class="col-xs-6 col-md-4"></div>
           <div class="col-xs-12 col-md-6">
               
               <div class="embed-responsive embed-responsive-16by9"> <iframe class=embed-responsive-item src="${FilmSel.url_trailer}" allowfullscreen></iframe> </div>
               
           </div>
        </div>
        


        <table class="table table-striped">
        <c:forEach items="${ShowSel}" var="show">   
            <tr>                        
                <td><a href="PostiServlet?idShow=${show.id_spettacolo}&idHall=${show.id_sala}">ORA: ${show.data_ora}</a></td>
                <td><a href="PostiServlet?idShow=${show.id_spettacolo}&idHall=${show.id_sala}">SALA: ${show.id_sala}</a></td>
            </tr>
        </c:forEach>
        </table>
        
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

</html>
