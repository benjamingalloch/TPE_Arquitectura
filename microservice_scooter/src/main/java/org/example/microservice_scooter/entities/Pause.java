package org.example.microservice_scooter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

@Entity
@Data
@Getter
@Table(name = "pause")
public class Pause {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private PausePK id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "time")
    private int time;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "trip_id")
    private long tripId;

    @ManyToOne
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;

    public Pause() {
        super();
    }
    public Pause(Scooter scooter, long userId, long tripId) {
        super();
        this.startTime = Timestamp.from(Instant.now());
        this.endTime = null;
        this.time = 0;
        this.scooter = scooter;
        this.id = new PausePK(userId, tripId);
    }

    public void endPause() {
        Timestamp now = Timestamp.from(Instant.now());
        Duration duration = Duration.between(this.startTime.toInstant(), now.toInstant());
        long minutes = duration.toMinutes();

        if (minutes > 15) {
            this.endTime = Timestamp.from(this.startTime.toInstant().plusSeconds(15 * 60)); // Agrega 15 minutos al startTime
            this.time = 15; // Establece el tiempo en 15 minutos
        } else {
            this.endTime = now;
            this.time = (int) minutes;
        }
    }

}
