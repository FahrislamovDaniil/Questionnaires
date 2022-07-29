package com.practice.questionnaires.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class EntityCore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdWhen;
    @UpdateTimestamp
    private Date updatedWhen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    public Date getUpdatedWhen() {
        return updatedWhen;
    }

    public void setUpdatedWhen(Date updatedWhen) {
        this.updatedWhen = updatedWhen;
    }
}
