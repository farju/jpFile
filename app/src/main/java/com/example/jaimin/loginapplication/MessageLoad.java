package com.example.jaimin.loginapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MessageLoad extends AppCompatActivity {
    private ListView listView;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_load);
        Intent intent = this.getIntent();
        phone = intent.getStringExtra("phone");
        System.out.println(phone);
        getMessage();
        new testing().execute();
    }
    public void getMessage(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_load, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class testing extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("sjfdjgdkfjg");
            try{
//step1 load the driver class
                Class.forName("oracle.jdbc.driver.OracleDriver");
                System.out.println("sjfdjgdkfjg2");

//step2 create  the connection object
                Connection con=DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe","system","jimmy3010");
                if(con==null) {
                    System.out.println("sjfdjgdkfjg3");
                }
                else{
                    System.out.println("sjfdjgdkfjg4");
                }
//step3 create the statement object
                Statement stmt=con.createStatement();
                System.out.println("sjfdjgdkfjg");
//step4 execute query
                ResultSet rs=stmt.executeQuery("select * from test");
                while(rs.next())
                    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3));

//step5 close the connection object
                con.close();

            }catch(Exception e){
                System.out.println("sjfdjgdkfjg");
                System.out.println(e);}


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}
