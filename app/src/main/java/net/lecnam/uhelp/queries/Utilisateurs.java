package net.lecnam.uhelp.queries;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

@IgnoreExtraProperties
public class Utilisateurs {
    private static DatabaseReference mDatabase;
    public String Nom;
    public String Prenom;
    public String Age;
    public String Profession;
    public Utilisateurs() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Utilisateurs(String Nom, String Prenom){
        this.Nom = Nom;
        this.Prenom = Prenom;
    }

    public static void addUtilisateurs(String userID, String Nom, String Prenom){
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        Utilisateurs utilisateur = new Utilisateurs(Nom, Prenom);
        mDatabase.child("Utilisateurs").child(userID).setValue(utilisateur);
    }

    public static int getBiggestUserKey(){
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Utilisateurs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    /*
                    childSnapshot contient l'objet complet {Nom:"", Prenom:""}
                    childSnapshot.getKey() permet de récupérer la clé (1)
                    childSnapshot.child("Prenom") permet de récupérer l'objet JSON du fils "prenom"
                        soit {Prenom:""}
                    childSnapshot.child("Prenom").getValue() permet de récupérer la valeur pure
                        soit "Germain"
                     */
                    System.out.println(childSnapshot.child("Prenom").getValue());
                    System.out.println(key);
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError snapshot) {

            }
        });


        return 0;
    }


}
