<%-- 
    Document   : error
    Created on : 1-feb-2016, 11.17.04
    Author     : Mattia
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%--se inculudo sta roba da errore--%>
<%@page import="java.sql.ResultSet"%>
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
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-md-3"></div>
                <div class="col-xs-12 col-md-6 "> 
                    <div class="page-header">
                        <h2>
                            <c:if test="${empty errorMessage}" >            
                                Nessun Errore da Visualizzare
                            </c:if>
                            <c:if test="${!empty errorMessage}" >            
                                <c:out value ="${errorMessage}"/>
                            </c:if>
                             

                        </h2>
                    </div>
                    <p>
                        <c:if test="${!empty errorMessage}" >
                        Eccezione: <c:out value ="${errorEx}"/>
                        </c:if>
                    </p>
                    <nav>
                        <ul class="pager">
                            <li class="next"><a href="#" onclick="history.go(-1)">(ノಠ益ಠ)ノ彡┻━┻ </a></li>
                        </ul>
                    </nav>

                </div>
            </div>




        </div>



        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
