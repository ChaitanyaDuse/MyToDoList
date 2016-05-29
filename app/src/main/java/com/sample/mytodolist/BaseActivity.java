package com.sample.mytodolist;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chaitanyaduse on 5/29/2016.
 */
public class BaseActivity extends AppCompatActivity {
  private   ProgressDialog progressDialog;

    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
        }

        if (!isFinishing())
            progressDialog.show();
    }

    public void hideProgress() {
        try {
            if (!isFinishing() && progressDialog != null)
                progressDialog.dismiss();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
