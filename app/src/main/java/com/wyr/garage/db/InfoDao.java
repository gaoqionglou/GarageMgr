package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.wyr.garage.data.model.Info;

import java.util.List;

@Dao
public interface InfoDao {

    @Insert
    void insertCar(Info info);

    @Query("SELECT * FROM info")
    List<Info> getInfoList();


    @Query("DELETE FROM info")
    int deleteAll();
}
