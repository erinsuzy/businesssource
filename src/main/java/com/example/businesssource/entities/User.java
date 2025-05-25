package com.example.businesssource.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private boolean hasCompletedQuiz = false;
    private String quizResult;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BusinessPlan> businessPlans = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles;

    // Constructors, getters, and setters
    public User() {}

    public User(Long id, String username, String password, String email, String firstName, String lastName, boolean hasCompletedQuiz, String quizResult, List<BusinessPlan> businessPlans, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hasCompletedQuiz = hasCompletedQuiz;
        this.quizResult = quizResult;
        this.businessPlans = businessPlans;
        this.roles = roles;
    }

    public String getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(String quizResult) {
        this.quizResult = quizResult;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isHasCompletedQuiz() {
        return hasCompletedQuiz;
    }

    public void setHasCompletedQuiz(boolean hasCompletedQuiz) {
        this.hasCompletedQuiz = hasCompletedQuiz;
    }

    public List<BusinessPlan> getBusinessPlans() {
        return businessPlans;
    }

    public void setBusinessPlans(List<BusinessPlan> businessPlans) {
        this.businessPlans = businessPlans;
    }
}
