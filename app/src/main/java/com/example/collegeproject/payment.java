package com.example.collegeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class payment extends AppCompatActivity
{
   TextView mfare;
   EditText msource, mdestination;
   ImageButton mpaytm;
   Button mgenerate;
  public int c=0,c1=0,result;
   String[] arr ={"noida","mathura","agra","kanpur","lucknow"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mfare=findViewById(R.id.fare);
        msource=findViewById(R.id.source);
        mdestination=findViewById(R.id.destination);
        mpaytm=findViewById(R.id.paytm);
        mgenerate=findViewById(R.id.button2);

        mgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=msource.getText().toString().toLowerCase();
                String s2=mdestination.getText().toString().toLowerCase();
                c=0;
                c1=0;
                for (String s : arr)
                {
                    c += 1;
                    if (s.equals(s1)) {

                        break;
                    }
                }
                for (String s : arr)
                {
                    c1 += 1;
                    if (s.equals(s2)) {
                        break;
                    }
                }
                result=(c1-c)*70;
                if(result<0)
                {
                    result=-result;
                }
                mfare.setText(Integer.toString(result));
            }
        });
        mpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(payment.this, finall.class);
                startActivity(intent);
            }
        });




    }
}
