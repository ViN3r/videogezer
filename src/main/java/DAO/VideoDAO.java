/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import data.bdd.Bdd;
import data.bdd.BddV2;
import data.object.Message;
import data.object.Profil;
import data.object.Video;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.PageContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.neo4j.graphdb.GraphDatabaseService;
import servlet.traitement.Commentaire;

/**
 *
 * @author Ervin
 */
public class VideoDAO {

    private static void getIdBdd() {

    }

    /*
     Ajout d'un video après son upload
     */
    public static URI uploadVideo(Profil p, Video v) {
        System.out.println("uploadVideo");
        URI location = BddV2.createNode("Video");
        BddV2.addLabelToNode(location, "Video");
        BddV2.addPropertyToNode(location, "idBdd", v.getidBdd());
        BddV2.addPropertyToNode(location, "nomVideo", v.getNom());
        BddV2.addPropertyToNode(location, "nomVideo", v.getNom());
        BddV2.addPropertyToNode(location, "dateUpload", System.currentTimeMillis());
        BddV2.addPropertyToNode(location, "emplacement", v.getEmplacement());
        BddV2.addPropertyToNode(location, "nbVues", v.getnbVues());
        BddV2.addPropertyToNode(location, "description", "");
        BddV2.addPropertyToNode(location, "prive", false);
        return location;
    }

