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


    public Booking() {
    }

    public Booking(User creator, float dayDuration, LocalDate date, String status) {
        this.creator = creator;
        this.dayDuration = dayDuration;
        this.date = date;
        this.status = status;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public float getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(float dayDuration) {
        this.dayDuration = dayDuration;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
