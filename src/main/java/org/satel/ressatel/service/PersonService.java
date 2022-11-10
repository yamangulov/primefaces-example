package org.satel.ressatel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.primefaces.util.Constants;
import org.satel.ressatel.bean.list.person.Person;
import org.satel.ressatel.entity.Skill;
import org.satel.ressatel.entity.Specialty;
import org.satel.ressatel.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
@ApplicationScoped
public class PersonService {
    private final PersonRepository personRepository;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address}")
    private String serverHost;

    @Value("${server.protocol}")
    private String serverProtocol;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getShortList() {
        List<Person> persons = new ArrayList<>();
        List<org.satel.ressatel.entity.Person> fullPersons = getList();
        fullPersons.forEach(fullPerson -> {
            Person person = new Person(
                    getFullName(fullPerson),
                    getINN(fullPerson),
                    getSkills(fullPerson),
                    getSpecialties(fullPerson),
                    getLink(fullPerson)
            );
            persons.add(person);
        });
        return persons;
    }

    private String getLink(org.satel.ressatel.entity.Person fullPerson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(serverProtocol);
        stringBuilder.append("://");
        stringBuilder.append(serverHost);
        stringBuilder.append(":");
        stringBuilder.append(serverPort);
        stringBuilder.append("/person/");
        stringBuilder.append(fullPerson.getId());
        return stringBuilder.toString();
    }

    private String getSpecialties(org.satel.ressatel.entity.Person fullPerson) {
        List<Specialty> specialties = new ArrayList<>(fullPerson.getSpecialties());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < specialties.size(); i++) {
            stringBuilder.append(specialties.get(i).getName());
            if (i != specialties.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String getSkills(org.satel.ressatel.entity.Person fullPerson) {
        List<Skill> skills = new ArrayList<>(fullPerson.getSkills());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            stringBuilder.append(skills.get(i).getName());
            if (i != skills.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private String getINN(org.satel.ressatel.entity.Person fullPerson) {
        return fullPerson.getInn();
    }

    private String getFullName(org.satel.ressatel.entity.Person fullPerson) {
        if (fullPerson.getSurname() == null
                || fullPerson.getFirstName() == null
                || fullPerson.getSecondName() == null) {
            return null;
        }
        return fullPerson.getSurname() + Constants.SPACE + fullPerson.getFirstName().substring(0, 1).toUpperCase() + "." +
                Constants.SPACE + fullPerson.getSecondName().substring(0, 1).toUpperCase() + ".";
    }

    private List<org.satel.ressatel.entity.Person> getList() {
        return personRepository.findAll();
    }


    public org.satel.ressatel.entity.Person getByStringId(String personId) {
        return personRepository.findById(Integer.valueOf(personId)).orElse(null);
    }

    public List<String> getHierarchicalSkills(org.satel.ressatel.entity.Person person) {
        Set<Skill> skills = person.getSkills();
        if (!skills.isEmpty()) {
            List<String> strings = new ArrayList<>();
            skills.forEach(skill -> {
                if (skill.getParent() == null) {
                    AtomicReference<Integer> level = new AtomicReference<>(0);
                    addSkills(skill, strings, skills, level);
                }
            });
            List<String> hierarchy = new ArrayList<>();
            strings.forEach(s -> {
                String first = s.substring(0, 1);
                String other = s.substring(1);
                hierarchy.add("-".repeat(Integer.parseInt(first)) + other);
            });
            return hierarchy;
        }
        return null;
    }

    private void addSkills(Skill skill, List<String> strings, Set<Skill> skills, AtomicReference<Integer> level) {
        strings.add(level.get() + skill.getName());
        skills.forEach(skl -> {
            if (skl.getParent() != null && skl.getParent().getId().intValue() == skill.getId().intValue()) {
                level.getAndSet(level.get() + 1);
                addSkills(skl, strings, skills, level);
                level.getAndSet(level.get() - 1);
            }
        });
    }

    public void createOrUpdatePerson(org.satel.ressatel.entity.Person person) {
        personRepository.save(person);
    }

}
