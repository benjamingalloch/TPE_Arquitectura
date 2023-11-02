package org.example.microservice_scooter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PausePK implements Serializable {

    @Column(name = "user_id")
    private long userId;

    @Column(name = "trip_id")
    private long tripId;

    public PausePK() {
        super();
    }

    public PausePK(long userId, long tripId) {
        this.userId = userId;
        this.tripId = tripId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PausePK pausePK = (PausePK) o;

        if (userId != pausePK.getUserId()) return false;
        return tripId == pausePK.getTripId();
    }
}
