package com.steve.paymybuddy.service;

import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto) throws SQLException;
    List<Relation> listEmailRelation(String emailOwner);
    Relation addBuddy(String emailOwner, String emailBuddy) throws SQLException;
    Boolean deleteRelation(Integer id);
}
