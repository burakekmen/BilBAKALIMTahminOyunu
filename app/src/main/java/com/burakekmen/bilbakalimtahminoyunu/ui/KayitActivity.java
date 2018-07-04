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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerKayit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KayitActivity extends AppCompatActivity{

    ImageView imgLogo;
    EditText txtKullaniciAdi, txtSifre, txtEposta;
    Button btnKayitOl;
    AlertDialog alertDialog;
    TextView txtKayitOl;

    public static String rslt="";
    public static Boolean sonuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        nesneleriTanimla();

    }



    private void nesneleriTanimla(){
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        txtKullaniciAdi = (EditText)findViewById(R.id.txtKullaniciAdi);
        txtSifre = (EditText)findViewById(R.id.txtSifre);
        btnKayitOl = (Button)findViewById(R.id.btnKayitOl);
        alertDialog = new AlertDialog.Builder(this).create();
        txtKayitOl =  (TextView)findViewById(R.id.txtKayitOl);
        txtEposta = (EditText) findViewById(R.id.txtMail);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtKayitOl.setTypeface(typeface);


        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKayitOl.setEnabled(false);

                if (txtKullaniciAdi.getText().toString().trim().length() != 0 && txtSifre.getText().toString().trim().length() != 0 && txtEposta.getText().toString().trim().length() != 0 && isEmailValid(txtEposta.getText().toString())) {
                    if (!isEmailValid(txtKullaniciAdi.getText().toString())) {

                        final String kAdi = txtKullaniciAdi.getText().toString();
                        final String kSifre = txtSifre.getText().toString();
                        final String kEposta = txtEposta.getText().toString();

                        if (kSifre.length() > 3) {
                            try {
                                final ProgressDialog progressDialog = new ProgressDialog(KayitActivity.this);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Kayıt Olunuyor...");
                                progressDialog.show();


                                rslt = "START";
                                CallerKayit c = new CallerKayit();
                                c.kAdi = kAdi;
                                c.kSifre = kSifre;
                                c.keposta = kEposta;
                                c.join();
                                c.start();
                                while (rslt == "START") {
                                    try {
                                        Thread.sleep(10);
                                    } catch (Exception ex) {
                                    }
                                }

                                sonuc = Boolean.valueOf(rslt);

                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                // On complete call either onLoginSuccess or onLoginFailed
                                                if (sonuc) {
                                                    Intent i = new Intent(KayitActivity.this, UyelikActivity.class);
                                                    startActivity(i);
                                                    Toast.makeText(getApplicationContext(), "Kayıt Olma Başarılı", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(getApplicationContext(), "Kullanıcı Adınız: " + txtKullaniciAdi.getText().toString() + ", Şifreniz: " + txtSifre.getText().toString(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Kullanıcı Adı kullanılıyor!", Toast.LENGTH_SHORT).show();
                                                }
                                                // onLoginFailed();
                                                progressDialog.dismiss();
                                            }
                                        }, 2000);


                            } catch (Exception ex) {
                                alertDialog.setTitle("Error!");
                                alertDialog.setMessage(ex.toString());
                                alertDialog.show();
                            }
                        } else
                            Toast.makeText(getApplicationContext(), "Lütfen alanları 3 karakterden daha uzun giriniz", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(KayitActivity.this, "Kullanıcı Adı Mail Adresi Olamaz!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Lütfen Bilgileri Eksiksiz Giriniz!", Toast.LENGTH_SHORT).show();
                btnKayitOl.setEnabled(false);
            }});

    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(KayitActivity.this, UyelikActivity.class));

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
