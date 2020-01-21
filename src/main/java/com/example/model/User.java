package com.example.model;

import java.io.Serializable;
import java.util.Set;

//import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "user_id")
    private int id;
    
//    @Column(name="email")
    @Email(message="*Please provide a valid Email")
    @NotEmpty(message="*Please provide an email")
    private String email;
    
//    @Column(name="password")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    
//    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;
    
//    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    
//    @Column(name = "active")
    private int active;
    
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", lastName="
                + lastName + ", active=" + active + ", roles=" + roles + "]";
    }
    
    
    
}
