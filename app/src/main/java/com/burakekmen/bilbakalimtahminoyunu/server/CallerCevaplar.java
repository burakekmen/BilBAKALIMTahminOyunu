package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.CevaplarActivity;

public class CallerCevaplar extends Thread {
    public CallSOAPCevaplar cs;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";
    public String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;

    public void run() {
        try {
            cs = new CallSOAPCevaplar();
            String resp = cs.Call(ApiKey, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
            CevaplarActivity.rslt = resp;
        } catch (Exception ex) {
            CevaplarActivity.rslt = ex.toString();
        }
    }
}