package com.cg.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(nullable = false, columnDefinition = "BIT default 0")
    private Boolean deleted;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount, Date createdAt, String createdBy, Boolean deleted) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
