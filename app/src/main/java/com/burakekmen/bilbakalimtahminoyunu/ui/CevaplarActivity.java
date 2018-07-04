package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.model.Cevap;
import com.burakekmen.bilbakalimtahminoyunu.R;
import com.burakekmen.bilbakalimtahminoyunu.model.Soru;
import com.burakekmen.bilbakalimtahminoyunu.server.CallerCevaplar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CevaplarActivity extends AppCompatActivity {

    public static String rslt="";
    ListView lv;
    AlertDialog adialog;

    TextView txtCevap;

    ArrayList<Soru> sorular;
    ArrayList<Cevap> verilenCevaplar;
    ArrayList<Cevap> dogruCevaplar;

    ArrayList<HashMap<String,String>> list ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cevaplar);

        sorular = OyunActivity.sorular;
        verilenCevaplar = OyunActivity.cevaplar;
        dogruCevaplar = new ArrayList<>();

        txtCevap = (TextView) findViewById(R.id.txtCevap);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtCevap.setTypeface(typeface);

        lv = (ListView)findViewById(R.id.listCevaplar);
        list = new ArrayList<>();
        Toast.makeText(this, "Cevaplar Alınıyor", Toast.LENGTH_LONG).show();
        new CevaplarActivity.getCevaplar().execute();
    }



    private class getCevaplar extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                rslt = "START";
                CallerCevaplar c = new CallerCevaplar();
                c.s1 = String.valueOf(OyunActivity.sorular.get(0).ID);
                c.s2 = String.valueOf(OyunActivity.sorular.get(1).ID);
                c.s3 = String.valueOf(OyunActivity.sorular.get(2).ID);
                c.s4 = String.valueOf(OyunActivity.sorular.get(3).ID);
                c.s5 = String.valueOf(OyunActivity.sorular.get(4).ID);
                c.s6 = String.valueOf(OyunActivity.sorular.get(5).ID);
                c.s7 = String.valueOf(OyunActivity.sorular.get(6).ID);
                c.s8 = String.valueOf(OyunActivity.sorular.get(7).ID);
                c.s9 = String.valueOf(OyunActivity.sorular.get(8).ID);
                c.s10 = String.valueOf(OyunActivity.sorular.get(9).ID);
                c.join();
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


            Log.e("rslt", rslt);

            if (rslt!=null) {
                //eğer herhangi bir hata yoksa json işlemleride alt satıra devam
                try {
                    //artık elimizde bir json satırı var ve bunu işleme zamanı
                    JSONObject jsonObj = new JSONObject(rslt);

                    //json nesnemizin title' da ögrenciler di unutmayın
                    JSONArray cevaplar = jsonObj.getJSONArray("soruCevaplar");

                    for (int i=0;i<cevaplar.length();i++) {
                        JSONObject c = cevaplar.getJSONObject(i);
                        //sırayla okuma var burada
                        //verileri alıp birer stringe atıyoruz
                        String cevapID = c.getString("cevapID");
                        String cevap = c.getString("cevap");
                        String soruID = c.getString("soruID");

                        HashMap<String,String> answer = new HashMap<>();
                        //geçici bir contact nesnesine atayıp bunuda ana listemize atıyoruz

                        for (Soru s  : OyunActivity.sorular){
                            if(s.ID == Integer.valueOf(soruID)) {
                                answer.put("soru", s.soru);
                                Log.e("soru11",s.soru);
                            }
                        }

                        answer.put("cevap",cevap);
                        Log.e("cevap11", cevap);

                        for (Cevap a  : OyunActivity.cevaplar){
                            if(a.soruID == Integer.valueOf(soruID)) {
                                answer.put("verilenCevap", String.valueOf(a.cevap));
                                Log.e("verilenCevap11", String.valueOf(a.cevap));
                            }
                        }



                        list.add(answer);

                    }


                } catch (final JSONException e) {
                    //json dizilimde bir hata var
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CevaplarActivity.this, "Json error"+e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            else {
                //servere bağlanmada eğer açık değilse hata verir
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CevaplarActivity.this, "Couldnt get json from server Check logcat", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //listemize gömüyoruz
            ListAdapter adapter = new SimpleAdapter(CevaplarActivity.this
                    ,list,R.layout.list_item_cevap
                    ,new String[]{"soru","cevap", "verilenCevap"}
                    ,new int[]{R.id.txtSoru,R.id.txtDogruCevap, R.id.txtVerilenCevap});
            lv.setAdapter(adapter);
        }
    }




}
