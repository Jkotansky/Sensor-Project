package com.example.joshuak.sensorproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SensorSelector extends AppCompatActivity {
    SensorManager sm;
    List<Sensor> ls;
    Sensor accel, light;
    TextView accelText, lightText;
    Button accelBtn, lightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        ls = sm.getSensorList(Sensor.TYPE_ALL);
        accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelText = (TextView) findViewById(R.id.statusAccel);
        lightText = (TextView) findViewById(R.id.statusLight);
        accelBtn = (Button) findViewById(R.id.btnAccel);
        lightBtn = (Button) findViewById(R.id.btnProx);
        lightText.setTextSize(12);
        accelText.setTextSize(12);

        if(light != null){
            lightText.setText("Status: Light is present \r\n" +
                    " Info: " +"\r\n"+
                    " Range: " +light.getMaximumRange() +"\r\n" +
                    " Resolution: " +light.getResolution() + "\r\n" +
                    " Delay: " + light.getMinDelay());

        }else{
            lightText.setText("Status: Light is  not present \r\n");

        }

        if(accel != null){
            accelText.setText("Status: Accelerometer is present \r\n" +
                    " Info: " +"\r\n"+
                    " Range: "+accel.getMaximumRange()+"\r\n" +
                    " Resolution: "+ accel.getResolution() +"\r\n" +
                    " Delay: " + accel.getMinDelay());

        }else{
            accelText.setText("Status: Accelerometer is not present \r\n");

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v){


       switch(v.getId()){
           case R.id.btnAccel:
               Intent i = new Intent(SensorSelector.this, AccelPlot.class);
               startActivity(i);
               break;
           case R.id.btnProx:
               Intent j = new Intent(SensorSelector.this, LightPlot.class);
               startActivity(j);
               break;
       }

    }

}
