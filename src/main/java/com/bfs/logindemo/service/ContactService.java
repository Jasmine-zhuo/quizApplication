package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactDao contactDao;

    @Autowired
    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public List<Contact> getAllContacts() {
        return contactDao.findAll();
    }

    public Contact getContactById(int id) {
        return contactDao.findById(id);
    }

    public void saveContact(Contact contact) {
        contactDao.save(contact);
    }

    public void updateContact(Contact contact) {
        contactDao.update(contact);
    }

    public void deleteContact(int id) {
        contactDao.delete(id);
    }
}
