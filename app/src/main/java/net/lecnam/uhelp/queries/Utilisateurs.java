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
    public String Pseudo;
    public String Age;
    public String Profession;

    private static int maxKey = -1;
    private static String pseudo = "";
    private static int Result = 0;

    public Utilisateurs() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }



    public interface CallBack {
        void onCallback (int value);
    }
    public Utilisateurs(String Nom, String Prenom){
        this.Nom = Nom;
        this.Prenom = Prenom;
    }
    public Utilisateurs(String pseudo) {
        this.Pseudo = pseudo;
    }

    public static void EditUser(int userID, String Nom, String Prenom){
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        Utilisateurs utilisateur = new Utilisateurs(Nom, Prenom);
        mDatabase.child("Utilisateurs").child(String.valueOf(userID)).setValue(utilisateur);
    }
    public static void addUtilisateurs(int userID, String Pseudo){
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        Utilisateurs utilisateur = new Utilisateurs(Pseudo);
        mDatabase.child("Utilisateurs").child(String.valueOf(userID)).setValue(utilisateur);
    }

    public static void readUser(String Pseudo, CallBack callback){


        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Utilisateurs");
        mDatabase.orderByChild("Pseudo").equalTo(Pseudo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    Result = 1;
                }
                callback.onCallback(Result);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void getBiggestUserKey(CallBack callBack){
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
                    System.out.println("Clé : " + key);
                    System.out.println("Max Key : " + maxKey);
                    int Ikey = Integer.parseInt(key);
                    if(Integer.parseInt(key) > maxKey) {
                        maxKey = Ikey;
                    }
            }
            callBack.onCallback(maxKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError snapshot) {

            }
        });
    }




}
