package com.example.jaimin.loginapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class User extends AppCompatActivity {
    private ListView listView;
    private String[] names={"jimmy","dhrumil","het","choksi","palak","aalo","bhavik","mislo"};
    private Button list ;
    String TAG_NAME = "name";
    String TAG_PHNO = "phno";
    ArrayList<HashMap<String,String>> contactsList = new ArrayList<HashMap<String,String>>();
//    int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        listView=(ListView)findViewById(R.id.listView);
        list = (Button)findViewById(R.id.button2);
        list.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent("com.example.jaimin.loginapplication.FileExplorer");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        //LoadContactsAyscn lca = new LoadContactsAyscn();
                        //lca.execute();
                    }
                }
        );
    }
    public void listViewEx(){
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value = (String) listView.getItemAtPosition(position);
                        Toast.makeText(User.this, "position " + position + " value: " + value, Toast.LENGTH_SHORT).show();
                    }
                }

        );
        listView.setEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3 ;
        private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 4;
        ProgressDialog pd;
        String phoneNumber;
        HashMap<String,String> phoneBook;
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

//        pd = ProgressDialog.show(User.this, "Loading Contacts", "Please Wait");
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        // TODO Auto-generated method stub

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else {
            // Android version is lesser than 6.0 or the permission is already granted.
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            phoneNumber = tm.getLine1Number();
            phoneBook = new HashMap<>();
            Cursor c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            while (c.moveToNext()) {

                String contactName = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println(contactName + ":" + phNumber);
                HashMap<String,String> contacts = new HashMap<String,String>();
                contacts.put(TAG_NAME,contactName);
                contacts.put(TAG_PHNO,phNumber);
                phoneBook.put(contactName,phNumber);
                contactsList.add(contacts);

            }
            c.close();

        }
        return contactsList;
    }


        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    phoneNumber = tm.getLine1Number();
                    phoneBook = new HashMap<>();
                    System.out.println("Permission grnated...");
                    // Permission is granted
                    // Android version is lesser than 6.0 or the permission is already granted.
                    Cursor c = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            null, null, null);
                    while (c.moveToNext()) {

                        String contactName = c
                                .getString(c
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phNumber = c
                                .getString(c
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println(contactName + ":" + phNumber);
                        HashMap<String,String> contacts = new HashMap<String,String>();
                        contacts.put(TAG_NAME,contactName);
                        contacts.put(TAG_PHNO, phNumber);
                        phoneBook.put(contactName,phNumber);
                        contactsList.add(contacts);

                    }
                    c.close();

                } else {
                    Toast.makeText(User.this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
                }
            }
            if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    phoneNumber = tm.getLine1Number();
                    phoneBook = new HashMap<>();
                    System.out.println("Permission grnated...");
                    // Permission is granted
                    // Android version is lesser than 6.0 or the permission is already granted.
                    Cursor c = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            null, null, null);
                    while (c.moveToNext()) {

                        String contactName = c
                                .getString(c
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phNumber = c
                                .getString(c
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println(contactName + ":" + phNumber);
                        HashMap<String,String> contacts = new HashMap<String,String>();
                        contacts.put(TAG_NAME,contactName);
                        contacts.put(TAG_PHNO, phNumber);
                        phoneBook.put(contactName,phNumber);
                        contactsList.add(contacts);

                    }
                    c.close();

                } else {
                    Toast.makeText(User.this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
                }
            }
        }

    @Override
    protected void onPostExecute(ArrayList contacts) {
        // TODO Auto-generated method stub
        super.onPostExecute(contacts);
        ArrayList<String> contact = new ArrayList<String>();
        HashMap<String,String> fetchContact = new HashMap<String,String>();
        Iterator<HashMap<String,String>> iterator = contacts.iterator();
        int i=0;
        while(iterator.hasNext()){
            fetchContact = (HashMap<String,String>) iterator.next();
            String phone =(String) fetchContact.get(TAG_NAME);
            contact.add(phone);
        }
//        pd.cancel();

        //listView.removeView(list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(User.this, R.layout.message_list, contact);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String value=(String)listView.getItemAtPosition(position);
                        String l = "704815095";
                        System.out.println(value);
                        String no = phoneBook.get(value);
                        Intent intent=new Intent("com.example.jaimin.loginapplication.chatList");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Myphone",phoneNumber);
                        intent.putExtra("phone",no);
                        startActivity(intent);

                    }
                }
        );
        listView.setEnabled(true);

    }

    }
}
