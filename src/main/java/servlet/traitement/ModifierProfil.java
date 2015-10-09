/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.traitement;

import DAO.ProfilDAO;
import data.object.Profil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author manuelrodrigues
 */
public class ModifierProfil extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModifierProfil</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModifierProfil at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        String mail = request.getParameter("email");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String birth = request.getParameter("birth");
        String pseudo = request.getParameter("pseudo");
        String mdp = request.getParameter("mdp");

        String erreurs = "";
        if(mail.equals("")) erreurs += "<b>Le mail</b> n'est pas renseigné<br>";
        if(nom.equals("")) erreurs += "<b>Le nom</b> n'est pas renseigné <br>";
        if(prenom.equals("")) erreurs += "<b>Le prenom</b> n'est pas renseigné <br>";
        if(pseudo.equals("")) erreurs += "<b>Le pseudo</b> n'est pas renseigné <br>";
        if(mdp.equals("")) erreurs += "<b>Le mot de passe</b> n'est pas renseigné <br>";
        if(birth.equals("")) erreurs += "<b>La date de naissance</b> n'est pas renseignée <br>";

        
        
        
        HttpSession session = request.getSession(true);
        if(erreurs.equals("")){
                System.out.println("TTTEEESSSTTT"); 
                Profil p = new Profil(nom,prenom,pseudo,birth,mail,mdp);        
                HashMap m = new HashMap<>();
                m.put("email",p.getEmail());
                m.put("mdp", p.getMdp());
                m.put("nom", p.getNom());
                m.put("prenom", p.getPrenom());
                m.put("pseudo", p.getPseudo());
                m.put("birth", p.getBirth());
                
                Profil tmp = (Profil) session.getAttribute("profil");
                ProfilDAO.modifierProfil(m, tmp.getEmail());
                
                session.setAttribute("profil", p);
        }
        else{
            session.setAttribute("alerte", "<p style='margin-left:200px' class='bg-danger'>"+erreurs+"</p>");
        }
        
        request.getRequestDispatcher("/profil.jsp").forward(request, response);
        return;
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
