package com.example.crowdfunding.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private double requiredFunding;

    @Column
    private double totalFunding;

    @Column
    private boolean status;
    // True if the project is approved by the admin, false if the project has yet to be approved

    // Σχέση με Supporters
    @ManyToMany(mappedBy = "projects", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Supporter> supporters;

    // Σχέση με Contributions
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Contribution> contributions;

    // Σχέση με τον Creator
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    // Constructors
    public Project(Integer id, String title, String description, double requiredFunding, Creator creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredFunding = requiredFunding;
        this.totalFunding = 0;
        this.status = false;
        this.creator = creator;
    }

    public Project(String title, String description, double requiredFunding, Creator creator) {
        this.title = title;
        this.description = description;
        this.requiredFunding = requiredFunding;
        this.totalFunding = 0;
        this.status = false;
        this.creator = creator;
    }

    public Project() {
        this.status = false;
        this.totalFunding = 0;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRequiredFunding() {
        return requiredFunding;
    }

    public void setRequiredFunding(double requiredFunding) {
        this.requiredFunding = requiredFunding;
    }

    public double getTotalFunding() {
        return totalFunding;
    }

    public void setTotalFunding(double totalFunding) {
        this.totalFunding = totalFunding;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Supporter> getSupporters() {
        return supporters;
    }

    public void setSupporters(List<Supporter> supporters) {
        this.supporters = supporters;
    }

    public void addSupporter(Supporter sup) {
        supporters.add(sup);
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public void addAmount(double amount) {
        if (this.totalFunding < this.requiredFunding) {
            double remaining = this.requiredFunding - this.totalFunding;
            this.totalFunding += Math.min(amount, remaining);
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", requiredFunding=" + requiredFunding +
                ", totalFunding=" + totalFunding +
                ", status=" + status +
                ", creator=" + (creator != null ? creator.getUsername() : "null") +
                '}';
    }
}
