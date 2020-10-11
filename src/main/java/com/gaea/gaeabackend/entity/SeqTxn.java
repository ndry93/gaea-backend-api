/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ndry93
 */
@Entity
@Table(name = "seq_txn")
@NamedQueries({
    @NamedQuery(name = "SeqTxn.findAll", query = "SELECT s FROM SeqTxn s")})
public class SeqTxn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "txn_num")
    private String txnNum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "seq_name")
    private String seqName;

    public SeqTxn() {
    }

    public SeqTxn(Integer id) {
        this.id = id;
    }

    public SeqTxn(Integer id, String txnNum, String seqName) {
        this.id = id;
        this.txnNum = txnNum;
        this.seqName = seqName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnNum() {
        return txnNum;
    }

    public void setTxnNum(String txnNum) {
        this.txnNum = txnNum;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
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
        if (!(object instanceof SeqTxn)) {
            return false;
        }
        SeqTxn other = (SeqTxn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gaea.gaeabackend.entity.SeqTxn[ id=" + id + " ]";
    }
    
}
