package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name_role;

    @OneToOne(optional = true, mappedBy = "role")
    private User user;

    public Role(String name_role, User user) {
        this.name_role = name_role;
        this.user = user;
    }

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRole() {
        return name_role;
    }

    public void setNameRole(String name_role) {
        this.name_role = name_role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

