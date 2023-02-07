package com.example.glorious.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glorious.R;
import com.google.android.material.snackbar.Snackbar;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    private static final String ALPHA_NUMERIC_SPECIAL_PASSWORD_REGEX
            = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])(?=\\S+$)[A-Za-z\\d$@$!%*#?&]{7,15}$";
    //    private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])([a-zA-Z0-9@*#])(?=.*[@#$%^&+=])(?=\\S+$).{7,15}$";
    private static final String MIN_EIGHT_REGEX = "^.{6,15}$";
    private static final String NAME_REGEX = "^[a-zA-Z\\\\s](?=\\S+$).{2,20}$";
    private static final String NAME_NUMBER_WITHOUT_SPACE_REGEX
            = "^[a-zA-Z0-9\\\\s](?=\\S+$).{2,20}$";

    private static final String NAME_WITHOUT_NUMBER_REGEX
            = "^[a-zA-Z\\\\s](?!^\\d+$)(?=\\S+$).{2,20}$";

    //    private static final String PASSWORD_REGEX = "^([a-zA-Z0-9@*#]{8,15})$";
    private static final String EMAIL_REGEX
            = "^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
    private static final String PHONE_REGEX
            = "^[+#*\\(\\)\\[\\]]*([0-9][ ext+-pw#*\\(\\)\\[\\]]*){10,15}$";

    public static boolean getNetworkState(Context context) {

        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo()
                .isConnectedOrConnecting();
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void callDialIntent(String contact, Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
           // showToast(context.getString(R.string.no_app_found_open_dialer));
        }
    }

    public static Snackbar showSnackBar(View view, String text, int duration) {
        // create instance
        Snackbar snackbar = Snackbar.make(view, text, duration);

// get snackbar view
        View snackbarView = snackbar.getView();

// change snackbar text color
        int snackbarTextId = com.google.android.material.R.id.snackbar_text ;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTextColor(Color.WHITE);

        return snackbar;
    }
    public static Snackbar showInternetSnackBar(View view, String text, int duration) {
        // create instance
        Snackbar snackbar = Snackbar.make(view, text, duration);

// get snackbar view
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);

// change snackbar text color
        int snackbarTextId = com.google.android.material.R.id.snackbar_text;
        TextView textView = snackbarView.findViewById(snackbarTextId);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        return snackbar;
    }

    public static void openWhatsApp(Context context, String mobile, String textValue) {
        PackageManager packageManager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + mobile + "&text=" + URLEncoder
                    .encode(textValue, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
