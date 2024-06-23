package com.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    private Project project;

    @OneToMany(mappedBy = "chat", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Messages> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    private String chatName;
}