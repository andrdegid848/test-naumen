package com.example.naumen.database.repository;

import com.example.naumen.database.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> getPersonByFirstname(String firstname);

    Optional<Person> findFirstByOrderByAgeDesc();
}
