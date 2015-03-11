/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flatmatesapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iulian.dafinoiu
 */
@Entity
@Table(name = "spending")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spending.findAll", query = "SELECT s FROM Spending s"),
    @NamedQuery(name = "Spending.findById", query = "SELECT s FROM Spending s WHERE s.id = :id"),
    @NamedQuery(name = "Spending.findByAmount", query = "SELECT s FROM Spending s WHERE s.amount = :amount"),
    @NamedQuery(name = "Spending.findBySpendingDate", query = "SELECT s FROM Spending s WHERE s.spendingDate = :spendingDate")})
public class Spending implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spending_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spendingDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spending", fetch = FetchType.LAZY)
    private List<Item> itemList;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSpending", fetch = FetchType.LAZY)
    private List<Payment> paymentList;

    public Spending() {
    }

    public Spending(Integer id) {
        this.id = id;
    }

    public Spending(Integer id, int amount, Date spendingDate) {
        this.id = id;
        this.amount = amount;
        this.spendingDate = spendingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getSpendingDate() {
        return spendingDate;
    }

    public void setSpendingDate(Date spendingDate) {
        this.spendingDate = spendingDate;
    }

    @XmlTransient
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
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
        if (!(object instanceof Spending)) {
            return false;
        }
        Spending other = (Spending) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.flatmatesapp.model.Spending[ id=" + id + " ]";
    }
    
}
