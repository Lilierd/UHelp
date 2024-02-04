package net.lecnam.uhelp;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

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
            //TODO: appel à la méthode d'insertion dans la BDD (mettre un bool de retour qui valide l'insertion dans la BDD)
            boolean src = false; // = méthode bdd
            if(src)
                Toast.makeText(this, "Demande créée", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Erreur : veuillez réessayer", Toast.LENGTH_LONG).show();
        });
    }
}
