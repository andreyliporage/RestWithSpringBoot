package com.estudo.springrest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.estudo.springrest.model.Person;

@Service
public class PersonService {
    
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {
        logger.info("Buscado pessoa");
        var person = new Person();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Andrey");
        person.setLastName("Matos");
        person.setaddress("Rio de Janeiro - Rio de Janeiro");
        person.setGender("Masculino");
        return person;
    }

    public List<Person> findAll() {
        ArrayList<Person> pessoas = new ArrayList<Person>();
        for (int i = 0; i < 5; i++) {
            var pessoa = this.mockPerson(i);
            pessoas.add(pessoa);
        }

        return pessoas;
    }

    public Person create(Person person) {
        logger.info("Criando pessoa");

        return person;
    }

    public Person update(Person person) {
        logger.info("Atualizando pessoa");
        
        return person;
    }

    private Person mockPerson(int i) {
        var person = new Person();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Pessoa " + i);
        person.setLastName("Sobrenome " + i);
        person.setGender("Masculino");
        person.setaddress("Endereço " + i);

        return person;
    }
}
