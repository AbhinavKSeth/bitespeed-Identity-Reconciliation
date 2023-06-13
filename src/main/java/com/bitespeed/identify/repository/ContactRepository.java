package com.bitespeed.identify.repository;

import com.bitespeed.identify.entitty.Contact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findByLinkedId(Integer primaryKey);

    List<Contact> findByPhoneNumberOrEmail(String phoneNumber, String email);

    Contact findFirstByIdInOrderByCreatedAtDesc(@Param("ids") List<Integer> ids);


    List<Contact> findByPhoneNumber(String phoneNumber);

    List<Contact> findByEmail(String email);
    @Modifying
    @Query("UPDATE Contact c SET c.linkedId = :p2, c.updatedAt=current_timestamp WHERE c.linkedId = :p1")
    void updateLinkedId(@Param("p1") Integer oldLinkedId, @Param("p2") Integer newLinkedId);

}
