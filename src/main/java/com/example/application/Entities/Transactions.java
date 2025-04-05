package com.example.application.Entities;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;


@Entity
@Table(name= "Transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transactions")
    private Long idTransactions;

    @Column(nullable = false)
    private String transactionName;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private double transactionAmount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private String transactionType;

//JPA Relationship

    //Account
   // @ManyToOne
   // @JoinColumn(name = "account_id", nullable = false)
    //private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
//Getters and Setters

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getIdTransactions() {
        return idTransactions;
    }

    public void setIdTransactions(Long idTransactions) {
        this.idTransactions = idTransactions;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
