/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AsusVM
 */
@Entity
@Table(name = "groupsandusers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupsandusers.findAll", query = "SELECT g FROM Groupsandusers g"),
    @NamedQuery(name = "Groupsandusers.findById", query = "SELECT g FROM Groupsandusers g WHERE g.id = :id"),
    @NamedQuery(name = "Groupsandusers.findByStatus", query = "SELECT g FROM Groupsandusers g WHERE g.status = :status")})
public class Groupsandusers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private Users userid;
    @JoinColumn(name = "groupname", referencedColumnName = "groupname")
    @ManyToOne(optional = false)
    private Groupnameandowner groupname;

    public Groupsandusers() {
    }

    public Groupsandusers(Integer id) {
        this.id = id;
    }

    public Groupsandusers(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    public Groupnameandowner getGroupname() {
        return groupname;
    }

    public void setGroupname(Groupnameandowner groupname) {
        this.groupname = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupsandusers)) {
            return false;
        }
        Groupsandusers other = (Groupsandusers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.entity.Groupsandusers[ id=" + id + " ]";
    }
    
}
