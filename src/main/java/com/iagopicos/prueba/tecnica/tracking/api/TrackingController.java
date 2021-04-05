package com.iagopicos.prueba.tecnica.tracking.api;

import com.iagopicos.prueba.tecnica.tracking.order.Order;
import com.iagopicos.prueba.tecnica.tracking.order.OrderTracking;
import com.iagopicos.prueba.tecnica.tracking.order.TrackHistory;
import com.iagopicos.prueba.tecnica.tracking.repository.OrderMongoRepository;
import com.iagopicos.prueba.tecnica.tracking.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import util.exception.IllegalStatusException;
import util.exception.OrderNotFoundException;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RestController
public class TrackingController {

    private OrderMongoRepository repository;
    private static final Logger log = LoggerFactory.getLogger(TrackingController.class);

    public TrackingController(OrderMongoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/order/tracking")
    public List<Order> tracking(@RequestBody OrderTracking orderTracking) {

        orderTracking.getOrderTracking().forEach(order -> {
            try {
                updateStatus(order.getTrackId(), order.getStatus());
            } catch (IllegalStatusException ex) {
                log.error("Illegal status change requests dor order {}", order);
            } catch (OrderNotFoundException ex) {
                createNewOrder(order); //Si la order que viene en el fichero no esta la inserto aqui para empezar el tracking
            }

        });

        return repository.findAll();

    }

    private void updateStatus(Long trackingId, Status newStatus) throws IllegalStatusException, OrderNotFoundException {

        Optional<Order> order = repository.findByTrackId(trackingId);
        Order updatedOrder;

        if (!order.isPresent()) {
            throw new OrderNotFoundException("The order its not on the system");
        } else {

            if (newStatus.equals(Status.RECOGIDO_EN_ALMACEN)) {
                throw new IllegalStatusException("The status update it's not valid");
            }
            if (order.get().getStatus().equals(Status.ENTREGADO)) {
                throw new IllegalStatusException("The order has been already delivered");
            }

            updatedOrder = order.get();

            List<TrackHistory> trackHistories = updatedOrder.getTrackHistory();
            if(null==trackHistories){
                trackHistories = new ArrayList<>();
            }
            trackHistories.add(new TrackHistory(newStatus, Date.from(Instant.now())));
            updatedOrder.setStatus(newStatus);
            updatedOrder.setTrackHistory(trackHistories);
            updatedOrder.setChangeStatusDate(Date.from(Instant.now()));


            repository.save(updatedOrder);
        }
    }

    private void createNewOrder(Order order) {
        Order newOrder = new Order(order.getOrderId(),order.getTrackId(),order.getStatus());
        repository.save(newOrder);
    }

}


