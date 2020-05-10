package com.wyr.garage.util;

import android.util.Log;
import android.widget.DatePicker;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class Utils {

    private static String TAG = "DATA";

    public static void showDB() {
        List<Car> cars = AppDatabase.getInstance().carDao().getCarList();
        List<Garage> garages = AppDatabase.getInstance().garageDao().getGarages();
        List<Order> orders = AppDatabase.getInstance().orderDao().getOrderList();
        List<ParkingSpace> parkingSpaces = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaces();
        Log.i(TAG, "----------------------------------cars------------------------------------------");
        Log.i(TAG, cars.toString());
        Log.i(TAG, "----------------------------------garages------------------------------------------");
        Log.i(TAG, garages.toString());
        Log.i(TAG, "----------------------------------orders------------------------------------------");
        Log.i(TAG, orders.toString());
        Log.i(TAG, "----------------------------------parkingSpaces------------------------------------------");
        Log.i(TAG, parkingSpaces.toString());
    }

    public static void fakeData() {
        try {
            AppDatabase.getInstance().garageDao().deleteAll();
            AppDatabase.getInstance().parkingSpaceDao().deleteAll();
            AppDatabase.getInstance().carDao().deleteAll();
//            AppDatabase.getInstance().orderDao().deleteAll();
            Garage garage = new Garage();
            garage.setGarageId(1);
            garage.setName("OCG");
            garage.setAddress("成都市武侯区高新区天府大道中段966号");
            garage.setLongitude(104.065392);
            garage.setLatitude(30.544541);
            garage.setPrice(8);
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            insertParkingSpace(1, 11, 0, "A1111");
            insertParkingSpace(1, 12, 0, "A2222");
            insertParkingSpace(1, 13, 1, "A3333");
            insertParkingSpace(1, 14, 2, "A4444");
            insertParkingSpace(1, 15, 2, "A55555");
            insertParkingSpace(1, 16, 2, "A66666");

            insertParkingSpace(2, 21, 0, "B1111");
            insertParkingSpace(2, 22, 0, "B2222");
            insertParkingSpace(2, 23, 1, "B3333");
            insertParkingSpace(2, 24, 2, "B4444");
            insertParkingSpace(2, 25, 2, "B55555");
            insertParkingSpace(2, 26, 2, "B66666");

            garage = new Garage();
            garage.setGarageId(2);
            garage.setName("九龙广场");
            garage.setAddress("成都市锦江区青年路8号");
            garage.setLongitude(104.072988);
            garage.setLatitude(30.657322);
            garage.setPrice(10);
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            garage = new Garage();
            garage.setGarageId(3);
            garage.setName("新城市广场");
            garage.setAddress("成都市青羊区西大街1号");
            garage.setLongitude(104.057625);
            garage.setLatitude(30.674007);
            garage.setPrice(7);
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            garage = new Garage();
            garage.setGarageId(4);
            garage.setName("成都339");
            garage.setAddress("成都市成华区猛追湾街94号");
            garage.setLongitude(104.094747);
            garage.setLatitude(30.66201);
            garage.setPrice(15);
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            garage = new Garage();
            garage.setGarageId(5);
            garage.setName("春熙路72号停车场");
            garage.setAddress("春熙路与小道路交汇处100米");
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            garage = new Garage();
            garage.setGarageId(6);
            garage.setName("春熙路73号停车场");
            garage.setAddress("春熙路与小道路交汇处100米");
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            garage = new Garage();
            garage.setGarageId(17);
            garage.setName("春熙路74号停车场");
            garage.setAddress("春熙路与小道路交汇处100米");
            AppDatabase.getInstance().garageDao().insertGarage(garage);

            Car car = new Car();
            car.setCarId(1);
            car.setCarNumber("粤A9797");
            car.setCarType("BMW");
            AppDatabase.getInstance().carDao().insertCar(car);

            car = new Car();
            car.setCarId(2);
            car.setCarNumber("粤A9898");
            car.setCarType("BMW");
            AppDatabase.getInstance().carDao().insertCar(car);


            car = new Car();
            car.setCarId(3);
            car.setCarNumber("粤A7848");
            car.setCarType("BMW");
            AppDatabase.getInstance().carDao().insertCar(car);


            car = new Car();
            car.setCarId(4);
            car.setCarNumber("粤A6848");
            car.setCarType("BMW");
            AppDatabase.getInstance().carDao().insertCar(car);


            car = new Car();
            car.setCarId(5);
            car.setCarNumber("粤A2848");
            car.setCarType("BMW");
            AppDatabase.getInstance().carDao().insertCar(car);

            showDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertParkingSpace(int garageId, int parkingSpaceId, int status, String number) {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setGarageId(garageId);
        parkingSpace.setParkingSpaceId(parkingSpaceId);
        parkingSpace.setStatus(status);
        parkingSpace.setParkingSpaceNumber(number);
        AppDatabase.getInstance().parkingSpaceDao().insertPardingSpace(parkingSpace);
    }
}
