package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.PuanTablosuActivity;

public class CallerPuanTablosu extends Thread {
    public CallSOAPPuanTablosu cs;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run() {
        try {
            cs = new CallSOAPPuanTablosu();
            String resp = cs.Call(ApiKey);
            PuanTablosuActivity.rslt = resp;
        } catch (Exception ex) {
            PuanTablosuActivity.rslt = ex.toString();
        }
    }
}