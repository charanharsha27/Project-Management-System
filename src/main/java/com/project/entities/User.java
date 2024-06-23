package com.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Issues> assignedIssues = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @ToString.Exclude
    private List<Chat> chat = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Messages> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Project> projects = new ArrayList<>();

//    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Project> nprojects;

    private int projectsSize;
}