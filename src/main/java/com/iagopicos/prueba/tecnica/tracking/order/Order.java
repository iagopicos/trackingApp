package com.iagopicos.prueba.tecnica.tracking.order;

import com.iagopicos.prueba.tecnica.tracking.status.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Document(collection = "orders")
@NoArgsConstructor
public class Order {

    @Id
    private Long orderId;
    private Long trackId;
    private Status status;
    private Date changeStatusDate;
    private List<TrackHistory> trackHistory;

    public Order(Long orderId, Long trackId, Status status) {
        this.status = status;
        this.orderId = orderId;
        this.changeStatusDate = Date.from(Instant.now());
        this.trackId = trackId;
        this.trackHistory= Collections.singletonList(new TrackHistory(this.status,this.changeStatusDate));
    }

}
