package com.example.crowdfunding.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer Id;

    @Column
    private double amount;

    @Column
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "supporter_id")
    private Supporter supporter;


    public Contribution(Integer id, double amount) {
        Id = id;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    public Contribution(double amount, Project project, Supporter supporter) {
        this.amount = amount;
        this.time = LocalDateTime.now();
        this.project = project;
        this.supporter = supporter;
    }

    public Contribution() {}

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Supporter getSupporter() {
        return supporter;
    }

    public void setSupporter(Supporter supporter) {
        this.supporter = supporter;
    }

}
