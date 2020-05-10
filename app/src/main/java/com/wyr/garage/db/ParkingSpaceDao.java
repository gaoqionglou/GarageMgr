package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.data.model.ParkingSpaceStatusCount;

import java.util.List;

@Dao
public interface ParkingSpaceDao {

    @Query("SELECT * FROM parking_space WHERE parking_space_id = :parkingSpaceId")
    ParkingSpace getParkingSpaceById(int parkingSpaceId);

    @Query("SELECT * FROM parking_space WHERE garage_id = :garageId")
    List<ParkingSpace> getParkingSpaceListByGarageId(int garageId);

    @Query("SELECT garage_id as garageId, status, COUNT(*) as count FROM PARKING_SPACE WHERE garage_id = :garageId GROUP BY status")
    List<ParkingSpaceStatusCount> getParkingSpaceStatusCountByGarageId(int garageId);

    @Insert
    void insertPardingSpace(ParkingSpace parkingSpace);

    @Query("SELECT * FROM parking_space WHERE status = " + ParkingSpace.STATUS_REMAINING
            + " AND garage_id = :garageId")
    List<ParkingSpace> getRemainingParkingSpaces(int garageId);

    @Query("DELETE FROM parking_space")
    int deleteAll();

    @Update
    int updateParkingSpace(ParkingSpace space);

    @Query("SELECT * FROM parking_space")
    List<ParkingSpace> getParkingSpaces();
}
