package com.project.entities;

import com.project.helper.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subId;

    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private PlanType planType;
    private boolean isValid;

    @OneToOne
    @ToString.Exclude
    private User user;
}