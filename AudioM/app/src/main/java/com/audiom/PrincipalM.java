package com.audiom;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalM extends ActionBarActivity {

    int setVolumen=6;
    int Volumenmax=10;
    int Volumenmin=0;
    SoundPool sonidos;
    int identificadorsonido;
    AudioManager manejador;
    boolean PlayAudio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_m);

        manejador=(AudioManager)getSystemService(AUDIO_SERVICE);

        final TextView ver=(TextView)findViewById(R.id.textView1);
        ver.setText(String.valueOf(setVolumen));

        Button subir=(Button)findViewById(R.id.button2);
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setVolumen < Volumenmax) {
                    setVolumen += 2;
                    ver.setText(String.valueOf(setVolumen));
                }


            }
        });

        Button bajar=(Button)findViewById(R.id.button1);
        bajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setVolumen > Volumenmin) {
                    setVolumen -= 2;
                    ver.setText(String.valueOf(setVolumen));
                }


            }
        });

        final Button Play=(Button)findViewById(R.id.button3);
        Play.setEnabled(false);

        sonidos=new SoundPool(1,AudioManager.STREAM_MUSIC,0);

        identificadorsonido=sonidos.load(this,R.raw.slow_whoop_bubble_pop,1);

        sonidos.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                if (status == 0) {

                    Play.setEnabled(true);

                }

            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayAudio){

                    sonidos.play(identificadorsonido,(float)setVolumen/Volumenmax,(float)setVolumen/Volumenmax,1,0,1.0f);
                }

            }
        });

        int result=manejador.requestAudioFocus(cambios, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);

        PlayAudio=AudioManager.AUDIOFOCUS_REQUEST_GRANTED==result;
    }

    public void onResume(){
        super.onResume();
        manejador.setSpeakerphoneOn(true);
        manejador.loadSoundEffects();
    }
    public void onPause(){
        super.onPause();


        if(sonidos!=null){

            sonidos.unload(identificadorsonido);
            sonidos.release();
            sonidos=null;

        }

        manejador.setSpeakerphoneOn(false);
        manejador.unloadSoundEffects();
    }

    AudioManager.OnAudioFocusChangeListener cambios= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(AudioManager.AUDIOFOCUS_LOSS==focusChange){
                manejador.abandonAudioFocus(cambios);
                PlayAudio=false;
            }


        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_m, menu);
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
}
