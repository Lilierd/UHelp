package net.lecnam.uhelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class AccueilActivity extends AppCompatActivity {

    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        retour = (Button) findViewById(R.id.retour);

        retour.setOnClickListener(v -> retourLanding());
    }

    private void retourLanding(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
