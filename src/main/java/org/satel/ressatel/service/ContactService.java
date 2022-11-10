package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.satel.ressatel.entity.Contact;
import org.satel.ressatel.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
@ApplicationScoped
public class ContactService {
    private final ContactRepository contactRepository;

    @Inject
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void createOrUpdate(Contact con) {
        contactRepository.saveAndFlush(con);
    }
}
