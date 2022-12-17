package org.firstinspires.ftc.teamcode.customUtil;


import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorDetector {
    private ColorSensor colorSensor;
    private String color;


    public ColorDetector (ColorSensor colorSensor){
        this.colorSensor = colorSensor;
    }

    public String getColor(){
        senseColor();
        return color;
    }

    private void senseColor(){
        int red = this.colorSensor.red();
        int green = this.colorSensor.green();
        int blue = this.colorSensor.blue();

        if (green > red && green > blue){
            color = "green";
        }
        else if (blue > red && blue > green){
            color = "blue";
        }
        else{
            color = "red";
        }


    }
}
