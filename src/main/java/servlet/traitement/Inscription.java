package servlet.traitement;

import DAO.ProfilDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.object.Profil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Inscription extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mail = request.getParameter("email");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String pseudo = request.getParameter("pseudo");
            String birth = request.getParameter("birth");
            String mdp = request.getParameter("mdp");

            String erreurs = "";

            if (mail.equals("")) {
                erreurs += "<b>Le mail</b> n'est pas renseigné<br>";
            }
            if (nom.equals("")) {
                erreurs += "<b>Le nom</b> n'est pas renseigné <br>";
            }
            if (prenom.equals("")) {
                erreurs += "<b>Le prenom</b> n'est pas renseigné <br>";
            }
            if (pseudo.equals("")) {
                erreurs += "<b>Le pseudo</b> n'est pas renseigné <br>";
            }
            if (birth.equals("")) {
                erreurs += "<b>La date de naissance</b> n'est pas renseignée <br>";
            }
            if (mdp.equals("")) {
                erreurs += "<b>Le mot de passe</b> n'est pas renseigné <br>";
            }

            Profil p = new Profil(nom, prenom, pseudo, birth, mail, mdp);

            //Si le mail n'est pas utiliser on inscrit
            HashMap m = new HashMap<>();
            m.put("email", p.getEmail());
            if (!ProfilDAO.existProfil(m) && erreurs.equals("")) {
                m.put("mdp", p.getMdp());
                m.put("nom", p.getNom());
                m.put("prenom", p.getPrenom());
                m.put("pseudo", p.getPseudo());
                m.put("birth", p.getBirth());
                ProfilDAO.inscriptionPrfil(m);
                HttpSession session = request.getSession(true);
                session.setAttribute("profil", p);
                try {
                    String verification = "Inscription réussi";

                    erreurs += "Vous êtes maintenant <b>connecté</b>";
                    session.setAttribute("alerte", "<p class='bg-success'>" + erreurs + "</p>");

                    request.getRequestDispatcher("profil.jsp").forward(request, response);
                    System.out.println(verification);
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            } //Sinon erreur
            else {
                try {
                    HttpSession session = request.getSession(true);
                    if (erreurs.equals("")) {
                        erreurs += "<b>Email déjà existant</b>, veuillez choisir un autre email<br>";
                    }
                    session.setAttribute("alerte", "<p style='margin-left:200px' class='bg-danger'>" + erreurs + "</p>");
                    request.getRequestDispatcher("Identification.jsp").forward(request, response);

                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
