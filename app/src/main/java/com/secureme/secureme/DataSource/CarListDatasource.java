package com.secureme.secureme.DataSource;

import com.secureme.secureme.Entity.Cars;

import java.util.ArrayList;

/**
 * Created by mkumail on 8/5/16.
 */
public class CarListDatasource {

    // String url="https://raw.githubusercontent.com/mobilesiri/JSON-Parsing-in-Android/master/index.html";

    public ArrayList<Cars> getList() {
        //JsonParser jsonParser = new JsonParser();

        ArrayList<Cars> arrayListLiveData = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            Cars cars=new Cars();
            cars.setCarNAme("Honda 125"+i);
            cars.setCarPlateNumber("12S45GS"+i*34);
            arrayListLiveData.add(cars);
        }

            return arrayListLiveData;

    }
}
