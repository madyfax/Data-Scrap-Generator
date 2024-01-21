package com.data.extract.generator;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import java.util.Objects;


public class Loader {
    private AlertDialog alertDialogRW;

    public Loader(Activity activity, boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.loader_dialog, (ViewGroup) null));
        this.alertDialogRW = builder.create();
        ((Window) Objects.requireNonNull(this.alertDialogRW.getWindow())).setBackgroundDrawable(new ColorDrawable(0));
        this.alertDialogRW.setCancelable(z);
    }

    public Loader(Activity activity, boolean z, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.loader_dialog, (ViewGroup) null);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(msg + "");
        builder.setView(view);
        this.alertDialogRW = builder.create();
        ((Window) Objects.requireNonNull(this.alertDialogRW.getWindow())).setBackgroundDrawable(new ColorDrawable(0));
        this.alertDialogRW.setCancelable(z);
    }

    public void show() {
        AlertDialog alertDialog = this.alertDialogRW;
        if (alertDialog != null && !alertDialog.isShowing()) {
            this.alertDialogRW.show();
        }
    }

    public void dismiss() {
        AlertDialog alertDialog = this.alertDialogRW;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.alertDialogRW.dismiss();
        }
    }
}