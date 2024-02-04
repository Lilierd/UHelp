package net.lecnam.uhelp;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import net.lecnam.uhelp.queries.Utilisateurs;
import net.lecnam.uhelp.utils.ActivityUtilities;
import net.lecnam.uhelp.utils.MenuBar;

public class NewDemandeActivity extends Activity {

    private ImageView retour;
    private MenuBar mb;

    private Button valider;
    private TextInputEditText nomDemande;
    private TextInputEditText typeDemande;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.new_demande);

        //Récupération de tous les ID
        retour = (ImageView) findViewById(R.id.retour);
        nomDemande = (TextInputEditText) findViewById(R.id.nomDemande);
        typeDemande = (TextInputEditText) findViewById(R.id.typeDemande);
        valider = (Button) findViewById(R.id.validDemande);

        //Mise en place du fonctionnement back de la vue
        mb = new MenuBar(this);
        retour.setOnClickListener(v -> { ActivityUtilities.openActivity(this, AccueilActivity.class); });

        valider.setOnClickListener(v-> {

            Utilisateurs.getBiggestDemandKey(new Utilisateurs.CallBack() {
                int key = 0;
                @Override
                public void onCallback(int value) {
                    key = value +1;
                    Utilisateurs.insertDemand(key, Utilisateurs.userID, nomDemande.getText().toString(), typeDemande.getText().toString());
                }
            });
            //TODO (Last)
            boolean src = true; // = méthode bdd
            if(src) {
                Toast.makeText(this, "Demande créée", Toast.LENGTH_LONG).show();
                Bundle b = new Bundle();
                ActivityUtilities.openActivity(this, AccueilActivity.class, b);
            }
            else
                Toast.makeText(this, "Erreur : veuillez réessayer", Toast.LENGTH_LONG).show();
        });
    }
}
