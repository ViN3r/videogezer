/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet.traitement;

import DAO.VideoDAO;
import data.object.Profil;
import data.object.Video;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 *
 * @author Ervin
 */
@MultipartConfig
public class ServletUploadVideo extends HttpServlet {

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
            out.println("<title>Servlet ServletUploadVideo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletUploadVideo at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        System.out.println("Upload en cours");
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("mon_fichier"); // Retrieves <input type="file" name="file">
        String filename = request.getParameter("titre");
        String mots[]=filename.split(" ");
        boolean privee ;
        if(request.getParameter("privee") == null){
            privee = false;
        }else{
            privee=true;
        }
        InputStream filecontent = filePart.getInputStream();
        //Donner nom aléatoire à la vidéo + chemin de la video VideGezer/{nomUtilisateur}/nomVideo
        int numVideo = Video.getNbVideo();

        String nomBdd = UUID.randomUUID()+".mp4";
        String emplacement = "/home/thomas/glassfish4/glassfish/domains/domain1/applications/VideoGezer/video/"+nomBdd; //"/home/thomas/glassfish4/glassfish/domains/domain1/applications/VideoGezer/video/"+nomBdd;
        try (FileOutputStream file = new FileOutputStream(emplacement)) {
            int i = 0;
            byte b[] = new byte[1024];
            while ((i = filecontent.read(b)) != -1) {
                file.write(b,0,i);
            }
            System.out.println("la description " + description);
            System.out.println("-----> path : ");
            
            Video v = new Video(filename,emplacement,nomBdd,description,privee);
            
            //URI node1=VideoDAO.uploadVideo((Profil)request.getAttribute("profil"), v);
            VideoDAO.uploadVideoV2(((Profil)request.getSession().getAttribute("profil")), v,mots);
            //VideoDAO.ajoutMotCle(node1,v,mots);
            file.flush();
        }catch(IOException e){
            System.err.println("Erreur dans la copie du fichier");
        }
        //Ajouter les données à la base de donnée
        request.getRequestDispatcher("profil.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private static String getFilename(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
        if (cd.trim().startsWith("filename")) {
            String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
        }
    }
    return null;
}
}
