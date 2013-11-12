/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AsusVM
 */
@Entity
@Table(name = "groupnameandowner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupnameandowner.findAll", query = "SELECT g FROM Groupnameandowner g"),
    @NamedQuery(name = "Groupnameandowner.findByGroupname", query = "SELECT g FROM Groupnameandowner g WHERE g.groupname = :groupname"),
    @NamedQuery(name = "Groupnameandowner.findByPriority", query = "SELECT g FROM Groupnameandowner g WHERE g.priority = :priority")})
public class Groupnameandowner implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "groupname")
    private String groupname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "priority")
    private String priority;
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Users userid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupname")
    private Collection<Groupsandusers> groupsandusersCollection;

    public Groupnameandowner() {
    }

    public Groupnameandowner(String groupname) {
        this.groupname = groupname;
    }

    public Groupnameandowner(String groupname, String priority) {
        this.groupname = groupname;
        this.priority = priority;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
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
        hash += (groupname != null ? groupname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupnameandowner)) {
            return false;
        }
        Groupnameandowner other = (Groupnameandowner) object;
        if ((this.groupname == null && other.groupname != null) || (this.groupname != null && !this.groupname.equals(other.groupname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.entity.Groupnameandowner[ groupname=" + groupname + " ]";
    }
    
}
