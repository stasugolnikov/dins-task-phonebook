package com.dins.internship.task.phonebook.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @JsonProperty("name")
    @Column(name = "name")
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Setter
    @Getter
    private List<ContactEntity> contactEntities = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(Long id) {
        this.setId(id);
    }

    public UserEntity(String name) {
        this.name = name;
    }

}
