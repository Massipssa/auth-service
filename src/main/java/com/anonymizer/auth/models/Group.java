package com.anonymizer.auth.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String groupName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

  /*  @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;*/


    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

  /*  public Set<User> getUsers() {
        return users;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public void setCreateTime(final LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

/*    public void setUsers(Set<User> users) {
        this.users = users;
    }*/
}
