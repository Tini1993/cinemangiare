<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

            <c:if test="${empty user}"> 
                <%-- FORM DI LOGIN IN CASO L'UTENTE SIA SLOGGATO --%>

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
                                                <div class="help-block text-right"><a href="recupero.jsp">Password dimenticata ?</a></div>
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
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>                                        
            </c:if>

            <%-- questo lo fa se invece c'è un utente connesso --%>
            <c:if test="${! empty user}"> 
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><b>${user.email}</b> <span class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <a href="LoadUserInfoServlet"><button type="button" class="btn btn-success" >Visualizza profilo</button></a>
                                        <button type="button" data-toggle="modal" class="btn btn-info" onclick="location.href = 'LogoutServlet'">Logout</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>  
            </c:if>

        </div>
        <!-- /.navbar-collapse -->
    </div>
</nav>