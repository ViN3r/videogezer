/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.bdd;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Ervin
 */
public class BddV2 {

    private static final String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
    
    public static void setContraint(){
        HashMap m = new HashMap();
        try {
            getData(m,"create constraint on (m:KEYWORD) assert m.mot is unique ");
            getData(m,"create constraint on (v:Video) assert v.idBdd is unique ");
        } catch (JSONException ex) {
            Logger.getLogger(BddV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JSONObject getData(Map<String, Object> params, String query) throws JSONException {
        JSONObject jObject = new JSONObject();
        //String SERVER_ROOT_URI = "http://localhost:7474/db/data/";
        String cypherUri = SERVER_ROOT_URI + "cypher/";
        try {
            jObject.put("query", query);
            jObject.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebResource resource = Client.create()
                .resource(cypherUri);

        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jObject.toString())
                .post(ClientResponse.class);
        try {
            jObject = null;
            jObject = new JSONObject(response.getEntity(String.class).toString());
        } catch (ClientHandlerException e) {
            /*LogsUtil.getInstance().log(e, "RESTUtil",
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        } catch (UniformInterfaceException e) {
            /*LogsUtil.getInstance().log(e, "RESTUtil",
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        } catch (JSONException e) {
            /*LogsUtil.getInstance().log(e, "RESTUtil",
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }
        System.out.println(jObject.toString());
        return jObject;
    }

    public String getPseudoByUUID(String UUID) {
        String pseudo = null;
        HashMap<String, Object> params = new HashMap<String, Object>();
        //String query = "MATCH (n:Profil) RETURN n LIMIT 25";
         String query = "MERGE (n:Test {nom:'test'}) RETURN n LIMIT 25";
        JSONObject result = new JSONObject();
        JSONArray jsonArray = null;
        try {
            jsonArray = BddV2.getData(params, query).getJSONArray("data");
            JSONArray array;
            for (int i = 0; i < jsonArray.length(); i++) {
                array = jsonArray.getJSONArray(i);
                System.out.println(array);
                for (int j = 0; j < array.length(); j++) {
                    JSONObject item = array.getJSONObject(j);
                    JSONObject data = new JSONObject(item.getString("data"));
                    pseudo = data.get("nom").toString();
                    System.out.println(pseudo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            /*LogsUtil.getInstance().log(e, this.getClass().getName(),
             Thread.currentThread().getStackTrace()[1].getMethodName());*/
        }

        return pseudo;
    }

    public static URI createNode(String name) {
        //URI propertyUri = new URI(relationshipUri.toString() + "/properties");
        String nodeEntryPointUri = SERVER_ROOT_URI + "node/";
        //String entity = toJsonNameValuePairCollection(name, value);
        WebResource resource = Client.create()
                .resource(nodeEntryPointUri);
// POST {} to the node entry point URI
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .entity("")
                .post(ClientResponse.class);
        System.out.println( "url est " +resource.getURI());
        final URI location = response.getLocation();
        System.out.println(String.format(
                "POST to [%s], status code [%d], location header [%s]",
                nodeEntryPointUri, response.getStatus(), location.toString()));
        response.close();
        
        return location;
    }

        public static URI addLabelToNode(URI url,String name) {
        //URI propertyUri = new URI(relationshipUri.toString() + "/properties");
        String nodeEntryPointUri = url.toString() + "/labels";
        //String entity = toJsonNameValuePairCollection(name, value);
        WebResource resource = Client.create()
                .resource(nodeEntryPointUri);
// POST {} to the node entry point URI
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .entity( "\"" + name + "\"" )
                .post(ClientResponse.class);

        final URI location = response.getLocation();
        System.out.println(String.format(
                "POST to [%s], status code [%d]",
                nodeEntryPointUri, response.getStatus()));
        response.close();
        
        return location;
    }
        
    public static void addPropertyToNode( URI nodeUri, String propertyName,
            Object propertyValue )
    {
        // START SNIPPET: addProp
        String propertyUri = nodeUri.toString() + "/properties/" + propertyName;
        // http://localhost:7474/db/data/node/{node_id}/properties/{property_name}

        WebResource resource = Client.create()
                .resource( propertyUri );
        ClientResponse response = resource.accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( "\"" + propertyValue + "\"" )
                .put( ClientResponse.class );

        System.out.println( String.format( "PUT to [%s], status code [%d]",
                propertyUri, response.getStatus() ) );
        response.close();
        // END SNIPPET: addProp
    }
    
    private static void checkDatabaseIsRunning()
    {
        // START SNIPPET: checkServer
        WebResource resource = Client.create()
                .resource( SERVER_ROOT_URI );
        ClientResponse response = resource.get( ClientResponse.class );

        System.out.println( String.format( "GET on [%s], status code [%d]",
                SERVER_ROOT_URI, response.getStatus() ) );
        response.close();
        // END SNIPPET: checkServer
    }
    
    public static URI addRelationship( URI startNode, URI endNode,
            String relationshipType, String jsonAttributes )
            throws URISyntaxException
    {
        URI fromUri = new URI( startNode.toString() + "/relationships" );
        String relationshipJson = generateJsonRelationship( endNode,
                relationshipType, jsonAttributes );

        WebResource resource = Client.create()
                .resource( fromUri );
        // POST JSON to the relationships URI
        ClientResponse response = resource.accept( MediaType.APPLICATION_JSON )
                .type( MediaType.APPLICATION_JSON )
                .entity( relationshipJson )
                .post( ClientResponse.class );

        final URI location = response.getLocation();
        System.out.println( String.format(
                "POST to [%s], status code [%d]",
                fromUri, response.getStatus() ) );

        response.close();
        return location;
    }
    
    private static String generateJsonRelationship( URI endNode,
            String relationshipType, String... jsonAttributes )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "{ \"to\" : \"" );
        sb.append( endNode.toString() );
        sb.append( "\", " );

        sb.append( "\"type\" : \"" );
        sb.append( relationshipType );
        if ( jsonAttributes == null || jsonAttributes.length < 1 )
        {
            sb.append( "\"" );
        }
        else
        {
            sb.append( "\", \"data\" : " );
            for ( int i = 0; i < jsonAttributes.length; i++ )
            {
                sb.append( jsonAttributes[i] );
                if ( i < jsonAttributes.length - 1 )
                { // Miss off the final comma
                    sb.append( ", " );
                }
            }
        }

        sb.append( " }" );
        return sb.toString();
    }
    
    public static void main(String[] args) {
        BddV2 bdd = new BddV2();
        bdd.getPseudoByUUID("");
    }

}
