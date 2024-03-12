package net.lecnam.uhelp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.lecnam.uhelp.queries.Utilisateurs;
import net.lecnam.uhelp.utils.ActivityUtilities;
import net.lecnam.uhelp.utils.MenuBar;

public class ListeDemandesActivity extends Activity {
    private RelativeLayout fond;
    private ImageView retour;
    private LinearLayout liste;
    private MenuBar menuBar; //Barre de menu

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demandes_liste);
        //Récupération des éléments graphiques du layout actuel
        fond = (RelativeLayout) findViewById(R.id.fond);
        retour = (ImageView) findViewById(R.id.retour);
        liste = (LinearLayout) findViewById(R.id.liste_demandes);

        liste.setPadding(0,20,0,50);

        //Init de la barre de menu
        menuBar = new MenuBar(this);
        retour.setOnClickListener(v -> { ActivityUtilities.openActivity(this, AccueilActivity.class); });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,0,10,0);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Demandes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot demande : snapshot.getChildren()){
                            String s = demande.child("Demandeur").getValue().toString();
                            if(Utilisateurs.userID != Integer.parseInt(s)){
                                TextView t = new TextView(ListeDemandesActivity.this);
                                t.setTextSize(20f);
                                t.setLayoutParams(params);
                                t.setBackgroundResource(R.drawable.contour);
                                t.setText(demande.child("Nom").getValue().toString());
                                t.setTextColor(Color.WHITE);
                                t.setId(Integer.parseInt(demande.getKey()));
                                t.setPadding(10,10,10,30);
                                Bundle b = new Bundle();
                                b.putString("demande", t.getText().toString());
                                t.setOnClickListener(v -> ActivityUtilities.openActivity(ListeDemandesActivity.this, DemandeActivity.class, b));
                                liste.addView(t);
                            }
                        }
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
