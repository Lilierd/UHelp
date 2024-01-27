package net.lecnam.uhelp.utils;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.lecnam.uhelp.R;
import net.lecnam.uhelp.utils.PopupButtons;

import java.util.ArrayList;



public class Popup extends PopupWindow {
    private int OK = 0;
    private int Yes = 0;
    private int No = 0;

    //Constructeur de Popup
    public Popup(Context context)
    {
        super(context);
    }

    public int getOK()
    {
        return OK;
    }

    public int getYes()
    {
        return Yes;
    }
    public int getNo()
    {
        return No;
    }

    /*
    Méthode de fabrication de popup
     */
    public static Popup createPopup(Context context, String title, String description, PopupButtons buttons) {
        Popup popUp = new Popup(context);
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setBackgroundColor((255) << 24 | (0x40) << 16 | (0x4C) << 8 | (0x84));
        //Textes de la popup
        TextView titleView = new TextView(context, null, R.style.TextViewUHelp); titleView.setText(title);
        titleView.setTextSize(30.0f); titleView.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
        TextView descView = new TextView(context, null, R.style.TextViewUHelp); descView.setText(description);
        descView.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));

        //Boutons de la popup (dépend de l'Enum PopupButtons)
        LinearLayout buttonsLayout = new LinearLayout(context);
        ArrayList<Button> boutons = new ArrayList<Button>();
        switch (buttons)
        {
            case OK:
                Button OK = new Button(new ContextThemeWrapper(context, R.style.PopupBoutons), null, 0);
                OK.setText("OK");
                OK.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
                OK.setOnClickListener(v -> {
                    popUp.OK = 1;
                    popUp.dismiss();
                });
                boutons.add(OK);
                break;
            case OKCancel:
                Button OKc = new Button(new ContextThemeWrapper(context, R.style.PopupBoutons), null, 0);
                OKc.setText("OK");
                OKc.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
                OKc.setOnClickListener(v -> {
                    popUp.OK = 1;
                    popUp.dismiss();
                });
                Button Cancel = new Button(new ContextThemeWrapper(context, R.style.PopupBoutons), null, 0);
                Cancel.setText("Annuler");
                Cancel.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
                Cancel.setOnClickListener(v -> {
                    popUp.dismiss();
                });
                boutons.add(OKc);
                boutons.add(Cancel);
                break;
            case YesNo:
                Button Yes = new Button(new ContextThemeWrapper(context, R.style.PopupBoutons), null, 0);
                Yes.setText("Oui");
                Yes.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
                Yes.setOnClickListener(v -> {
                    popUp.Yes = 1;
                    popUp.dismiss();
                });
                Button No = new Button(new ContextThemeWrapper(context, R.style.PopupBoutons), null, 0);
                No.setText("Non");
                No.setTextColor((255) << 24 | (255) << 16 | (255) << 8 | (255));
                No.setOnClickListener(v -> {
                    popUp.No = 1;
                    popUp.dismiss();
                });
                boutons.add(Yes);
                boutons.add(No);
                break;
        }

        //Paramètres des Layout
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //Ajout des différents éléments de la vue de la popup
        for (Button b : boutons) {
            buttonsLayout.addView(b);
        }
        mainLayout.addView(titleView, params);
        mainLayout.addView(descView, params);
        mainLayout.addView(buttonsLayout, params);
        mainLayout.setLayoutParams(new LayoutParams(200,200));

        popUp.setContentView(mainLayout);

        return popUp;
    }
}
