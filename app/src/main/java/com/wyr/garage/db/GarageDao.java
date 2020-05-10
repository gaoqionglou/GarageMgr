package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wyr.garage.data.model.Garage;

import java.util.List;

@Dao
public interface GarageDao {

    @Query("SELECT * FROM garage")
    List<Garage> getGarages();

    @Insert
    void insertGarage(Garage garage);

    @Query("SELECT * FROM garage WHERE garage_id = :garageId")
    Garage getGarageById(int garageId);

    @Query("DELETE FROM garage")
    int deleteAll();
}
