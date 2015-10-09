/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import data.bdd.BddV2;
import data.object.Profil;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Ervin
 */
public class ProfilDAO {

    public static void inscriptionPrfil(HashMap map) {
        // TODO Auto-generated method stub
        System.out.println("InscriptionProfil");
        Map<String, Object> params = new HashMap<>();
        params.put("map", map);
        try {
            BddV2.getData(params, "CREATE (p: Profil {map})");
        } catch (JSONException ex) {
            Logger.getLogger(ProfilDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   public static Profil connexion(Map propriete) {
        //String query = "match (p:Profil {params}) return p";
        System.out.println("Recherche du profil");
        String query = "match (p:Profil) where p.email='" + propriete.get("email") + "' and p.mdp='" + propriete.get("mdp") + "' return p";
        System.out.println(query);
        Map<String, Object> params = new HashMap<>();
        params.put("params", propriete);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        try {
            jsonArray = BddV2.getData(propriete, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);

                    JSONObject item = array.getJSONObject(0);
                    JSONObject data = new JSONObject(item.getString("data"));
                    System.out.println(data);
                    String pseudo = data.getString("email");
                    System.out.println("ici " + pseudo);
                return new Profil(data.getString("nom"),data.getString("prenom"),data.getString("pseudo"),data.getString("birth"),data.getString("email"), data.getString("mdp"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }

        return null;
    }

    public static boolean existProfil(Map propriete) {
        System.out.print("Est ce qu'il existe ?");
        String query = "match (p:Profil) where p.email ={email } return count(*)";
        Map<String, Object> params = new HashMap<>();
        params.put("params", propriete);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        boolean inscrit = false;
        try {
            jsonArray = BddV2.getData(propriete, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                for (int j = 0; j < array.length(); j++) {
                    Long item = array.getLong(j);
                    System.out.println(item);
                    if (item > 0) {
                        inscrit = true;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }

        return inscrit;
    }
    
    public static Profil modifierProfil(HashMap map, String mail){
        
        System.out.println("Modification du profil");

        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
                
        try {
            jsonArray = BddV2.getData(map, "match (p : Profil { email : '" + mail + "' }) set p.pseudo = \""+map.get("pseudo")+"\", p.nom = \""+map.get("nom")+"\", p.prenom = \""+map.get("prenom")+"\", p.mdp = \""+map.get("mdp")+"\", p.birth = \""+map.get("birth")+"\", p.email = \""+map.get("email")+"\"").getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);

                    JSONObject item = array.getJSONObject(0);
                    JSONObject data = new JSONObject(item.getString("data"));
                    System.out.println(data);
                    String pseudo = data.getString("email");
                    System.out.println("ici " + pseudo);
                return new Profil(data.getString("nom"),data.getString("prenom"),data.getString("pseudo"),data.getString("birth"),data.getString("email"), data.getString("mdp"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
        
        
        
    }

    //Test si le mail est deja dans la base
   /* public boolean exists(String mail) {
     long total = 0;
     GraphDatabaseService graphDb = Bdd.getBdd();
     try (Transaction tx = graphDb.beginTx()) {
     ExecutionEngine requete = new ExecutionEngine(graphDb, StringLogger.SYSTEM);
     ExecutionResult result;
     result = requete.execute("match (p:Profil {email : '" + mail + "'}) return count(*)");
     for (Map<String, Object> row : result) {
     for (Entry<String, Object> column : row.entrySet()) {
     total = (long) column.getValue();
     System.err.println(total);
     }
     }
     } finally {
     graphDb.shutdown();
     if (total == 0) {
     return false;
     }
     return true;
     }
     }*/
}
