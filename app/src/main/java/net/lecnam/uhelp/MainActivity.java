package net.lecnam.uhelp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import net.lecnam.uhelp.databinding.ActivityMainBinding;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

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

        confirm.setOnClickListener(v -> openAccueil());

    }

    public void openAccueil(){
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
        finish();
    }
}