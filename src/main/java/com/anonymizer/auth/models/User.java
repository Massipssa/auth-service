package com.anonymizer.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

// TODO (use lombok later)
/*import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
*/
@Entity
@Table(name = "users")
public class User {

    // TODO
    /*
        - Ajouter un validator
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String userName;
    private String password;
    private String email;
    private boolean enabled;
    private boolean notExpired;
    private boolean notLocked;
    private boolean notCredentialsExpired;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;

    private User() {}

    public User(int id, String userName, String password, String email) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isNotExpired() {
        return notExpired;
    }

    public boolean isNotLocked() {
        return notLocked;
    }

    public boolean isNotCredentialsExpired() {
        return notCredentialsExpired;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setNotExpired(boolean notExpired) {
        this.notExpired = notExpired;
    }

    public void setNotLocked(boolean notLocked) {
        this.notLocked = notLocked;
    }

    public void setNotCredentialsExpired(boolean notCredentialsExpired) {
        this.notCredentialsExpired = notCredentialsExpired;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName='" + userName + '\'' + '}';
    }
}
