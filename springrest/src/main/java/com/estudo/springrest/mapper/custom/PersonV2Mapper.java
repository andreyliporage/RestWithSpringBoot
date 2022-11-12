package com.estudo.springrest.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.estudo.springrest.data.VO.V2.PersonVOV2;
import com.estudo.springrest.model.Person;

@Service
public class PersonV2Mapper {
    
    public PersonVOV2 convertEntityToVo(Person person) {
        PersonVOV2 vo = new PersonVOV2();
        vo.setKey(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());
        vo.setaddress(person.getaddress());
        vo.setBirthday(new Date());
        return vo;
    }

    public Person convertVoToEntity(PersonVOV2 personVO) {
        Person entity = new Person();
        entity.setId(personVO.getKey());
        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setGender(personVO.getGender());
        entity.setaddress(personVO.getaddress());
        return entity;
    }
}
