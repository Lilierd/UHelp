package net.lecnam.uhelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class AccueilActivity extends AppCompatActivity {

    private Button retour;

    private GridLayout mesDemandes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        retour = (Button) findViewById(R.id.retour);
        mesDemandes = (GridLayout) findViewById(R.id.mesdemandes);
        for(int i = 0; i < 4; i++){
            TextView t = new TextView(this);
            t.setText("patate"+i);
            t.setTextColor(Color.WHITE);
            mesDemandes.addView(t);

        }

        retour.setOnClickListener(v -> retourLanding());
    }

    private void retourLanding(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
