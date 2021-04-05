package com.iagopicos.prueba.tecnica.tracking.order;

import com.iagopicos.prueba.tecnica.tracking.status.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class TrackHistory {
    Status status;
    Date changeStatusDate;

    public TrackHistory(Status status, Date changeStatusDate) {
        this.status = status;
        this.changeStatusDate = changeStatusDate;
    }
}
