package com.burakekmen.bilbakalimtahminoyunu.server;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class CallSOAPKayit {

    public String SOAP_ACTION = "http://tempuri.org/uyeEkle";
    public String OPERATION_NAME = "uyeEkle";
    public String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public String SOAP_ADDRESS = "http://bbwebservisim.somee.com/servisim.asmx";

    public CallSOAPKayit()
    {
    }
    public String Call(String ApiKey, String kAdi, String kSifre, String kEposta)
    {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();
        pi.setName("key");
        pi.setValue(ApiKey);
        pi.setType(String.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("kullaniciAdi");
        pi.setValue(kAdi);
        pi.setType(String.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("sifre");
        pi.setValue(kSifre);
        pi.setType(String.class);
        request.addProperty(pi);
        pi=new PropertyInfo();
        pi.setName("eposta");
        pi.setValue(kEposta);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        Object response=null;
        try
        {
            httpTransport.call(SOAP_ACTION, envelope);
            response = envelope.getResponse();
        }
        catch (Exception exception)
        {
            response=exception.toString();
        }
        return response.toString();
    }



}
