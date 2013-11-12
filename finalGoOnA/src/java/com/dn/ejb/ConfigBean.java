/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.ejb;


import com.dn.entity.Groupnameandowner;
import com.dn.entity.Groupsandusers;
import com.dn.entity.Users;
import com.dn.service.UsersFacadeREST;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private UsersFacadeREST usersFacadeREST;
    
    @PersistenceContext(unitName = "finalGoOnPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
       
        Users dat = new Users();
        dat.setUserid("DAT");
        dat.setPassword("123456");
        dat.setLatitude((float)37.431566);
        dat.setLongitude((float)-121.891257);
        usersFacadeREST.create(dat);
      
      
        Users hai = new Users();
        hai.setUserid("HAI");
        hai.setPassword("123456");
        hai.setLatitude((float)37.374481);
        hai.setLongitude((float)-121.891995);
        usersFacadeREST.create(hai);
        
        Users anna = new Users();
        anna.setUserid("ANNA");
        anna.setPassword("123456");
        anna.setLatitude((float)37.428272);
        anna.setLongitude((float)-121.906624);
        usersFacadeREST.create(anna);
        
        Users tony = new Users();
        tony.setUserid("TONY");
        tony.setPassword("123456");
        tony.setLatitude((float)37.54827);
        tony.setLongitude((float)-121.988572);
        usersFacadeREST.create(tony);
        
        Groupnameandowner fishDat = new Groupnameandowner();
        fishDat.setGroupname("FISH");
        fishDat.setUserid(dat);
        fishDat.setPriority("noSpecial");
        em.persist(fishDat);
        
        Groupsandusers haiFishGroup = new Groupsandusers();
        haiFishGroup.setGroupname(fishDat);
        haiFishGroup.setUserid(hai);
        haiFishGroup.setStatus("accepted");
        em.persist(haiFishGroup);
        
        Groupsandusers annaFishGroup = new Groupsandusers();
        annaFishGroup.setGroupname(fishDat);
        annaFishGroup.setUserid(anna);
        annaFishGroup.setStatus("accepted");
        em.persist(annaFishGroup);
       
        Groupnameandowner catHai = new Groupnameandowner();
        catHai.setGroupname("CAT");
        catHai.setUserid(hai);
        catHai.setPriority("noSpecial");
        em.persist(catHai);
    }
}

