package net.lecnam.uhelp;

import static net.lecnam.uhelp.queries.Utilisateurs.addUtilisateurs;
import static net.lecnam.uhelp.queries.Utilisateurs.getBiggestUserKey;
import static net.lecnam.uhelp.queries.Utilisateurs.readUser;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import net.lecnam.uhelp.databinding.ActivityMainBinding;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import net.lecnam.uhelp.utils.ActivityUtilities;
import net.lecnam.uhelp.queries.*;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Button confirm;
    private TextInputEditText pseudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        confirm = (Button)findViewById(R.id.confirm);
        pseudo = (TextInputEditText) findViewById(R.id.pseudo);

        confirm.setOnClickListener(v -> openAccueil(pseudo.getText().toString()));

    }

    public void openAccueil(String pseudo){
        if(!pseudo.isEmpty())
        {

            confirm.setBackgroundColor((255) << 24 | (10) << 16 | (175) << 8 | (10));
            Bundle b = new Bundle();
            b.putString("pseudo", pseudo);
            ActivityUtilities.openActivity(this, AccueilActivity.class, b);
            readUser(pseudo, new Utilisateurs.CallBack() {
                @Override
                public void onCallback(int value) {
                    if(value == 0){
                        getBiggestUserKey(new Utilisateurs.CallBack() {
                            @Override
                            public void onCallback(int nextKey) {
                                addUtilisateurs(nextKey+1,pseudo);
                            }
                        });
                    }
                }
            });
        }
        else
        {
            confirm.setBackgroundColor((255) << 24 | (255) << 16 | (10) << 8 | (60));
            return;
        }
/*
        // Insertion d'un utilisateur après avoir lu la plus grande valeur de key, +1
        getBiggestUserKey(new Utilisateurs.CallBack() {
            @Override
            public void onCallback(int nextKey) {
                addUtilisateurs(nextKey+1, "Timmonier", "Clément");
            }
        });*/
        //addUtilisateurs("4", "Bastien", "MERLETTE");
    }
}