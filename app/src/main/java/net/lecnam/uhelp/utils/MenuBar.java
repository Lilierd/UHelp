package net.lecnam.uhelp.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import net.lecnam.uhelp.AccueilActivity;
import net.lecnam.uhelp.NewDemandeActivity;
import net.lecnam.uhelp.ProfilActivity;
import net.lecnam.uhelp.R;

public class MenuBar extends Activity {
    //Activité dans laquelle est incluse la barre de menu
    private Activity activity;
    public MenuBar(Activity activity)
    {
        this.activity = activity;

        ImageView home = (ImageView) activity.findViewById(R.id.home);
        ImageView carte = (ImageView) activity.findViewById(R.id.carte);
        ImageView demande = (ImageView) activity.findViewById(R.id.newDemande);
        ImageView msg = (ImageView) activity.findViewById(R.id.msg);
        ImageView profil = (ImageView) activity.findViewById(R.id.profil);

        //Création des click listener pour l'ouverture de chacune des activités liées
        home.setOnClickListener(v -> { ActivityUtilities.openActivity(activity, AccueilActivity.class); }); //Accueil
        //carte.setOnClickListener(v -> { ActivityUtilities.openActivity(activity, AccueilActivity.class); });
        demande.setOnClickListener(v -> { ActivityUtilities.openActivity(activity, NewDemandeActivity.class); }); //Nouvelle demande
        //msg.setOnClickListener(v -> { ActivityUtilities.openActivity(activity, AccueilActivity.class); });
        profil.setOnClickListener(v -> { ActivityUtilities.openActivity(activity, ProfilActivity.class); }); //Profil
    }
}
