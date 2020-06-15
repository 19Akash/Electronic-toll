package com.example.collegeproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class register extends AppCompatActivity {
    EditText mname,mphone,memail,mpassword,mregister;
    ImageView imageView;
    Button capture,reg,backlogin;
    FirebaseAuth fAuth;
    public static final int CAMERA_PERM_CODE = 101;
    DatabaseReference deff;
    Member member;
    public Uri imguri;
    StorageReference mStorageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mname=findViewById(R.id.name);
        mphone=findViewById(R.id.phone);
        memail=findViewById(R.id.email1);
        mpassword=findViewById(R.id.password1);
        mregister=findViewById(R.id.number);
        imageView=findViewById(R.id.imageView);
        capture=findViewById(R.id.capture);
        reg=findViewById(R.id.button4);
        backlogin=findViewById(R.id.backlogin);
        fAuth=FirebaseAuth.getInstance();
        member=new Member();
        deff= FirebaseDatabase.getInstance().getReference().child("Member");
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");



        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                filechooser();
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString().trim();
                String password=mpassword.getText().toString().trim();
                String name=mname.getText().toString().trim();
                String mobile=mphone.getText().toString().trim();
                String register=mregister.getText().toString().trim();

                member.setName(name);
                member.setMobile(mobile);
                member.setRegister(register);
                member.setEmail(email);


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
                if(TextUtils.isEmpty((name)))
                {
                    memail.setError("Name is invalid.");
                    return;
                }
                if(TextUtils.isEmpty((mobile)))
                {
                    memail.setError("Mobile is invalid.");
                    return;
                }
                if(TextUtils.isEmpty((register)))
                {
                    memail.setError("Registeration is invalid.");
                    return;
                }
                if(password.length()<6)
                {
                    mpassword.setError("Password Must be >= 6 characters");
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            fileuploader();
                            deff.push().setValue(member);
                            Toast.makeText(register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(register.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(register.this,"Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
     private String getExtension(Uri uri) {
         ContentResolver cr = getContentResolver();
         MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
         return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
     }

    private void fileuploader()
    {
        StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(register.this, "Image uploaded. ", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void filechooser()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            imageView.setImageURI(imguri);

        }
    }
}
