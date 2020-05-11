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
        List<Info> infos = AppDatabase.getInstance().infoDao().getInfoList();
        Log.i(TAG, "----------------------------------cars------------------------------------------");
        Log.i(TAG, cars.toString());
        Log.i(TAG, "----------------------------------garages------------------------------------------");
        Log.i(TAG, garages.toString());
        Log.i(TAG, "----------------------------------orders------------------------------------------");
        Log.i(TAG, orders.toString());
        Log.i(TAG, "----------------------------------parkingSpaces------------------------------------------");
        Log.i(TAG, parkingSpaces.toString());
        Log.i(TAG, "----------------------------------infos------------------------------------------");
//        Log.i(TAG, infos.toString());
    }

    public static void fakeData() {
        try {
            AppDatabase.getInstance().garageDao().deleteAll();
            AppDatabase.getInstance().parkingSpaceDao().deleteAll();
            AppDatabase.getInstance().carDao().deleteAll();
            AppDatabase.getInstance().infoDao().deleteAll();
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
            info.setTitle("最美人间四月天，有福青年春游临沂雕塑公园");
            info.setDesc("我说你是人间的四月天；笑响点亮了四面风；轻灵，在春的光自艳中交舞着变。你是四月早天里的云烟，黄昏吹着风的软，星子在无意中闪，细雨点洒在花前。那轻，那娉婷你是百，鲜妍百花的冠冕你戴着...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-21032727.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2005/07/c137/206929299_1588844045494_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(5);
            info.setTitle("【汽车故事会】为爱车进行十三保！");
            info.setDesc("我这次要介绍的是爱车2019年底时进行的第十三次保养，这次保养还是有些故事的，主要就是因为此次保养所用的机油是参加太平洋汽车网活动得到的，用着得奖获得的机油保养心里感觉还是非常美滋滋的...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-21064844.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2005/08/c104/207082163_1588925609865_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(6);
            info.setTitle("【2020新车总动员】到店鉴赏吉利星越");
            info.setDesc("前一阵看到了长安的CS85这款车，感觉这种COUPE风格的车还是有自己的一种独特感觉的，后来查资料发现吉利也推出了一款COUPE风格的车，命名为星越，于是我造访了吉利的4S店，也看一下这款新出的轿跑车型...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-21069722.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2005/08/c131/207106649_1588933961704_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(7);
            info.setTitle("好开省油性价比高，90后创业小伙的哈弗H6提车作业");
            info.setDesc(" 宅在家里的日子突发奇想发一篇提车作业；分享一下我的哈弗H6！我在上学期间，就一直有国产情怀，对国产车抱有一种好感，觉得各方面尤其是性价比和配置上都特别厚道，比起合资车优点尤为显著。这促使我将哈弗H6选作我人生的第一辆车，让它伴我继续打拼我接下来的江山！...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-20805980.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2003/11/c3/196493170_1583889697194_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(8);
            info.setTitle("改色分享——如何拥有一台黑色飞度？");
            info.setDesc("出险的钱到账，这个时候根据自身的购买能力和消费水平，购置适量的外观件，比如包围件，前后杠等等，到货后马上送到漆厂，打包加工。＃划重点，改色施工的这段时间不能操之过急，万万不能不断打电话追问进度，因为一旦你这样做，那么喷漆的师傅会用实际行动教育你什么叫耐性...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-18230930.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/1901/31/c54/130656987_1548918169797_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);


            info = new Info();
            info.setInfoId(9);
            info.setTitle("朗逸手动挡节油我有妙招");
            info.setDesc("我的爱车是上汽大众2013改款朗逸1.6L手动舒适版。自2015年12月购入，爱车已陪伴我4年多时间。那时流行白色，也是跟风选择了雅致白这款颜色。四年多来小白陪伴我走过风风雨雨，风雨兼程，一路有你...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-20972205.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2004/10/c41/202252017_1586489055891_700x700.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(10);
            info.setTitle("【致最美的她】#最美女神#长腿小姐姐驾萌车游岭南");
            info.setDesc("大家好，我是军林天下！爱车，爱摄影，爱生活，爱旅行，当然也爱小姐姐。我相信，这是我们大家的共同爱好，对不对。如果不对，那至少你爱小姐姐，对吧。...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-20818850.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/2003/16/c194/197551480_1584344915267_240x160.jpg");
            AppDatabase.getInstance().infoDao().insertCar(info);

            info = new Info();
            info.setInfoId(11);
            info.setTitle("【女神驾到】假期采风周边游，跟美女们一起玩学习");
            info.setDesc("广州简称 羊城 花城 是一座国际大都市  周边有许多休闲旅游的好去处 人口众多 一到假日 热闹非凡 奈何楼主不怎么喜欢人头涌动的地方 况且 对于去过云南西藏新疆 这些看了大山大河 对热闹小景没什么兴趣了 不过看景也要看是如何看 跟谁一起去看！ 约上三五朋友美女 周末休闲一下  那也是不错的选择 一个人去那是万万不可的...");
            info.setUrl("https://bbs.pcauto.com.cn/topic-20530557.html");
            info.setImageUrl("https://img.pcauto.com.cn/images/upload/upc/tx/bbs6/1911/29/c31/182106571_1574995930109_240x160.jpg");
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
