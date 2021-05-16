package com.dins.internship.task.phonebook.services;

import com.dins.internship.task.phonebook.entities.UserEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.User;
import com.dins.internship.task.phonebook.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void create() throws PhonebookException {
        UserEntity userEntity = new UserEntity(7L);
        userEntity.setName("User");
        Mockito.doReturn(false)
                .when(userRepository)
                .existsById(7L);
        Mockito.doReturn(userEntity)
                .when(userRepository)
                .save(userEntity);
        User user = userService.create(userEntity);
        Assertions.assertEquals(7L, user.getId());
    }

    @Test
    void getAll() {
        UserEntity userEntity1 = new UserEntity(7L);
        userEntity1.setName("User1");
        UserEntity userEntity2 = new UserEntity(8L);
        userEntity2.setName("User2");
        Mockito.doReturn(Arrays.asList(userEntity1, userEntity2))
                .when(userRepository)
                .findAll();
        List<User> list = userService.getAll();
        Assertions.assertEquals(list.get(0).getId(), userEntity1.getId());
        Assertions.assertEquals(list.get(1).getId(), userEntity2.getId());
    }

    @Test
    void getById() {
        UserEntity userEntity1 = new UserEntity(7L);
        userEntity1.setName("User1");
        UserEntity userEntity2 = new UserEntity(8L);
        userEntity2.setName("User2");
        Mockito.doReturn(Optional.of(userEntity2))
                .when(userRepository)
                .findById(8L);
        Optional<User> user = userService.getById(8L);
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void update() throws PhonebookException {
        UserEntity userEntity1 = new UserEntity(7L);
        userEntity1.setName("User1");
        UserEntity userEntity2 = new UserEntity(8L);
        userEntity2.setName("User2");
        UserEntity userEntity3 = new UserEntity(9L);
        userEntity3.setName("User3");
        Mockito.doReturn(userEntity3)
                .when(userRepository)
                .save(userEntity3);
        Mockito.doReturn(true)
                .when(userRepository)
                .existsById(7L);
        User user = userService.update(7L, userEntity3);
        Assertions.assertEquals("User3", user.getName());
    }

    @Test
    void deleteById() throws PhonebookException {
        UserEntity userEntity1 = new UserEntity(7L);
        userEntity1.setName("User1");
        UserEntity userEntity2 = new UserEntity(8L);
        userEntity2.setName("User2");
        UserEntity userEntity3 = new UserEntity(9L);
        userEntity3.setName("User3");
        Mockito.doReturn(true)
                .when(userRepository)
                .existsById(7L);
        userService.deleteById(7L);
        Assertions.assertTrue(userRepository.findById(7L).isEmpty());
    }

    @Test
    void getUsersByName() {
        UserEntity userEntity1 = new UserEntity(7L);
        userEntity1.setName("User1");
        UserEntity userEntity2 = new UserEntity(8L);
        userEntity2.setName("User2");
        UserEntity userEntity3 = new UserEntity(9L);
        userEntity3.setName("User3");
        Mockito.doReturn(Arrays.asList(userEntity1, userEntity2, userEntity3))
                .when(userRepository)
                .findAll();
        List<User> users = userService.getUsersByName("User");
        Assertions.assertEquals(3, users.size());
    }
}