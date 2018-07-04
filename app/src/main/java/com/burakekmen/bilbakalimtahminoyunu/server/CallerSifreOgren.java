package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.SifreOgrenActivity;

public class CallerSifreOgren extends Thread {
    public CallSOAPSifreOgren cs;
    public String kEposta;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run() {
        try {
            cs = new CallSOAPSifreOgren();
            String resp = cs.Call(ApiKey, kEposta);
            SifreOgrenActivity.rslt = resp;
        } catch (Exception ex) {
            SifreOgrenActivity.rslt = ex.toString();
        }
    }
}