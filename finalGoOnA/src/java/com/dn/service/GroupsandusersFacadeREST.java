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
import java.util.Set;
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
@Path("com.dn.entity.groupsandusers")
public class GroupsandusersFacadeREST extends AbstractFacade<Groupsandusers> {
    
    @EJB
    private GroupnameandownerFacadeREST groupnameandownerFacadeREST;
    @EJB
    private UsersFacadeREST usersFacadeREST;
    
    @PersistenceContext(unitName = "finalGoOnPU")
    private EntityManager em;

    public GroupsandusersFacadeREST() {
        super(Groupsandusers.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Groupsandusers entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Groupsandusers entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Groupsandusers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Groupsandusers> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Groupsandusers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    
    @GET
    @Path("api/allUsersOfGroup/{groupname}")
    @Produces({"application/xml", "application/json"})
    public List<String> allUsersOfGroup(@PathParam("groupname") String groupname) {
        List<Groupsandusers> listGroups = this.findAll();
        List<String> listReturn = new ArrayList<String>();
        for (Groupsandusers row:listGroups)
        {
            if ((groupname.equals(row.getGroupname().getGroupname()))&&(row.getStatus().equals("accepted")))
                listReturn.add(row.getUserid().getUserid());
        }
        Collections.sort(listReturn);
        return listReturn;
    }
    
    @DELETE
    @Path("api/deleteUserofGroup/{groupname}/{userid}")
    @Produces("text/plain")
    public String deleteUserofGroup(@PathParam("groupname") String groupname, @PathParam("userid") String userid) {
        List<Groupsandusers> listGroups = this.findAll();
       
        for (Groupsandusers row:listGroups)
        {
            if (groupname.equals(row.getGroupname().getGroupname()))
                if (userid.equals(row.getUserid().getUserid()))
                {
                    this.remove(row.getId());
                    return "delete "+row.getId()+row.getUserid().getUserid();
                }
              
        }
        return "nodelete";
    }
    
    @GET
    @Path("api/getGroupsToJoin/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<String> getGroupsToJoin(@PathParam("userid") String userid) {
        
        List<Groupsandusers> listAllFromGroupsandusers = this.findAll();
        List<String> listJoinedGroupsOfUserid = new ArrayList<String>();
        List<Groupnameandowner> ListGroups = groupnameandownerFacadeREST.findAll();
        List<String> listReturn = new ArrayList<String>();
        
        for (Groupsandusers row:listAllFromGroupsandusers)
        {
            if (userid.equals(row.getUserid().getUserid()))
                if (row.getStatus().equals("accepted")||row.getStatus().equals("waiting accepted"))
                    listJoinedGroupsOfUserid.add(row.getGroupname().getGroupname());
        }
        
        for (Groupnameandowner row:ListGroups)
        {
            if (userid.equals(row.getUserid().getUserid()))
                listJoinedGroupsOfUserid.add(row.getGroupname());
            
            if (!(row.getPriority().equals("special")))
                if (!(listJoinedGroupsOfUserid.contains(row.getGroupname())))
                    listReturn.add(row.getGroupname());
        }
       
        Collections.sort(listReturn);
        return listReturn;
    }
    
    @GET
    @Path("api/getJoinedGroupsOfUserid/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<String> getJoinedGroupsOfUserid(@PathParam("userid") String userid) {
        List<Groupsandusers> listAllFromGroupsandusers = this.findAll();
        List<String> listJoinedGroupsOfUserid = new ArrayList<String>();
        for (Groupsandusers row:listAllFromGroupsandusers)
        {
            if (userid.equals(row.getUserid().getUserid()))
                if (row.getStatus().equals("accepted"))
                    listJoinedGroupsOfUserid.add(row.getGroupname().getGroupname());
        }
        
        for (Users userEntity: usersFacadeREST.findAll()){
            if (!(userEntity.getUserid().equals(userid)))
            {
                listJoinedGroupsOfUserid.remove(userEntity.getUserid()+" FRIENDS");
            }
        }
     Collections.sort(listJoinedGroupsOfUserid);
        return listJoinedGroupsOfUserid;
    }
   
    @DELETE
    @Path("api/deleteGroup/{groupname}")
    public String deleteGroup(@PathParam("groupname") String groupname) {
        List<Groupsandusers> listAllFromGroupsandusers = this.findAll();
        
        for (Groupsandusers row:listAllFromGroupsandusers)
        {
            if (groupname.equals(row.getGroupname().getGroupname()))
                if (row.getStatus().equals("accepted")||row.getStatus().equals("waiting accepted"))
                    this.remove(row);
        }
        
        groupnameandownerFacadeREST.remove(groupname);
        return"ok";
    }
    
    @POST
    @Path("api/joinGroupRequestFromUser/{groupname}/{userid}")
    @Produces("text/plain")
    public String joinGroupRequestFromUser(@PathParam("groupname") String groupname, @PathParam("userid") String userid) {
        
        Groupsandusers groupsandusers = new Groupsandusers();
        Users useridEntity = usersFacadeREST.find(userid);
        Groupnameandowner groupnameandowner = groupnameandownerFacadeREST.find(groupname);
        
        groupsandusers.setUserid(useridEntity);
        groupsandusers.setGroupname(groupnameandowner);
        groupsandusers.setStatus("waiting accepted");
        
        em.persist(groupsandusers);
        return"ok";
    }
    
    @GET
    @Path("api/listUserWaitingAccepted/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<Groupsandusers> listUserWaitingAccepted(@PathParam("userid") String userid) {
        List<Groupsandusers> listGroups = this.findAll();
        List<Groupsandusers> listReturn = new ArrayList<Groupsandusers>();
        for (Groupsandusers row:listGroups)
        {
            if (row.getStatus().equals("waiting accepted"))
                if (userid.equals(row.getGroupname().getUserid().getUserid()))
                    listReturn.add(row);
            
        }
        return listReturn;
    }
    
    @POST
    @Path("api/joinGroupRequestDecision/{groupname}/{userid}/{action}/{useridSpecial}")
    @Produces("text/plain")
    public String joinGroupRequestDecision(@PathParam("groupname") String groupname, @PathParam("userid") String userid, @PathParam("action") String action, @PathParam("useridSpecial") String useridSpecial) {
        
        List<Groupsandusers> listGroups = this.findAll();
        Groupsandusers groupsandusers = new Groupsandusers();
        for (Groupsandusers row:listGroups)
        {
            if (row.getGroupname().getGroupname().equals(groupname))
                if (row.getUserid().getUserid().equals(userid))
                    groupsandusers = row;
            
        }
        
        
        if (action.equals("allow")){
            groupsandusers.setStatus("accepted");
            this.edit(groupsandusers);
            if (groupsandusers.getGroupname().getGroupname().equals(useridSpecial+" FRIENDS")){
                Groupsandusers groupsandusersTmp = new Groupsandusers();
                Users useridEntityTmp = usersFacadeREST.find(useridSpecial);
                String groupSpecial = userid + " FRIENDS";
                Groupnameandowner groupnameandownerTmp = groupnameandownerFacadeREST.find(groupSpecial);
        
                groupsandusersTmp.setUserid(useridEntityTmp);
                groupsandusersTmp.setGroupname(groupnameandownerTmp);
                groupsandusersTmp.setStatus("accepted");
        
                em.persist(groupsandusersTmp);
            }
        }
            
        
        if (action.equals("deny"))
            this.remove(groupsandusers);
        
        return"ok";
    }
    
    @GET
    @Path("api/notificationAlert/{userid}")
    @Produces("text/plain")
    public String notificationAlert(@PathParam("userid") String userid) {
        List<Groupsandusers> listGroups = this.findAll();
        List<Groupsandusers> listReturn = new ArrayList<Groupsandusers>();
        for (Groupsandusers row:listGroups)
        {
            if (row.getStatus().equals("waiting accepted"))
                if (userid.equals(row.getGroupname().getUserid().getUserid()))
                    listReturn.add(row);
            
        }
        if (listReturn.size()>0)
            return "alert";
       
        return "no";
    }
    
    @GET
    @Path("api/totalGroupsOfUserid/{userid}")
    @Produces({"application/xml", "application/json"})
    public List<String> totalGroupsOfUserid(@PathParam("userid") String userid) {
        List<Groupnameandowner> listOwnGroups = groupnameandownerFacadeREST.findAll();
        List<Groupsandusers> listJoinedGroupsTmp = this.findAll();
        List<String> listReturn = new ArrayList<String>();
        for (Groupnameandowner row:listOwnGroups)
        {
            if (userid.equals(row.getUserid().getUserid()))
                listReturn.add(row.getGroupname());
        }
        for (Groupsandusers row:listJoinedGroupsTmp)
        {
            if (userid.equals(row.getUserid().getUserid()))
                listReturn.add(row.getGroupname().getGroupname());
        }
        
        for (Users userEntity: usersFacadeREST.findAll()){
            if (!(userEntity.getUserid().equals(userid)))
            {
                listReturn.remove(userEntity.getUserid()+" FRIENDS");
            }
        }
            
        Collections.sort(listReturn);
        return listReturn;
    }
    
    @GET
    @Path("api/totalUsersOfGroup/{groupname}")
    @Produces({"application/xml", "application/json"})
    public List<String> totalUsersOfGroup(@PathParam("groupname") String groupname) {
        List<Groupsandusers> listGroups = this.findAll();
        List<String> listReturn = new ArrayList<String>();
        for (Groupsandusers row:listGroups)
        {
            if ((groupname.equals(row.getGroupname().getGroupname()))&&(row.getStatus().equals("accepted")))
                listReturn.add(row.getUserid().getUserid());
        }
        Groupnameandowner groupnameandowner = groupnameandownerFacadeREST.find(groupname);
        listReturn.add(groupnameandowner.getUserid().getUserid());
        Collections.sort(listReturn);
        return listReturn;
    }
    
    @GET
    @Path("api/getListUserEntityFromListUserid/{listUseridString}")
    @Produces({"application/xml", "application/json"})
    public List<Users> getListUserEntityFromListUserid(@PathParam("listUseridString") String listUseridString) {
       
        List<Users> listReturn = new ArrayList<Users>();
        String[] listUserId = listUseridString.split(",");
        for (String userid: listUserId)
        {
           listReturn.add(usersFacadeREST.find(userid));
        }
        return listReturn;
    
    }
    
    @GET
    @Path("api/editUserLocation/{userid}/{lat}/{lng}")
    @Produces("text/plain")
    public String editUserLocation(@PathParam("userid") String userid,
                                   @PathParam("lat") Float lat,
                                   @PathParam("lng") Float lng) {
       
        Users usersEntity = usersFacadeREST.find(userid);
        usersEntity.setLatitude(lat);
        usersEntity.setLongitude(lng);
        usersFacadeREST.edit(usersEntity);
        return "ok";
    
    }
    
    @GET
    @Path("api/getAllUseridExceptYou/{you}")
    @Produces({"application/xml", "application/json"})
    public List<String> getAllUseridExceptYou(@PathParam("you") String you) {
        List<String> listReturn = new ArrayList<String>();
        List<Users> allUsers = usersFacadeREST.findAll();
        String yourSpecialGroup = you+" FRIENDS";
        List<String> totalUsersOfSpecialGroup = totalUsersOfGroup(yourSpecialGroup);
        List<String> listRemove = new ArrayList<String>();
        for (Users user: allUsers)
        {
            if (!totalUsersOfSpecialGroup.contains(user.getUserid()))
                listReturn.add(user.getUserid());
        }
        listReturn.remove(you);
        
       
            for (Groupsandusers row: this.findAll())
            {
                if (row.getStatus().equals("waiting accepted"))
                    if (row.getUserid().getUserid().equals(you))
                       if (row.getGroupname().getGroupname().equals(row.getGroupname().getUserid().getUserid()+" FRIENDS"))
                            listRemove.add(row.getGroupname().getUserid().getUserid());
            }
            
         for (String tmp: listRemove){
             listReturn.remove(tmp);
         }
           
        Collections.sort(listReturn);
        return listReturn;
    
    }
}
