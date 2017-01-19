package com.example.jaimin.loginapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Struct;

/**
 * Created by Jaimin on 5/17/2016.
 */
public class BorderWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String result="";
    String URL_str = "http://www.hunt4place.in/hunt4place/loginApp.php";
    BorderWorker(Context ct){
        context = ct.getApplicationContext();
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        if(type.equals("login")){
            String userName = params[1];
            String passWord = params[2];
            try {
                URL url = new URL(URL_str);
                System.out.println("type "+type+" user "+userName+" passs "+passWord  );
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("passWord","UTF-8")+"="+URLEncoder.encode(passWord,"UTF-8");
                System.out.println(post_data);
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                System.out.println(result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
//        alertDialog.setMessage(aVoid);
//        alertDialog.show();
        System.out.println(result);
        if(aVoid.equals("login Success!!!")){
            System.out.println("onpost after login success");
            Intent intent=new Intent("com.example.jaimin.loginapplication.User");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            System.out.println("onpost...............");
        }
    }


    @Override
    protected void onPreExecute() {
        System.out.println("onpreexecute");
 //       alertDialog = new AlertDialog.Builder(context).create();
 //       alertDialog.setTitle("Login Status");
 //       alertDialog.setMessage("checking status...");
 //       alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
 //       alertDialog.setMessage("Processing...");
 //       alertDialog.show();
    }
}
