package com.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Issues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user; //user who assigned the issue

    @ManyToOne
    @JsonIgnore
    private Project project;

    private String title;
    private String description;
    private String status;
    private String priority;
    private Long projectID;
    private LocalDate dueDate;

    private List<String> tags;

    @JsonIgnore
    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments; //inside issues we can write comments abt the particular issue.
}