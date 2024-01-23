package net.lecnam.uhelp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/*
*   Classe de fonctions réutilisable
*/
public class ActivityUtilities {
    //Fonction pour ouvrir une nouvelle activité
    public static void openActivity(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
    //Surcharge de la fonction précédente permettant de charger des extras (e.g. données supplémentaires)
    public static void openActivity(Context context, Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtras(extras);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
