package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerPuanaGoreResim;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;


public class SonucActivity  extends AppCompatActivity{

    InterstitialAd InterstitialAds;

    public static String rslt = "";
    String puan = "";
    String resimUrl="";

    TextView txtSonuc;
    ImageView imgSonuc;
    Button btnTekrarOyna, btnPuanTablosu, btnCevaplar;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        InterstitialAds = new InterstitialAd(this);
        InterstitialAds.setAdUnitId("ca-app-pub-1757058856747719/9753619187");
        reklamiYukle();

        InterstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                InterstitialAds.show();
            }
        });


        Bundle extras = getIntent().getExtras();
        puan = extras.getString("string_puan");

        Log.e("sonucPuan", puan);

        txtSonuc = (TextView)findViewById(R.id.txtSonuc);
        imgSonuc = (ImageView) findViewById(R.id.imgSonuc);
        btnTekrarOyna = (Button)findViewById(R.id.btnTekrarOyna);
        btnPuanTablosu = (Button)findViewById(R.id.btnPuanTablosu);
        btnCevaplar = (Button) findViewById(R.id.btnCevaplar);
        alertDialog = new AlertDialog.Builder(this).create();


        int Int_puan = Integer.parseInt(puan);
        MediaPlayer mp_endGame ;

        if( Int_puan >= 65 ){
            mp_endGame = MediaPlayer.create(SonucActivity.this,R.raw.highscore); //oyun music
            mp_endGame.start();
        }
        else if( Int_puan < 65 && Int_puan >= 35 ){
            mp_endGame = MediaPlayer.create(SonucActivity.this,R.raw.mediumscore); //oyun music
            mp_endGame.start();
        }else if( Int_puan < 35 ){
            mp_endGame = MediaPlayer.create(SonucActivity.this,R.raw.lowscore); //oyun music
            mp_endGame.start();
        }


        Toast.makeText(this, "Puanınız Alınıyor", Toast.LENGTH_SHORT).show();
        puanaGoreURLAl();
        ekranBoyutuCek();

        txtSonuc.setText("PUANIN : " + puan);

        btnTekrarOyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SonucActivity.this, OyunActivity.class));
            }
        });

        btnPuanTablosu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SonucActivity.this, PuanTablosuActivity.class));
            }
        });

        btnCevaplar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SonucActivity.this, CevaplarActivity.class));
            }
        });


    }


    private void reklamiYukle() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        InterstitialAds.loadAd(adRequest);
    }


    private void puanaGoreURLAl(){

        try {
            progressDialog = new ProgressDialog(SonucActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Cevap Gönderiliyor...");
            progressDialog.show();

            rslt = "START";
            CallerPuanaGoreResim c = new CallerPuanaGoreResim();
            c.kullaniciAdi = GirisActivity.kullaniciAdi;
            c.puan = puan;
            c.join();
            c.start();
            while (rslt == "START") {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }
            progressDialog.dismiss();
        }catch (Exception ex){
            alertDialog.setTitle("Error!");
            alertDialog.setMessage(ex.toString());
            alertDialog.show();
        }


        resimUrl = rslt;
        Log.e("resim URL: ", resimUrl);
        try {
            Picasso.with(this).load(resimUrl).into(imgSonuc);
        }catch (Exception ex){

        }


    }



    public void ekranBoyutuCek()
    {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();

        int resimGenislik  = 0;
        int resimYukseklik = 0;


        if(width >= 1400)
        {
            resimGenislik  = 1000;
            resimYukseklik =  1185;
        }
        else if( width >= 1300 && width < 1400)
        {
            resimGenislik  = 1000;
            resimYukseklik =  1185;
        }
        else if( width >= 1200 && width < 1300)
        {
            resimGenislik  = 900;
            resimYukseklik =  1066;
        }
        else if( width >= 1100 && width < 1200)
        {
            resimGenislik  = 900;
            resimYukseklik =  1066;
        }
        else if( width >= 1000 && width < 1100)
        {
            resimGenislik  = 800;
            resimYukseklik =  948;
        }
        else if( width >= 900 && width < 1000)
        {
            resimGenislik  = 700;
            resimYukseklik =  829;
        }
        else if( width >= 800 && width < 900)
        {
            resimGenislik  = 600;
            resimYukseklik =  711;
        }
        else if( width >= 700 && width < 800)
        {
            resimGenislik  = 500;
            resimYukseklik =  592;
        }
        else if( width >= 600 && width < 700)
        {
            resimGenislik  = 400;
            resimYukseklik =  474;
        }
        else if( width >= 500 && width < 600)
        {
            resimGenislik  = 400;
            resimYukseklik =  474;
        }
        else if( width >= 400 && width < 500)
        {
            resimGenislik  = 300;
            resimYukseklik =  355;
        }
        else if( width >= 300 && width < 400)
        {
            resimGenislik  = 200;
            resimYukseklik =  237;
        }
        else if(width < 300)
        {
            resimGenislik  = 200;
            resimYukseklik =  237;
        }


        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(resimGenislik, resimYukseklik);
        parms.gravity= Gravity.CENTER;
        imgSonuc.setLayoutParams(parms);


    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(SonucActivity.this, GirisActivity.class));
    }


}
