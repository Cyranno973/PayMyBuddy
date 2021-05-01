package com.steve.paymybuddy.web.controller;

import com.steve.paymybuddy.dto.UserDto;
import com.steve.paymybuddy.dto.UserRegistrationDto;
import com.steve.paymybuddy.dto.UserSaveDto;
import com.steve.paymybuddy.model.User;
import com.steve.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/inscription")
public class RegistrationController {

   final private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    // Pour le log4J
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }
    @GetMapping
    public String showRegistrationForm() {
        return "/fragments/formInscription";
    }
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto){
        userService.save(userRegistrationDto);
        return "redirect:/fragments/formInscription?success";
    }


//    @GetMapping("/home")
//    public String welcome(Model model){
//        model.addAttribute("users", users());
//        return "fragments/home";
//    }
//    @GetMapping("/connect/formConnexion")
//    public String signIn(Model model){
//        return "fragments/formConnexion";
//    }
//
//    @GetMapping("/connect/formInscription")
//    public String signUp(Model model){
//        return "fragments/formInscription";
//    }
//    @PostMapping(value = "/userAdd")
//    public ResponseEntity<User> createUser(@RequestBody @Valid UserSaveDto userAdd) throws Exception {
//        logger.info("CreateUser : appel du controller");
//        userService.createUser(userAdd);
//        return new ResponseEntity(userAdd, HttpStatus.CREATED);
//    }
//
//    @PostMapping(value = "/addBuddy/{id}")
//    public ResponseEntity<User> addBuddy(@RequestBody UserSaveDto addBuddy, @PathVariable Integer idOwner) throws Exception {
//        logger.info("CreateUser : appel du controller");
//        userService.addBuddy(addBuddy,idOwner);
//        return new ResponseEntity(addBuddy, HttpStatus.CREATED);
//    }
//
//    @PutMapping(value = "/userUpdate")
//    public ResponseEntity<User> updateUser(@RequestBody @Valid UserSaveDto updateUser) throws Exception {
//        logger.info("UpdateUser : appel du controller");
//        userService.updateUser(updateUser);
//        return new ResponseEntity(updateUser, HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping(value = "/UserDelete")
//    public ResponseEntity<User> deleteUser(@RequestBody @Valid UserSaveDto deleteUser) throws Exception {
//        logger.info("deleteUser : appel du controller");
//        userService.deleteUser(deleteUser);
//        return new ResponseEntity(deleteUser, HttpStatus.RESET_CONTENT);
//    }
//
//    @GetMapping(value = "/users")
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserDto> users() {
//        return userService.findAll();
//    }
//
//    @GetMapping(value = "/UsersCount")
//    @ResponseStatus(HttpStatus.OK)
//    public long countUsers() {
//        return userService.countUsers();
//    }
//
//    @GetMapping(value = "/Users/{email}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDto user(@PathVariable String email) {
//        return userService.userByEmail(email);
//    }
//
//
////    @PostMapping(value = "/connect")
////    public ResponseEntity<Boolean> connectUser(@RequestBody UserSaveDto connectUser) throws Exception {
////        logger.info("connectUser : appel du controller");
////        userService.connectUser(connectUser);
////        return new ResponseEntity(connectUser, HttpStatus.CREATED);
////    }
//    @RequestMapping(value = "/connect", method = RequestMethod.POST)
//    public String doRegistration(@ModelAttribute("user") User user){
//            return "home";
//    }

}