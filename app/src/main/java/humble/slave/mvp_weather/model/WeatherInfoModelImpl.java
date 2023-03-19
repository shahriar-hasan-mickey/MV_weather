package humble.slave.mvp_weather.model;

import static android.provider.UserDictionary.Words.APP_ID;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

import humble.slave.mvp_weather.common.RequestCompleteListener;
import humble.slave.mvp_weather.model.data.City;
import humble.slave.mvp_weather.model.data.WeatherInfoResponse;
import humble.slave.mvp_weather.network.API;
import humble.slave.mvp_weather.network.RetrofitClient;
import humble.slave.mvp_weather.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherInfoModelImpl implements WeatherInfoModel{

    Context context;

    public WeatherInfoModelImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getCityList(RequestCompleteListener<List<City>> callback) {

        try {
            InputStream is = context.getAssets().open("city_list.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonFileString = new String(buffer, StandardCharsets.UTF_8);

            Log.i("data", jsonFileString);

            Gson gson = new Gson();
            Type listUserType = new TypeToken<List<City>>() {
            }.getType();

            List<City> cityList = gson.fromJson(jsonFileString, listUserType);
            for (int i = 0; i < cityList.size(); i++) {
                Log.i("data", "> Item " + i + "\n" + cityList.get(i));

            }

            callback.onRequestSuccess(cityList);
        }catch (IOException e) {
            e.printStackTrace();
            callback.onRequestFailed(e.getLocalizedMessage());
        }
    }



    @Override
    public void getWeatherInformation(int cityId, RequestCompleteListener<WeatherInfoResponse> callback) {
        API api = new RetrofitClient().client().create(API.class);
        Log.i("API", "=>>>>>>after generating api object");

//        TODO : DEMO APP_ID
        String APP_ID = "b6907d289e10d714a6e88b30761fae22";

        Call<WeatherInfoResponse> call = api.apiWeatherInfoResponse(cityId, APP_ID);
        Log.i("CALL", "=>>>>>After generating call object");


        /****************************************************************************************************
        TODO : THE API CALL IS NEVER GOING TO WORK HOW HARD YOUR TRY IF PERMISSION IS NOT STATED FOR INTERNET
        ****************************************************************************************************/


        call.enqueue(new Callback<WeatherInfoResponse>() {
            @Override
            public void onResponse(Call<WeatherInfoResponse> call, Response<WeatherInfoResponse> response) {
                if(response.body() != null){
                    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                    callback.onRequestSuccess(response.body());
                }else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    callback.onRequestFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherInfoResponse> call, Throwable t) {
                Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
                callback.onRequestFailed(t.getLocalizedMessage());
            }
        });
    }

}
