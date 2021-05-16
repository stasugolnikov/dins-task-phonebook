package com.dins.internship.task.phonebook.models;

import com.dins.internship.task.phonebook.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    @Getter
    @Setter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private List<Contact> contacts = new ArrayList<>();

    public static User toModel(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setContacts(userEntity.getContactEntities().stream().map(Contact::toModel).collect(Collectors.toList()));
        return user;
    }

    public User() {

    }

}
