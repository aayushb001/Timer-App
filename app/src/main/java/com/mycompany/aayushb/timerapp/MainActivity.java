package com.mycompany.aayushb.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    SeekBar timeBar;
    TextView timeText;
    Button startButton;
    Boolean timerInProgress = false; //false when timer is not running, else it's true
    CountDownTimer countDownTimer;

    public void startTimer(View view){

        if(timerInProgress == false){
            timerInProgress = true;
            startButton.setText("Stop"); //changing start button's text to "Stop"
        }
        else{
            timerInProgress = false;
        }

        if(timerInProgress == true) {
            countDownTimer = new CountDownTimer(timeBar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), "Timer Finished!",
                            Toast.LENGTH_LONG).show();
                    MediaPlayer finishSound = MediaPlayer.create(getApplicationContext(), R.raw.soundalarm);
                    finishSound.start();
                    resetTimer();
                }
            }.start(); //Don't forget to put .start()
        }
        else{
            resetTimer();
        }
    }

    public void updateTimer(int timeLeft){

        String startZero;
        int minutes = timeLeft /60;
        int seconds = timeLeft - (60* minutes);

        /* Making sure that the seconds position has another zero, so that there are
        double digits (as seconds is an int variable) and it looks nice.*/

        if(seconds < 10){
            startZero = "0";
        }
        else{
            startZero = "";
        }

        if(timerInProgress == true){
            timeBar.setEnabled(false); /*disabling time bar so that it cannot be interacted with
            while timer is in progress*/
        }

        timeText.setText(minutes + ":" + startZero + seconds);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeBar = (SeekBar) findViewById(R.id.setBar);
        timeText = (TextView) findViewById(R.id.timeText);
        startButton = (Button) findViewById(R.id.startButton);

        timeBar.setMax(1800); //set maximum amount of time on the bar
        timeBar.setProgress(0);
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public void resetTimer(){
        timeText.setText("0:00");
        timeBar.setProgress(0);
        startButton.setText("Start");
        countDownTimer.cancel();
        timeBar.setEnabled(true); //re-enabling time bar
    }
}
