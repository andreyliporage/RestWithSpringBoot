package com.estudo.springrest.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import com.estudo.springrest.controllers.PersonController;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
    private final Logger logger = Logger.getLogger(PersonService.class.getName());
    
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonV2Mapper personV2Mapper;

    public PersonVO findById(Long id) {
        logger.info("Buscado pessoa");
        var person = new PersonVO();
        person.setKey(counter.getAndIncrement());
        person.setFirstName("Andrey");
        person.setLastName("Matos");
        person.setaddress("Rio de Janeiro - Rio de Janeiro");
        person.setGender("Masculino");
        
        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        var vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVO> findAll() {
        logger.info("Procurando pessoas");
        var vos =  DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
        vos.stream().forEach(p -> {
            p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
        });
        return vos;
    }

    public PersonVO create(PersonVO person) {
        logger.info("Criando pessoa");
        var entity = DozerMapper.parseObject(person, Person.class);
        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Criando pessoa v2");
        var entity = personV2Mapper.convertVoToEntity(person);
        var vo = personV2Mapper.convertEntityToVo(personRepository.save(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person) {
        logger.info("Atualizando pessoa");
        
        var entity = personRepository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setaddress(person.getaddress());

        var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando pessoa");

        var entity = personRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhuma pessoa encontrada"));
        
        personRepository.delete(entity);
    }
}
