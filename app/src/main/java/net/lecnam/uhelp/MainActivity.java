package net.lecnam.uhelp;

import static net.lecnam.uhelp.queries.Utilisateurs.addUtilisateurs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        }
        else
        {
            confirm.setBackgroundColor((255) << 24 | (255) << 16 | (10) << 8 | (60));
            return;
        }
        addUtilisateurs("4", "Bastien", "MERLETTE");
    }
}