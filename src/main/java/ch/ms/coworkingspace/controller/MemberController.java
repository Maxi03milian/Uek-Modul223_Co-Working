package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateUserById(@PathVariable UUID id, @RequestBody Member member){
        return memberService.updateUserById(id, member);
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
