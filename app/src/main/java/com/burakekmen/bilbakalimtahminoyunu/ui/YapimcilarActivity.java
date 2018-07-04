package com.burakekmen.bilbakalimtahminoyunu.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burakekmen.bilbakalimtahminoyunu.R;


public class YapimcilarActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yapimcilar);

        ImageView img1 = (ImageView)findViewById(R.id.img1);
        ImageView img2 = (ImageView)findViewById(R.id.img2);
        ImageView img3 = (ImageView)findViewById(R.id.img3);
        ImageView img4 = (ImageView)findViewById(R.id.img4);

        TextView txtMemo = (TextView)findViewById(R.id.txt1);
        TextView txtBurak = (TextView)findViewById(R.id.txt2);
        TextView txtApo = (TextView)findViewById(R.id.txt3);
        TextView txtBilal = (TextView)findViewById(R.id.txt4);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Turtles.otf");
        txtMemo.setTypeface(typeface);
        txtBurak.setTypeface(typeface);
        txtApo.setTypeface(typeface);
        txtBilal.setTypeface(typeface);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img1:

                Toast.makeText(this, "LinkedIn sayfasına gidiliyor", Toast.LENGTH_SHORT).show();
                Intent i4 = new Intent();
                i4.setAction(Intent.ACTION_VIEW);
                i4.addCategory(Intent.CATEGORY_BROWSABLE);
                i4.setData(Uri.parse("https://www.linkedin.com/in/mehmet-aslan-839a1bb5"));
                startActivity(i4);

                break;
            case R.id.img2:
                Toast.makeText(this, "LinkedIn sayfasına gidiliyor", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse("https://www.linkedin.com/in/ekmenburak"));
                startActivity(i);

                break;
            case R.id.img3:
                Toast.makeText(this, "LinkedIn sayfasına gidiliyor", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent();
                i2.setAction(Intent.ACTION_VIEW);
                i2.addCategory(Intent.CATEGORY_BROWSABLE);
                i2.setData(Uri.parse("https://www.linkedin.com/in/abdullah-demirel-684751a5"));
                startActivity(i2);

                break;
            case R.id.img4:
                Toast.makeText(this, "LinkedIn sayfasına gidiliyor", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent();
                i3.setAction(Intent.ACTION_VIEW);
                i3.addCategory(Intent.CATEGORY_BROWSABLE);
                i3.setData(Uri.parse("https://www.linkedin.com/in/bilal-emre-g%C3%BCrkan-a5218413b"));
                startActivity(i3);

                break;
            default:
                break;

        }
    }
}
