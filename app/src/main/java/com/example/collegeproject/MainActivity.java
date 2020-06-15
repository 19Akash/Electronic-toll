package com.example.collegeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    Button mlogin,goreg,mforgot;
    EditText memail,mpassword;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlogin=findViewById(R.id.login);
        goreg=findViewById(R.id.button);
        mforgot=findViewById(R.id.forgot);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        fAuth=FirebaseAuth.getInstance();
        goreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });




        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                if(TextUtils.isEmpty((email)))
                {
                    memail.setError("Email is invalid.");
                    return;
                }
                if(TextUtils.isEmpty((password)))
                {
                    memail.setError("Password is invalid.");
                    return;
                }
                if(password.length()<6)
                {
                    mpassword.setError("Password Must be >= 6 characters");
                }
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Logged in successful. ", Toast.LENGTH_SHORT).show();
                             Intent intent=new Intent(MainActivity.this, payment.class);
                             startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
