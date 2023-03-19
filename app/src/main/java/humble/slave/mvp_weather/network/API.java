package humble.slave.mvp_weather.network;

import humble.slave.mvp_weather.model.data.WeatherInfoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("weather")
//    Call<WeatherInfoResponse> weatherInfoResponse(@Query("q") String cityName, @Query("appid") String APP_ID);
    Call<WeatherInfoResponse> apiWeatherInfoResponse(@Query("id") int cityId, @Query("appid") String APP_ID);
}