    public static void uploadVideoV2(Profil p, Video v, String[] mots) {
        List<Map<String, Object>> tmp = new ArrayList<>();
        List<Map<String, Object>> tmp2 = new ArrayList<>();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        Map map;
        Map<String, Object>[] tab;
        String query;
        BddV2.setContraint();
        System.out.println("uploadVideo V2");
        Map propriete = new HashMap();
        propriete.put("idBdd", v.getidBdd());
        propriete.put("nomVideo", v.getNom());
        propriete.put("dateUpload", System.currentTimeMillis());
        propriete.put("emplacement", v.getEmplacement());
        propriete.put("nbVues", 0);
        System.out.println(v.getDescription());
        propriete.put("description", v.getDescription());
        propriete.put("prive", v.getPrivee());
        Map param = new HashMap();
        param.put("prop", propriete);
        System.out.println(p.getEmail());
        query = "Match (p:Profil {email:'" + p.getEmail() + "'}) CREATE (v:Video {prop}) CREATE (p)-[u:UPLOADE]->(v) return p,v";
        System.out.println(query);
        try {
            BddV2.getData(param, query);

            param.clear();
            for (String mot : mots) {
                if (mot.length() > 3) {
                    query = "MATCH (v:Video { idBdd:'" + v.getidBdd() + "' }) MERGE (k:KEYWORD {mot:'" + mot + "'}) CREATE (v)-[m:MOTCLE]->(k) return v,k";
                    BddV2.getData(param, query);
                    /*
                     map = new HashMap<>();
                     map.put("mot", mot);
                     tmp.add(map);
                     */
                }
            }
            /*  
             tab = new Map[tmp.size()];
             for(int i=0; i< tmp.size(); i++){
             tab[i] = tmp.get(i);
             }
             tmp2 = Arrays.asList(tab);
             param.put("props", tmp2);
             //query = "CREATE  (k:KEYWORD {props})  return k";
             //create constraint on (t:Test) assert t.mot is unique 
             query = "MATCH (v:Video {nom:'" + v.getNom() + "', emplacement:'C:/Users/Ervin/Videos/VideoGezer/upload/" + v.getidBdd() + "' }) CREATE (k:KEYWORD {props}) CREATE (v)-[m:MOTCLE]->(k) return v,k";
             //BddV2.getData(param, query);
             //query = "MATCH (v:Video {nom:'" + v.getNom() + "', emplacement:'C:/Users/Ervin/Videos/VideoGezer/upload/" + v.getidBdd() + "' }),(k:KEYWORD {props}) CREATE (v)-[m:MOTCLE]->(k) return v,k";
             BddV2.getData(param, query);
             */
        } catch (JSONException ex) {
            Logger.getLogger(VideoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // return location;
    }

    public static void ajoutMotCle(URI node1, Video v, String[] mots) {
        System.out.println("Ajout mot cle");
        Map<String, Object> params = new HashMap<>();
        URI location;
        for (String mot : mots) {
            if (mot.length() > 3) {
                location = BddV2.createNode("KEYWORD");
                BddV2.addLabelToNode(location, "KEYWORD");
                BddV2.addPropertyToNode(location, "mot", mot);
                try {
                    BddV2.addRelationship(node1, location, "MOTCLE", "{}");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(VideoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /*
     Ancien recherche par like
     */
    public static List<Video> rechercheVideo(String nom, String tri, String mode, Map propriete) {
        // (?i) non sensible à la casse
        // .* n'importe après
        System.out.println("Recherche video");
        List<Video> listVideo = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("params", propriete);
        String query = "";
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        if (mode.equals("aleatoire")) {
            query = "match (v:Video) " + conditionWhere(nom) + " return count(distinct v) as total";
            long item = 0;
            try {
                jsonArray = BddV2.getData(propriete, query).getJSONArray("data");
                JSONArray array;
                for (int i = 0; i < jsonArray.length(); i++) {
                    array = jsonArray.getJSONArray(i);
                    for (int j = 0; j < array.length(); j++) {
                        item = array.getLong(j);
                        System.out.println(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    
            int random = (int) (Math.random() * (item - 1));
            System.out.println(random);
            query = "MATCH (v:Video) " + conditionWhere(nom) + " RETURN v, id(v) SKIP " + random + " limit 5";
        } else if (mode.equals("recommandation")) {
            query = "match (video:Video)-[:MOTCLE]->(stuff)<-[:MOTCLE]-(v:Video) where not (video)-[:MOTCLE]->(video) and v.idBdd= {idBdd} return v, id(v), count(stuff) order by count(stuff) desc";
        } else {
            query = "MATCH (v:Video)-[m:MOTCLE]->(k:KEYWORD)" + conditionWhere(nom) + " RETURN v, id(v),type(m),count(m),count(v) " + order(tri);
        }
        System.out.println(query);
        try {
            jsonArray = BddV2.getData(propriete, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                //System.out.println(" indice : "+ i + ", " + array);
                JSONObject item = array.getJSONObject(0);
                JSONObject data = new JSONObject(item.getString("data"));
                System.out.println(data);
                listVideo.add(new Video(array.getLong(1), data.get("nomVideo").toString(), data.get("idBdd").toString(), data.get("emplacement").toString(), data.getLong("dateUpload"), data.get("idBdd").toString(), data.getInt("nbVues"), data.getBoolean("prive")));
                System.out.println(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }
        return listVideo;
    }

    public static List<Video> mesVideosUploade(String email) {
        Map<String, Object> params = new HashMap<>();
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        List<Video> mesVideos = new ArrayList<>();
        String query = "match (p:Profil)-[u:UPLOADE]->(v:Video) where p.email = '" + email + "' return v, id(v) order by v.dateUpload desc";
        try {
            jsonArray = BddV2.getData(params, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                System.out.println(" indice : " + i + ", " + array);
                JSONObject item = array.getJSONObject(0);
                JSONObject data = new JSONObject(item.getString("data"));
                System.out.println(data);
                System.out.println(data.getString("idBdd"));
                mesVideos.add(new Video(array.getLong(1), data.get("nomVideo").toString(), data.get("idBdd").toString(), data.get("emplacement").toString(), data.getLong("dateUpload"), data.get("idBdd").toString(), data.getInt("nbVues"), data.getBoolean("prive")));
                System.out.println(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }
        return mesVideos;

    }

    protected static String conditionWhere(String recherche) {
        if (recherche.compareTo(" ") != 0) {
            String where = " where ";
            String[] mots = recherche.split(" ");
            String tmp = "";
            for (String mot : mots) {
                if (mot.length() >= 4) {
                    if (tmp.compareTo("") != 0 && tmp.compareTo(mot) != 0) {
                        System.out.println("good");
                        where = where.concat(" or ");
                    }
                    where = where.concat(" k.mot =~ '(?i)" + mot + "'");
                    tmp = mot;
                    System.out.println(tmp);
                }
            }
            return where;
        }
        return "";
    }

    private static String order(String type) {
        if (type.equals("Recent")) {
            return "order by v.dateUpload desc";
        }
        return "order by count(m) desc";
    }

    public static Profil rechercherVideoParId(Video v, Long id) {
        System.out.println("Recherche video par Id");
        Map params = new HashMap<>();
        params.put("id", id);
        String query = "Match (p:Profil)-[u:UPLOADE]->(v:Video) where id(v)={id} RETURN v,p";
        //String query = "  MATCH (a)-[u:`commentaire`]->(n:`Video`) match (p:`Profil`)-[y:`acommente`]->(a) where id(n) ="+ Long.parseLong(request.getParameter("video"))+" return p,a order by id(u) desc;";
        System.out.println(query);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        Profil p = null;
        try {
            jsonArray = BddV2.getData(params, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                System.out.println(i);
                for (int j = 0; j < array.length(); j++) {
                    System.out.println(j);
                    JSONObject item = array.getJSONObject(0);
                    JSONObject data = new JSONObject(item.getString("data"));
                    v.setNomVideo(data.getString("nomVideo"));
                    v.setEmplacement(data.getString("emplacement"));
                    v.setIdBdd(data.getString("idBdd"));
                    v.setNbVues(data.getLong("nbVues"));
                    v.setDescription(data.getString("description"));
                    Timestamp stamp = new Timestamp(data.getLong("dateUpload"));
                    v.setDateUpload(new Date(stamp.getTime()));
                    /*pseudo = data.get("nom").toString();
                     System.out.println(pseudo);*/
                }
                System.out.println("La personne qui a uploade la video");

                array = jsonArray.getJSONArray(0);
                JSONObject item = array.getJSONObject(1);
                JSONObject data = new JSONObject(item.getString("data"));
                p = new Profil(data.getString("email"), data.getString("mdp"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }
        getCommentaire(id,v);
        return p;
    }

    public static void ajouterVue(Video v) {
        System.out.println("Ajouter vue a la video");
        Map params = new HashMap<>();
        //params.put("idBdd", v.getId());
        String query = "MATCH  (v:Video {idBdd : '" + v.getidBdd() + "'}) set v.nbVues = " + v.getnbVues() + " RETURN v";
        System.out.println(query);
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        try {
            jsonArray = BddV2.getData(params, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                for (int j = 0; j < array.length(); j++) {
                    JSONObject item = array.getJSONObject(i);
                    JSONObject data = new JSONObject(item.getString("data"));
                    v.setNomVideo(data.getString("nomVideo"));
                    /*pseudo = data.get("nom").toString();
                     System.out.println(pseudo);*/
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }
    }

    public static void getCommentaire(Long id, Video v) {
        System.out.println("Recherche commentaire");
        Map params = new HashMap<>();
        params.put("id", id);
        System.out.println(id);
        String query = "MATCH (a)-[u:`commentaire`]->(n:`Video`) match (p:`Profil`)-[y:`acommente`]->(a) where id(n)={id} return p,a order by id(u) desc;";
        System.out.println(query);
        try {
            JSONObject result = new JSONObject();
            JSONArray jsonArray = null;
            jsonArray = BddV2.getData(params, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                JSONObject item = array.getJSONObject(0);
                JSONObject item1 = array.getJSONObject(1);

                JSONObject data = new JSONObject(item.getString("data"));
                JSONObject data1 = new JSONObject(item1.getString("data"));
                System.out.println("data " + data);
                System.out.println("data1 " + data1);
                v.setMessage(data1.getString("Texte"), data.getString("pseudo"));
                /*
                 % > Par <  % out.print(data.getString("email")); % > : <%
                out.print(data1.getString("Texte")); % > <br /
                        > < %*/
            }
        } catch (JSONException ex) {
            Logger.getLogger(Commentaire.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
