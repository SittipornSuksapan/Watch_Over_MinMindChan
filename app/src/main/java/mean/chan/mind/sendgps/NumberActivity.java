package mean.chan.mind.sendgps;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class NumberActivity extends AppCompatActivity {

    //Explicit
    private ListView listView;
    private String[] telString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);


        listView = (ListView) findViewById(R.id.listView);

        //Synchronize JSON
        synJSON();

        //button back
        backController();



    }   // Main Method

    @Override
    protected void onRestart() {
        super.onRestart();
        synJSON();
    }

    private void synJSON() {
        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();
    }

    //Create Inner Class
    public class MySynJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://androidthai.in.th/dom/getContact.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("21April", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                final String[] nameString = new String[jsonArray.length()];
                        telString = new String[jsonArray.length()];
                //lngStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameString[i] = jsonObject.getString("Name");
                    telString[i] = jsonObject.getString("Tel");

                    //lngStrings[i] = jsonObject.getString("Lng");

                    Log.d("21April", "Name ==> " + i + " == " + nameString[i]);
                    Log.d("21April", "Tel ==> " + i + " == " + telString[i]);

                }   // for

                Log.d("21April", "Name length ==> " + nameString.length);



                NumberAdapter numberAdapter = new NumberAdapter(NumberActivity.this, nameString,telString);
                listView.setAdapter(numberAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String uri = "tel:" + telString[i];
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                        startActivity(intent);


                    }   // onItem
                });






            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // MySynJSON class




    public void clickAddContact(View view) {
        startActivity(new Intent(NumberActivity.this, AddNumberActivity.class));
    }



    private void backController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    } //button back




}// Main Class
