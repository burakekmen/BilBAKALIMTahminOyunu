package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerUyelik;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class UyelikActivity  extends AppCompatActivity{

    public static InterstitialAd girisReklam;

    ImageView imgLogo;
    EditText txtKullaniciAdi, txtSifre;
    Button btnGirisYap;
    TextView txtKayitOl, txtSifreOgren;
    AlertDialog alertDialog;

    public static String rslt="";
    public static Boolean sonuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uyelik);

        if(GirisActivity.mp_giris != null)
            GirisActivity.mp_giris.stop();

        girisReklam = new InterstitialAd(this);
        girisReklam.setAdUnitId("ca-app-pub-1757058856747719/9753619187");
        girisReklam.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });
        reklamiYukle();
        nesneleriTanimla();

    }

    public void reklamiYukle() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        girisReklam.loadAd(adRequest);
    }


    private void nesneleriTanimla(){

        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        txtKullaniciAdi = (EditText)findViewById(R.id.txtKullaniciAdi);
        txtSifre = (EditText)findViewById(R.id.txtSifre);
        btnGirisYap = (Button)findViewById(R.id.btnGirisYap);
        txtKayitOl = (TextView)findViewById(R.id.txtKayitOl);
        txtSifreOgren = (TextView)findViewById(R.id.txtSifreOgren);
        alertDialog = new AlertDialog.Builder(this).create();

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("session",MODE_PRIVATE);

        if(pref.getBoolean("isLogged",false)){ //TODO Burada bilgileri Kontrol Ediyorum
            Intent i = new Intent(UyelikActivity.this, GirisActivity.class);

            i.putExtra("kullaniciAdi", pref.getString("kullaniciAdi", null));
            i.putExtra("sifre", pref.getString("sifre", null));

            startActivity(i);
        }else
            {

            btnGirisYap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtKullaniciAdi.getText().toString().trim().length() != 0 && txtSifre.getText().toString().trim().length() != 0) {
                        try {
                            final ProgressDialog progressDialog = new ProgressDialog(UyelikActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Kontrol Ediliyor...");
                            progressDialog.show();

                            final String kAdi = txtKullaniciAdi.getText().toString();
                            final String kSifre = txtSifre.getText().toString();
                            rslt = "START";
                            CallerUyelik c = new CallerUyelik();
                            c.kAdi = kAdi;
                            c.kSifre = kSifre;
                            c.join();
                            c.start();
                            while (rslt == "START") {
                                try {
                                    Thread.sleep(10);
                                } catch (Exception ex) {
                                }
                            }

                            sonuc = Boolean.valueOf(rslt);
                            Log.e("brk", sonuc.toString());

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            if (sonuc) {
                                                Intent i = new Intent(UyelikActivity.this, GirisActivity.class);
                                                i.putExtra("kullaniciAdi", kAdi);
                                                i.putExtra("sifre", kSifre);

                                                //TODO Burada Giriş Bilgilerini Kaydediyorum
                                                SharedPreferences.Editor edit = pref.edit();
                                                edit.putBoolean("isLogged",true);
                                                edit.putString("kullaniciAdi", kAdi);
                                                edit.putString("sifre", kSifre);
                                                edit.apply();

                                                reklamiYukle();
                                                startActivity(i);
                                                Toast.makeText(getApplicationContext(), "Hoş Geldiniz", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Bilgileri Kontrol Edin!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Lütfen Bilgileri Eksiksiz Giriniz!", Toast.LENGTH_SHORT).show();
                }
            });
        }


        txtKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UyelikActivity.this, KayitActivity.class));
            }
        });

        txtSifreOgren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UyelikActivity.this, SifreOgrenActivity.class));
            }
        });

    }



}
