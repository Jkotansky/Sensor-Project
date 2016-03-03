package com.example.joshuak.sensorproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.util.List;

public class AccelPlot extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor s;
    List<Sensor> l;
    PlotView pv;
    private long lastPrint = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_plot);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this, s, 100000000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, 100000000);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
       if(event.timestamp - lastPrint >= 1e9 ){

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float first = ((x*x) + (y*y) + (z*z));
        float f = (float) Math.sqrt(first);


        pv = (PlotView) findViewById(R.id.graphAccel);
        pv.addPoint(f, false);
        pv.invalidate();
           lastPrint = event.timestamp;

       }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.btnBack:
                Intent i = new Intent(this, SensorSelector.class);
                startActivity(i);
                break;
        }
    }

}
