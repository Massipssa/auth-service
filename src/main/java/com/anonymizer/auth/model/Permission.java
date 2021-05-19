package com.anonymizer.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

  /*  public Permission(int id, String name, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }*/

}
