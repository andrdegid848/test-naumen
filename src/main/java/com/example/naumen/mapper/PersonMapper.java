package com.example.naumen.mapper;

import com.example.naumen.database.entity.Person;
import com.example.naumen.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Person mapToEntity(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    public PersonDto mapToDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    public Person updateEntity(PersonDto personDto, Person person) {
        modelMapper.typeMap(PersonDto.class, Person.class)
                .addMappings(mapping -> mapping.skip(Person::setId));
        modelMapper.map(personDto, person);
        return person;
    }

    private void skipId(ModelMapper modelMapper) {
        modelMapper.typeMap(PersonDto.class, Person.class)
                .addMappings(mapping -> mapping.skip(Person::setId));
    }
}
