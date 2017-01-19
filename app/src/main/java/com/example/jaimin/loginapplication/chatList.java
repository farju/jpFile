package com.example.jaimin.loginapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shaded.apache.http.HttpEntity;
import org.shaded.apache.http.HttpResponse;
import org.shaded.apache.http.NameValuePair;
import org.shaded.apache.http.client.HttpClient;
import org.shaded.apache.http.client.methods.HttpPost;
import org.shaded.apache.http.impl.client.DefaultHttpClient;
import org.shaded.apache.http.message.BasicNameValuePair;
import org.shaded.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class chatList extends AppCompatActivity {

    JSONParser jParser;
    String URL_TO_PHP = "http://www.hunt4place.in/hunt4place/message.php";
    String URL_TO_PHP_SENDMSG = "http://www.hunt4place.in/hunt4place/sendmessage.php";
    String TAG_SUCCESS = "success";
    String TAG_STUFF = "stuff";
    String TAG_MSGID = "msgid";
    String TAG_SID = "sid";
    String TAG_RID = "rid";
    String TAG_MSG = "msg";
    String TAG_SDATE = "sdate";
    String TAG_RDATE = "rdate";
    String TAG_MSSTATUS = "mstatus";
    ArrayList<HashMap<String, String>> msgList = new ArrayList<HashMap<String, String>>();
    ArrayList<String> showMsg = new ArrayList<>();
    Intent intent;
    ListView listView;
    String opPhone;
    String myPhone;
    EditText sendMsg;
    Button sendBtn;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        listView=(ListView)findViewById(R.id.listView3);
        sendMsg = (EditText)findViewById(R.id.editText2);
        sendBtn = (Button)findViewById(R.id.button3);
//        new testing().execute();
        Intent intent = this.getIntent();
        opPhone = intent.getStringExtra("phone");
        myPhone = intent.getStringExtra("Myphone");
        System.out.println(myPhone + " myPhone ...........");
        new testing().execute("http://www.hunt4place.in/hunt4place/message.php");
        sendBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        msg = sendMsg.getText().toString();
                        if(!msg.equals(null)){
                            new sendMsg().execute("http://www.hunt4place.in/hunt4place/sendmessage.php");
                        }
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_list, menu);
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

    private class testing extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            jParser = new JSONParser();
            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("myPhone", myPhone));
            params.add(new BasicNameValuePair("opPhone", opPhone));
            /* getting JSON string from URL */

            JSONObject json = jParser.makeHttpRequest(URL_TO_PHP, "POST", params);

            try {
                /* Checking for SUCCESS TAG */
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray JAStuff = json.getJSONArray(TAG_STUFF);

                    /** CHECK THE NUMBER OF RECORDS **/
                    int intStuff = JAStuff.length();
                    System.out.println(intStuff+"      Status");
                    if (intStuff != 0) {

                        for (int i = 0; i < JAStuff.length(); i++) {
                            JSONObject JOStuff = JAStuff.getJSONObject(i);
                            String msgid = JOStuff.getString(TAG_MSGID);
                            String sid = JOStuff.getString(TAG_SID);
                            String rid = JOStuff.getString(TAG_RID);
                            String messasge = JOStuff.getString(TAG_MSG);
                            String sdate = JOStuff.getString(TAG_SDATE);
                            String rdate = JOStuff.getString(TAG_RDATE);
                            String mstatus = JOStuff.getString(TAG_MSSTATUS);

                            HashMap<String,String> msg = new HashMap<String,String>();

                            msg.put(TAG_MSGID,msgid);
                            msg.put(TAG_SID,sid);
                            msg.put(TAG_RID,rid);
                            msg.put(TAG_MSG,messasge);
                            msg.put(TAG_SDATE,sdate);
                            msg.put(TAG_RDATE,rdate);
                            msg.put(TAG_MSSTATUS,mstatus);
                            msgList.add(msg);
                            showMsg.add(messasge);
                        }
                    }
                    else{
                        System.out.println("No chat");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(chatList.this, R.layout.message_list, msgList);
            ArrayAdapter<String> adapterMsg = new ArrayAdapter<String>(chatList.this, R.layout.message_list, showMsg);
            ChatListAdapter chatListAdapter = new ChatListAdapter(chatList.this,R.layout.activity_chat_list,msgList);
            listView.setAdapter(adapterMsg);
            listView.setEnabled(true);


        }
    }
    private  class ChatListAdapter extends ArrayAdapter<HashMap<String, String>> {
        Context context;
        int resourceId;
        ArrayList<HashMap<String,String>> data;
        LinearLayout linearLayout;
        public ChatListAdapter(Context context, int resource, ArrayList<HashMap<String,String>> objects) {
            super(context, resource, objects);
            this.context=context;
            this.resourceId=resource;
            this.data = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(resourceId, parent, false);
                linearLayout = (LinearLayout)row.findViewById(R.id.lineraMain);
                HashMap<String,String> cmsg = data.get(position);
                String sid = cmsg.get(TAG_SID);
                TextView label = new TextView(context);
                System.out.println(cmsg.get(TAG_MSG));
                label.setText(cmsg.get(TAG_MSG));
                if(sid.equals(myPhone)){
                    label.setTextColor(Color.parseColor("#003366"));
                }
                linearLayout.addView(label);
            }

            return row;

        }

    }

    private class sendMsg extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            jParser = new JSONParser();
            /* Building Parameters */
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sid", myPhone));
            params.add(new BasicNameValuePair("rid", opPhone));
            params.add(new BasicNameValuePair("msg", msg));
            /* getting JSON string from URL */
            try {
                JSONObject json = jParser.makeHttpRequest(URL_TO_PHP_SENDMSG, "POST", params);
                Log.d("Create Response", json.toString());
                return null;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showMsg.add(msg);
            //ArrayAdapter<HashMap<String, String>> adapter = new ArrayAdapter<HashMap<String, String>>(chatList.this, R.layout.message_list, msgList);
            ArrayAdapter<String> adapterMsg = new ArrayAdapter<String>(chatList.this, R.layout.message_list, showMsg);

            listView.setAdapter(adapterMsg);
            listView.setEnabled(true);


        }
    }
    class TheTask extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // update textview here
            //textView.setText("Server message is " + result);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost method = new HttpPost(params[0]);
                HttpResponse response = httpclient.execute(method);
                HttpEntity entity = response.getEntity();
                System.out.println(entity);
                System.out.println(EntityUtils.toString(entity));
                if(entity != null){
                    return EntityUtils.toString(entity);
                }
                else{
                    return "No string.";
                }
            }
            catch(Exception e){
                return "Network problem";
            }

        }
    }
}
