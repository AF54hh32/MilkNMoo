package com.example.navbar;

import static com.example.navbar.registeration_page.MyPREFERENCES;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class login_signup extends AppCompatActivity {
      Button sign;
      Button login;
      FirebaseAuth fauth;
      EditText emle;
      EditText pass;
      EditText nm;
      SharedPreferences sh;
      String val4;
      TextView reset;
      DatabaseReference fd;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);
        sign=findViewById(R.id.sign);
        login=findViewById(R.id.log);
        emle=findViewById(R.id.EmailAddress);
        pass=findViewById(R.id.Password);
        nm=findViewById(R.id.user);
        reset=findViewById(R.id.reset);
        fauth=FirebaseAuth.getInstance();

          sh=getSharedPreferences("credentials",Context.MODE_PRIVATE);
          SharedPreferences.Editor editor=sh.edit();
          editor.putString("email",emle.getText().toString());
          editor.commit();

          reset.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  EditText resetMail=new EditText(view.getContext());
                  AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(view.getContext());
                  passwordResetDialog.setTitle("Reset Password ?");
                  passwordResetDialog.setMessage("Enter Your Email To receive reset link");
                  passwordResetDialog.setView(resetMail);

                  passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          //extract email and send reset link

                        String mail=resetMail.getText().toString();



                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(login_signup.this, "Reset Link Send to Your Email", Toast.LENGTH_LONG).show();

                            }



                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(login_signup.this, "Error ! Reset Link is Not Sent " +e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });



                      }
                  });

                  passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          //close the dialog

                      }
                  });
                     passwordResetDialog.create().show();

              }
          });





          login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fauth.signInWithEmailAndPassword(emle.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           if(fauth.getCurrentUser().isEmailVerified()){


                               editor.clear();
                               editor.putString("name",nm.getText().toString());
                               editor.putString("email",emle.getText().toString());
                               editor.putString("passcode",pass.getText().toString());
                               editor.commit();
                               val4=sh.getString("email","");
                               emle.setText(val4);
                               updatePassword(fd);
                               Intent inn=new Intent(login_signup.this,MainActivity.class);
                               finish();
                               startActivity(inn);
                             }
                           else
                            {
                               Toast.makeText(login_signup.this, "Please check your Email ID for verification link", Toast.LENGTH_LONG).show();
                             }
                           }
                       else
                       {
                           Toast.makeText(login_signup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                           emle.setText("");
                           pass.setText("");
                           nm.setText("");

                       }
                    }
                });

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login_signup.this,registeration_page.class);
                startActivity(intent);
            }
        });





    }


    public void updatePassword(DatabaseReference fd)
    {

       fd= FirebaseDatabase.getInstance().getReference().child(emle.getText().toString().replace(".",""));
        fd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String password=snapshot.child("Password").getValue().toString();
                if(password!=pass.getText().toString())
                {
                  DatabaseReference ft= FirebaseDatabase.getInstance().getReference().child(emle.getText().toString().replace(".",""));
                    ft.child("Password").setValue(pass.getText().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
