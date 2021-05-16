package com.dins.internship.task.phonebook.controllers;

import com.dins.internship.task.phonebook.entities.ContactEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.Contact;
import com.dins.internship.task.phonebook.models.User;
import com.dins.internship.task.phonebook.services.ContactService;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
class ContactControllerTest {

    @Autowired
    private ContactController contactController;

    @MockBean
    private ContactService contactService;

    @Test
    void getContactById() throws PhonebookException {
        Contact contact1 = new Contact();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        user.getContacts().add(contact1);
        user.getContacts().add(contact2);
        Mockito.doReturn(contact2)
                .when(contactService)
                .getById(7L, 2L);
        ResponseEntity<Contact> contact = contactController.getContactById(7L, 2L);
        Assertions.assertEquals(contact2.getId(), Objects.requireNonNull(contact.getBody()).getId());
    }

    @Test
    void getAllUsersContacts() throws PhonebookException {
        Contact contact1 = new Contact();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        user.getContacts().add(contact1);
        user.getContacts().add(contact2);
        Mockito.doReturn(user.getContacts())
                .when(contactService)
                .getAllUsersContacts(7L);
        ResponseEntity<List<Contact>> contacts = contactController.getAllUsersContacts(7L);
        Assertions.assertEquals(Objects.requireNonNull(contacts.getBody()).get(0), user.getContacts().get(0));
        Assertions.assertEquals(contacts.getBody().get(1), user.getContacts().get(1));
    }

    @Test
    void create() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        Mockito.doReturn(Contact.toModel(contact1))
                .when(contactService)
                .create(contact1, 7L);
        ResponseEntity<Contact> contact = contactController.create(7L, contact1);
        Assertions.assertEquals(Objects.requireNonNull(contact.getBody()).getId(), contact1.getId());
    }

    @Test
    void update() throws PhonebookException {
        Contact contact1 = new Contact();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(3L);
        contactEntity.setName("Bob");
        Mockito.doReturn(Contact.toModel(contactEntity))
                .when(contactService)
                .update(7L, 2L, contactEntity);
        ResponseEntity<Contact> contact = contactController.update(7L, 2L, contactEntity);
        Assertions.assertEquals(Objects.requireNonNull(contact.getBody()).getId(), contactEntity.getId());
    }

    @Test
    void delete() {
        Contact contact1 = new Contact();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        ResponseEntity<Contact> contact = contactController.delete(7L, 2L);
        Assertions.assertEquals(contact.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findContactByPhoneNumber() throws PhonebookException {
        Contact contact1 = new Contact();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Contact contact2 = new Contact();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        User user = new User();
        user.setId(7L);
        user.setName("John");
        Mockito.doReturn(Optional.of(contact2))
                .when(contactService)
                .findContactByPhoneNumber(7L, "45634893422");
        ResponseEntity<Optional<Contact>> contact =
                contactController.findContactByPhoneNumber(7L, "45634893422");
        Assertions.assertTrue(Objects.requireNonNull(contact.getBody()).isPresent());
        Assertions.assertEquals(Objects.requireNonNull(contact.getBody()).get().getId(), contact2.getId());
    }
}