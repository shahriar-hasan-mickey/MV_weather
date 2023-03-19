package humble.slave.mvp_weather.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    @Expose
    public int id = 0;
    @Expose
    @SerializedName("name")
    public String name = "";
    @Expose
    @SerializedName("country")
    public String country = "";
}
