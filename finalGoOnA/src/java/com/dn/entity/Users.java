/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AsusVM
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByTimesignup", query = "SELECT u FROM Users u WHERE u.timesignup = :timesignup"),
    @NamedQuery(name = "Users.findByLatitude", query = "SELECT u FROM Users u WHERE u.latitude = :latitude"),
    @NamedQuery(name = "Users.findByLongitude", query = "SELECT u FROM Users u WHERE u.longitude = :longitude"),
    @NamedQuery(name = "Users.findByTimeoflocation", query = "SELECT u FROM Users u WHERE u.timeoflocation = :timeoflocation")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "userid")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password")
    private String password;
    @Column(name = "timesignup")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timesignup;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Float latitude;
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "timeoflocation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeoflocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Groupnameandowner> groupnameandownerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Groupsandusers> groupsandusersCollection;

    public Users() {
    }

    public Users(String userid) {
        this.userid = userid;
    }

    public Users(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getTimesignup() {
        return timesignup;
    }

    public void setTimesignup(Date timesignup) {
        this.timesignup = timesignup;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Date getTimeoflocation() {
        return timeoflocation;
    }

    public void setTimeoflocation(Date timeoflocation) {
        this.timeoflocation = timeoflocation;
    }

    @XmlTransient
    public Collection<Groupnameandowner> getGroupnameandownerCollection() {
        return groupnameandownerCollection;
    }

    public void setGroupnameandownerCollection(Collection<Groupnameandowner> groupnameandownerCollection) {
        this.groupnameandownerCollection = groupnameandownerCollection;
    }

    @XmlTransient
    public Collection<Groupsandusers> getGroupsandusersCollection() {
        return groupsandusersCollection;
    }

    public void setGroupsandusersCollection(Collection<Groupsandusers> groupsandusersCollection) {
        this.groupsandusersCollection = groupsandusersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.entity.Users[ userid=" + userid + " ]";
    }
    
}
