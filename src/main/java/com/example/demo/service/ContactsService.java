package com.example.demo.service;

import com.example.demo.dao.ContactsDao;
import com.example.demo.dto.ContactDto;
import com.example.demo.enums.AccountType;
import com.example.demo.enums.ContactType;
import com.example.demo.model.Contacts;
import com.example.demo.model.Resume;
import com.example.demo.repository.ContactsRepository;
import com.example.demo.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactsService {
    private final ContactsDao contactsDao;
//    private final ContactsRepository contactsRepository;
//    private final ResumeService resumeService;

    public List<ContactDto> getContactsDtoByResumeId(int id) {
        List<Contacts> contacts = contactsDao.getContactsByResumeId(id);
        return contacts.stream()
                .map(contact -> ContactDto.builder()
                        .type(getType(contact.getType()))
                        .value(contact.getValue()).build()
                ).collect(Collectors.toList());

    }

    public void saveContacts(List<ContactDto> contacts, int resumeId) {
     // Resume resume
        List<Contacts> contactsList = contacts.stream()
                .map(e -> Contacts.builder()
                        .value(e.getValue())
                        .type(e.getType().toString())
                       // .resume(resumeService.getResumeById(resumeId))
                        .build()
                ).collect(Collectors.toList());
        for (int i = 0; i < contactsList.size(); i++) {
            contactsDao.save(contactsList.get(i));
        }
    }

    public ContactType getType(String type) {
        if (type.equalsIgnoreCase("TELEGRAM")) {
            return ContactType.TELEGRAM;
        } else if (type.equalsIgnoreCase("EMAIL")) {
            return ContactType.EMAIL;
        } else if (type.equalsIgnoreCase(" PHONE")) {
            return ContactType.PHONE;
        } else if (type.equalsIgnoreCase("FACEBOOK")) {
            return ContactType.FACEBOOK;
        } else {
            return ContactType.LINKEDIN;
        }
    }
}
