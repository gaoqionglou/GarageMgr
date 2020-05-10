package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wyr.garage.data.model.Car;

import java.io.CharArrayReader;
import java.util.List;

@Dao
public interface CarDao {

    @Insert
    void insertCar(Car car);

    @Query("SELECT * FROM car")
    List<Car> getCarList();

    @Delete
    int deleteCar(Car car);

    @Query("SELECT count(*) FROM car")
    int getCarCount();


    @Query("SELECT * FROM car WHERE car_id = :carId")
    Car getCarById(int carId);

    @Query("DELETE FROM car")
    int deleteAll();
}
