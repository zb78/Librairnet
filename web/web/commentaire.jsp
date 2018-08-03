<%-- 
    Document   : commentaire
    Created on : 2 août 2018, 12:56:31
    Author     : yavuz
--%>

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



        <title>Critique</title>
    </head>
    <body>
        
        <div class="row" >
            <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div>
            <form class="form-horizontal col-xs-6 col-md-6 col-lg-6 col-sm-6">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Ecrire une critique</legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-12 control-label" for="textinput">Text Input</label>  
                        <div class="col-md-12">
                            <input id="textinput" name="textinput" placeholder="placeholder" class="form-control input-md" type="text">
                            <span class="help-block">help</span>  
                        </div>
                    </div>

                    <!-- Textarea -->
                    <div class="form-group">
                        <label class="col-md-12 control-label" for="textarea">Text Area</label>
                        <div class="col-md-12">                     
                            <textarea class="form-control" id="textarea" name="textarea">default text</textarea>
                        </div>
                    </div>

                    <!-- Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="singlebutton">Single Button</label>
                        <div class="col-md-4">
                            <button id="singlebutton" name="singlebutton" class="btn btn-primary">Button</button>
                        </div>
                    </div>

                </fieldset>
            </form>
            <div class="col-xs-3 col-md-3 col-lg-3 col-sm-3"></div>
        </div>
    </body>
</html>
