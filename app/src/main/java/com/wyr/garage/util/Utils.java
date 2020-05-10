package com.wyr.garage.util;

import android.util.Log;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.Info;
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


            Info info = new Info();
            info.setInfoId(1);
            info.setTitle("油箱已满，行程已定，五月旅途情满爱卡");
            info.setDesc("选择在母亲节这天出发，可以说母亲节这次旅行是计划了很久的，工作之后，异地，又远嫁，有时很忙，有时和朋友在外聚餐，有时在外游玩，可是却总是忘记平常的时...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510184104_501708052_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);


            info = new Info();
            info.setInfoId(2);
            info.setTitle("我在荣威RX5论坛打卡之鹏程万里(2) 南头古城的点点滴滴");
            info.setDesc("一直计划走遍Xcar数百个分论坛的山山水水，顺便一睹各位版主风采。估计也完成了不少了吧，众多版主都热情互动，也为剩下的“行程”增加了不少动力。...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510160416_441197332_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(3);
            info.setTitle("携手卡友资源，文旅地产项目考察。");
            info.setDesc("爱卡重分携手重庆康养协会一行考察会员公司禾旭行代理的文旅地产项目，位于中国大陆最南端，三面环海，坐享无敌海景，总价30余万起，投资养老不二选择。第一...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95187561");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/2020051016_7a8bc5d835d4faa203f6149KuVtg2CY7.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);


            info = new Info();
            info.setInfoId(4);
            info.setTitle("【自驾达人秀】寻找户外乐呵");
            info.setDesc("选择在母亲节这天出发，可以说母亲节这次旅行是计划了很久的，工作之后，异地，又远嫁，有时很忙，有时和朋友在外聚餐，有时在外游玩，可是却总是忘记平常的时...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510184104_501708052_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(5);
            info.setTitle("【自驾达人秀】寻找户外乐呵");
            info.setDesc("今年5天假肯定能玩嗨，自驾游寻找户外乐！自驾第一天坐标呼和浩特市哈拉沁水库走起疫情过后的一个全家放松，自驾游还是能很好亲近大自然的机会，...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/2020051017_c8d0cce39f19a51939c8WZHi5nHyI3PR.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(6);
            info.setTitle("【自驾达人秀】寻找户外乐呵");
            info.setDesc("选择在母亲节这天出发，可以说母亲节这次旅行是计划了很久的，工作之后，异地，又远嫁，有时很忙，有时和朋友在外聚餐，有时在外游玩，可是却总是忘记平常的时...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510184104_501708052_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(7);
            info.setTitle("【自驾达人秀】寻找户外乐呵");
            info.setDesc("选择在母亲节这天出发，可以说母亲节这次旅行是计划了很久的，工作之后，异地，又远嫁，有时很忙，有时和朋友在外聚餐，有时在外游玩，可是却总是忘记平常的时...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510184104_501708052_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(8);
            info.setTitle("【自驾达人秀】寻找户外乐呵");
            info.setDesc("选择在母亲节这天出发，可以说母亲节这次旅行是计划了很久的，工作之后，异地，又远嫁，有时很忙，有时和朋友在外聚餐，有时在外游玩，可是却总是忘记平常的时...");
            info.setUrl("http://www.xcar.com.cn/bbs/viewthread.php?tid=95188001");
            info.setImageUrl("http://image.xcar.com.cn/attachments/a/day_200510/20200510184104_501708052_tour.jpg?&imageView2/1/w/362/h/240");
            AppDatabase.getInstance().infoDao().insertCar(info);

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
