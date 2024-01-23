package net.lecnam.uhelp;

import android.animation.IntArrayEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import net.lecnam.uhelp.utils.ActivityUtilities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class AccueilActivity extends AppCompatActivity {

    private Button retour;

    private LinearLayout mesDemandes;
    private TextView hello;
    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        retour = (Button) findViewById(R.id.retourAccueil);
        mesDemandes = (LinearLayout) findViewById(R.id.mesdemandes);
        hello = (TextView) findViewById(R.id.hello);

        b = getIntent().getExtras();
        String pseudo = "";
        if(b != null)
            pseudo = "Hello "+b.getString("pseudo")+" !";
        else
            pseudo = "Nique ta mère";
        hello.setText(pseudo);

        for(int i = 0; i < 15; i++){
            TextView t = new TextView(this);
            t.setTextSize(20f);
            t.setPadding(0,0,0,40);
            t.setBackgroundResource(R.drawable.contour);
            t.setText("patate"+i);
            t.setTextColor(Color.WHITE);
            t.setId(i);
            Bundle b = new Bundle();
            b.putString("demande", t.getText().toString());
            t.setOnClickListener(v -> ActivityUtilities.openActivity(this, DemandeActivity.class, b));
            mesDemandes.addView(t);
        }

        retour.setOnClickListener(v -> ActivityUtilities.openActivity(this, MainActivity.class));
    }

}
