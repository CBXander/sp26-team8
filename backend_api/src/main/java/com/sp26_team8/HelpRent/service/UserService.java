package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sp26_team8.HelpRent.repository.UserRepository;
import com.sp26_team8.HelpRent.entity.User;
import com.sp26_team8.HelpRent.entity.UserStatus;
import com.sp26_team8.HelpRent.entity.UserRole;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //create
    public User createUser(User user){
        return userRepository.save(user);
    }

    //update
    public User updateUser(Long id, User updatedUser){
        return userRepository.findById(id).map(user->{
            user.setEmail(updatedUser.getEmail());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setStatus(updatedUser.getStatus());
            user.setRole(updatedUser.getRole());

            return userRepository.save(user);
        }).orElse(null);
    }

    //read
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    //delete
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    

}
