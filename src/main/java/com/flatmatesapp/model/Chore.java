package com.flatmatesapp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dafi
 */
@Entity
@Table(name = "chore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chore.findAll", query = "SELECT c FROM Chore c"),
    @NamedQuery(name = "Chore.findById", query = "SELECT c FROM Chore c WHERE c.id = :id"),
    @NamedQuery(name = "Chore.findByName", query = "SELECT c FROM Chore c WHERE c.name = :name"),
    @NamedQuery(name = "Chore.findByAddedDate", query = "SELECT c FROM Chore c WHERE c.addedDate = :addedDate"),
    @NamedQuery(name = "Chore.findByRecurrentInterval", query = "SELECT c FROM Chore c WHERE c.recurrentInterval = :recurrentInterval"),
    @NamedQuery(name = "Chore.findByStartingDate", query = "SELECT c FROM Chore c WHERE c.startingDate = :startingDate"),
    @NamedQuery(name = "Chore.findByImportance", query = "SELECT c FROM Chore c WHERE c.importance = :importance"),
    @NamedQuery(name = "Chore.findByDueDate", query = "SELECT c FROM Chore c WHERE c.dueDate = :dueDate")})
public class Chore implements Serializable {
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
    @Column(name = "added_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;
    @Column(name = "recurrent_interval")
    private Integer recurrentInterval;
    @Column(name = "starting_date")
    @Temporal(TemporalType.DATE)
    private Date startingDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "importance")
    private String importance;
    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User idUser;
    @JoinColumn(name = "id_group", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FriendlyGroup idGroup;

    public Chore() {
    }

    public Chore(Integer id) {
        this.id = id;
    }

    public Chore(Integer id, String name, String importance) {
        this.id = id;
        this.name = name;
        this.importance = importance;
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

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Integer getRecurrentInterval() {
        return recurrentInterval;
    }

    public void setRecurrentInterval(Integer recurrentInterval) {
        this.recurrentInterval = recurrentInterval;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public FriendlyGroup getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(FriendlyGroup idGroup) {
        this.idGroup = idGroup;
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
        if (!(object instanceof Chore)) {
            return false;
        }
        Chore other = (Chore) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flatmatesapp.model.Chore[ id=" + id + " ]";
    }
    
}
