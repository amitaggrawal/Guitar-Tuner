package com.aggrawal.amit.beatbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {

    ImageView a,b,d,e,g,E;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = (ImageView)findViewById(R.id.a_image);
        b = (ImageView)findViewById(R.id.b_image);
        d = (ImageView)findViewById(R.id.d_image);
        e = (ImageView)findViewById(R.id.e_image);
        g = (ImageView)findViewById(R.id.g_image);
        E = (ImageView)findViewById(R.id.E_image);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            check_permission();
        }
        else
        {
            start();
        }

    }

    private MediaRecorder mRecorder = null;

    private void check_permission() {

        // check if permission is not granted
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},102);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch(requestCode)
        {

            case 102:
            {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    start();

                } else {

                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
                return;
            }

            default: super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
         }


    }


    private void start()
    {
        Toast.makeText(MainActivity.this,"listening .. ",Toast.LENGTH_SHORT).show();
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);


        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                final float pitchInHz = pitchDetectionResult.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(pitchInHz >80 && pitchInHz < 85)
                        {

                            e.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }

                        else if(pitchInHz > 85 && pitchInHz < 112)
                        {

                            a.setVisibility(View.VISIBLE);
                            e.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }
                        else if (pitchInHz > 112 && pitchInHz < 150)
                        {

                            d.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            e.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }
                        else if(pitchInHz > 150 && pitchInHz < 200)
                        {

                            g.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            e.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }
                        else if (pitchInHz > 200 && pitchInHz < 250)
                        {

                            b.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            e.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }

                        else if (pitchInHz > 250 && pitchInHz < 335)
                        {

                            E.setVisibility(View.VISIBLE);
                            a.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            e.setVisibility(View.GONE);

                        }
                        else
                        {

                            e.setVisibility(View.GONE);
                            a.setVisibility(View.GONE);
                            d.setVisibility(View.GONE);
                            g.setVisibility(View.GONE);
                            b.setVisibility(View.GONE);
                            E.setVisibility(View.GONE);

                        }

                    }
                });

            }
        }));
        new Thread(dispatcher,"Audio Dispatcher").start();
    }
}
