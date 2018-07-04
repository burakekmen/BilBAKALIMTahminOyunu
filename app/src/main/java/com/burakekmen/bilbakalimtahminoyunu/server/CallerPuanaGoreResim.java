package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.SonucActivity;

public class CallerPuanaGoreResim extends Thread {
    public CallSOAPPuanaGoreResim cs;
    public String kullaniciAdi, puan;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run() {
        try {
            cs = new CallSOAPPuanaGoreResim();
            String resp = cs.Call(ApiKey, kullaniciAdi, puan);
            SonucActivity.rslt = resp;
        } catch (Exception ex) {
            SonucActivity.rslt = ex.toString();
        }
    }
}