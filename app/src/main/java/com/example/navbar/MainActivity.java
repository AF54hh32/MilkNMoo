package com.example.navbar;


import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static com.example.navbar.registeration_page.MyPREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Shader;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private Toolbar tool;
    private ImageView im;
    public TextView loc;
    Button makepay;
    FirebaseAuth pauth;
    FusedLocationProviderClient fusedLocationProviderClient;
    String c;
    SharedPreferences sh;
    SharedPreferences shp;
    String j;
    Button button;
    Button button2;
    Button button3;
    Button button4;
    Button plus;
    Button plus2;
    Button plus3;
    Button plus4;
    Button minus;
    Button minus2;
    Button minus3;
    Button minus4;
    String val1;
    TextView val;
    TextView val2;
    TextView val3;
    TextView val4;
    String val5;
    TextView prodCost;
    TextView prodCost2;
    TextView prodCost3;
    TextView prodCost4;
    float quant= 0.5F;
    float quant2= 0.5F;
    float quant3= 0.5F;
    float quant4= 0.5F;
    float mult=0;
    float mult2=0;
    float mult3=0;
    float mult4=0;
    float res1=0.0F;
    float res2=0.0F;
    float res3=0.0F;
    float res4=0.0F;
    float total;
    float sum;
    SharedPreferences sh2;
    String number;
    String name1;
    String adress;
    Double payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<imageSliderModel> imageSliderModelList;
        imageSliderModelList = new ArrayList<>();

        SliderView sliderView = findViewById(R.id.imageSlider);
        imageSliderModelList.add(new imageSliderModel(R.drawable.buffmilk));
        imageSliderModelList.add(new imageSliderModel(R.drawable.cow_milk));
        imageSliderModelList.add(new imageSliderModel(R.drawable.dahi));
        imageSliderModelList.add(new imageSliderModel(R.drawable.download));
        sliderView.setSliderAdapter(new SliderAdapterExample(this, imageSliderModelList));
        sliderView.setAutoCycle(true);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        sh2 = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        val1 = sh2.getString("email", "");
        // ViewPager viewPager=findViewById(R.id.viewPager);

        //  ImageAdapter imageAdapter=new ImageAdapter(this);
        // viewPager.setAdapter(imageAdapter);
        button = findViewById(R.id.button);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        val = findViewById(R.id.val);
        prodCost = findViewById(R.id.finalCost);
        makepay = findViewById(R.id.makePayment);
        button2 = findViewById(R.id.button2);
        plus2 = findViewById(R.id.plus2);
        minus2 = findViewById(R.id.minus2);
        val2 = findViewById(R.id.val2);
        prodCost2 = findViewById(R.id.finalCost2);
        button3 = findViewById(R.id.button3);
        plus3 = findViewById(R.id.plus3);
        minus3 = findViewById(R.id.minus3);
        val3 = findViewById(R.id.val3);
        prodCost3 = findViewById(R.id.finalCost3);
        button4 = findViewById(R.id.button4);
        plus4 = findViewById(R.id.plus4);
        minus4 = findViewById(R.id.minus4);
        val4 = findViewById(R.id.val4);
        prodCost4 = findViewById(R.id.finalCost4);
        pauth = FirebaseAuth.getInstance();
        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        tool = findViewById(R.id.tooltip);
        loc = findViewById(R.id.locate);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle(null);
        dl.addDrawerListener(t);
        t.syncState();
        im = findViewById(R.id.profile);

        if(val1!="")
      {
        val5 = val1.replace(".", "");

            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(val5);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                number = snapshot.child("Phone Number").getValue().toString();
                name1=snapshot.child("Name").getValue().toString();
                adress=snapshot.child("Address").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val1=="")
                {
                 Toast.makeText(MainActivity.this,"Please Login In to make purchases",Toast.LENGTH_LONG).show();
                 return;
                }
                mult=(float)(quant*(65.0));
                val.setText(Float.toString(quant));
                res1=mult;
                prodCost.setText("\u20B9"+Float.toString(mult));
                button.setVisibility(View.INVISIBLE);
                plus.setVisibility(View.VISIBLE);
                minus.setVisibility(View.VISIBLE);
                val.setVisibility(View.VISIBLE);

            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val1=="")
                {
                    Toast.makeText(MainActivity.this,"Please Login In to make purchases",Toast.LENGTH_LONG).show();
                    return;
                }
                mult2=(float)(quant2*(50.0));
                val2.setText(Float.toString(quant2));
                res2=mult2;
                prodCost2.setText("\u20B9"+Float.toString(mult2));
                button2.setVisibility(View.INVISIBLE);
                plus2.setVisibility(View.VISIBLE);
                minus2.setVisibility(View.VISIBLE);
                val2.setVisibility(View.VISIBLE);

            }
        });



        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val1=="")
                {
                    Toast.makeText(MainActivity.this,"Please Login In to make purchases",Toast.LENGTH_LONG).show();
                    return;
                }
                mult3=(float)(quant3*(25.0));
                val3.setText(Float.toString(quant3));
                res3=mult3;
                prodCost3.setText("\u20B9"+Float.toString(mult3));
                button3.setVisibility(View.INVISIBLE);
                plus3.setVisibility(View.VISIBLE);
                minus3.setVisibility(View.VISIBLE);
                val3.setVisibility(View.VISIBLE);

            }
        });



        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val1=="")
                {
                    Toast.makeText(MainActivity.this,"Please Login In to make purchases",Toast.LENGTH_LONG).show();
                    return;
                }
                mult4=(float)(quant4*(360.0));
                val4.setText(Float.toString(quant4));
                res4=mult4;
                prodCost4.setText("\u20B9"+Float.toString(mult4));
                button4.setVisibility(View.INVISIBLE);
                plus4.setVisibility(View.VISIBLE);
                minus4.setVisibility(View.VISIBLE);
                val4.setVisibility(View.VISIBLE);

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant=(float)(quant+0.5);
                mult=(float)(quant*65.0);
                val.setText(Float.toString(quant));
                res1=mult;
                prodCost.setText("\u20B9"+Float.toString(mult));
            }
        });
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant2=(float)(quant2+0.5);
                mult2=(float)(quant2*50.0);
                val2.setText(Float.toString(quant2));
                res2=mult2;
                prodCost2.setText("\u20B9"+Float.toString(mult2));
            }
        });
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant3=(float)(quant3+0.5);
                mult3=(float)(quant3*25.0);
                val3.setText(Float.toString(quant3));
                res3=mult3;
                prodCost3.setText("\u20B9"+Float.toString(mult3));
            }
        });
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant4=(float)(quant4+0.5);
                mult4=(float)(quant4*360.0);
                res4=mult4;
                val4.setText(Float.toString(quant4));
                prodCost4.setText("\u20B9"+Float.toString(mult4));
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant = (float) (quant - 0.5);
                if(quant>=0.5)
                {
                    val.setText(Float.toString(quant));
                    mult=(float)(quant*65.0);
                    res1=mult;
                    prodCost.setText("\u20B9"+Float.toString(mult));
                }
                else
                {
                    button.setVisibility(View.VISIBLE);
                    quant= 0.5F;
                    res1=0.0F;
                    prodCost.setText("");
                    plus.setVisibility(View.INVISIBLE);
                    minus.setVisibility(View.INVISIBLE);
                    val.setVisibility(View.INVISIBLE);
                }
            }
        });

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant2 = (float) (quant2 - 0.5);
                if(quant2>=0.5)
                {
                    val2.setText(Float.toString(quant2));
                    mult2=(float)(quant2*50.0);
                    res2=mult2;
                    prodCost2.setText("\u20B9"+Float.toString(mult2));
                }
                else
                {
                    button2.setVisibility(View.VISIBLE);
                    quant2= 0.5F;
                    res2=0.0F;
                    prodCost2.setText("");
                    plus2.setVisibility(View.INVISIBLE);
                    minus2.setVisibility(View.INVISIBLE);
                    val2.setVisibility(View.INVISIBLE);
                }
            }
        });

        minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant3 = (float) (quant3 - 0.5);
                if(quant3>=0.5)
                {
                    val3.setText(Float.toString(quant3));
                    mult3=(float)(quant3*25.0);
                    res3=mult3;
                    prodCost3.setText("\u20B9"+Float.toString(mult3));
                }
                else
                {
                    button3.setVisibility(View.VISIBLE);
                    quant3= 0.5F;
                    res3=0.0F;
                    prodCost3.setText("");
                    plus3.setVisibility(View.INVISIBLE);
                    minus3.setVisibility(View.INVISIBLE);
                    val3.setVisibility(View.INVISIBLE);
                }
            }
        });

        minus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant4= (float) (quant4 - 0.5);
                if(quant4>=0.5)
                {
                    val4.setText(Float.toString(quant4));
                    mult4=(float)(quant4*360.0);
                    res4=mult4;
                    prodCost4.setText("\u20B9"+Float.toString(mult4));
                }
                else
                {
                    button4.setVisibility(View.VISIBLE);
                    quant4= 0.5F;
                    res4=0.0F;
                    prodCost4.setText("");
                    plus4.setVisibility(View.INVISIBLE);
                    minus4.setVisibility(View.INVISIBLE);
                    val4.setVisibility(View.INVISIBLE);
                }
            }
        });

        makepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();

            }
        });



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
       /* shp=getSharedPreferences("cred",Context.MODE_PRIVATE);
        j=shp.getString("adress","");
        tool.setTitle(j);*/
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
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
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            //Set Latitute on EditText
                            tool.setTitle(Html.fromHtml("<font color='#6200EE'><b>Latitude:</b><br></font>" + addresses.get(0).getLatitude()


                            ));

                            //Set Longitude on EditText
                            tool.setTitle(Html.fromHtml("<font color='#6200EE'><b>Longitude:</b><br></font>" + addresses.get(0).getLongitude()


                            ));
                            //Set country name

                            tool.setTitle(Html.fromHtml("<font color='#6200EE'><b>Country Name:</b><br></font>" + addresses.get(0).getCountryName()


                            ));

                            //Set Locality
                            tool.setTitle(Html.fromHtml("<font color='#6200EE'><b>Locality:</b><br></font>" + addresses.get(0).getLocality()

                            ));

                            //Set address
                            tool.setTitle(Html.fromHtml(addresses.get(0).getAddressLine(0)

                            ));


                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                }
            });

        } else {
            //When permission denied
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        im.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                          sh=getSharedPreferences("credentials",MODE_PRIVATE);
                                          c=sh.getString("email","");
                                          if(c!="")
                                          {
                                          Intent userpro = new Intent(MainActivity.this, credentials.class);
                                          startActivity(userpro);
                                         }
                                         else
                                        {
                                          finish();
                                          Intent intent = new Intent(MainActivity.this, login_signup.class);
                                          startActivity(intent);
                                        }

                                  }


                              });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }




            }
        });


    }


    public void startPayment()
    {
        final Activity activity=this;
        final Checkout co=new Checkout();
             sum=(res1+res2+res3+res4);
             total=sum*100;





        try {

            JSONObject options=new JSONObject();
            options.put("name","RazorPay Corp");
            options.put("description","Demoing Charges");
            options.put("currency","INR");

             payment=Double.parseDouble(String.valueOf(total));

            options.put("amount",payment);


            JSONObject prefill=new JSONObject();
            prefill.put("email",val1);
            prefill.put("contact",number);
            options.put("prefill",prefill);

            co.open(activity,options);


        }catch (Exception e){

        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String s) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzhF9c-0hFk6-qhmtgMNvdu2SKOd0LFQaws4eSCOq46fptRjXFhwcrBM4vcCr7wX1FP1Q/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String,String> getParams() {
               Map<String,String> params=new HashMap<>();
               params.put("action","addData");
                params.put("vName",name1);
                params.put("vPhone",number);
                params.put("vAddress",adress);
                params.put("vEmail",val1);
                params.put("BuffaloMilk",prodCost.getText().toString());
                params.put("CowMilk",prodCost2.getText().toString());
                params.put("Dahi",prodCost3.getText().toString());
                params.put("Paneer",prodCost4.getText().toString());
                params.put("Amount",Float.toString(sum));

                return params;
            }
    };
        int socketTimeOut=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        Toast.makeText(this,"Payment successfull!" + s,Toast.LENGTH_LONG).show();

 //  new SendRequest(name1,number,val1,adress,prodCost.getText().toString(),prodCost2.getText().toString(),prodCost3.getText().toString(),prodCost4.getText().toString(),String.valueOf(total));

    }
    @Override
    public void onPaymentError(int i, String s) {

    }
}
