package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.sp26_team8.HelpRent.repository.UserRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

//------------------------------------- POST METHODS -------------------------------------//
   

    //create
    public User createUser(User user){
        if (userRepository.findByEmailIgnoreCase(user.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this email already exists.");
        }
        return userRepository.save(user);
    }

//------------------------------------- PUT METHODS -------------------------------------//
    //update
    public User updateUser(Long id, User updatedUser){
        return userRepository.findById(id).map(user->{
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setStatus(updatedUser.getStatus());
            user.setRole(updatedUser.getRole());

            return userRepository.save(user);
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        
    }

//------------------------------------- GET METHODS -------------------------------------//
    //read
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //delete
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        userRepository.deleteById(id);
    }

//------------------------------------- DELETE METHODS -------------------------------------//
    
    //USER ROLE VALIDATION
    public User validateUserRole(Long userId, UserRole... allowedRoles){
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    for(UserRole role : allowedRoles){
        if(user.getRole() == role) return user;
    }
    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have the required role.");
}

    

}
