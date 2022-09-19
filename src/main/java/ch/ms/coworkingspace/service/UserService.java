package ch.ms.coworkingspace.service;

import ch.ms.coworkingspace.model.User;
import ch.ms.coworkingspace.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User>  getAllUsers() {
        List<User> userList = (List<User>) userRepository.findAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    public ResponseEntity<User> getUserById(Long id) {
        boolean userExists = userRepository.existsById(id);
        if(userExists){
            User user = userRepository.findById(id).get();
            return new ResponseEntity(user, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> updateUserById(Long id, User user) {
        boolean userExists = userRepository.existsById(id);
        if(userExists){
            User userToUpdate = userRepository.findById(id).get();
            userToUpdate.setName(user.getName());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setRole(user.getRole());
            userRepository.save(userToUpdate);
            return new ResponseEntity(userToUpdate, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> createUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }else{
            userRepository.save(user);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }

    public ResponseEntity<User> deleteUserById(Long id) {
        boolean userExists = userRepository.existsById(id);
        if(userExists){
            userRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
