package com.example.jaimin.loginapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText userName;
    String userNamestr;
    private EditText passWord;
    String passWordstr;
    private Button login;
    BorderWorker background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.loginUsername);
        passWord=(EditText)findViewById(R.id.loginPassword);
        background = new BorderWorker(this);

        submitEvent();
    }
    public void submitEvent(){
        login=(Button)findViewById(R.id.button);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = "login";
                        passWordstr = passWord.getText().toString();
                        userNamestr = userName.getText().toString();
                        System.out.println("pass..." + passWordstr);
                        background.execute(type, userNamestr, passWordstr);
                        System.out.println("background executing");

//                            Toast.makeText(Login.this,"you are logged in",Toast.LENGTH_SHORT).show();
                     //       Intent intent=new Intent("com.example.jaimin.loginapplication.LoginUser");
                      //                            startActivity(intent);
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
