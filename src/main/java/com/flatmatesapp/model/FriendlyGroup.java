package com.flatmatesapp.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author iulian.dafinoiu
 */
@Entity
@Table(name = "group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FriendlyGroup.findAll", query = "SELECT f FROM FriendlyGroup f"),
    @NamedQuery(name = "FriendlyGroup.findById", query = "SELECT f FROM FriendlyGroup f WHERE f.id = :id"),
    @NamedQuery(name = "FriendlyGroup.findByName", query = "SELECT f FROM FriendlyGroup f WHERE f.name = :name"),
    @NamedQuery(name = "FriendlyGroup.findByDescription", query = "SELECT f FROM FriendlyGroup f WHERE f.description = :description")})
public class FriendlyGroup implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGroup", fetch = FetchType.LAZY)
    private List<Item> itemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGroup", fetch = FetchType.LAZY)
    private List<Chore> choreList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGroup", fetch = FetchType.LAZY)
    private List<UserGroup> userGroupList;

    public FriendlyGroup() {
    }

    public FriendlyGroup(Integer id) {
        this.id = id;
    }

    public FriendlyGroup(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
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
        if (!(object instanceof FriendlyGroup)) {
            return false;
        }
        FriendlyGroup other = (FriendlyGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flatmatesapp.model.FriendlyGroup[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @XmlTransient
    public List<Chore> getChoreList() {
        return choreList;
    }

    public void setChoreList(List<Chore> choreList) {
        this.choreList = choreList;
    }
    
}
