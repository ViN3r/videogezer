/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.object;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ervin
 */
public class Video {

    private static int nbVideo = 13;
    private static SimpleDateFormat formatDate = new SimpleDateFormat("dd MM yyyy");

    private long id;
    private String nomVideo;
    private String emplacement;
    private String idBdd;
    private int nbVues;
    private String description;
    private Date dateUpload;
    private boolean privee;
    private List<String> tableauResolution;
    private List<Message> messages;

    //private Commentaire commentaire;
    public Video() {

    }

    public Video(String nom, String emplacement, String idBdd) {
        nomVideo = nom;
        this.emplacement = "video/" + idBdd;
        this.idBdd = idBdd;
        System.out.println(nom + " " + emplacement + " " + idBdd);
        nbVues=0;
        nbVideo++;
    }

    public Video(Long id,String nom, String idBdd, String emplacement,long time,String description, int nbVues, boolean privee) {
        this.id = id;
        nomVideo = nom;
        this.emplacement = emplacement;
        this.idBdd = idBdd;
        this.description= description;
        this.nbVues=nbVues;
        this.dateUpload=new Timestamp(time);
        this.privee = privee;
    }
    
    public Video(long id, String n, String e, String idBdd) {
        this.id = id;
        nomVideo = n;
        emplacement = "video/" + idBdd;
        this.idBdd = idBdd;
        tableauResolution = new ArrayList<String>();
        System.out.println(n + " " + emplacement + " " + idBdd);
    }

    public Video(String n, String e, String idBdd,String description, boolean privee) {
        System.out.println("MA video");
        nomVideo = n;
        emplacement = "video/" + idBdd;
        this.idBdd = idBdd;
        tableauResolution = new ArrayList<String>();
        this.description=description;
        this.privee = privee;
        System.out.println(n + " " + emplacement + " " + idBdd + " " + description);
    }
    
    public String getdateUpload() {
        return formatDate.format(dateUpload);
    }

    public String getNom() {
        return nomVideo;
    }

    public int getnbVues() {
        return nbVues;
    }

    public String getidBdd() {
        return idBdd;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public String getDescription(){
        return description;
    }
    
    public static void ajouterCommentaire() {

    }

    public long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public static int getNbVideo() {
        return nbVideo;
    }
    
    public Map<String,Object> getPropriete(){
        Map<String,Object> propriete = new HashMap<>();
        propriete.put("nom", nomVideo);
        propriete.put("idBdd", idBdd);
        propriete.put("emplacement","C:/Users/Ervin/Videos/VideoGezer/upload/" + idBdd );
        propriete.put("dateUpload", "timestamp()");
        propriete.put("prive",privee);
        propriete.put("nbVues", nbVues);
        return propriete;
    }

    public void addVue() {
        nbVues++;
    }

    public int nbResolution() {
        //return tableauResolution.size();
        return 1;
    }

    public void setNomVideo(String nomVideo) {
        this.nomVideo=nomVideo;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement=emplacement;
    }

    public void setIdBdd(String idBdd) {
        this.idBdd=idBdd;
    }

    public void setDateUpload(Date date) {
        this.dateUpload=date;
    }

    public void setNbVues(long nbVues) {
        this.nbVues=(int)nbVues;
    }

    public void setDescription(String description) {
        if(messages == null){
            messages = new ArrayList<>();
        }
        this.description=description;
    }
    
    public List<Message> getMessage(){
        return messages;
    }
    public void setMessage(String msg, String auteur){
        messages.add(new Message(msg,auteur));
    }

    public boolean getPrivee() {
        return privee;
    }
}
