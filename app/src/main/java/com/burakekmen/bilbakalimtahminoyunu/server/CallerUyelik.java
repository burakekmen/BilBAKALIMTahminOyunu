package com.burakekmen.bilbakalimtahminoyunu.server;


import com.burakekmen.bilbakalimtahminoyunu.ui.UyelikActivity;

public class CallerUyelik extends Thread
{
    public CallSOAPUyelik cs;
    public String kAdi, kSifre;
    public String ApiKey = "Sw51QWvmP9-hjk2830ALXzvyT";

    public void run(){
        try{
            cs=new CallSOAPUyelik();
            String resp=cs.Call(ApiKey, kAdi, kSifre);
            UyelikActivity.rslt=resp;
        }catch(Exception ex)
        {UyelikActivity.rslt=ex.toString();}
    }
}
