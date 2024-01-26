package net.lecnam.uhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.lecnam.uhelp.utils.ActivityUtilities;

public class DemandeActivity extends Activity {

    private RelativeLayout fond;
    private ImageView retour;
    private TextView demandeTexte;
    private Sensor accel;
    private Bundle b; //Bundle de chargement de données


    private PopupWindow popUp;
    boolean click = true;

    //Barre de menu
    private ImageView home;
    private ImageView carte;

    //Variables d'état de l'utilisateur qui consulte la demande
    /*
    reponse|demandeur :
          0|1     = demandeur
          1|0     = repondeur
          1|1     = erreur
          0|0     = visionneur
    */
    private boolean repondeur;
    private boolean demandeur;

    //Pour détection de la secousse d'acceptation de la tâche
    private SensorManager sensorManager;
    private float accelVal, accelLast, shake;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande);

        //Récupération des éléments graphiques du layout actuel
        fond = (RelativeLayout) findViewById(R.id.fond);
        retour = (ImageView) findViewById(R.id.retourDemande);
        demandeTexte = (TextView) findViewById(R.id.demande);
        home = (ImageView) findViewById(R.id.home);
        carte = (ImageView) findViewById(R.id.carte);

        ActivityUtilities.createPopup();
        popUp = new PopupWindow(this);
        popUp.setContentView(findViewById(R.id.popup_fen));

        //Définition du comportement des boutons
        home.setOnClickListener(v -> ActivityUtilities.openActivity(this, AccueilActivity.class));
        retour.setOnClickListener(v -> ActivityUtilities.openActivity(this, AccueilActivity.class));
        carte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    popUp.showAtLocation(fond, Gravity.BOTTOM, 10, 10);
                    popUp.update(50, 50, 300, 80);
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }
        });

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

    /*
    Listener de détection de la secousse du téléphone.
    Cette secousse provoquera l'acceptation de la demande affichée à l'écran
     */
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            accelLast=accelVal;
            accelVal=(float) Math.sqrt((double) (x*x)+(y*y)+(z*z));
            float delta= accelVal-accelLast;
            shake =shake*0.9f+delta;

            if(shake>12){
                counter++;
            }

            if(counter>=2)  //On détecte la secousse
            {
                //TODO : Popup de confirmation d'acceptation
                PopupWindow accept = new PopupWindow();
                counter = 0;
                Log.d("sensor", "omg ça secoue");
                Toast.makeText(getApplicationContext(), "Demande acceptée", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
