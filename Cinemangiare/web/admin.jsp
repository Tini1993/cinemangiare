<%-- 
    Document   : admin
    Created on : 1-feb-2016, 11.49.56
    Author     : jack
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <div class="row">
            <div class="col-xs-12 col-md-2"></div>
            <div class="col-xs-12 col-md-8">

                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
                    <li><a data-toggle="tab" href="#menu1">Script</a></li>
                    <li><a data-toggle="tab" href="#menu2">Delete</a></li>
                    <li><a data-toggle="tab" href="#menu3">Blocca</a></li>
                </ul>

                <div class="tab-content">
                    <div id="home" class="tab-pane fade in active">
       
                        <c:if test="${ShowSel.size()>0}">
                            <h2>Incasso per Spettacolo</h2>
                            <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Spettacolo</th>
                                        <th>Sala</th>
                                        <th>Film</th>
                                        <th>Numero di prenotazioni</th>
                                        <th>Data Spettacolo</th>
                                        <th>Incassi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${ShowSel}" var="show">   
                                        <tr>
                                            <td>${show.id_spettacolo}</td>
                                            <td>${show.id_sala}</td>
                                            <td>${show.titolo}</td>
                                            <td>${show.posti}</td>
                                            <td>
                                                <fmt:formatDate value="${show.data_ora}" pattern="MM/dd/yyyy HH:mm"/>
                                            </td>
                                            <td>${show.prezzo}€</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </c:if>
                        <c:if test="${Incassi.size()>0}">
                            <h2>Incasso per Film</h2>
                            <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Titolo</th>
                                        <th>Incassi</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${Incassi}" var="incassi">   
                                        <tr>
                                            <td>${incassi.titolo}</td>
                                            <td>${incassi.incassi}€</td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </c:if>
                        <c:if test="${Utente.size()>0}">
                            <h2>Incasso per Utente</h2>
                            <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>Utente</th>
                                        <th>Incassi</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${Utente}" var="utente">   
                                        <tr>
                                            <td>${utente.email}</td>
                                            <td>${utente.paga}€</td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            </div>
                        </c:if>

                    </div>
                    <div id="menu1" class="tab-pane fade">
                        <div class="page-header">
                            <h3>Avvia Script</h3>
                        </div>

                        <form action="${pageContext.request.contextPath}/AdminServlet" method="post">
                            <div class="input-append">
                                <div class="col-xs-12 col-md-4">
                                    <div class="input-group input-group-sm">

                                        <input type="number" class="form-control"  aria-describedby="basic-addon1"name="x" required>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-md-4">
                                    <button type="submit" class="btn">Esegui</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div id="menu2" class="tab-pane fade">
                        <div class="col-xs-12 col-md-5">
                            <div class="page-header">
                                <h3>Rimuovi Prenotazione</h3>
                            </div>

                            <form action="${pageContext.request.contextPath}/DeletePrenotazione" method="post">
                                <div class="input-append">
                                    <div class="input-group input-group-sm">
                                        <span class="input-group-addon" id="basic-addon1">@</span>
                                        <input type="text" class="form-control" placeholder="Email utente" aria-describedby="basic-addon1"name="email" required>
                                    </div>
                                    <div class="input-group input-group-sm">
                                        <span class="input-group-addon" id="basic-addon1">Numero prenotazione</span>
                                        <input type="number" class="form-control" placeholder="Numero prenotazione" aria-describedby="basic-addon1"name="id_p" required>
                                    </div>
                                    <button type="submit" class="btn">Cancella prenotazione</button>
                                </div>
                            </form>
                        </div>
                    </div>
                                <div id="menu3" class="tab-pane fade">
                        <div class="col-xs-12 col-md-5">
                            <div class="page-header">
                                <h3>Blocca posto</h3>
                            </div>

                            <form action="${pageContext.request.contextPath}/bloccaServlet" method="post">
                                <div class="input-append">
                                    <div class="input-group input-group-sm">
                                        <span class="input-group-addon" id="basic-addon1">ID</span>
                                        <input type="number" class="form-control" placeholder="ID sala" aria-describedby="basic-addon1"name="id_sala" min="1" max="5" required>
                                    </div>
                                    <div class="input-group input-group-sm">
                                        <span class="input-group-addon" id="basic-addon1">Posto</span>
                                        <input type="number" class="form-control" placeholder="Numero posto" aria-describedby="basic-addon1"name="id_posto" min="1" max="50" required>
                                    </div>
                                    <button type="submit" class="btn">Blocca/Sblocca Posto</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

            </div>
        </div>




    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</html>
