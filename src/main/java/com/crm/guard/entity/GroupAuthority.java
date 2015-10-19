package com.crm.guard.entity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name="group_authorities")
public class GroupAuthority implements AbstractEntity {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @Id
    @Column(name = "authority")
    private String authority;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
