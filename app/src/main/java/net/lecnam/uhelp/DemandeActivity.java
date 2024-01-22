package net.lecnam.uhelp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DemandeActivity extends Activity {

    private ImageView retour;
    private TextView demandeTexte;
    private Bundle b;
    private ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demande);

        //Récupération des éléments graphiques du layout actuel
        retour = (ImageView) findViewById(R.id.retourDemande);
        demandeTexte = (TextView) findViewById(R.id.demande);
        home = (ImageView) findViewById(R.id.home);

        //Actualisation des données en fonction de la demande sélectionnée
        b = getIntent().getExtras();
        String str = "";
        if(b != null)
            str = b.getString("demande");
        demandeTexte.setText(str);


        home.setOnClickListener(v -> OpenAccueil());
        retour.setOnClickListener(v -> OpenAccueil());
    }

    private void OpenAccueil() {
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
        finish();
    }
}
