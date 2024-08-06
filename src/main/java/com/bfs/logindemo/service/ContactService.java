package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {
    private final ContactDao contactDao;

    @Autowired
    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Transactional(readOnly = true)
    public List<Contact> getAllContacts() {
        return contactDao.findAllHibernate();
    }

    @Transactional(readOnly = true)
    public Contact getContactById(int id) {
        return contactDao.findByIdHibernate(id);
    }

    @Transactional
    public void saveContact(Contact contact) {
        contactDao.saveHibernate(contact);
    }

    @Transactional
    public void updateContact(Contact contact) {
        contactDao.updateHibernate(contact);
    }

    @Transactional
    public void deleteContact(int id) {
        contactDao.deleteHibernate(id);
    }
}
