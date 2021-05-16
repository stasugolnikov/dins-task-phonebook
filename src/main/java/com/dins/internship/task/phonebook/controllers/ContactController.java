package com.dins.internship.task.phonebook.controllers;

import com.dins.internship.task.phonebook.entities.ContactEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.Contact;
import com.dins.internship.task.phonebook.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {
    @Autowired
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/users/{userId}/contacts/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable("userId") Long userId,
                                                  @PathVariable("contactId") Long contactId) {
        try {
            return new ResponseEntity<>(contactService.getById(userId, contactId), HttpStatus.OK);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{id}/contacts")
    public ResponseEntity<List<Contact>> getAllUsersContacts(@PathVariable("id") Long userId) {
        try {
            return new ResponseEntity<>(contactService.getAllUsersContacts(userId), HttpStatus.OK);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/users/{id}/contacts/numbers/{number}")
    public ResponseEntity<Optional<Contact>> findContactByPhoneNumber(@PathVariable("id") Long userId,
                                                                      @PathVariable("number") String number) {
        try {
            return new ResponseEntity<>(contactService.findContactByPhoneNumber(userId, number), HttpStatus.OK);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/users/{id}/contacts")
    public ResponseEntity<Contact> create(@PathVariable("id") Long userId, @RequestBody ContactEntity contactEntity) {
        try {
            return new ResponseEntity<>(contactService.create(contactEntity, userId), HttpStatus.OK);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{userId}/contacts/{contactId}")
    public ResponseEntity<Contact> update(@PathVariable("userId") Long userId,
                                          @PathVariable("contactId") Long contactId,
                                          @RequestBody ContactEntity contactEntity) {
        try {
            return new ResponseEntity<>(contactService.update(userId, contactId, contactEntity), HttpStatus.OK);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{userId}/contacts/{contactId}")
    public ResponseEntity<Contact> delete(@PathVariable("userId") Long userId,
                                          @PathVariable("contactId") Long contactId) {
        try {
            contactService.deleteById(userId, contactId);
        } catch (PhonebookException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
