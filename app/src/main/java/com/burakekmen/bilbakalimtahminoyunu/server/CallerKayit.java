package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.KayitActivity;

public class CallerKayit extends Thread {
    public CallSOAPKayit cs;
    public String kAdi, kSifre, keposta;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run() {
        try {
            cs = new CallSOAPKayit();
            String resp = cs.Call(ApiKey, kAdi, kSifre, keposta);
            KayitActivity.rslt = resp;
        } catch (Exception ex) {
            KayitActivity.rslt = ex.toString();
        }
    }
}