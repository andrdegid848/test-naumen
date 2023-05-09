package com.example.naumen.service;

import com.example.naumen.database.entity.Person;
import com.example.naumen.database.repository.PersonRepository;
import com.example.naumen.dto.PersonDto;
import com.example.naumen.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public Optional<PersonDto> getPersonByFirstname(String firstname) {
        return personRepository.getPersonByFirstname(firstname)
                .map(personMapper::mapToDto);
    }

    public Optional<Person> getNameByMaxAge() {
        return personRepository.findFirstByOrderByAgeDesc();
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<PersonDto> findById(Long id) {
        return personRepository.findById(id)
                .map(personMapper::mapToDto);
    }

    @Transactional
    public PersonDto create(PersonDto personDto) {
        return Optional.of(personDto)
                .map(personMapper::mapToEntity)
                .map(personRepository::save)
                .map(personMapper::mapToDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<Person> update(Long id, PersonDto personDto) {
        return personRepository.findById(id)
                .map(item -> personMapper.updateEntity(personDto, item))
                .map(personRepository::saveAndFlush);
    }

    @Transactional
    public boolean delete(Long id) {
        return personRepository.findById(id)
                .map(item -> {
                    personRepository.delete(item);
                    personRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
