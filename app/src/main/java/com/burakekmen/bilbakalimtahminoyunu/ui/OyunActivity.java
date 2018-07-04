package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.model.Cevap;
import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.model.Soru;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerCevapPUANLA;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerSorular;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.burakekmen.bilbakalimtahminoyunu.ui.GirisActivity.mp_giris;


public class OyunActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSoruNumarasi, txtSure , txtSoru;
    EditText txtCevap;
    Button btnCevapla;
    AlertDialog alertDialog;

    int counter = 16;
    public CountDownTimer cdt;
    int durmusSure=0;

    public  static MediaPlayer mp_game ;

    int soruNumarasi = 0;
    int toplamPuan=0;

    private boolean acilmisMi=false;

    public static String rslt = "", rslt2="";
    public static Boolean sonuc;
    ProgressDialog progressDialog;

     public static ArrayList<Soru> sorular;
     public static ArrayList<Cevap> cevaplar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        mp_giris.stop();

        mp_game = MediaPlayer.create(OyunActivity.this,R.raw.music_game); //oyun music
        mp_game.start();
        mp_game.setLooping(true); //sarkının loop olması

        nesneleriTanimla();
        sorulariCek();

        txtSure.setText(Integer.toString(counter));

        txtSoruNumarasi.setText("SORU " + (soruNumarasi+1));
        txtSoru.setText(sorular.get(0).soru);

        cdt = new CountDownTimer(60 * 1000 , 1000 ) {

            @Override
            public void onTick(long millisUntilFinished) {

                acilmisMi =true;
                counter--;

                txtSure.setText(String.valueOf(counter));
               if(counter == -1 ){
                   txtSure.setText("Sureniz Doldu");

                   this.cancel();

                   txtCevap.setText("-1");

                   txtCevap.setEnabled(false);
                   btnCevapla.setText("Soruyu Gec");
               }

               durmusSure = counter;
            }

            @Override
            public void onFinish(){
               //sure bitince oluscak olay.

            }
        }; //sure

        cdt.start();
    }



    private void nesneleriTanimla() {

        txtSure = (TextView) findViewById(R.id.txtSure) ;
        txtSoruNumarasi = (TextView) findViewById(R.id.txtSoruNumarasi);
        txtSoru = (TextView) findViewById(R.id.txtSoru);
        txtCevap = (EditText) findViewById(R.id.txtCevap);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtSoruNumarasi.setTypeface(typeface);
        txtSure.setTypeface(typeface);

        alertDialog = new AlertDialog.Builder(this).create();

        btnCevapla = (Button) findViewById(R.id.btnCevapla);
        btnCevapla.setTypeface(typeface);

        sorular = new ArrayList<>();
        cevaplar = new ArrayList<>();
        btnCevapla.setOnClickListener(this);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCevapla:

                txtCevap.setEnabled(true);
                btnCevapla.setText("CEVAPLA");

                String cevap = txtCevap.getText().toString().trim();
                if(cevap.length() != 0 && !cevap.isEmpty() && !cevap.equals("") && cevap != null) {
                    Soru s = new Soru(sorular.get(soruNumarasi).ID, sorular.get(soruNumarasi).soru);
                    Cevap c = new Cevap(s.ID, Integer.parseInt(txtCevap.getText().toString()));
                    cevaplar.add(c);
                    cevabPuanla(c.soruID, c.cevap, toplamPuan);

                    txtCevap.setText("");

                    cdt.cancel(); //kapat
                    counter = 16 ; //cevaplanınca sure basa alınır.
                    cdt.start(); //baslat


                    soruNumarasi++;
                    if (soruNumarasi < 10) {
                        txtSoru.setText(sorular.get(soruNumarasi).soru);
                        txtSoruNumarasi.setText("SORU " + (soruNumarasi + 1));
                    } else {

                        mp_game.stop();
                        Intent i = new Intent(OyunActivity.this, SonucActivity.class);
                        i.putExtra("string_puan", String.valueOf(toplamPuan));
                        startActivity(i);
                    }

                }
                else
                    Toast.makeText(this, "Cevap Boş Girilemez", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



    private void sorulariCek(){

        try {
            progressDialog = new ProgressDialog(OyunActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Sorular Çekiliyor...");
            progressDialog.show();

            rslt = "START";
            CallerSorular c = new CallerSorular();
            c.start();
            while (rslt == "START") {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                }
            }
        }catch (Exception ex){
            alertDialog.setTitle("Error!");
            alertDialog.setMessage(ex.toString());
            alertDialog.show();
        }

        if (rslt != null) {
            //eğer herhangi bir hata yoksa json işlemleride alt satıra devam
            try {
                //artık elimizde bir json satırı var ve bunu işleme zamanı
                JSONObject jsonObj = new JSONObject(rslt);

                //json nesnemizin title' da ögrenciler di unutmayın
                JSONArray quest = jsonObj.getJSONArray("sorular");

                for (int i = 0; i < quest.length(); i++) {
                    JSONObject c = quest.getJSONObject(i);
                    //sırayla okuma var burada
                    //verileri alıp birer stringe atıyoruz
                    int id = c.getInt("soruID");
                    String soru = c.getString("soru");


                    Soru s = new Soru(id, soru);
                    Log.e("soru", s.ID + "," + s.soru);
                    sorular.add(s);

                }
            } catch (final JSONException e) {
                //json dizilimde bir hata var
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OyunActivity.this, "Json error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            progressDialog.dismiss();

        }
    }



    private void cevabPuanla(int soruID, int verilenCevap, int mevcutPuan){

        try {
            progressDialog = new ProgressDialog(OyunActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Cevap Gönderiliyor...");
            progressDialog.show();

            rslt2 = "START";
            CallerCevapPUANLA c = new CallerCevapPUANLA();
            c.mevcutPuan = String.valueOf(mevcutPuan);
            c.soruID= String.valueOf(soruID);
            c.verilenCevap=String.valueOf(verilenCevap);
            c.join();
            c.start();
            while (rslt2 == "START") {
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

        int puan = Integer.parseInt(rslt2);
        toplamPuan = puan;
        Log.e("toplamPuan", String.valueOf(toplamPuan));


    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Oyun Bitsin Mi?");
        alertDialogBuilder
                .setMessage("Bu oyundan puan alamayacaksınız!")
                .setCancelable(false)
                .setPositiveButton("TAMAM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                OyunActivity.mp_game.stop();
                                moveTaskToBack(true);
                                startActivity(new Intent(OyunActivity.this, GirisActivity.class));
                            }
                        })

                .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mp_game.pause();
        cdt.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mp_game.start();
        if (acilmisMi) {
            counter = durmusSure;
            cdt.start();
        }
    }
}
