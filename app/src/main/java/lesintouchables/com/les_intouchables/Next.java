package lesintouchables.com.les_intouchables;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;


import static android.os.Build.ID;


public class Next extends AppCompatActivity {

    private String TAG = Next.class.getSimpleName();

    private ProgressDialog pDialog;
    ListView lv;


    // URL to get contacts JSON
    private String url = "http://10.0.2.2/webservice/test.php";
    ArrayList<HashMap<String, ?>> contactList;


    String[] a = {"result", "bitmapArray"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);



        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);


        new GetContacts().execute();

        getLounges();





    }


    public class GetContacts extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(Next.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(true);
                pDialog.show();

            }

            @Override
            public Void doInBackground(Void... arg0) {
                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url);


                Log.e(TAG, "Response from url: " + jsonStr);


                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray les_intouchables = jsonObj.getJSONArray("result");

                        // looping durch ganze Tabelleneinträge
                        for (int i = 0; i < les_intouchables.length(); i++) {
                            JSONObject c = les_intouchables.getJSONObject(i);


                            String name = c.getString("Name");
                            String date = c.getString("Date");
                            String beschreibung = c.getString("Beschreibung");
                            String image = c.getString("Image");

                            // decode imagestring


                            byte[] decodeString = Base64.decode(image, Base64.DEFAULT);
                            Bitmap bm = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


                            // tmp hash map for single contact
                            HashMap<String, Object> result = new HashMap<>();

                            // adding each child node to HashMap key => value
                            result.put("Name", name);
                            result.put("Date", date);
                            result.put("Beschreibung", beschreibung);
                            result.put("Image", bm);


                            // adding contact to contact list
                            contactList.add(result);




                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }




                return null;
            }

            @Override
            public void onPostExecute(Void a) {
                super.onPostExecute(a);

                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();



                ListAdapter adapter = new CustomList(
                        Next.this, contactList,
                        R.layout.list_item, new String[]{"Name", "Date", "Beschreibung", "Image"}, new int[]{R.id.Endlezz_Info, R.id.Endlezz_Info2, R.id.Endlezz_Info1, R.id.Titelbild_1});

                        lv.setAdapter(adapter);






            }


        }

    private void getLounges(){
        Button btn = (Button)findViewById(R.id.Guestlist);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), PopUp.class));
            }
        });






    }

    }


















