package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * permet de sauvegarder
     * @param userRegistrationDto
     * @return
     */
    User save(UserRegistrationDto userRegistrationDto);

    List<Relation> listEmailRelation(String emailOwner);
    Relation addBuddy(String emailOwner, String emailBuddy);
    Boolean deleteRelation(Integer id);

}
