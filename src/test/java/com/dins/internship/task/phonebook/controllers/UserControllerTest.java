package com.dins.internship.task.phonebook.controllers;

import com.dins.internship.task.phonebook.entities.UserEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.User;
import com.dins.internship.task.phonebook.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        User user3 = new User();
        user3.setId(9L);
        user3.setName("User3");
        Mockito.doReturn(Arrays.asList(user1, user2, user3))
                .when(userService)
                .getAll();
        List<User> users = userController.getAllUsers();
        Assertions.assertEquals(users.size(), 3);
    }

    @Test
    void getUserById() {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        User user3 = new User();
        user3.setId(9L);
        user3.setName("User3");
        Mockito.doReturn(Optional.of(user2))
                .when(userService)
                .getById(8L);
        ResponseEntity<User> user = userController.getUserById(8L);
        Assertions.assertEquals(Objects.requireNonNull(user.getBody()).getId(), 8L);
    }

    @Test
    void getUserByName() {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        User user3 = new User();
        user3.setId(9L);
        user3.setName("User3");
        Mockito.doReturn(Arrays.asList(user1, user2, user3))
                .when(userService)
                .getUsersByName("User");
        List<User> users = userController.getUserByName("User");
        Assertions.assertEquals(users.size(), 3);
    }

    @Test
    void create() throws PhonebookException {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        UserEntity user3 = new UserEntity();
        user3.setId(9L);
        user3.setName("User3");
        Mockito.doReturn(User.toModel(user3))
                .when(userService)
                .create(user3);
        ResponseEntity<User> user = userController.create(user3);
        Assertions.assertEquals(Objects.requireNonNull(user.getBody()).getId(), 9L);
    }

    @Test
    void update() throws PhonebookException {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        UserEntity user3 = new UserEntity();
        user3.setId(6L);
        user3.setName("User3");
        Mockito.doReturn(User.toModel(user3))
                .when(userService)
                .update(9L, user3);
        ResponseEntity<User> user = userController.update(9L, user3);
        Assertions.assertEquals(Objects.requireNonNull(user.getBody()).getId(), 6L);
    }

    @Test
    void delete() throws PhonebookException {
        User user1 = new User();
        user1.setId(7L);
        user1.setName("User1");
        User user2 = new User();
        user2.setId(8L);
        user2.setName("User2");
        UserEntity user3 = new UserEntity();
        user3.setId(9L);
        user3.setName("User3");
        Mockito.doReturn(Optional.of(User.toModel(user3)))
                .when(userService)
                .getById(9L);
        ResponseEntity<User> user = userController.delete(9L);
        Assertions.assertEquals(user.getStatusCode(), HttpStatus.OK);
    }
}