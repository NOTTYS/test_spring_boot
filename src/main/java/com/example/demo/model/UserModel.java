package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_model", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}),
    @UniqueConstraint(columnNames = {"email"})
})
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "username must not be empty")
    @NotNull(message = "Please enter your username")
    private String username;

    @NotBlank(message = "password must not be empty")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "email must not be empty")
    @NotNull(message = "Please enter your email")
    @Email(message = "The email must be correct format")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();

    public UserModel() {

    }

   public UserModel(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
   }

   public Integer getId() {
    return id;
   }

   public void setId(Integer id) {
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

   public void setPassword() {
    this.password = password;
   }

   public String getEmail() {
    return email;
   }

   public void setEmail(String email) {
    this.email = email;
   }

   public Set<RoleModel> getRoles() {
    return roles;
   }

   public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
   }
    
}
