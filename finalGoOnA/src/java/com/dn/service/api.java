/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.service;

import com.dn.entity.Messages;
import com.dn.entity.Users;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//import com.google.gson.Gson;
//import com.sun.jersey.api.json.JSONWithPadding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author tommypho
 */
@Path("api")
@Stateless
public class api {
    @PersistenceContext(unitName = "finalGoOnPU")
    private EntityManager em;

    @EJB
    private UsersFacadeREST usersFacadeREST;
    
    @EJB
    private MessagesFacadeREST messagesFacadeREST;
    /**
     * Creates a new instance of api
     */
    public api() {}
          
    @GET
    @Path("getUserEntityById/{id}")
    @Produces({"application/xml", "application/json"})
    public Users getUserEntityById(@PathParam("id") String id) {
        return usersFacadeREST.find(id);
    }

    @GET
    @Path("getAllUsersEntity")
    @Produces({"application/xml", "application/json"})
    public List<Users> getAllUsersEntity() {
        return usersFacadeREST.findAll();
    }
    
    
    @GET
    @Path("getListAllUsersId")
    @Produces({"application/xml", "application/json"})
    public List<String> getListAllUsersId() {
        List<String> listAllUserId = new ArrayList<String>();
        for(Users user:usersFacadeREST.findAll())
        {
            listAllUserId.add(user.getUserid());
        }
        
        return listAllUserId;
    }
   
    
    @POST
    @Path("createMessage")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public String createMessage(Messages entity) {
        em.persist(entity);
        return entity.getMessageid().toString();
    }
    
    @PUT
    @Path("changeMessageStatus/{messageid}")
    @Consumes({"application/xml", "application/json"})
    public void ChangeMessageStatus(@PathParam("messageid") String messageid) {
        Messages m = messagesFacadeREST.find(Integer.parseInt(messageid));
        m.setStatus("seen");
        messagesFacadeREST.edit(m);
    }
    
    @GET
    @Path("messageAlert/{id}")
    @Produces("text/plain")
    public String messageAlert(@PathParam("id") String id) {
        String alert = "no";
        for (Messages m:messagesFacadeREST.findAll())
        {
            
                if ((m.getReceiver().equals(id)) && (m.getStatus().equals("un-seen")))
                {
                    alert="alert";
                    break;
                }
        }
        
        return alert;
    }
      
    @GET
    @Path("getListOfSenders/{id}")
    @Produces({"application/xml", "application/json"})
    public List<String> getListOfSenders(@PathParam("id") String id) {
        
        List<String> listSenders = new ArrayList<String>();
        for (Messages m:messagesFacadeREST.findAll())
        {
            if (!(listSenders.contains(m.getSender())))
                 if ((m.getReceiver().equals(id)) && (m.getStatus().equals("un-seen")))
                {
                    listSenders.add(m.getSender());
                }
        }
        Collections.sort(listSenders);
        return listSenders;
    }
    
    @GET
    @Path("getAllUnseenMessages/{sender}/{receiver}")
    @Produces({"application/xml", "application/json"})
    public List<Messages> getAllUnseenMessages(@PathParam("sender") String sender, @PathParam("receiver") String receiver) {
        
        List<Messages> listMessages= new ArrayList<Messages>();
        for (Messages m:messagesFacadeREST.findAll())
        {
            
            if ((m.getReceiver().equals(receiver)) && (m.getSender().equals(sender)) && (m.getStatus().equals("un-seen")))
            {
                listMessages.add(m);
                m.setStatus("seen");
                messagesFacadeREST.edit(m);
            }
        }
        
        return listMessages;
    }
    
    @GET
    @Path("updatePassword/{userid}/{password}")
    @Produces("text/plain")
    public String updatePassword(@PathParam("userid") String userid, @PathParam("password") String password) {
        
        Users tmp = usersFacadeREST.find(userid);
        tmp.setPassword(password);
        usersFacadeREST.edit(tmp);
        return "yes";
    }
    
  /*  @GET
    @Produces("application/x-javascript")
    @Path("getUserEntityByIdJsonp/{id}")
    public JSONWithPadding getUserEntityByIdJsonp (@QueryParam("callback") String callback,
                                 @PathParam("id") String id) {
        Gson gson = new Gson();
        Users msg = usersFacadeREST.find(id);
        //String msg= new String("ll");
       // msg = new Users();
        
        String json = gson.toJson(msg,Users.class);
        return new JSONWithPadding(json,callback);
    } */
    
    @GET
    @Path("earthquakeInfo")
    @Produces("text/plain")
    public String getEarthquakeInfo() {
        String result="";
        URL url;
        try {
                url = new URL("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson");
		URLConnection conn = url.openConnection();
 
		BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
 
		String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    result=result+inputLine;
		}
 
                br.close();
 
			
 
	} catch (MalformedURLException e) {
			e.printStackTrace();
	} catch (IOException e) {
			e.printStackTrace();
	}
        return result;
    }
    
    
    @POST
    @Path("pythonProcess/{code}")
    @Produces("text/plain")
    public String pythonProcess(@PathParam("code") String code){
        final String dosCommand = "cmd /c C:\\Python27\\python ";
        final String filenamePy = "D:\\pyy.py";
        String result="";
        try {
      	  FileOutputStream out = new FileOutputStream(filenamePy);
      	  String s =code.replaceAll("<br>", System.getProperty("line.separator"));
          s =s.replaceAll("<tab>", "\t");
      	  byte data[] = s.getBytes();
      	  out.write(data, 0, data.length);
      	  
           final Process process = Runtime.getRuntime().exec(
              dosCommand + " " + filenamePy);
           
           final InputStream in = process.getInputStream();
           int ch;
           while((ch = in.read()) != -1) {
        	   result=result+ (char) ch;
        	   
           }
        } catch (IOException e1) {
           e1.printStackTrace();
        }
        result=result.replaceAll("\n", "<br>");
        return result;
}
 }

        





















