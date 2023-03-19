package humble.slave.mvp_weather.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import humble.slave.mvp_weather.R;
import humble.slave.mvp_weather.common.RequestCompleteListener;
import humble.slave.mvp_weather.databinding.ActivityMainBinding;
import humble.slave.mvp_weather.model.WeatherInfoModel;
import humble.slave.mvp_weather.model.WeatherInfoModelImpl;
import humble.slave.mvp_weather.model.data.City;
import humble.slave.mvp_weather.model.data.WeatherInfoResponse;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    WeatherInfoModel model;
    List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new WeatherInfoModelImpl(getApplicationContext());
        model.getCityList(new RequestCompleteListener<List<City>>() {
            @Override
            public void onRequestSuccess(List<City> data) {
                binding.output.setText("");
                onFetchCitySuccess(data);
            }

            @Override
            public void onRequestFailed(String errorMessage) {
                binding.output.setText(errorMessage);
            }
        });



        binding.showWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWeatherInfoFetchSuccess();
            }
        });


    }




    public void onFetchCitySuccess(List<City> cityList){

        this.cityList = cityList;
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                convertToCityNameList(cityList)
        );
        binding.spinner.setAdapter(cityAdapter);
    }




    public void onWeatherInfoFetchSuccess(){

        binding.showWeather.setVisibility(View.GONE);
        model.getWeatherInformation(cityList.get(binding.spinner.getSelectedItemPosition()).id, new RequestCompleteListener<WeatherInfoResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRequestSuccess(WeatherInfoResponse data) {
                binding.output.setText(data.getName().toString());
            }

            @Override
            public void onRequestFailed(String errorMessage) {
                binding.output.setText(errorMessage);
            }
        });
    }



    private List<String> convertToCityNameList(List<City> city_list){
        List<String> cityNameList = new ArrayList<>();
        for(City i : city_list){
            cityNameList.add(i.name);
        }
        return cityNameList;

    }
}