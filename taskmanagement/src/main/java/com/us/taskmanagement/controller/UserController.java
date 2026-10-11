package com.us.taskmanagement.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

/*    POST   /api/users
    GET    /api/users
    GET    /api/users/{id}
    PUT    /api/users/{id}
    DELETE /api/users/{id}*/

   /* private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserRequest request){
        return new ResponseEntity<>(service.addUser(request), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id,@RequestBody User user){
        return new ResponseEntity<>(service.updateUserById(id,user), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        return new ResponseEntity<>(service.deleteUserById(id), HttpStatus.OK);
    }*/
}
