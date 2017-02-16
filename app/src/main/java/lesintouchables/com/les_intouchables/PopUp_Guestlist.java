package lesintouchables.com.les_intouchables;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static lesintouchables.com.les_intouchables.Next.getBitmapFromURL;
import static lesintouchables.com.les_intouchables.R.layout.rowofspinner;

public class PopUp_Guestlist extends Activity{

    String name;
    String date;
    String party;


    private String TAG = PopUp_Guestlist.class.getSimpleName();
    private ProgressDialog pDialog;
    private String url = "https://edev.york.im/test.php";
    ArrayList<HashMap<String, ?>> contactList;

    public static String insertUrl;

    String[] a = {"result", "bitmapArray"};
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_guestlist);



        //setting up Data to insert
        final EditText e_name= (EditText) findViewById(R.id.Name);
        final EditText e_date = (EditText) findViewById(R.id.Geburtstag);

        Button insert = (Button) findViewById(R.id.insert);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = e_name.getText().toString();
                date = e_date.getText().toString();
                    if (name.isEmpty()|| date.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Bitte Vollständig ausfüllen", Toast.LENGTH_SHORT).show();

                    } else {
                        writeToDataBase();
                    }


            }
        });


        contactList = new ArrayList<>();


        new GetContacts().execute();



        }

    public class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PopUp_Guestlist.this);
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
                        String image = c.getString("Image");

                        // decode imagestring





                        // tmp hash map for single contact
                        HashMap<String, Object> result = new HashMap<>();

                        // adding each child node to HashMap key => value

                        result.put("Name", name);
                        result.put("Image", getBitmapFromURL(image));


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

            /*
            ListAdapter adapter = new CustomList(
                    PopUp_Guestlist.this, contactList,
                    R.layout.guestlistitem, new String[]{"Image"}, new int[]{R.id.ImagePopUp});

            lv.setAdapter(adapter);
            */

            //setting up spinner
            Spinner spin = (Spinner) findViewById(R.id.spinner1);
            CustomSpinnerAdapter adapter1 = new CustomSpinnerAdapter(
                    PopUp_Guestlist.this, contactList,
                    rowofspinner, new String[]{"Image", "Name"}, new int[]{R.id.icon, R.id.partyname});

            spin.setAdapter(adapter1);

            //setting text of position
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView txt = (TextView) findViewById(R.id.testtext);

                    String partysname = parent.getItemAtPosition(position).toString();
                    party = partysname.substring(partysname.indexOf("Name") + 5, partysname.length()).replace("}", "");

                    txt.setText(party);
                    txt.setGravity(Gravity.CENTER);






                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }


    }




    private void writeToDataBase() {

        Toast.makeText(getApplicationContext(), name + date, Toast.LENGTH_SHORT).show();


            class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                @Override
                protected String doInBackground(String... params) {

                    String QuickParty = party;
                    String QuickName = name;
                    String QuickDate = date;


                    String DataParseUrl = "https://edev.york.im/insert_to_guestlists.php";


                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("Party", QuickParty));
                    nameValuePairs.add(new BasicNameValuePair("Name", QuickName));
                    nameValuePairs.add(new BasicNameValuePair("Date", QuickDate));


                    try {
                        HttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost(DataParseUrl);

                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        HttpResponse response = httpClient.execute(httpPost);

                        HttpEntity entity = response.getEntity();


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {

                    }
                    return "Data Submit Successfully";
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);

                    Toast.makeText(PopUp_Guestlist.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

                }
            }
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            sendPostReqAsyncTask.execute(null, null, null);
        }

}





