package humble.slave.mvp_weather.common;

public interface RequestCompleteListener<T>{
    public void onRequestSuccess(T data);
    public void onRequestFailed(String errorMessage);
}
