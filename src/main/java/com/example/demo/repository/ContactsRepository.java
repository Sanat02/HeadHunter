package com.example.demo.repository;

import com.example.demo.model.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactsRepository extends JpaRepository<Contacts,Integer> {
    List<Contacts> findByResumeId(int resumeId);
}
