package com.anonymizer.auth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private int id;
    private  String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @JsonBackReference(value="user-role")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST
            },
            mappedBy = "roles")
    private Set<User> users;

    @JsonBackReference(value="group-role")
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST
            },
            mappedBy = "roles")
    private Set<Group> groups;

    @ManyToMany
    @JoinTable(
            name ="roles_permissions",
            joinColumns = @JoinColumn(name = "role_id",  referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<Permission> permissions;

}
