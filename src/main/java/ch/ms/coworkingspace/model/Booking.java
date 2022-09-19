package ch.ms.coworkingspace.model;

import org.hibernate.type.LocalDateType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "BOOKING")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private User creator;
    @Column(name = "day_duration", nullable = false)
    private float dayDuration;
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();
    @Column(name = "status", nullable = false)
    private String status;
}
