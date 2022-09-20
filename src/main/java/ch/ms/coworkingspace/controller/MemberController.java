package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.security.JwtServiceHMAC;
import ch.ms.coworkingspace.service.MemberService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class MemberController {

    MemberService memberService;

    JwtServiceHMAC jwtService;

    public MemberController(MemberService memberService, JwtServiceHMAC jwtService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Get all users",
            description = "Loads all users from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Member> getAllUsers(){
        return memberService.getAllUsers();
    }

    @Operation(
            summary = "Get one specific user",
            description = "Loads one specific user by ID from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Member> getUserById(@PathVariable UUID id){
        return memberService.getUserById(id);
    }

    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Member> createUser(@RequestBody Member member){
        return memberService.createUser(member);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update information from a specific user by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateUserById(@PathVariable UUID id, @RequestBody Member member, @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        return memberService.updateUserById(id, member, token);
    }

    @Operation(
            summary = "Delete an existing user",
            description = "Delete a user by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Member> deleteUserById(@PathVariable UUID id){
        return memberService.deleteUserById(id);
    }





}
