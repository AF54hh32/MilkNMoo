package com.example.navbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class credentials extends AppCompatActivity {
EditText Name,Email,Password,Address;
Button Logout;
SharedPreferences sh;
String val1,val3;

    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credentials);
              Name=findViewById(R.id.pname);
              Email=findViewById(R.id.pemail);
              Password=findViewById(R.id.ppassword);
              Address=findViewById(R.id.address);
              Logout=findViewById(R.id.log);
              sh=getSharedPreferences("credentials", Context.MODE_PRIVATE);
             /* val1=sh.getString("name","");
              val2=sh.getString("email","");
              val3=sh.getString("passcode","");

              Name.setText(val1);
              Email.setText(val2);
              Password.setText(val3);
*/
        val1=sh.getString("email","");
        String val2=val1.replace(".","");

            DatabaseReference db=FirebaseDatabase.getInstance().getReference().child(val2);
              db.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      String name=snapshot.child("Name").getValue().toString();
                      String email=snapshot.child("Email").getValue().toString();
                      String password=snapshot.child("Password").getValue().toString();
                      String address=snapshot.child("Address").getValue().toString();
                      Name.setText(name);
                      Email.setText(email);
                      Password.setText(password);
                      Address.setText(address);
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
       /* shp=getSharedPreferences("cred",Context.MODE_PRIVATE);
        j=shp.getString("adress","");
        tool.setTitle(j);*/
   //     if (ActivityCompat.checkSelfPermission(credentials.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
     //       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
       //         // TODO: Consider calling
         //       //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
           //     return;
          //  }
  /*          fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(credentials.this, Locale.getDefault());
                        try {
                            List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            //Set Latitute on EditText
                            Address.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b><br></font>" + addresses.get(0).getLatitude()


                            ));

                            //Set Longitude on EditText
                            Address.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude:</b><br></font>" + addresses.get(0).getLongitude()


                            ));
                            //Set country name

                            Address.setText(Html.fromHtml("<font color='#6200EE'><b>Country Name:</b><br></font>" + addresses.get(0).getCountryName()


                            ));

                            //Set Locality
                            Address.setText(Html.fromHtml("<font color='#6200EE'><b>Locality:</b><br></font>" + addresses.get(0).getLocality()

                            ));

                            //Set address
                            Address.setText(Html.fromHtml(addresses.get(0).getAddressLine(0)

                            ));


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                }
            });

        } else {
            //When permission denied
            ActivityCompat.requestPermissions(credentials.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
*/
        Logout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Name.setText("");
                      Email.setText("");
                      Password.setText("");
                      SharedPreferences.Editor editor=sh.edit();
                      editor.clear();
                      editor.putString("email","");
                      editor.commit();
                      finish();
                      Intent iii=new Intent(credentials.this,MainActivity.class);
                      startActivity(iii);
                  }
              });
   }


}