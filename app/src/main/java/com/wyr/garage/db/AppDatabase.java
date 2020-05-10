package com.wyr.garage.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wyr.garage.AppApplication;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.Info;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;

@Database(entities = {LoggedInUser.class, Garage.class, Car.class, Order.class, ParkingSpace.class, Info.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "garage_app.db";

    private static AppDatabase mAppDatabase;

    public abstract UserDao userDao();

    public abstract GarageDao garageDao();

    public abstract CarDao carDao();

    public abstract OrderDao orderDao();

    public abstract ParkingSpaceDao parkingSpaceDao();


    public abstract InfoDao infoDao();

    public static AppDatabase getInstance() {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = Room.databaseBuilder(AppApplication.getContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mAppDatabase;
    }
}
