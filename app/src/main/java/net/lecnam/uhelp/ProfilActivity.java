package net.lecnam.uhelp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import net.lecnam.uhelp.queries.Utilisateurs;
import net.lecnam.uhelp.utils.ActivityUtilities;
import net.lecnam.uhelp.utils.MenuBar;

public class ProfilActivity extends Activity {

    private MenuBar menuBar; //Barre de menu

    private ImageView retour;
    private TextView pseudo;
    private TextInputEditText nom;
    private TextInputEditText prenom;

    private Button validBouton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.profil);

        retour = (ImageView) findViewById(R.id.retourProfil);
        pseudo = (TextView) findViewById(R.id.pseudo);
        nom = (TextInputEditText) findViewById(R.id.nom);
        prenom = (TextInputEditText) findViewById(R.id.prenom);
        validBouton = (Button) findViewById(R.id.validProfil);

        menuBar = new MenuBar(this);
        retour.setOnClickListener(v -> {
            ActivityUtilities.openActivity(this, AccueilActivity.class);
        });

        validBouton.setOnClickListener(v -> {
            //Vérification du remplissage du formulaire
            if(nom.getText().toString() == "" || prenom.getText().toString() == "")
                return;

            Utilisateurs.getUserKey(Utilisateurs.Pseudo, new Utilisateurs.CallBack() {
                @Override
                public void onCallback(int value) {
                    Utilisateurs.EditUser(value,nom.getText().toString(), prenom.getText().toString());
                }
            });
        });

        pseudo.setText(Utilisateurs.Pseudo);
        nom.setText(Utilisateurs.Nom);
        prenom.setText(Utilisateurs.Prenom);
    }
}
