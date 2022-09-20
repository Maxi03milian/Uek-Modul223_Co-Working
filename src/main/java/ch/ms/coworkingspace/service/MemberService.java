package ch.ms.coworkingspace.service;

import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<Member>  getAllUsers() {
        List<Member> memberList = (List<Member>) memberRepository.findAll();
        return new ResponseEntity(memberList, HttpStatus.OK);
    }

    public ResponseEntity<Member> getUserById(UUID id) {
        boolean userExists = memberRepository.existsById(id);
        if(userExists){
            Member member = memberRepository.findById(id).get();
            return new ResponseEntity(member, HttpStatus.OK);
        }else{
            return new ResponseEntity("User with given ID not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Member> updateUserById(UUID id, Member member) {
        boolean userExists = memberRepository.existsById(id);
        if(userExists){
            Member memberToUpdate = memberRepository.findById(id).get();
            memberToUpdate.setName(member.getName());
            memberToUpdate.setLastname(member.getLastname());
            memberToUpdate.setEmail(member.getEmail());
            memberToUpdate.setPassword(member.getPassword());
            memberToUpdate.setRole(member.getRole());
            memberRepository.save(memberToUpdate);
            return new ResponseEntity(memberToUpdate, HttpStatus.OK);
        }else{
            return new ResponseEntity("User with given ID not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Member> createUser(Member member) {
        Optional<Member> userOptional = memberRepository.findByEmail(member.getEmail());
        if(userOptional.isPresent()) {
            return new ResponseEntity("Email already used by different User", HttpStatus.CONFLICT);
        }else{
            memberRepository.save(member);
            return new ResponseEntity(member, HttpStatus.OK);
        }
    }

    public ResponseEntity<Member> deleteUserById(UUID id) {
        boolean userExists = memberRepository.existsById(id);
        if(userExists){
            memberRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity("User with given ID does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Member> getByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
