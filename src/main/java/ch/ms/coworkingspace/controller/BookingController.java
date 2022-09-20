package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/booking")
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
    @GetMapping("/all")
    public ResponseEntity<Booking> getAllBookings(){
        return bookingService.getBookings();
    }

    @Operation(
            summary = "Get one specific booking by user ID",
            description = "Loads one specific booking by ID from the creater from the database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/user/{id}")
    public ResponseEntity<Booking> getBookingByUser(@PathVariable Long id){
        return bookingService.getBookingByUser(id);
    }

    @Operation(
            summary = "Get all bookings by status",
            description = "Gets all bookings by status in database.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @GetMapping("/{status}")
    public ResponseEntity<Booking> getBookingByStatus(@PathVariable String status){
        return bookingService.getBookingByStatus(status);
    }

    @Operation(
            summary = "Create a new booking",
            description = "Creates a new booking in database",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        return bookingService.createBooking(booking);
    }

    @Operation(
            summary = "Update an existing booking",
            description = "Update information from a specific booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking){
        return bookingService.updateBooking(id, booking);
    }

    @Operation(
            summary = "Update the status from an existing booking",
            description = "Update information from a specific booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @PutMapping("/update/status/{id}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestBody Booking booking){
        return bookingService.updateBookingStatus(id, booking);
    }

    @Operation(
            summary = "Delete an existing booking",
            description = "Delete a booking by ID.",
            security = {@SecurityRequirement(name = "JWT Auth")}
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long id){
        return bookingService.deleteBooking(id);
    }




}
