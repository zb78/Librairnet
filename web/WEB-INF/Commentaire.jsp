<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">

        <link rel="stylesheet" href="css/css.css">

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em" crossorigin="anonymous"></script>

    </head>

    <body>
        <title>Critique</title>
        <%@include file="/WEB-INF/nav.jsp" %>
        <div class="critique ">
            <c:if test="${isconnected}" >
                <c:if test="${achete}" >
                    <div class="row critique-form" >
                        <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div>
                        <form action="Controller" method="POST" class="form-horizontal col-xs-6 col-md-6 col-lg-6 col-sm-6">
                            <fieldset>

                                <!-- Form Name -->
                                <legend>Ecrire une critique</legend>

                                <input type="hidden" name="isbn" value="${lac.isbn}">

                                Vous allez critiquer le livre ${lac.titre} ${lac.sousTitre}
                                <br>

                                <!-- Text input-->
                                <div class="form-group">
                                    <label class="col-md-12 control-label" for="textinput">Note</label>  
                                    <div class="col-md-12">
                                        <select id="textinput" name="note" placeholder="placeholder" class="form-control input-md" type="text">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                        <span class="help-block">Choisir une note de 1 à 5</span>  
                                    </div>
                                </div>

                                <!-- Textarea -->
                                <div class="form-group">
                                    <label class="col-md-12 control-label" for="textarea">Votre commentaire sur le livre:</label>
                                    <div class="col-md-12">                     
                                        <textarea name="commentaire" class="form-control" id="textarea" name="textarea"></textarea>
                                    </div>
                                </div>

                                <!-- Button -->
                                <div class="form-group">
                                    <label class="col-md-4 control-label" for="singlebutton"></label>
                                    <div class="col-md-4">
                                        <button type="submit" id="singlebutton" name="critiquebouton" class="btn btn-primary">Envoyer</button>
                                    </div>
                                </div>

                            </fieldset>
                        </form>
                        <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div>
                    </div>

                </c:if>
                <c:if test="${!achete}" > 
                    Il faut d'abord acheter le livre avant de pouvoir le commenter
                    <div class="card" style="width: 10rem;">

                        <img height="200px" width="70px" class="card-img-top" src="${lac.couverture}" alt="${lac.titre}">

                        <div></div>
                        <div class="card-body">
                            <h5 class="card-title">${lac.titre}</h5>
                            <p class="card-text">${lac.sousTitre}</p>

                        </div>

                        <a href="Controller?add=${lac.isbn}" class="btn btn-primary"><span class="glyphicon  glyphicon-shopping-cart"></span> Ajouter au panier</a>
                    </div>
                </c:if>        
            </c:if>
            <c:if test="${!isconnected}" >
                <a href="Controller?identification"> Connectez vous d'abord!</a>

            </c:if>
        </div>
    </body>
</html>
