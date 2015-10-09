<%-- 
    Document   : Identification
    Created on : 12 mars 2014, 10:49:53
    Author     : manuelrodrigues
--%>
<jsp:include page="<%="includes/header.jsp"%>"/>
<jsp:include page="<%="includes/sidebar.jsp"%>"/>

    <div id='container'>
        
        <% if(request.getSession().getAttribute("alerte") != null){
            out.println(request.getSession().getAttribute("alerte"));
            request.getSession().setAttribute("alerte",null);     
        }
        %>
        
        <div style="margin-left:200px;" class="jumbotron">
            <h1>Bienvenue sur VideoGezer</h1>
            <p>Connecté vous ou inscrivez vous</p>
        </div>
        
        
        
        <!-- Formulaire connexion -->
        <form style="margin-left: 200px;" class="form-horizontal" role="form" method="post" action="servletConnexion">
            <legend>Me connecter</legend>
            <div class="form-group">
              <label for="email" class="col-sm-2 control-label">Email</label>
              <div class="col-sm-4">
                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
              </div>
            </div>
            <div class="form-group">
              <label for="mdp" class="col-sm-2 control-label">Mot de passe</label>
              <div class="col-sm-4">
                <input type="password" class="form-control" id="mdp" name="mdp" placeholder="Password">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Me connecter</button>
              </div>
            </div>
       </form>
        
        
        
        
        <!-- Formulaire inscription -->
        <form style="margin-left: 200px;" class="form-horizontal" role="form" method="post" action="Inscription">
            <legend>M'inscrire</legend>
            
            <div class="form-group">
              <label for="nom" class="col-sm-2 control-label">Nom</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="nom" name="nom" placeholder="Nom">
              </div>
            </div>
            <div class="form-group">
              <label for="prenom" class="col-sm-2 control-label">Prénom</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Prenom">
              </div>
            </div>
            <div class="form-group">
              <label for="pseudo" class="col-sm-2 control-label">Pseudo</label>
              <div class="col-sm-4">
                <input type="text" class="form-control" id="pseudo" name="pseudo" placeholder="Pseudo visible sur l'application">
              </div>
            </div>
            <div class="form-group">
              <label for="email" class="col-sm-2 control-label">Email</label>
              <div class="col-sm-4">
                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
              </div>
            </div>
            <div class="form-group">
              <label for="birth" class="col-sm-2 control-label">Date de naissance</label>
              <div class="col-sm-4">
                  <input type="date" class="form-control" id="birth" name="birth" placeholder="jj/mm/aaaa">
              </div>
            </div>
            <div class="form-group">
              <label for="mdp" class="col-sm-2 control-label">Mot de passe</label>
              <div class="col-sm-4">
                <input type="password" class="form-control" id="mdp" name="mdp" placeholder="Mon mot de passe">
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">M'inscrire</button>
              </div>
            </div>
       </form>
           
<jsp:include page="<%="includes/footer.jsp"%>"/>
