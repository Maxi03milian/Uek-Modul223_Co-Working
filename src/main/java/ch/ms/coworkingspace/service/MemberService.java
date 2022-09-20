package ch.ms.coworkingspace.service;

import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.repository.MemberRepository;
import ch.ms.coworkingspace.security.JwtServiceHMAC;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    MemberRepository memberRepository;
    JwtServiceHMAC jwtService;

    public MemberService(MemberRepository memberRepository, JwtServiceHMAC jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
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

    public ResponseEntity<Member> updateUserById(UUID id, Member member, String token) throws GeneralSecurityException, IOException {
        boolean userExists = memberRepository.existsById(id);
        token = token.substring(7);
        DecodedJWT decoded = jwtService.verifyJwt(token, true);
        String user_id = decoded.getClaim("user_id").asString();
        String[] scope = decoded.getClaim("scope").asArray(String.class);
        String email = decoded.getClaim("name").asString();
        String role = scope[0];

        Member memberSelf = memberRepository.findByEmail(email).get();
        Member updateMember = memberRepository.findById(id).get();
        if(!userExists){
            return new ResponseEntity("User with given ID not found", HttpStatus.BAD_REQUEST);
        }else if(memberSelf.getRole().equals("ADMIN") || updateMember.getId().equals(UUID.fromString(user_id))){
            Member memberToUpdate = memberRepository.findById(id).get();
            memberToUpdate.setName(member.getName());
            memberToUpdate.setLastname(member.getLastname());
            memberToUpdate.setPassword(member.getPassword());
            memberRepository.save(memberToUpdate);
            return new ResponseEntity(memberToUpdate, HttpStatus.OK);
        } else {
            return new ResponseEntity("You are not allowed to update this user", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Member> createUser(Member member) {
        Optional<Member> userOptional = memberRepository.findByEmail(member.getEmail());
        if(userOptional.isPresent()) {
            return new ResponseEntity("Email already used by different User", HttpStatus.CONFLICT);
        }else{
            member.setRole("MEMBER");
            member.setId(UUID.randomUUID());
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
