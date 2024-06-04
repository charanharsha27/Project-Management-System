package com.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Issues> assignedIssues = new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Issues> issues = new ArrayList<>();


    @JsonIgnore
    @ManyToMany
    private List<Chat> chat = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Messages> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "team",cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    private int projectsSize;
}
