package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings", description = "Booking management endpoints")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(
            summary = "Get all bookings",
            description = "Loads all bookings from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getAllBookings(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "userid", required = false) UUID userid){
        if(status != null && userid != null){
            if(bookingService.getBookingsByStatusAndUserId(status, userid) != null){
                return new ResponseEntity(bookingService.getBookingsByStatusAndUserId(status, userid), HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else if(status != null){
            if(bookingService.getBookingByStatus(status) != null){
                return new ResponseEntity(bookingService.getBookingByStatus(status), HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else if(userid != null){
            if(bookingService.getBookingByUser(userid) != null){
                return new ResponseEntity(bookingService.getBookingByUser(userid), HttpStatus.OK);
            }else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(bookingService.getBookings(), HttpStatus.OK);
        }
    }

    @Operation(
            summary = "Get all bookings by status",
            description = "Gets all bookings by status in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID id){
        if(bookingService.getBookingById(id) != null){
            return new ResponseEntity(bookingService.getBookingById(id), HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Create a new booking",
            description = "Creates a new booking in database",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking, @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        Booking bookingworks = bookingService.createBooking(booking, token);
        if(bookingworks != null){
            return new ResponseEntity(bookingworks, HttpStatus.CREATED);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Update an existing booking",
            description = "Update information from a specific booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID id, @RequestBody Booking booking){
        Booking bookingworks = bookingService.updateBooking(id, booking);
        if(bookingworks != null){
            return new ResponseEntity(bookingworks, HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Update the status from an existing booking",
            description = "Update information from a specific booking by ID. NOTE: The user can only update his own booking to 'CANCELLED'. Admin can do all actions",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable UUID id, @RequestBody Booking booking, @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        Booking bookingworks = bookingService.updateBookingStatus(id, booking, token);
        if(bookingworks != null){
            return new ResponseEntity(bookingworks, HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Delete an existing booking",
            description = "Delete a booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> deleteBooking(@PathVariable UUID id){
        if(bookingService.deleteBooking(id)){
            return new ResponseEntity(HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }




}
