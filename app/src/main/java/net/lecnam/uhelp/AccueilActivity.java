package net.lecnam.uhelp;

import android.animation.IntArrayEvaluator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import net.lecnam.uhelp.queries.Utilisateurs;
import net.lecnam.uhelp.utils.ActivityUtilities;
import net.lecnam.uhelp.utils.MenuBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccueilActivity extends AppCompatActivity {

    private Button retour;

    private LinearLayout mesDemandes;
    private TextView hello;
    private Bundle b;

    //Barre de menu
    private MenuBar menuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        retour = (Button) findViewById(R.id.retourAccueil);
        mesDemandes = (LinearLayout) findViewById(R.id.mesdemandes);
        hello = (TextView) findViewById(R.id.hello);

        //Init de la barre de menu
        menuBar = new MenuBar(this);

        b = getIntent().getExtras();
        String pseudo = "";
        if(b != null)
            pseudo = "Hello "+b.getString("pseudo")+" !";
        else
            pseudo = "Hello pseudo !";
        hello.setText(pseudo);

        //Génération d'exemples de demandes
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(10,0,10,0);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Demandes");
        Task<DataSnapshot> liste = mDatabase.orderByChild("Demandeur").equalTo(Utilisateurs.userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        for(int i = 0; i < 15; i++){
            TextView t = new TextView(this);
            t.setTextSize(20f);
            t.setLayoutParams(params);
            t.setBackgroundResource(R.drawable.contour);
            t.setText("patate"+i);
            t.setTextColor(Color.WHITE);
            t.setId(i);
            t.setPadding(10,10,10,30);
            Bundle b = new Bundle();
            b.putString("demande", t.getText().toString());
            t.setOnClickListener(v -> ActivityUtilities.openActivity(this, DemandeActivity.class, b));
            mesDemandes.addView(t);
        }

        retour.setOnClickListener(v -> ActivityUtilities.openActivity(this, MainActivity.class));
    }}

