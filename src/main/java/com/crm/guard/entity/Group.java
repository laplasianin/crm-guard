package com.crm.guard.entity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name="groups")
public class Group implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

    @Column(name="group_name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
