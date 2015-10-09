<%-- 
    Document   : Profil
    Created on : 2 mai 2014, 17:46:35
    Author     : Ervin
--%>

<%
    Profil p = null;
    if (request.getSession().getAttribute("profil") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
        p = (Profil) session.getAttribute("profil");
    }
%>
<%@page import="data.object.Profil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="data.object.Video"%>
<%@page import="DAO.VideoDAO"%>
<%
    Map<String, Video> m = new HashMap<String, Video>();
%>

<jsp:include page="<%="includes/header.jsp"%>"/>
<jsp:include page="<%="includes/sidebar.jsp"%>"/>
<div id="content">
    <div id="content-inner">	

        <% if (request.getSession().getAttribute("alerte") != null) {
                out.println(request.getSession().getAttribute("alerte"));
                request.getSession().setAttribute("alerte", null);
            }
        %>

        <!-- Post -->
        <article class="is-post is-post-excerpt">
            <header>
                <h2><a href="#">Mon profil</a></h2>
            </header>
            <div class="info">
                <span class="date"><span class="month">Jan<span>uary</span></span> <span class="day">8</span><span class="year">, 2013</span></span>
                <ul class="stats">
                    <li><a href="#formUploadVideo" class="fa fa-upload"><% out.print(p.nbVideoUpload()); %> </a></li>
                    <li><a href="#mesVideos" class="fa fa-video-camera"><% out.print(p.nbVideoUpload()); %></a></li>
                    <li><a href="#Gezer" class="fa fa-user">64</a></li>
                    <li><a href="#followers" class="fa fa-users">128</a></li>
                </ul>
            </div>
            <a href="#" class="image image-full"><img src="images/fotogrph-dark-stairwell.jpg" alt="" /></a>

            <form action="ModifierProfil" method="post">
                <legend><h4>Modifier mes infos :</h4></legend>
                <label for="email">Mon email :</label>
                <input type="email" name="email" value="<% out.print(p.getEmail());%>" /><br />
                <label for="prenom">Mon prénom :</label>
                <input type="text" name="prenom" value="<% out.print(p.getPrenom());%>" /><br />
                <label for="nom">Mon nom :</label>
                <input type="text" name="nom" value="<% out.print(p.getNom());%>" /><br />
                <label for="pseudo">Mon pseudo :</label>
                <input type="text" name="pseudo" value="<% out.print(p.getPseudo());%>" /><br />
                <label for="birth">Ma date de naissance :</label>
                <input type="date" name="birth" value="<% out.print(p.getBirth());%>" /><br />
                <label for="mdp">Mon mot de passe :</label>
                <input type="password" name="mdp" value="<% out.print(p.getMdp());%>" /><br /><br/>

                <input type="submit" value="Valider modification"/>
            </form>

            <form method="post" id='formUploadVideo' action='ServletUploadVideo' enctype="multipart/form-data">
                <legend><h4>Upload de vidéos :</h4></legend>
                <label for="mon_fichier">Video à uploader (tous formats | max. 1 Mo) :</label><br />
                <input type="hidden" name="MAX_FILE_SIZE" value="1048576" />
                <input type="file" name="mon_fichier" id="mon_fichier" accept='video/mp4'<br />
                <label for="titre">Titre du fichier (max. 50 caractères) :</label><br />
                <input type="text" name="titre" id="titre" /><br />
                <label for="description">Description de votre fichier (max. 255 caractères) :</label><br />
                <textarea name="description" id="description"></textarea><br />

                <label for="privee" class="checkbox">Vidéo privée
                    <input type="checkbox" name="privee" value="true" style="width:15px; height:15px;">
                </label>


                <input type="submit" name="submit" value="Envoyer"/>

            </form>
            <br>
            <div id="mesVideos" style="width: 500px; height: 500px;">
                <h4>Mes vidéos :</h4>
                <%
                    List<Video> myVideos = VideoDAO.mesVideosUploade(((Profil) session.getAttribute("profil")).getEmail());
                    for (Video v : myVideos) {
                %>
                <div style="float: left; width: 200px"> <video id="video<% out.print(v.getId());%>" controls preload="none" poster="" width="200" height="100" class="video-js vjs-default-skin">
                        <source src=<% out.print(v.getEmplacement()); %> type="video/mp4" data-res="HD" />
                    </video>
                    <a href="regarder.jsp?video=<% out.print(v.getId());%>" ><% out.print(v.getNom());%></a>
                </div>
                <% }%>
            </div></br><br>
            <div id="Gezer">
                <h4>Mes gezer :</h4>

            </div>
            <div id="followers">
                <h4>Mes followers :</h4>



            </div>
        </article>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $('li>a').click(function() {
            var pos = $($(this).attr('href')).offset().top;
            $('body,thml').animate({scrollTop: pos}, 50);
        });
        
        /*$('#formUploadVideo').validate({
            rules:{
                mon_fichier:{
                    required:true,
                    accept:"video/mp4"
                }
            }
        })*/
        
    });
</script>

<jsp:include page="<%="includes/footer.jsp"%>"/>
