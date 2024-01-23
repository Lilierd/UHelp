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
    private SensorManager sensorManager;
    private Sensor accel;
    private Bundle b;
    private ImageView home;

    //Pour détection de la secousse
    private static final int SHAKE_THRESHOLD = 800;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;

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
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener accelerometerEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                long curTime = System.currentTimeMillis();

                if((curTime-event.timestamp) > 100)
                {
                    long diffTime = curTime - lastUpdate;
                    lastUpdate = curTime;

                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                    if(speed > SHAKE_THRESHOLD)
                        Log.d("sensor", "shake detected");

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println(sensor.getPower());
            }
        };
        sensorManager.registerListener(accelerometerEventListener, accel, SensorManager.SENSOR_DELAY_NORMAL);

        //Actualisation des données en fonction de la demande sélectionnée
        b = getIntent().getExtras();
        String str = "";
        if(b != null)
            str = b.getString("demande");
        demandeTexte.setText(str);

    }
}
