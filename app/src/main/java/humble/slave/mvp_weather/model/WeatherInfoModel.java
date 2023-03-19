package humble.slave.mvp_weather.model;

import java.util.List;

import humble.slave.mvp_weather.common.RequestCompleteListener;
import humble.slave.mvp_weather.model.data.City;
import humble.slave.mvp_weather.model.data.WeatherInfoResponse;

public interface WeatherInfoModel {
    public void getCityList(RequestCompleteListener<List<City>> callback);
    public void getWeatherInformation(int cityId , RequestCompleteListener<WeatherInfoResponse> callback);
}
