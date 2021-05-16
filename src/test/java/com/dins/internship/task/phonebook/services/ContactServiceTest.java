package com.dins.internship.task.phonebook.services;

import com.dins.internship.task.phonebook.entities.ContactEntity;
import com.dins.internship.task.phonebook.entities.UserEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.Contact;
import com.dins.internship.task.phonebook.repositories.ContactRepository;
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
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    void create() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        Mockito.doReturn(Optional.of(new UserEntity(7L)))
                .when(userRepository)
                .findById(7L);
        Mockito.doReturn(contact1)
                .when(contactRepository)
                .save(contact1);
        Contact contact = contactService.create(contact1, 7L);
        Assertions.assertEquals(contact, Contact.toModel(contact1));
        Assertions.assertEquals(contact.getId(), Contact.toModel(contact1).getId());
    }

    @Test
    void getById() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        UserEntity userEntity = new UserEntity(7L);
        userEntity.getContactEntities().add(contact1);
        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(7L);
        Contact contact = contactService.getById(7L, 1L);
        Assertions.assertEquals(contact, Contact.toModel(contact1));
        Assertions.assertEquals(contact.getId(), Contact.toModel(contact1).getId());
    }

    @Test
    void getAllUsersContacts() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);
        ContactEntity contact2 = new ContactEntity();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        UserEntity userEntity = new UserEntity(7L);
        userEntity.getContactEntities().add(contact1);
        userEntity.getContactEntities().add(contact2);
        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(7L);
        List<Contact> contacts = contactService.getAllUsersContacts(7L);
        Assertions.assertEquals(contacts.get(0), Contact.toModel(contact1));
        Assertions.assertEquals(contacts.get(1), Contact.toModel(contact2));
    }

    @Test
    void update() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);

        ContactEntity contact2 = new ContactEntity();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);

        UserEntity userEntity = new UserEntity(7L);
        userEntity.getContactEntities().add(contact1);
        userEntity.getContactEntities().add(contact2);

        ContactEntity contact3 = new ContactEntity();
        contact3.setName("Mike");
        contact3.setId(3L);
        contact3.setPhoneNumber("321432234");

        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(7L);
        Mockito.doReturn(Optional.of(contact2))
                .when(contactRepository)
                .findById(2L);
        Mockito.doReturn(contact3)
                .when(contactRepository)
                .save(contact3);
        Contact contact = contactService.update(7L, 2L, contact3);
        Assertions.assertEquals(2L, contact.getId());
        Assertions.assertEquals("Mike", contact.getName());

    }

    @Test
    void deleteById() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);

        ContactEntity contact2 = new ContactEntity();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);
        ContactEntity contact3 = new ContactEntity();
        contact3.setName("Mike");
        contact3.setId(3L);
        contact3.setPhoneNumber("321432234");
        UserEntity userEntity = new UserEntity(7L);
        userEntity.getContactEntities().add(contact1);
        userEntity.getContactEntities().add(contact2);
        userEntity.getContactEntities().add(contact3);
        contactRepository.save(contact1);
        contactRepository.save(contact2);
        contactRepository.save(contact3);
        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(7L);
        Mockito.doReturn(Optional.of(contact2))
                .when(contactRepository)
                .findById(2L);
        contactService.deleteById(7L, 2L);
        Assertions.assertFalse(contactRepository.existsById(2L));
    }

    @Test
    void findContactByPhoneNumber() throws PhonebookException {
        ContactEntity contact1 = new ContactEntity();
        contact1.setName("Alex");
        contact1.setPhoneNumber("11122233344");
        contact1.setId(1L);

        ContactEntity contact2 = new ContactEntity();
        contact2.setName("Ilya");
        contact2.setPhoneNumber("45634893422");
        contact2.setId(2L);

        ContactEntity contact3 = new ContactEntity();
        contact3.setName("Mike");
        contact3.setId(3L);
        contact3.setPhoneNumber("321432234");

        UserEntity userEntity = new UserEntity(7L);
        userEntity.getContactEntities().add(contact1);
        userEntity.getContactEntities().add(contact2);
        userEntity.getContactEntities().add(contact3);

        Mockito.doReturn(Optional.of(userEntity))
                .when(userRepository)
                .findById(7L);
        Mockito.doReturn(Arrays.asList(contact1, contact2, contact3))
                .when(contactRepository)
                .findAll();


        Optional<Contact> contact = contactService.findContactByPhoneNumber(7L, "321432234");
        Assertions.assertTrue(contact.isPresent());
    }
}