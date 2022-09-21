package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
    public ResponseEntity<Booking> getAllBookings(@RequestParam(value = "status", required = false) String status, @RequestParam(value = "userid", required = false) UUID userid){
        if(status != null && userid != null){
            return bookingService.getBookingsByStatusAndUserId(status, userid);
        } else if(status != null){
            return bookingService.getBookingByStatus(status);
        } else if(userid != null){
            return bookingService.getBookingByUser(userid);
        } else {
            return bookingService.getBookings();
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
        return bookingService.getBookingById(id);
    }

    @Operation(
            summary = "Create a new booking",
            description = "Creates a new booking in database",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking, @RequestBody Member member, @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        return bookingService.createBooking(booking, token);
    }

    @Operation(
            summary = "Update an existing booking",
            description = "Update information from a specific booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID id, @RequestBody Booking booking){
        return bookingService.updateBooking(id, booking);
    }

    @Operation(
            summary = "Update the status from an existing booking",
            description = "Update information from a specific booking by ID. NOTE: The user can only update his own booking to 'CANCELLED'. Admin can do all actions",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/status/{id}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable UUID id, @RequestBody Booking booking, @RequestHeader("Authorization") String token) throws GeneralSecurityException, IOException {
        return bookingService.updateBookingStatus(id, booking, token);
    }

    @Operation(
            summary = "Delete an existing booking",
            description = "Delete a booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> deleteBooking(@PathVariable UUID id){
        return bookingService.deleteBooking(id);
    }




}
