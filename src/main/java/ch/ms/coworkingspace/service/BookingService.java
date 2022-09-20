package ch.ms.coworkingspace.service;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.repository.BookingRepository;
import ch.ms.coworkingspace.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    BookingRepository bookingRepository;
    UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }


    //getBookings
    public ResponseEntity getBookings() {
        return new ResponseEntity(bookingRepository.findAll(), HttpStatus.OK);
    }

    //getBooking by user
    public ResponseEntity getBookingByUser(Long id) {
        boolean userExists = userRepository.existsById(id);
        if(userExists){
            return new ResponseEntity(bookingRepository.findByCreator(id), HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //getBooking by status
    public ResponseEntity getBookingByStatus(String status) {
        return new ResponseEntity(bookingRepository.findAllByStatus(status), HttpStatus.OK);
    }

    //createBooking (user auth)
    //TODO: add user to creator field
    public ResponseEntity createBooking(Booking booking) {
        bookingRepository.save(booking);
        return new ResponseEntity(booking, HttpStatus.OK);
    }

    //updateBooking (Full booking update. Intended for admin emergency use)
    public ResponseEntity updateBooking(Long id, Booking booking) {
        boolean bookingExists = bookingRepository.existsById(id);
        if(bookingExists){
            Booking bookingToUpdate = bookingRepository.findById(id).get();
            bookingToUpdate.setCreator(booking.getCreator());
            bookingToUpdate.setDayDuration(booking.getDayDuration());
            bookingToUpdate.setDate(booking.getDate());
            bookingToUpdate.setStatus(booking.getStatus());
            bookingRepository.save(bookingToUpdate);
            return new ResponseEntity(bookingToUpdate, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //updateBookingStatus
    public ResponseEntity updateBookingStatus(Long id, Booking booking) {
        boolean bookingExists = bookingRepository.existsById(id);
        if(bookingExists){
            Booking bookingToUpdate = bookingRepository.findById(id).get();
            bookingToUpdate.setStatus(booking.getStatus());
            bookingRepository.save(bookingToUpdate);
            return new ResponseEntity(bookingToUpdate, HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //deleteBooking by id
    public ResponseEntity deleteBooking(Long id) {
        boolean bookingExists = bookingRepository.existsById(id);
        if(bookingExists){
            bookingRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }



}