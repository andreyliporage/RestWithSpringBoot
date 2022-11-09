package com.estudo.springrest.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.springrest.exceptions.ResourceNotFoundException;
import com.estudo.springrest.model.Person;
import com.estudo.springrest.repositories.PersonRepository;

@Service
public class PersonService {
    
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    
    @Autowired
    private PersonRepository personRepository;

    public Person findById(Long id) {
        logger.info("Buscado pessoa");
        var person = new Person();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Andrey");
        person.setLastName("Matos");
        person.setaddress("Rio de Janeiro - Rio de Janeiro");
        person.setGender("Masculino");
        return personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Criando pessoa");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Atualizando pessoa");
        
        var entity = personRepository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setaddress(person.getaddress());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deletando pessoa");

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        personRepository.delete(entity);
    }
}
