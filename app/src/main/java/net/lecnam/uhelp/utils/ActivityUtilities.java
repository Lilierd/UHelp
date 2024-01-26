package net.lecnam.uhelp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.*;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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

    public static PopupWindow createPopup(Context context) {
        PopupWindow popUp = new PopupWindow(context);

        LinearLayout layout = new LinearLayout(context);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        //tv.setText("Hi this is a sample text for popup window");
        //layout.addView(tv, params);
        popUp.setContentView(layout);

        return popUp;
    }
}
