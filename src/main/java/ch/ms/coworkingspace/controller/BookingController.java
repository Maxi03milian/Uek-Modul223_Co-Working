package ch.ms.coworkingspace.controller;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public ResponseEntity<Booking> getAllBookings(){
        return bookingService.getBookings();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Booking> getBookingByUser(@PathVariable Long id){
        return bookingService.getBookingByUser(id);
    }

    @GetMapping("/{status}")
    public ResponseEntity<Booking> getBookingByStatus(@PathVariable String status){
        return bookingService.getBookingByStatus(status);
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking){
        return bookingService.createBooking(booking);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking){
        return bookingService.updateBooking(id, booking);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestBody Booking booking){
        return bookingService.updateBookingStatus(id, booking);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long id){
        return bookingService.deleteBooking(id);
    }




}
