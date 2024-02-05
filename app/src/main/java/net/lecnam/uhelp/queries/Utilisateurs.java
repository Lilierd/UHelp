package net.lecnam.uhelp.queries;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import javax.security.auth.callback.Callback;

@IgnoreExtraProperties
public class Utilisateurs {
    private static DatabaseReference mDatabase;
    public static String Nom;
    public static String Prenom;
    public static String Pseudo;
    public String Age;
    public String Profession;
    public static int userID = 0;
    private static int maxKey = -1;
    private static String pseudo = "";
    private static int Result = 0;

    public Utilisateurs() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // Fonction qui permet de récupérer des valeurs depuis une fonction asynchrone
    public interface CallBack {
        void onCallback(int value);
    }


    public Utilisateurs(String Nom, String Prenom) {
        this.Nom = Nom;
        this.Prenom = Prenom;
    }

    public Utilisateurs(String pseudo) {
        this.Pseudo = pseudo;
    }

    // Modification d'un utilisateur pour modifier Nom / Prénom
    public static void EditUser(int userID, String Nom, String Prenom) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Utilisateurs").child(String.valueOf(userID)).child("Nom").setValue(Nom);
        Utilisateurs.Nom = Nom;
        mDatabase.child("Utilisateurs").child(String.valueOf(userID)).child("Prenom").setValue(Prenom);
        Utilisateurs.Prenom = Prenom;
    }


    // Ajout d'un utilisateur par le pseudo
    public static void addUtilisateurs(int userID, String Pseudo) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        DatabaseReference child = mDatabase.child("Utilisateurs").child(String.valueOf(userID));
        child.child("Pseudo").setValue(Pseudo);
    }


    // Je voulais utiliser orderBy, mais impossible de faire fonctionner la fonction.
    // Fonction pour vérifier l'existence d'un utilisateur
    public static void userExists(String Pseudo, CallBack callback) {

        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Utilisateurs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Result = 0;
                for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                    if (childDataSnapshot.child("Pseudo").getValue().toString().equals(Pseudo)) {
                        Result = 1;
                    }
                }
                callback.onCallback(Result);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void getBiggestUserKey(CallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Utilisateurs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    int Ikey = Integer.parseInt(key);
                    if (Integer.parseInt(key) > maxKey) {
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

    // Récupération de la clé d'un utilisateur
    public static void getUserKey(String pseudo, CallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Utilisateurs").addListenerForSingleValueEvent(new ValueEventListener() {
            int key = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.child("Pseudo").getValue().toString().equals(pseudo)) {
                        String Skey = child.getKey();
                        try {
                            key = Integer.parseInt(Skey);
                        } catch (NumberFormatException e) {
                        }
                        break;
                    }
                }
                callBack.onCallback(key);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    // Insertion d'une demande
    public static void insertDemand(int id, int Demandeur, String nom, String type) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        DatabaseReference child = mDatabase.child("Demandes").child(String.valueOf(id));
        child.child("Demandeur").setValue(Demandeur);
        child.child("Nom").setValue(nom);
        child.child("Type").setValue(type);
    }

    // récupère la plus grande clé des demandes afin d'en ajouter une autre
    public static void getBiggestDemandKey(CallBack callBack) {
        mDatabase = FirebaseDatabase.getInstance("https://uhelp-68904-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mDatabase.child("Demandes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    int Ikey = Integer.parseInt(key);
                    if (Integer.parseInt(key) > maxKey) {
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
