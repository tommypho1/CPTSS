/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.service;

import com.dn.entity.Groupnameandowner;
import com.dn.entity.Groupsandusers;
import com.dn.entity.Users;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author DatNguyen
 */
@Stateless
@Path("com.dn.entity.groupnameandowner")
public class GroupnameandownerFacadeREST extends AbstractFacade<Groupnameandowner> {
    
    @EJB
    private UsersFacadeREST usersFacadeREST;
    
    @PersistenceContext(unitName = "finalGoOnPU")
    private EntityManager em;

    public GroupnameandownerFacadeREST() {
        super(Groupnameandowner.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Groupnameandowner entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Groupnameandowner entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Groupnameandowner find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Groupnameandowner> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Groupnameandowner> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("api/createGroupNoSpecial/{groupname}/{userid}")
    @Produces("text/plain")
    public String createGroupNoSpecial(@PathParam("groupname") String groupname, @PathParam("userid") String userid) {
        
        Groupnameandowner groupnameandowner = new Groupnameandowner();
       
        groupnameandowner.setGroupname(groupname);
        Users useridEntity = usersFacadeREST.find(userid);
        groupnameandowner.setUserid(useridEntity);
        groupnameandowner.setPriority("noSpecial");
        em.persist(groupnameandowner);
        
        return "OK";
        
    }
    
    @GET
    @Path("api/allGroupsOfUserid/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<String> allGroupsOfUserid(@PathParam("userid") String userid) {
        List<Groupnameandowner> listGroups = this.findAll();
        List<String> listReturn = new ArrayList<String>();
        for (Groupnameandowner row:listGroups)
        {
            if (userid.equals(row.getUserid().getUserid()))
                listReturn.add(row.getGroupname());
        }
        Collections.sort(listReturn);
        return listReturn;
    }
    
    @GET
    @Path("api/getDeleteGroupsOfUserid/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<String> getDeleteGroupsOfUserid(@PathParam("userid") String userid) {
        List<Groupnameandowner> listGroups = this.findAll();
        List<String> listReturn = new ArrayList<String>();
        for (Groupnameandowner row:listGroups)
        {
            if (userid.equals(row.getUserid().getUserid())&&(!(row.getPriority().equals("special"))))
                listReturn.add(row.getGroupname());
        }
        
        Collections.sort(listReturn);
        return listReturn;
    }
}
