package com.example.justin.pedometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Environment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justin.pedometer_test.R;

import java.io.*;
import java.text.*;
import java.util.*;



public class MainActivity extends Activity implements SensorEventListener {


    private SensorManager mSensorManager;
    Sensor mStepCounterSensor;
    Sensor mStepDetectorSensor;
    private TextView steps;
    boolean activityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        steps = (TextView) findViewById(R.id.steps);

        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


    }
    @Override
    protected void onResume(){
        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }


    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps.setText("Step Counter Detected : " + value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            steps.setText("Step Detector Detected : " + value);
        }
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

}
