package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insert(Order order);

    @Query("SELECT * FROM `Order` where user_id = :userId")
    List<Order> getOrderByUserId(int userId);

    @Delete
    int delete(Order order);

    @Query("SELECT * FROM `Order`")
    List<Order> getOrderList();

    @Query("DELETE FROM `Order`")
    int deleteAll();
}


