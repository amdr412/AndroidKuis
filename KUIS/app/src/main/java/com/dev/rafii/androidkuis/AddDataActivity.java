package com.dev.rafii.androidkuis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddDataActivity extends AppCompatActivity{
    private EditText editTextNama, editTextAlamat, editTextKota, editTextTelp;
    private Button btnTambah, btnKembali;
    private static final String ADD_DISTRIBUTOR_URL = "http://192.168.43.4/sekolah/add_data.php";
    String kode, nama, pemilik, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        editTextNama = (EditText) findViewById(R.id.etNameD);
        editTextAlamat = (EditText) findViewById(R.id.etAddressD);
        editTextKota = (EditText) findViewById(R.id.etCityD);
        editTextTelp = (EditText) findViewById(R.id.etTelpD);
        btnTambah = (Button) findViewById(R.id.btnAddD);
        btnKembali = (Button) findViewById(R.id.btnCancelD);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kode = editTextNama.getText().toString();
                nama = editTextAlamat.getText().toString();
                alamat = editTextKota.getText().toString();
                pemilik = editTextTelp.getText().toString();

                add_data(kode, nama, alamat, pemilik);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iad = new Intent(AddDataActivity.this, DataActivity.class);
                finish();
                startActivity(iad);
            }
        });
    }

    private void add_data(String kode, String nama, String alamat, String pemilik) {
        class AddDistributor extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddDataActivity.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextKota.getText().toString().trim().length() == 0
                        || editTextTelp.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(AddDataActivity.this, DataActivity.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("kode",params[0]);
                data.put("nama",params[1]);
                data.put("alamat",params[2]);
                data.put("pemilik",params[3]);

                String result = requestHandler.sendPostRequest(ADD_DISTRIBUTOR_URL,data);

                return  result;
            }
        }

        AddDistributor ad = new AddDistributor();
        ad.execute(kode, nama, pemilik, alamat);
    }
}
