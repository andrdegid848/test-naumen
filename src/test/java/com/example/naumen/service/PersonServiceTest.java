package com.example.naumen.service;

import com.example.naumen.database.entity.Person;
import com.example.naumen.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Sql("classpath:sql/data.sql")
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PersonServiceTest {

    private static final String FIRSTNAME = "Vasiliy";

    private static final Long PERSON_ID = 1L;

    private final PersonService personService;


    @Test
    void getPersonByFirstname() {
        Optional<PersonDto> personByFirstname = personService.getPersonByFirstname(FIRSTNAME);
        assertTrue(personByFirstname.isPresent());
        personByFirstname.ifPresent(person -> {
            assertEquals("Vasiliev", person.getLastname());
            assertEquals(LocalDate.of(2000, 1, 1), person.getBirthDate());
            assertEquals(23, person.getAge());
        });
    }

    @Test
    void getNameByMaxAge() {
        Optional<Person> personByMaxAge = personService.getNameByMaxAge();
        assertTrue(personByMaxAge.isPresent());
        personByMaxAge.ifPresent(person -> {
            assertEquals("Igor", person.getFirstname());
            assertEquals(47, person.getAge());
        });
    }

    @Test
    void findAll() {
        List<Person> people = personService.findAll();
        assertThat(people).hasSize(5);
    }

    @Test
    void findById() {
        Optional<PersonDto> maybePerson = personService.findById(PERSON_ID);
        assertTrue(maybePerson.isPresent());
        maybePerson.ifPresent(person -> {
            assertEquals("Vasiliy", person.getFirstname());
            assertEquals("Vasiliev", person.getLastname());
            assertEquals(LocalDate.of(2000, 1, 1), person.getBirthDate());
            assertEquals(23, person.getAge());
        });
    }
}