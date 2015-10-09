<%-- 
    Document   : sidebar
    Created on : 12 mars 2014, 11:33:13
    Author     : manuelrodrigues
--%>

<%@page import="data.object.Profil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  
   

  <script>
  $(function() {
    $( "#search" ).autocomplete({
     /* source: availableTags,
       position : {
        my : 'bottom',
        at : 'bottom'
    }*/
       source : function(requete, reponse){
           $.ajax({
      type:"POST",
      url: "http://localhost:7474/db/data/cypher",
      processData: false,
      accepts: "application/json",
      dataType:"json",
      contentType:"application/json",

      data: JSON.stringify({
       "query" : "match (v:Video) where v.nomVideo=~'(?i)"+$("#search").val()+".*'  return v;",
       "params" : {}
     }),
      success: function(data, textStatus, jqXHR){
                     reponse($.map(data.data, function(objet){
                    return objet[0].data.nomVideo ; // on retourne cette forme de suggestion
                }));
                        },
      error:function(jqXHR, textStatus, errorThrown){
                       alert(errorThrown);
                        }
             });
       },
       minLength: 2
    });
  });
  </script>


<div id='sidebar'>
    <!-- Logo -->
    <div id='logo'>
        <a href="index.jsp"> <h1>Videogezer</h1></a>
    </div>
    <!-- Nav -->
    <nav id='nav'>
        <ul>
            <%
                if(session.getAttribute("profil") != null){
                    out.println("Bonjour "+ ((Profil) session.getAttribute("profil")).getPseudo());
                    out.println("<form action='Deconnexion' method='post' ><input type='hidden' name='deco' value='1'/><input type='submit' value='Me deconnecter'/></form>");
                    %> <li><a href='profil.jsp'>Profil</a></li><%
                }
                else out.println("<li class='current_page_item'><a href='Identification.jsp'>Connexion</a></li>");                
            %>
        </ul>
    </nav>
    <section class='is-search'>
        <form method='post' action='BarreDeRecherche' >
            <input type='text' class='text' name='search' id="search" placeholder='Search' onKeyUp="recherche();" />
        </form>
    </section>

    <!-- Text -->
    <section class='is-text-style1'>
        <div class='inner'>
            <p>
                <strong>Striped:</strong> A free and fully responsive HTML5 site>
                template designed by <a href='http://n33.co/'>AJ</a> for <a href='http://html5up.net/'>HTML5 up!</a>
            </p>
        </div>
    </section>

</div>