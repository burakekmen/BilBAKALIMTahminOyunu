package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerSifreOgren;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SifreOgrenActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtEposta;
    Button btnSifreOgren;
    TextView txtSifreOgren;

    public static String rslt ="";
    public static Boolean sonuc;
    AlertDialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sifre_ogren);


        nesneleriTanimla();

    }


    private void nesneleriTanimla(){

        txtEposta = (EditText) findViewById(R.id.txtEposta);
        btnSifreOgren =(Button) findViewById(R.id.btnSifreOgren);
        txtSifreOgren = (TextView) findViewById(R.id.txtSifreOgren);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtSifreOgren.setTypeface(typeface);

        btnSifreOgren.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSifreOgren:
                if(txtEposta.getText().toString().trim().length() != 0 && isEmailValid(txtEposta.getText().toString())){

                    final String kEposta = txtEposta.getText().toString();

                    try {
                        final ProgressDialog progressDialog = new ProgressDialog(SifreOgrenActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Kullanıcı Kontrol Ediliyor...");
                        progressDialog.show();

                        rslt = "START";
                        CallerSifreOgren c = new CallerSifreOgren();
                        c.kEposta = kEposta;
                        c.join();
                        c.start();
                        while (rslt == "START") {
                            try {
                                Thread.sleep(10);
                            } catch (Exception ex) {
                            }
                        }

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        Intent i = new Intent(SifreOgrenActivity.this, UyelikActivity.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), rslt, Toast.LENGTH_LONG).show();
                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 2000);


                    } catch (Exception ex) {
                        alertDialog.setTitle("Error!");
                        alertDialog.setMessage(ex.toString());
                        alertDialog.show();
                    }

                }else
                    Toast.makeText(this, "Mail Adresinizi Boş veya Yanlış Girmeyiniz!", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
