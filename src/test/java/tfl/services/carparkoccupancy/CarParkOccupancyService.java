package tfl.services.carparkoccupancy;

import com.frameworkium.core.api.services.BaseService;
import com.frameworkium.core.api.services.ServiceFactory;
import ru.yandex.qatools.allure.annotations.Step;
import tfl.entities.Bay;
import tfl.entities.CarParkOccupancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robertgates55 on 25/04/2016.
 */
public class CarParkOccupancyService extends BaseService<CarParkOccupancyService> {

    public static CarParkOccupancyService newInstance(){
        return ServiceFactory.newInstance(CarParkOccupancyService.class, "https://api.tfl.gov.uk/Occupancy/CarPark");
    }

    public static CarParkOccupancyService newInstance(String id){
        return ServiceFactory.newInstance(CarParkOccupancyService.class, String.format("https://api.tfl.gov.uk/Occupancy/CarPark/%s",id));
    }

    @Step
    public List<CarParkOccupancy> getCarParkOccupancies(){
        try {
            return Arrays.asList(this.response.body().as(CarParkOccupancy[].class));
        } catch (Exception e){
            return Arrays.asList(this.response.body().as(CarParkOccupancy.class));
        }
    }

    @Step
    public List<String> getCarParkNames(){
        List<String> carParkNames = new ArrayList<>();
        for(CarParkOccupancy cpo : getCarParkOccupancies()){
            carParkNames.add(cpo.name);
        }
        return carParkNames;
    }

    @Step
    public int sumFreeSpaces(List<CarParkOccupancy> carParkOccupancies){
        int totalFree = 0;
        for(CarParkOccupancy cpo : getCarParkOccupancies()){
            for(Bay bay : cpo.bays) {
                totalFree += bay.free;
            }
        }
        return totalFree;
    }
}
