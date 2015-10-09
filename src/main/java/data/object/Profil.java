package data.object;


public class Profil {

    public static String table = "Profil";
    private String nom;
    private String prenom;
    private String pseudo;
    private String birth;
    private String email;
    private String mdp;
    private int nbVideoUpload;
    
    public Profil(String email, String mdp) {
       this(email,mdp,0);
    }

    public Profil(String nom, String prenom, String pseudo, String birth, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.birth = birth;
        this.email = email;
        this.mdp = mdp;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getBirth() {
        return birth;
    }

    public int getNbVideoUpload() {
        return nbVideoUpload;
    }
    
      
    public Profil(String email,String mdp, int nbVideo){
        this.email = email;
        this.mdp = mdp;
        this.nbVideoUpload=nbVideo;
    }

    public Profil() {

    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }
   
    public int nbVideoUpload(){
        return nbVideoUpload;
    }

}
