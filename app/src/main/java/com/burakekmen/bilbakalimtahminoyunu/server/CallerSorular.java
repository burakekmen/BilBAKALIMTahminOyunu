package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.OyunActivity;

public class CallerSorular extends Thread {
    public CallSOAPSorular cs;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run() {
        try {
            cs = new CallSOAPSorular();
            String resp = cs.Call(ApiKey);
            OyunActivity.rslt = resp;
        } catch (Exception ex) {
            OyunActivity.rslt = ex.toString();
        }
    }
}