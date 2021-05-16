package com.dins.internship.task.phonebook.models;

import com.dins.internship.task.phonebook.entities.ContactEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Contact {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String phoneNumber;

    public static Contact toModel(ContactEntity contactEntity) {
        Contact contact = new Contact();
        contact.setId(contactEntity.getId());
        contact.setName(contactEntity.getName());
        contact.setPhoneNumber(contactEntity.getPhoneNumber());
        return contact;
    }

    public Contact() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id) && Objects.equals(name, contact.name) && Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber);
    }
}
