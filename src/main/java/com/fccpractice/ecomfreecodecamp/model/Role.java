package com.fccpractice.ecomfreecodecamp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

//    @ManyToMany(mappedBy = "roles")
//    private Collection<User> users = new HashSet<>();
//    @ManyToMany(cascade = CascadeType.ALL)
//    private Collection<Role> roles;
// Removed cascade from the @ManyToMany relationship
    @ManyToMany
    private Collection<Role> roles;

}
