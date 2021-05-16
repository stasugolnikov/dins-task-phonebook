package com.dins.internship.task.phonebook.services;

import com.dins.internship.task.phonebook.entities.UserEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.User;
import com.dins.internship.task.phonebook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserEntity userEntity) throws PhonebookException {
        if (userRepository.existsById(userEntity.getId())) {
            throw new PhonebookException(String.format("User with id %s already exists", userEntity.getId()));
        }
        return User.toModel(userRepository.save(userEntity));
    }

    public List<User> getAll() {
        return userRepository.findAll().stream().map(User::toModel).collect(Collectors.toList());
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id).map(User::toModel);
    }

    public User update(Long id, UserEntity userEntity) throws PhonebookException {
        if (!userRepository.existsById(id)) {
            throw new PhonebookException(String.format("User with id %s does not exist", userEntity.getId()));
        }
        userEntity.setId(id);
        return User.toModel(userRepository.save(userEntity));
    }

    public void deleteById(Long id) throws PhonebookException {
        if (!userRepository.existsById(id)) {
            throw new PhonebookException(String.format("User with id %s does not exist", id));
        }
        userRepository.deleteById(id);
    }

    public List<User> getUsersByName(String name) {
        List<User> result = new ArrayList<>();
        for (User userEntity : getAll()) {
            if (userEntity.getName().contains(name)) {
                result.add(userEntity);
            }
        }
        return result;
    }

}