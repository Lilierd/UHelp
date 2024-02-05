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
import android.text.Layout;
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
import net.lecnam.uhelp.utils.MenuBar;
import net.lecnam.uhelp.utils.Popup;
import net.lecnam.uhelp.utils.PopupButtons;

public class DemandeActivity extends Activity {

    private RelativeLayout fond;
    private ImageView retour;
    private TextView nomDemande;
    private Sensor accel;
    private Bundle b; //Bundle de chargement de données


    private Popup popUp;
    private boolean click = true;

    private MenuBar menuBar; //Barre de menu

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
        retour = (ImageView) findViewById(R.id.retour);
        nomDemande = (TextView) findViewById(R.id.nomDemande);

        //Init de la barre de menu
        menuBar = new MenuBar(this);
        retour.setOnClickListener(v -> { ActivityUtilities.openActivity(this, AccueilActivity.class); });

        //Popup d'acceptation de la demande
        popUp = Popup.createPopup(this, "Accepter la demande ?", "yolo", PopupButtons.OKCancel);

        //Gestion des capteurs
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorListener, accel, SensorManager.SENSOR_DELAY_NORMAL);
        accelVal = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
        shake=0.0f;
        counter = 0;

        //Actualisation des données en fonction de la demande sélectionnée
        //TODO: Remplacer par la fonction qui va récup les données dans la BDD
        b = getIntent().getExtras();
        String str = "";
        if(b != null)
            str = b.getString("demande");
        nomDemande.setText(str);

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

            if(shake>12)
                counter++;

            if(counter>=1)  //On détecte la secousse
            {
                askAccept();
                counter = 0;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void askAccept()
    {
        if(popUp == null)
            return;
        popUp.showAtLocation(fond, Gravity.CENTER, 0, 0);
        popUp.update(0, 0, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(popUp.getOK())
                {
                    //TODO: Requête BDD pour acceptation de la demande
                    Log.d("sensor", "omg ça secoue");
                    Toast.makeText(getApplicationContext(), "Demande acceptée", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
