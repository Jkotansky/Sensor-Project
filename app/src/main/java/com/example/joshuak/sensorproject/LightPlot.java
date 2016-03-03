package com.example.joshuak.sensorproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class LightPlot extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor s;
    List<Sensor> l;
    PlotView pv;
    long lastPrint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_plot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        l = sm.getSensorList(Sensor.TYPE_ALL);
        s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

  ;      sm.registerListener(this, s, 100000000);

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

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnBack2:
                Intent i = new Intent(this, SensorSelector.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.timestamp - lastPrint >= 1e9 ) {
            float f = event.values[0];

            pv = (PlotView) findViewById(R.id.graphLight);
            pv.addPoint(f, true);
            pv.invalidate();
            lastPrint = event.timestamp;

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
