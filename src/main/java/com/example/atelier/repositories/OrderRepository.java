package com.example.atelier.repositories;

import com.example.atelier.entities.Order;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM order_material WHERE status=true", nativeQuery = true)
    List<Order> findAllByStatusTrue();

    @Query(value = "SELECT * FROM order_material WHERE worker_id = :id", nativeQuery = true)
    List<Order> findAllOrdersDoneByWorker(@PathParam("id") Long id);

    @Query(value = "SELECT * FROM order_material WHERE user_id = :id and status=true", nativeQuery = true)
    List<Order> findAllDoneOrders(@PathParam("id") Long id);
}
