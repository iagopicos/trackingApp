package com.iagopicos.prueba.tecnica.tracking.repository;

import com.iagopicos.prueba.tecnica.tracking.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order,String> {
     Optional<Order> findByTrackId(long trackId);
     Optional<Order> findFirstByOrderByOrderIdDesc();

}
