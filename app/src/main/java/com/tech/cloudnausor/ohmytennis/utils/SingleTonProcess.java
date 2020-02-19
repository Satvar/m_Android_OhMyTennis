package com.tech.cloudnausor.ohmytennis.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.tech.cloudnausor.ohmytennis.R;


public class SingleTonProcess {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    private static final SingleTonProcess ourInstance = new SingleTonProcess();

    public static SingleTonProcess getInstance() {
        return ourInstance;
    }

    private SingleTonProcess() { }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        builder = new AlertDialog.Builder(context);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.progressdialoglayout);
        builder.setCancelable(false);
        dialog  = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
