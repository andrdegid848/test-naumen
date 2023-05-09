package com.example.naumen.http.controller;

import com.example.naumen.dto.PersonDto;
import com.example.naumen.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class PersonController {

    private static final Integer MAX_PERSON_AGE = 116;

    private final PersonService personService;

    private Map<String, Long> nameCounter = new HashMap<>();

    @GetMapping
    public String getPeopleForm(Model model) {
        model.addAttribute("oldPerson", personService.getNameByMaxAge());
        model.addAttribute("people", personService.findAll());
        return "person/greeting";
    }

    @GetMapping("/person")
    public String getPersonForm(@ModelAttribute("person") PersonDto personDto) {
        return "person/person";
    }

    @GetMapping("/{firstname}/firstname")
    public String getPersonByFirstname(@PathVariable("firstname") String firstname, Model model) {
        Optional<PersonDto> maybePerson = personService.getPersonByFirstname(firstname);
        if (maybePerson.isEmpty())
            maybePerson = Optional.ofNullable(personService.create(PersonDto.builder()
                    .firstname(firstname)
                    .age(generateRandomAge())
                    .build()));
        nameCounter.merge(firstname, 1L, Long::sum);
        model.addAttribute("person", maybePerson.get());
        model.addAttribute("count", nameCounter.get(firstname));
        return "person/firstname";
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(Model model, @PathVariable("id") Long id) {
        PersonDto personDto = personService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("id", id);
        model.addAttribute("person", personDto);
        return "person/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Validated PersonDto personDto) {
        nameCounter.put(personDto.getFirstname(), 0L);
        return "redirect:/people/" + personService.create(personDto).getFirstname() + "/firstname";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("person") @Validated PersonDto personDto) {
        return personService.update(id, personDto)
                .map(item -> "redirect:/people/" + personDto.getFirstname() + "/firstname")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        Optional<PersonDto> maybePerson = personService.findById(id);
        maybePerson.ifPresent(person -> nameCounter.remove(person.getFirstname()));
        if (!personService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return "redirect:/people";
    }

    private Integer generateRandomAge() {
        Random random = new Random();
        return random.nextInt(MAX_PERSON_AGE);
    }
}
