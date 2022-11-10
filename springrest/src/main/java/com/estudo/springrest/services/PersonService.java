package com.estudo.springrest.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.springrest.exceptions.ResourceNotFoundException;
import com.estudo.springrest.mapper.DozerMapper;
import com.estudo.springrest.mapper.custom.PersonV2Mapper;
import com.estudo.springrest.model.Person;
import com.estudo.springrest.data.VO.V1.PersonVO;
import com.estudo.springrest.data.VO.V2.PersonVOV2;
import com.estudo.springrest.repositories.PersonRepository;

@Service
public class PersonService {
    
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());
    
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonV2Mapper personV2Mapper;

    public PersonVO findById(Long id) {
        logger.info("Buscado pessoa");
        var person = new PersonVO();
        person.setId(counter.getAndIncrement());
        person.setFirstName("Andrey");
        person.setLastName("Matos");
        person.setaddress("Rio de Janeiro - Rio de Janeiro");
        person.setGender("Masculino");
        
        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        return DozerMapper.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        logger.info("Procurando pessoas");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO create(PersonVO person) {
        logger.info("Criando pessoa");
        Person entity = DozerMapper.parseObject(person, Person.class);
        PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Criando pessoa v2");
        Person entity = personV2Mapper.convertVoToEntity(person);
        PersonVOV2 vo = personV2Mapper.convertEntityToVo(personRepository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Atualizando pessoa");
        
        var entity = personRepository.findById(person.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setaddress(person.getaddress());

        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando pessoa");

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        personRepository.delete(entity);
    }
}
