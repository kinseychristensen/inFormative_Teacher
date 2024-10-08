package com.techelevator.security.controller;

import com.techelevator.exception.DaoException;
import com.techelevator.security.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;
import com.techelevator.security.model.LoginDto;
import com.techelevator.security.model.LoginResponseDto;
import com.techelevator.security.model.RegisterUserDto;
import com.techelevator.security.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user;
        try {
            user = userDao.getUserByUsername(loginDto.getUsername());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password is incorrect.");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        try {
            if (userDao.getUserByUsername(newUser.getUsername()) != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
            } else {
            User user = userDao.createUser(newUser);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User registration failed.");
            }
        } }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed.");
        }
    }

    @RequestMapping(path= "/name", method=RequestMethod.GET)
    public String getName(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        return user.getFirstName() + " " + user.getLastName();

    }

    @RequestMapping(path= "/user", method=RequestMethod.GET)
    public User getUser(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        user.setPassword("");
        return user;
    }


    @RequestMapping(path= "/id/{userName}", method=RequestMethod.GET)
    public int getId(@PathVariable String userName){
        User user = userDao.getUserByUsername(userName);
        if(user != null){
            return user.getId();
        }
        return 0;
    }



    @RequestMapping(path="/update-password/{userId}", method=RequestMethod.PUT)
    public boolean updatePassword (@RequestBody RegisterUserDto user, @PathVariable int userId) {
        return userDao.updatePassword(user, userId);
    }
    @RequestMapping(path="/update-user/{userId}", method=RequestMethod.PUT)
    public boolean updateUser (@RequestBody RegisterUserDto user, @PathVariable int userId) {
     return userDao.updateUser(user, userId);
    }
}

