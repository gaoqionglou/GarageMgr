package com.wyr.garage.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wyr.garage.data.model.LoggedInUser;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(LoggedInUser user);

    @Query("SELECT * FROM user WHERE display_name=:userName AND password=:password")
    LoggedInUser getUser(String userName, String password);

    @Query("SELECT * FROM user")
    List<LoggedInUser> getUsers();

    @Query("SELECT * FROM user WHERE user_id=:userId")
    LoggedInUser getUserById(int userId);

    @Update
    void updateUser(LoggedInUser user);

}
