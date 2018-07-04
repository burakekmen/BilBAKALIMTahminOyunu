package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;


public class GirisActivity  extends AppCompatActivity implements View.OnClickListener{

    public static MediaPlayer mp_giris ;
    public static boolean oncedenAcilmisMi = false;

    public static String kullaniciAdi, sifre;
    Button btnOyunaBasla, btnPuamDurumu, btnYapimcilar, btnPuanVer, btnHarita;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        nesneleriTanimla();

        mp_giris = MediaPlayer.create(GirisActivity.this, R.raw.music_main);

        if (mp_giris.isPlaying() != true) {

            mp_giris.start();
            mp_giris.setLooping(true);
        }

    }

    private void nesneleriTanimla(){

        if(!oncedenAcilmisMi) {
            Bundle extras = getIntent().getExtras();
            kullaniciAdi = extras.getString("kullaniciAdi");
            sifre = extras.getString("sifre");

            oncedenAcilmisMi = true;
        }

        TextView txtBaslik = (TextView)findViewById(R.id.txt2);
        TextView txtBaslik2 = (TextView)findViewById(R.id.txtBaslik);

        btnOyunaBasla = (Button)findViewById(R.id.btnOyunaBasla);
        btnPuamDurumu = (Button)findViewById(R.id.btnPuanTablosu);
        btnYapimcilar = (Button)findViewById(R.id.btnHakkinda);
        btnPuanVer = (Button)findViewById(R.id.btnPuanVer);
        btnHarita = (Button)findViewById(R.id.btnHarita);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        btnOyunaBasla.setTypeface(typeface);
        btnYapimcilar.setTypeface(typeface);
        btnPuamDurumu.setTypeface(typeface);
        btnPuanVer.setTypeface(typeface);
        btnHarita.setTypeface(typeface);
        txtBaslik.setTypeface(typeface);
        txtBaslik2.setTypeface(typeface);

        btnOyunaBasla.setOnClickListener(this);
        btnPuamDurumu.setOnClickListener(this);
        btnYapimcilar.setOnClickListener(this);
        btnPuanVer.setOnClickListener(this);
        btnHarita.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Uygulama Kapansın mı?");
        alertDialogBuilder
                .setMessage("Kapatmak İçin EVET tuşuna basın!")
                .setCancelable(false)
                .setPositiveButton("EVET",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOyunaBasla:
                startActivity(new Intent(GirisActivity.this, OyunActivity.class));
                break;
            case R.id.btnPuanTablosu:
                startActivity(new Intent(GirisActivity.this, PuanTablosuActivity.class));
                break;
            case R.id.btnHakkinda:
                Toast.makeText(this, "İçerik Yükleniyor!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GirisActivity.this, YapimcilarActivity.class));
                break;
            case R.id.btnPuanVer:
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.burakekmen.bilbakalimtahminoyunu"));
                startActivity(i);
                break;
            case R.id.btnHarita://TODO Burada Çıkış İşlemi Yaptırdım
                SharedPreferences pref = getApplicationContext().getSharedPreferences("session",MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean("isLogged",false);
                edit.apply();

                Toast.makeText(this, "Çıkış Yapıldı!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GirisActivity.this,UyelikActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mp_giris.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mp_giris.start();
    }
}
