package com.bitespeed.identify.service;

import com.bitespeed.identify.entitty.Contact;
import com.bitespeed.identify.entitty.IdentifyRequest;
import com.bitespeed.identify.entitty.IdentifyResponse;
import com.bitespeed.identify.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class IdentifyServiceImpl {
    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public IdentifyResponse getAllContacts(IdentifyRequest identifyRequest){
        String phoneNumber = identifyRequest.getPhoneNumber();
        String email = identifyRequest.getEmail();
        if (phoneNumber!=null && email!=null){
            return buildResponseWhenBothExists(phoneNumber,email);
        }
        return buildResponseWhenOneExists(phoneNumber,email);
    }

    private IdentifyResponse buildResponseWhenOneExists(String phoneNumber, String email) {
        int primaryKey=-1;
        List<Contact> contactList;
        if (phoneNumber!=null){
            contactList = contactRepository.findByPhoneNumber(phoneNumber);
        }
        else {
            contactList = contactRepository.findByEmail(email);
        }
        primaryKey=getPrimaryKey(contactList);
        if (primaryKey==-1){
            primaryKey=createAndSaveContact(phoneNumber, email, "Primary", null);
        }
        return buildResponse(primaryKey);
    }

    private IdentifyResponse buildResponseWhenBothExists(String phoneNumber, String email) {
        List<Contact> contactList = contactRepository.findByPhoneNumberOrEmail(phoneNumber, email);

        Set<Integer> primaryKeys = new HashSet<>();
        boolean contactExists=false;
        for (Contact contact : contactList) {
            Integer linkedId = contact.getLinkedId();
            if(linkedId!=null)primaryKeys.add(linkedId);
            else{
                primaryKeys.add(contact.getId());
            }
            boolean b1 = email.equals(contact.getEmail());
            boolean b2 = phoneNumber.equals(contact.getPhoneNumber());

            if(b1 && b2)contactExists=true;
        }
        ArrayList<Integer> primaryKeyArr = new ArrayList<>(primaryKeys);
        int primaryKey;
        if(primaryKeyArr.size()==0){
            primaryKey=createAndSaveContact(phoneNumber, email, "Primary", null);

        }
        else if (primaryKeyArr.size()==1) {
            primaryKey = primaryKeyArr.get(0);
            if (!contactExists) createAndSaveContact(phoneNumber, email, "Secondary", primaryKey);
        }
        else{
            primaryKey= updateLatestPrimary(primaryKeyArr.get(0),primaryKeyArr.get(1));
            createAndSaveContact(phoneNumber, email, "Secondary", primaryKey);
        }

        return buildResponse(primaryKey);
    }

    private int getPrimaryKey(List<Contact> contactList) {
        if (contactList.isEmpty()) return -1;
        Contact contact = contactList.get(0);
        if (contact.getLinkedId()!=null)return contact.getLinkedId();
        return contact.getId();
    }

    private Integer updateLatestPrimary(Integer p1, Integer p2 ) {
        Contact latestPrimaryContact = contactRepository.findFirstByIdInOrderByCreatedAtDesc(Arrays.asList(p1,p2));
        Integer updatedPrimary;
        if(p1==latestPrimaryContact.getId()){
            updatedPrimary=p2;
        }else {
            updatedPrimary=p1;
        }
        latestPrimaryContact.setLinkedId(updatedPrimary);
        latestPrimaryContact.setLinkPrecedence("Secondary");
        contactRepository.save(latestPrimaryContact);
        contactRepository.updateLinkedId(latestPrimaryContact.getId(),updatedPrimary);
        return updatedPrimary;
    }

    private Integer createAndSaveContact(String phoneNumber, String email, String precedence, Integer linkedId) {
        Contact temp = new Contact();
        temp.setEmail(email);
        temp.setLinkPrecedence(precedence);
        temp.setPhoneNumber(phoneNumber);
        temp.setLinkedId(linkedId);
        Contact contact = contactRepository.save(temp);
        return contact.getId();
    }

    public IdentifyResponse buildResponse(Integer primaryKey) {
        // Fetch records from the table where linkPrecedence is equal to primaryKey
        List<Contact> contacts = contactRepository.findByLinkedId(primaryKey);
        Contact primaryContact = contactRepository.findById(primaryKey).get();

        // Build the IdentityResponse object using the fetched records
        IdentifyResponse response = new IdentifyResponse();
        response.setPrimaryContactId(primaryKey);

        Set<String> emails = new HashSet<>();
        Set<String> phoneNumbers = new HashSet<>();
        Set<Integer> secondaryContactIds = new HashSet<>();

        for (Contact contact : contacts) {
            if(contact.getEmail()!=null)emails.add(contact.getEmail());
            if(contact.getPhoneNumber()!=null)phoneNumbers.add(contact.getPhoneNumber());
            secondaryContactIds.add(contact.getId());
        }
        if (primaryContact!=null){
            if(primaryContact.getEmail()!=null)emails.add(primaryContact.getEmail());
            if(primaryContact.getPhoneNumber()!=null)phoneNumbers.add(primaryContact.getPhoneNumber());
        }


        response.setEmails(new ArrayList<>(emails));
        response.setPhoneNumbers(new ArrayList<>(phoneNumbers));
        response.setSecondaryContactIds(new ArrayList<>(secondaryContactIds));

        return response;
    }

}
