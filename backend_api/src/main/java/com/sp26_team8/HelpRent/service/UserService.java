package com.sp26_team8.HelpRent.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sp26_team8.HelpRent.repository.UserRepository;
import com.sp26_team8.HelpRent.entity.*;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//------------------------------------- POST METHODS -------------------------------------//
   

    //create
    public User createUser(User user){
        if (userRepository.findByEmailIgnoreCase(user.getEmail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user with this email already exists.");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
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

    public User updateUserStatus(Long id, UserStatus status){
        User user = userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        user.setStatus(status);
        userRepository.save(user);
        return user;
    }
//------------------------------------- GET METHODS -------------------------------------//
    //read
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public User getUserByEmail(String email){
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found");
        }

        return user;
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

//------------------------------------- User Details for Spring Security  -------------------------------------//
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Login attempt for: " + email);
        
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {

            System.out.println("User not found: " + email);

            throw new UsernameNotFoundException("User not found");
        }
        
        System.out.println("User found: " + user.getEmail() + " | Role: " + user.getRole());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();
    }

}
