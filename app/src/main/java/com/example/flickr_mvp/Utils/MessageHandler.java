package com.example.flickr_mvp.Utils;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;

public class MessageHandler {
    public static void showMessage(Context context, int id) {
        String message = context.getString(id);
        Snackbar.make(((Activity)context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
