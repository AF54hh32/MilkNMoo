package com.example.navbar;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class registeration_page extends AppCompatActivity {

    private EditText ad;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    FusedLocationProviderClient fusedLocationProviderClient;
    private EditText nam;
    private EditText mobile;
    private EditText emale;
    private EditText password;
    private Button sub;
    private ProgressBar progressBar;
    private Button verMobile;
    int flag=0;
    int c=0;
    EditText enterOtp;
    Button cont;
    private FirebaseAuth mAuth;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String address = "adress";
    public static final String Password="password";
    SharedPreferences shp;
    SharedPreferences shp2;
    String em="email";
    String ps="p";
    String verificationId;
    private FirebaseOptions options;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_page);
        ad = findViewById(R.id.adress);
        nam = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        emale = findViewById(R.id.email);
        password=findViewById(R.id.pass);
        sub = findViewById(R.id.submit);
        progressBar=findViewById(R.id.prog);
        cont=findViewById(R.id.cont);
        enterOtp=findViewById(R.id.enterOtp);
        verMobile=findViewById(R.id.verMobile);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mAuth = FirebaseAuth.getInstance();
       // password.setText("");






        verMobile.setOnClickListener(new View.OnClickListener() {
           /* @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(registeration_page.this,new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                              if(task.isSuccessful()) {
                                  Toast.makeText(registeration_page.this, "Email verification link has been sent", Toast.LENGTH_LONG).show();
                              }
                              else
                              {
                                  Toast.makeText(registeration_page.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                              }

                            }
                        });
            }*/
           public void onClick(View view) {

               if(TextUtils.isEmpty(mobile.getText().toString())) {

                   mobile.setError("Enter a valid phone number");
                   return;
               }
                  String phoneNum = "+91" + mobile.getText().toString();
                   verifyPhoneNumber(phoneNum);

           }
           });


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(enterOtp.getText().toString())){
                    enterOtp.setError("Enter Otp First");
                    return;
                }
               else
               {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, enterOtp.getText().toString());
                    authenticateUser(credential);
                }
            }
        });





        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check permission
                if (ActivityCompat.checkSelfPermission(registeration_page.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    getLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(registeration_page.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        shp2=getSharedPreferences("credentia",MODE_PRIVATE);

        shp=getSharedPreferences("cred",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= shp.edit();
        editor.putString("adress","");
        editor.commit();

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s,forceResendingToken);

                token=forceResendingToken;
                verificationId=s;
                verMobile.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.VISIBLE);
                enterOtp.setVisibility(View.VISIBLE);

            }


            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //    authenticateUser(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(registeration_page.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }



            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };










        sub.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                String e = emale.getText().toString();
                String p = password.getText().toString();
                FirebaseUser use = mAuth.getCurrentUser();



                if(flag==1)
                {
                    mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                if(mAuth.getCurrentUser().isEmailVerified()) {

                                    if(c==0)
                                    {
                                          Toast.makeText(registeration_page.this,"Please verify Your Mobile Number",Toast.LENGTH_SHORT).show();
                                          return;
                                    }
                                    else
                                    {
                                        String j=emale.getText().toString().replace(".","");
                                        String i = mAuth.getCurrentUser().getUid();
                                        DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
                                        DatabaseReference db = dataBase.child(j);
                                        db.child("Name").setValue(nam.getText().toString());
                                        db.child("Password").setValue(password.getText().toString());
                                        db.child("Address").setValue(ad.getText().toString());
                                        db.child("Email").setValue(emale.getText().toString());
                                        db.child("Phone Number").setValue(mobile.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    //Toast.makeText(registeration_page.this,"Registeration Successful!",Toast.LENGTH_LONG).show();


                                                } else {

                                                }
                                            }
                                        });

                                        Intent inn = new Intent(registeration_page.this, login_signup.class);
                                        finish();
                                        startActivity(inn);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(registeration_page.this,"Please check your email for verification link",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }



                authenticate(use);
                        //  if(use!=null) {
                //    Intent in=new Intent(registeration_page.this,login_signup.class);
                //    startActivity(in);
                // }
               /* shp = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                String n = nam.getText().toString();
                String m = mobile.getText().toString();
                String e = emale.getText().toString();
                String a = ad.getText().toString();
                String p =password.getText().toString();

                SharedPreferences.Editor editor = shp.edit();

                editor.putString(Name,n);
                editor.putString(Phone,m);
                editor.putString(Email,e);
                editor.putString(address,a);
                editor.putString(Password,p);
                editor.commit();
*/


            }
            });
           // public void onStart()
           // {
               // if(mAuth.getCurrentUser()!=null)
               // {

             //   }
           // }



    }





  /*      SharedPreferences getShared=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        String val1=getShared.getString(Name," ");
        String val2=getShared.getString(Phone," ");
        String val3=getShared.getString(Email," ");
        String val4=getShared.getString(address," ");
        String val5=getShared.getString(Password," ");
        nam.setText(val1);
        mobile.setText(val2);
        emale.setText(val3);
        ad.setText(val4);
        password.setText(val5);

*/




    public void authenticate(FirebaseUser use) {
/*
        mAuth.fetchSignInMethodsForEmail(e).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check=!task.getResult().getSignInMethods().isEmpty();
                if(check){
                    AuthCredential creden = EmailAuthProvider.getCredential(em,ps);
                    use.reauthenticate(creden).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                use.delete();
                            }
                        }
                    });
                }

            }
        });
*/

mAuth.createUserWithEmailAndPassword(emale.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.VISIBLE);

                if (task.isSuccessful()) {
                    //we will store the fields in firebase database

                    flag=1;

                    use.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Intent in=new Intent(registeration_page.this,login_signup.class);
                                //startActivity(in);

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(registeration_page.this, "Email verification link is mailed to you", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(registeration_page.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }






              /*  else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                  if(flag==1)
                  {
                    if(mAuth.getCurrentUser().isEmailVerified())
                    {

                        Intent inn = new Intent(registeration_page.this, login_signup.class);
                        finish();
                        startActivity(inn);
                    }
                    else
                    {
                        Toast.makeText(registeration_page.this, "Check your mail id for verification link", Toast.LENGTH_LONG).show();
                    }
                }
                  else
                  {
                      if(!mAuth.getCurrentUser().isEmailVerified())
                      {
                          use.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                  if (task.isSuccessful()) {
                                      //Intent in=new Intent(registeration_page.this,login_signup.class);
                                      //startActivity(in);

                                      progressBar.setVisibility(View.INVISIBLE);
                                      Toast.makeText(registeration_page.this, "Email verification link is mailed to you", Toast.LENGTH_LONG).show();
                                  } else {
                                      Toast.makeText(registeration_page.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                  }
                              }
                          });
                      }
                      else
                      {
                          Intent inn = new Intent(registeration_page.this, login_signup.class);
                          finish();
                          startActivity(inn);
                      }
                     }
                  }*/

            }
        });

    }

    public void authenticateUser(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
               // Toast.makeText(registeration_page.this, "Success", Toast.LENGTH_LONG).show();
                c=1;
                cont.setVisibility(View.GONE);
                enterOtp.setVisibility(View.GONE);
                verMobile.setVisibility(View.VISIBLE);
                verMobile.setEnabled(false);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registeration_page.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void verifyPhoneNumber(String phoneNum) {

        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth).setActivity(this).setPhoneNumber(phoneNum).setTimeout(60L, TimeUnit.SECONDS).setCallbacks(callbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }




    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(registeration_page.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        //Set Latitute on EditText
                        ad.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b><br></font>" + addresses.get(0).getLatitude()


                        ));

                        //Set Longitude on EditText
                        ad.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude:</b><br></font>" + addresses.get(0).getLongitude()


                        ));
                        //Set country name

                        ad.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name:</b><br></font>" + addresses.get(0).getCountryName()


                        ));

                        //Set Locality
                        ad.setText(Html.fromHtml("<font color='#6200EE'><b>Locality:</b><br></font>" + addresses.get(0).getLocality()

                        ));

                        //Set address
                        ad.setText(Html.fromHtml(addresses.get(0).getAddressLine(0)

                        ));


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        });
    }

}
