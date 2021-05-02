package com.steve.paymybuddy.service.impl;

import com.steve.paymybuddy.dao.RelationDao;
import com.steve.paymybuddy.dao.RoleDao;
import com.steve.paymybuddy.dao.UserDao;
import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.model.Relation;
import com.steve.paymybuddy.model.Role;
import com.steve.paymybuddy.model.User;
import com.steve.paymybuddy.service.UserService;
import com.steve.paymybuddy.web.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RelationDao relationDao;
    private final RoleDao roleDao;
    static Logger logger = LogManager.getLogger(UserServiceImpl.class);
    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
    public UserServiceImpl(UserDao userDao, RelationDao relationDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.relationDao = relationDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<Relation> listEmailRelation(String emailOwner) {
        return relationDao.findAllByOwner_Email(emailOwner);
    }

    @Override
    public Relation addBuddy(String emailOwner, String emailBuddy) {
        Relation relation = new Relation();
        relation.setOwner(userDao.findByEmail(emailOwner));
        relation.setBuddy(userDao.findByEmail(emailBuddy));
        if (relation.getBuddy() == null){
            throw new DataNotFoundException("Cette personne n'existe pas");
        }
        for (Relation existingRelation : relation.getOwner().getRelations()) {
            if (existingRelation.getBuddy().equals(relation.getBuddy())){
                throw new DataAlreadyExistException("Cette personne est déjà votre ami(e)");
            }
        }
        relationDao.save(relation);
        return relation;
    }

    @Override
    public Boolean deleteRelation(Integer id) {
        if (relationDao.existsById(id)){
            relationDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(UserRegistrationDto userRegistrationDto) {
        Role role = roleDao.findRoleByName("USER");
        User user = new User(userRegistrationDto.getFirstname(), userRegistrationDto.getLastname(), userRegistrationDto.getEmail(),
                encoder.encode(userRegistrationDto.getPassword()), BigDecimal.ZERO, Timestamp.valueOf(LocalDateTime.now()), Arrays.asList(role));
        return userDao.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    // On map nos roles avec ceux de spring et on leur attribut l'autorité correspondantes
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
