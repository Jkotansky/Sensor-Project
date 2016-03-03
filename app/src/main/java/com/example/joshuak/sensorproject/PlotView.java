package com.example.joshuak.sensorproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuak on 2/20/2016.
 */
public class PlotView extends View{
    ArrayList<Float> inputFloats = new ArrayList<Float>(11);
    ArrayList<Float> points = new ArrayList<Float>(11);
    ArrayList<Float> mean = new ArrayList<Float>(11);
    ArrayList<Float> std = new ArrayList<Float>(11);
    float maxValue = 23.5f, lastMean;
    Integer time = 0, value;
    boolean light = false;


    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);


        for(int i = 0; i < 7; i++) {
            float width = (getWidth() -80) / 6;
            float height = (getHeight() - 80) / 6;
            if(light == false){
            value = Math.round(maxValue)/6 * i;}
            else{
                value = Math.round(maxValue)/6 * i - 6;
            }


            //this is the horizontal lines
            c.drawLine(80, height * i, getWidth(), height * i, new Paint(Color.WHITE));
            //this is the vertical lines
            c.drawLine(80 + i * width, 0, 80 + i * width, getHeight()-80, new Paint(Color.WHITE));
            //this is the y values
            c.drawText(Integer.toString(value + 6), 19, (6-i) * height + 20 , new Paint(Color.BLACK));
            //this the time values
            c.drawText(Integer.toString(time + i), i * width + 49,getHeight()- 20,new Paint(Color.BLACK));
        }

//        if(inputFloats.size() > 0){
//            addPoints(inputFloats.get(0));
//        }
        calculateMean(inputFloats);
        calculateSTD(inputFloats);

        Paint white = new Paint(Color.WHITE);
        Paint green = new Paint(Color.GREEN);
        Paint yellow = new Paint(Color.YELLOW);

        for(int i = 0; i < points.size() - 1; i++){
            float width = (getWidth() -80) / 6;
            c.drawCircle(80 + i * width,getHeight() - 80 - inputFloats.get(i) *(getHeight() -80) / (maxValue + 10), 10, white);
            c.drawCircle(80 + i * width,getHeight() - 80 - mean.get(i) *(getHeight() -80) / (maxValue + 10), 10, green);
            c.drawCircle(80 + i * width,getHeight() - 80 - std.get(i) *(getHeight() -80) / (maxValue + 10), 10, yellow);
        }

        drawLines(c, inputFloats);
        drawLines(c, mean);
        drawLines(c, std );


        time++;


//


    }

    /*
    this method provides lines between points
     */
    private void drawLines(Canvas c, List<Float> af) {
        int width = (getWidth() - 80)/ 6;
        int offset = 80;
        int height = getHeight() - offset;


        Paint p = new Paint();
        p.setStrokeWidth(9);
        if(af.equals(inputFloats)){
            p.setColor(Color.WHITE);
        }else if(af.equals(mean)){
            p.setColor(Color.GREEN);
        }else{
            p.setColor(Color.YELLOW);
        }

        for(int i = 0; i < af.size()-1; i++ ){
            float startX = i * width + offset;
            float startY = height - af.get(i)*(height / (maxValue + 10));
            float endX = (i +1) * width  + offset;
            float endY = height - af.get(i+1)*(height / (maxValue + 10));

            c.drawLine(startX,startY,endX,endY,p);
        }

    }

    private void calculateSTD(ArrayList<Float> points) {
        for(float i: inputFloats){
            stdDev(i);
        }
    }

    private void calculateMean(ArrayList<Float> points) {
        for(float i: inputFloats){
            mean(i);
        }
    }

    public float max(ArrayList<Float> af){
        float max = 0;
        for(float i : af){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    public void addPoint(float point, boolean lite){
        Float f = point;
        Log.v("Value -------->",f.toString() );
        if(inputFloats.size() > 9){
            inputFloats.remove(0);
        }
        light = lite;
        inputFloats.add(point);
        maxValue = max(inputFloats);

    }
//    public void addPoints(float point){
//        if(points.size() > 9){
//            points.remove(0);
//        }
//        points.add(point);
//
//        maxValue = max(inputFloats);
//    }


    public void mean(Float f){
        if(points.size() != 0) {
            int index = inputFloats.indexOf(f);
            float sum = 0;
            int count = 0;
            for (int i = 0; i < index; i++) {
                sum +=  inputFloats.get(i);
                count++;
            }
            sum = sum / count;
            mean.add(sum);
            lastMean = sum;

        }else{
            mean.add(f);
            lastMean = f;
        }

        if(mean.size() > 9){
            mean.remove(0);
        }


    }

    public void stdDev(Float f){
        if(inputFloats.size() == 1 || inputFloats.size() ==2){
            std.add(0f);
        }else{
        int index = inputFloats.indexOf(f);
        float temp = 0;
        int count = 0;
         for(int i = 0; i < index; i++){
             temp += (lastMean - inputFloats.get(i)) * (lastMean - inputFloats.get(i));
             count++;
         }
        temp = temp / count;
        std.add(temp);
        }

        if(std.size() > 9){
            std.remove(0);
        }

    }


}
