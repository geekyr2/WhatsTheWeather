package com.example.pc.whatstheweather;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText e;
    private TextView textView;
    private String d;
    public static String BASEURL = "https://api.openweathermap.org/";
    public static String APPID = "PUT YOUR APP ID FROM openweathermap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.t2);
        e = findViewById(R.id.e1);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }

    public void buttonClicked(View view) {
        textView.setText("");
        d = e.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi service = retrofit.create(WeatherApi.class);
        Call<IncomingResult> call = service.getCurrentWeatherData(d, APPID);
        call.enqueue(new Callback<IncomingResult>() {
            @Override
            public void onResponse(@NonNull Call<IncomingResult> call, @NonNull Response<IncomingResult> response) {
                if (response.code() == 200) {
                    IncomingResult incomingResult = response.body();
                    String s = "";
                    double t = incomingResult.main.temp - 273.15;
                    int temp = (int) t;
                    double speed = incomingResult.wind.speed * 3.6;
                    int windSpeed = (int) speed;
                    s += "Temperature: " + temp + " C \n";
                    s += "Wind Speed: " + windSpeed + "Km/h \n";
                    s += "Weather: " + incomingResult.weather.get(0).main;
                    textView.append(s);
                }
            }

            @Override
            public void onFailure(@NonNull Call<IncomingResult> call, @NonNull Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}