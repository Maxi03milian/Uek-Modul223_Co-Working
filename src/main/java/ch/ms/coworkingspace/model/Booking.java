package ch.ms.coworkingspace.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "BOOKING")
public class Booking {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private Member creator;
    @Column(name = "day_duration", nullable = false)
    private float dayDuration;
    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();
    @Column(name = "status", nullable = false)
    private String status;


    public Booking() {
    }

    public Booking(Member creator, float dayDuration, LocalDate date, String status) {
        this.creator = creator;
        this.dayDuration = dayDuration;
        this.date = date;
        this.status = status;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Member getCreator() {
        return creator;
    }

    public void setCreator(Member creator) {
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
