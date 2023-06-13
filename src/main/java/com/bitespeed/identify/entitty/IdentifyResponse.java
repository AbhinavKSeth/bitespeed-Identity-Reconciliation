package com.bitespeed.identify.entitty;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeName("contact")
public class IdentityResponse {
    private int primaryContactId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Integer> secondaryContactIds;

    public int getPrimaryContactId() {
        return primaryContactId;
    }

    public void setPrimaryContactId(int primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Integer> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }
}
