package com.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Issues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Project project;

    private String title;
    private String description;
    private String status;
    private String priority;
    private Long projectID;
    private LocalDate dueDate;

    private List<String> tags;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}