package net.lecnam.uhelp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import net.lecnam.uhelp.utils.ActivityUtilities;

public class DemandeActivity extends Activity {

    private ImageView retour;
    private TextView demandeTexte;
    private Sensor accel;
    private Bundle b;
    private ImageView home;

    //Pour détection de la secousse
    private SensorManager sensorManager;
    private float accelVal, accelLast, shake;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande);

        //Récupération des éléments graphiques du layout actuel
        retour = (ImageView) findViewById(R.id.retourDemande);
        demandeTexte = (TextView) findViewById(R.id.demande);
        home = (ImageView) findViewById(R.id.home);

        //Définition du comportement des boutons
        home.setOnClickListener(v -> ActivityUtilities.openActivity(this, AccueilActivity.class));
        retour.setOnClickListener(v -> ActivityUtilities.openActivity(this, AccueilActivity.class));

        //Gestion des capteurs
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorListener, accel, SensorManager.SENSOR_DELAY_NORMAL);
        accelVal = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
        shake=0.0f;
        counter = 0;

        //Actualisation des données en fonction de la demande sélectionnée
        b = getIntent().getExtras();
        String str = "";
        if(b != null)
            str = b.getString("demande");
        demandeTexte.setText(str);

    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x =event.values[0];
            float y =event.values[1];
            float z =event.values[2];
            accelLast=accelVal;
            accelVal=(float) Math.sqrt((double) (x*x)+(y*y)+(z*z));
            float delta= accelVal-accelLast;
            shake =shake*0.9f+delta;

            if(shake>12){
                counter++;
            }

            if(counter>=1)
            {
                counter = 0;
                Log.d("sensor", "omg ça secoue");
                Toast.makeText(getApplicationContext(), "mg ça secoue", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
