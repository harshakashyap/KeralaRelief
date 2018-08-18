package com.example.kashyap.keralarelief;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    EditText ET;
    String string, jsonstring;
    JSONObject jsonObject;
    OkHttpClient okHttpClient;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET = findViewById(R.id.editText);
        //jsonObject = new JSONObject();
    }

    public void Clicked(View view) throws UnsupportedEncodingException {
        string = ET.getText().toString();
        Gson gson = new Gson();
        jsonObject = new JSONObject();
        /*try {
            jsonObject.put("key", "Chirag");

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        okHttpClient = new OkHttpClient();
        Data D = new Data("Key","Harsha");
        jsonstring = gson.toJson(D);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE,jsonstring);
        final Request request = new Request.Builder().url("http://192.168.2.14:3000/users/test")
                                .post(requestBody)
                                .addHeader("Content-Type","application/json")
                                .build();

        /*try {
            Response response = okHttpClient.newCall(request).execute();
            Log.d("Sync",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.d("failure Response", mMessage);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String mMessage = response.body().string();
                    if(response.isSuccessful())
                        try {
                            JSONObject JSON = new JSONObject(mMessage);
                            final String serverResponse = JSON.getString("message");
                            //Toast.makeText(MainActivity.this,serverResponse,Toast.LENGTH_SHORT).show();
                            Log.d("YayBitches",serverResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            });
    }
}
