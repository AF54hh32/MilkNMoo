package com.example.navbar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;

public class SendRequest extends AsyncTask<String, Void, String> {
    String name, phone, email, address, cost1, cost2, cost3, cost4, total;

    public SendRequest(String name, String phone, String email, String address, String cost1, String cost2, String cost3, String cost4, String total) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cost1 = cost1;
        this.cost2 = cost2;
        this.cost3 = cost3;
        this.cost4 = cost4;
        this.total = total;
    }

    protected void onPreExecute() {
    }

    protected String doInBackground(String... arg0) {

        try {

            //Change your web app deployed URL or u can use this for attributes (name, country)

            java.net.URL url = new java.net.URL("https://docs.google.com/spreadsheets/d/1OXPMHZ_d8wxfM21TZODvTelS3j0DaQRjyM-VTqoW1qg/edit?usp=sharing");


            org.json.JSONObject postDataParams = new org.json.JSONObject();

            //int i;

            //for(i=1;i<=70;i++)

            //    String usn = Integer.toString(i);

            String id = "1OXPMHZ_d8wxfM21TZODvTelS3j0DaQRjyM-VTqoW1qg";

            postDataParams.put("name", name);
            postDataParams.put("phone", address);
            postDataParams.put("email", email);
            postDataParams.put("address", address);
            postDataParams.put("Buffalo_Milk", cost1);
            postDataParams.put("Cow_Milk", cost2);
            postDataParams.put("Dahi", cost3);
            postDataParams.put("Paneer", cost4);
            postDataParams.put("Amount", total);
            postDataParams.put("id", id);

            Log.e("params", postDataParams.toString());

            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000 /* milliseconds */);

            conn.setConnectTimeout(15000 /* milliseconds */);

            conn.setRequestMethod("POST");

            conn.setDoInput(true);

            conn.setDoOutput(true);

            java.io.OutputStream os = conn.getOutputStream();

            java.io.BufferedWriter writer = new java.io.BufferedWriter(

                    new java.io.OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));

            writer.flush();

            writer.close();

            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == javax.net.ssl.HttpsURLConnection.HTTP_OK) {

                java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");

                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);

                    break;

                }

                in.close();

                return sb.toString();

            } else {

                return new String("false : " + responseCode);

            }

        } catch (Exception e) {

            return new String("Exception: " + e.getMessage());

        }

    }

    @Override

    protected void onPostExecute(String result) {


    }


    public String getPostDataString(org.json.JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();

        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();

            Object value = params.get(key);

            if (first)

                first = false;

            else

                result.append("&");

            result.append(java.net.URLEncoder.encode(key, "UTF-8"));

            result.append("=");

            result.append(java.net.URLEncoder.encode(value.toString(), "UTF-8"));

        }
              return result.toString();
    }

}
