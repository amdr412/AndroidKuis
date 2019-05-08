package com.dev.rafii.androidkuis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataActivity extends AppCompatActivity {
    private Button buttonTambah, buttonKembali;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        buttonTambah = (Button) findViewById(R.id.btnAddDistributor);
        buttonKembali = (Button) findViewById(R.id.btnCancel3);
        listView = (ListView)findViewById(R.id.listView2);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iad = new Intent(DataActivity.this, AddDataActivity.class);
                finish();
                startActivity(iad);
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DataActivity.this, AddDataActivity.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String UserId = map.get("id").toString();
                intent.putExtra("distributor_id",UserId);
                startActivity(intent);
                finish();
            }
        });
        getJSON();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDistributor();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://192.168.43.4/sekolah/get_all_data.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showDistributor(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("id");
                String kode = jo.getString("kode");
                String nama = jo.getString("nama");
                String pemilik = jo.getString("pemilik");
                String alamat = jo.getString("alamat");

                HashMap<String,String> distributors = new HashMap();
                distributors.put("id", id);
                distributors.put("kode", kode);
                distributors.put("nama", nama);
                distributors.put("pemilik", pemilik);
                distributors.put("alamat", alamat);
                list.add(distributors);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DataActivity.this, list, R.layout.list_item,
                new String[]{"id","kode","nama","pemilik","alamat"},
                new int[]{R.id.id, R.id.kode, R.id.name, R.id.pemilik, R.id.alamat});
        listView.setAdapter(adapter);
    }
}
