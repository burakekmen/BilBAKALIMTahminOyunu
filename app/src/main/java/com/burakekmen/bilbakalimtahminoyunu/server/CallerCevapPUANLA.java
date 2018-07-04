package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.OyunActivity;

public class CallerCevapPUANLA extends Thread {
    public CallSOAPCevapPUANLA cs;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";
    public String mevcutPuan, soruID, verilenCevap;

    public void run() {
        try {
            cs = new CallSOAPCevapPUANLA();
            String resp = cs.Call(ApiKey, mevcutPuan, soruID, verilenCevap);
            OyunActivity.rslt2 = resp;
        } catch (Exception ex) {
            OyunActivity.rslt2 = ex.toString();
        }
    }
}