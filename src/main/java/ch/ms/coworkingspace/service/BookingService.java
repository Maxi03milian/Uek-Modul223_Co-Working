package ch.ms.coworkingspace.service;

import ch.ms.coworkingspace.model.Booking;
import ch.ms.coworkingspace.model.Member;
import ch.ms.coworkingspace.repository.BookingRepository;
import ch.ms.coworkingspace.repository.MemberRepository;
import ch.ms.coworkingspace.security.JwtServiceHMAC;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingService {

    BookingRepository bookingRepository;
    MemberRepository memberRepository;
    JwtServiceHMAC jwtService;

    public BookingService(BookingRepository bookingRepository, MemberRepository memberRepository, JwtServiceHMAC jwtService) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }


    //getBookings
    public ResponseEntity getBookings() {
        return new ResponseEntity(bookingRepository.findAll(), HttpStatus.OK);
    }

    //getbooking by id
    public ResponseEntity getBookingById(UUID id) {
        return new ResponseEntity(bookingRepository.findById(id), HttpStatus.OK);
    }

    //getBooking by user
    public ResponseEntity getBookingByUser(UUID id) {
        boolean userExists = memberRepository.existsById(id);
        if(userExists){
            return new ResponseEntity(bookingRepository.findByCreatorId(id), HttpStatus.OK);
        }else{
            return new ResponseEntity("User with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }

    //getBooking by status
    public ResponseEntity getBookingByStatus(String status) {
        return new ResponseEntity(bookingRepository.findAllByStatus(status), HttpStatus.OK);
    }

    public ResponseEntity createBooking(Booking booking, String token) throws GeneralSecurityException, IOException {
        token = token.substring(7);
        DecodedJWT decode = jwtService.verifyJwt(token, true);
        String userId = decode.getClaim("user_id").asString();
        Member member = memberRepository.findById(UUID.fromString(userId)).get();
        booking.setStatus("PENDING");
        booking.setCreator(member);

        if(booking.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date, date in the past in not possible");
        }

        bookingRepository.save(booking);
        return new ResponseEntity(booking, HttpStatus.OK);
    }


    //updateBooking (Full booking update. Intended for admin emergency use)
    public ResponseEntity updateBooking(UUID id, Booking booking) {
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
            return new ResponseEntity("Booking with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }

    //updateBookingStatus
    public ResponseEntity updateBookingStatus(UUID id, Booking booking) {
        boolean bookingExists = bookingRepository.existsById(id);
        if(bookingExists){
            Booking bookingToUpdate = bookingRepository.findById(id).get();
            bookingToUpdate.setStatus(booking.getStatus());
            bookingRepository.save(bookingToUpdate);
            return new ResponseEntity(bookingToUpdate, HttpStatus.OK);
        }else{
            return new ResponseEntity("Booking with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }

    //deleteBooking by id
    public ResponseEntity deleteBooking(UUID id) {
        boolean bookingExists = bookingRepository.existsById(id);
        if(bookingExists){
            bookingRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity("Booking with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Booking> getBookingsByStatusAndUserId(String status, UUID userid) {
        boolean userExists = memberRepository.existsById(userid);
        if(userExists){
            return new ResponseEntity(bookingRepository.findAllByStatusAndCreatorId(status, userid), HttpStatus.OK);
        }else{
            return new ResponseEntity("User with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
