package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerPuanTablosu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class PuanTablosuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static String rslt="";
    ListView lv;
    AlertDialog adialog;
    TextView txtPuanTablosu;
    SwipeRefreshLayout swipeRefreshLayout = null;

    ArrayList<HashMap<String,String>> list ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puantablosu);


        txtPuanTablosu = (TextView) findViewById(R.id.txtPuanTablosu);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_puantablosu_swipeRefreshLayout);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtPuanTablosu.setTypeface(typeface);

        lv = (ListView)findViewById(R.id.listPuanlar);
        list = new ArrayList<>();
        Toast.makeText(this, "Puan Tablosu Alınıyor", Toast.LENGTH_LONG).show();
        new getKisiler().execute();

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        list = new ArrayList<>();
        Toast.makeText(this, "Puan Tablosu Alınıyor", Toast.LENGTH_LONG).show();
        new getKisiler().execute();

    }


    private class getKisiler extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                rslt = "START";
                CallerPuanTablosu c = new CallerPuanTablosu();
                c.start();
                while (rslt == "START") {
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception ex) {
                adialog.setTitle("Error!");
                adialog.setMessage(ex.toString());
                adialog.show();
            }



            if (rslt!=null) {
                //eğer herhangi bir hata yoksa json işlemleride alt satıra devam
                try {
                    //artık elimizde bir json satırı var ve bunu işleme zamanı
                    JSONObject jsonObj = new JSONObject(rslt);

                    //json nesnemizin title' da ögrenciler di unutmayın
                    JSONArray puanlar = jsonObj.getJSONArray("puanTablosu");

                    for (int i=0;i<puanlar.length();i++) {
                        JSONObject p = puanlar.getJSONObject(i);
                        //sırayla okuma var burada
                        //verileri alıp birer stringe atıyoruz
                        String kullaniciAdi = p.getString("kullaniciAdi");
                        String puani = p.getString("puan");

                        HashMap<String,String> puan = new HashMap<>();
                        //geçici bir contact nesnesine atayıp bunuda ana listemize atıyoruz
                        puan.put("kullaniciAdi",kullaniciAdi);
                        puan.put("puani",puani);


                        list.add(puan);

                    }


                } catch (final JSONException e) {
                    //json dizilimde bir hata var
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PuanTablosuActivity.this, "Json error"+e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            else {
                //servere bağlanmada eğer açık değilse hata verir
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PuanTablosuActivity.this, "Couldnt get json from server Check logcat", Toast.LENGTH_SHORT).show();
                    }
                });

            }


            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //listemize gömüyoruz
            ListAdapter adapter = new SimpleAdapter(PuanTablosuActivity.this
                    ,list,R.layout.list_item
                    ,new String[]{"puani","kullaniciAdi"}
                    ,new int[]{R.id.txtPuan,R.id.txtKullaniciAdi});
            lv.setAdapter(adapter);

            swipeRefreshLayout.setRefreshing(false);
        }
    }








}
