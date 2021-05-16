package com.dins.internship.task.phonebook.services;

import com.dins.internship.task.phonebook.entities.ContactEntity;
import com.dins.internship.task.phonebook.entities.UserEntity;
import com.dins.internship.task.phonebook.exceptions.PhonebookException;
import com.dins.internship.task.phonebook.models.Contact;
import com.dins.internship.task.phonebook.models.User;
import com.dins.internship.task.phonebook.repositories.ContactRepository;
import com.dins.internship.task.phonebook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ContactRepository contactRepository;

    public ContactService(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public Contact create(ContactEntity contactEntity, Long userId) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        contactEntity.setUser(user.get());
        return Contact.toModel(contactRepository.save(contactEntity));
    }

    public Contact getById(Long userId, Long contactId) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        ContactEntity contact = user.get().getContactEntities()
                .stream()
                .filter(contactEntity -> contactEntity.getId().equals(contactId))
                .findFirst()
                .orElse(null);
        if (contact == null) {
            throw new PhonebookException(String.format("Contact with id %s does not exist", contactId));
        }
        return Contact.toModel(contact);
    }

    public List<Contact> getAllUsersContacts(Long userId) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        return User.toModel(user.get()).getContacts();
    }

    public Contact update(Long userId, Long contactId, ContactEntity contactEntity) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        if (contactRepository.findById(contactId).isEmpty()) {
            throw new PhonebookException(String.format("Contact with id %s does not exist", contactId));
        }
        contactEntity.setId(contactId);
        contactEntity.setUser(user.get());
        return Contact.toModel(contactRepository.save(contactEntity));
    }

    public void deleteById(Long userId, Long contactId) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        if (user.get().getContactEntities().stream().noneMatch(contactEntity ->
                contactEntity.getId().equals(contactId))) {
            throw new PhonebookException(String.format("Contact with id %s does not exist", contactId));
        }
        contactRepository.deleteById(contactId);
    }

    public Optional<Contact> findContactByPhoneNumber(Long userId, String phoneNumber) throws PhonebookException {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new PhonebookException(String.format("User with id %s does not exist", userId));
        }
        for (ContactEntity contactEntity : user.get().getContactEntities()) {
            if (contactEntity.getPhoneNumber().equals(phoneNumber)) {
                return Optional.of(Contact.toModel(contactEntity));
            }
        }
        return Optional.empty();
    }


}
