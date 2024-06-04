package com.project.DTO;

import com.project.entities.Project;
import com.project.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {

    private Long id;
    private Project project;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long projectId;
    private LocalDate dueDate;
    private List<String> tags;
    private User user;

}
