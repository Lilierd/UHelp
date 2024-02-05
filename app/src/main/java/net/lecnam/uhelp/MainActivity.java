package net.lecnam.uhelp;

import static net.lecnam.uhelp.queries.Utilisateurs.addUtilisateurs;
import static net.lecnam.uhelp.queries.Utilisateurs.getBiggestUserKey;
import static net.lecnam.uhelp.queries.Utilisateurs.getUserKey;
import static net.lecnam.uhelp.queries.Utilisateurs.userExists;
import static net.lecnam.uhelp.queries.Utilisateurs.userID;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import net.lecnam.uhelp.databinding.ActivityMainBinding;

import android.util.Log;
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
            userExists(pseudo, new Utilisateurs.CallBack() {
                @Override
                public void onCallback(int value) {
                    if(value == 0){
                        getBiggestUserKey(new Utilisateurs.CallBack() {
                            @Override
                            public void onCallback(int nextKey) {
                                addUtilisateurs(nextKey+1,pseudo);
                                Utilisateurs.userID = nextKey+1;
                            }
                        });
                    } else {
                        getUserKey(pseudo, new Utilisateurs.CallBack() {
                            @Override
                            public void onCallback(int value) {
                                userID = value;
                            }
                        });
                    }
                    Utilisateurs.Pseudo = pseudo;
                }
            });
            ActivityUtilities.openActivity(this, AccueilActivity.class, b);
        }
        else
        {
            confirm.setBackgroundColor((255) << 24 | (255) << 16 | (10) << 8 | (60));
        }
    }
}