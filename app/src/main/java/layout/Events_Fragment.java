package layout;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lesintouchables.com.les_intouchables.HttpHandler;
import lesintouchables.com.les_intouchables.Next;
import lesintouchables.com.les_intouchables.Party;
import lesintouchables.com.les_intouchables.R;
import lesintouchables.com.les_intouchables.Startseit_Swipe;

import static android.R.attr.width;
import static android.support.v7.mediarouter.R.attr.height;

/**
 * A simple {@link Fragment} subclass.
 */
public class Events_Fragment extends Fragment {

    private String TAG = Events_Fragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private String url = "http://10.0.2.2/webservice/partys_fix.php";

    ArrayList<HashMap<String, ?>> contactList1;
    String[] a = {"result", "bitmapArray"};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_, container, false);


        new GetData().execute();

        return v;


    }



    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


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
                        JSONObject c = les_intouchables.getJSONObject(0);
                        JSONObject c1 = les_intouchables.getJSONObject(1);

                        //TODO: je nach bildanzahl ändern! Party_fix
                        /*
                        JSONObject c2 = les_intouchables.getJSONObject(2);
                        JSONObject c3 = les_intouchables.getJSONObject(3);
                        JSONObject c4 = les_intouchables.getJSONObject(4);
                        */









                        String image = c.getString("Image");
                        String image1 = c1.getString("Image");

                        //TODO: je nach bildanzahl ändern! Party_fix
                        /*
                        String image2 = c2.getString("Image");
                        String image3 = c3.getString("Image");
                        String image4 = c4.getString("Image");
                        */








                        // decode imagestring

                        Set<String> set = new HashSet<String>();
                        set.add(image);
                        set.add(image1);

                        //TODO: je nach bildanzahl ändern! Party_fix
                        /*
                        set.add(image2);
                        set.add(image3);
                        set.add(image4);
                        */










                        SharedPreferences settings = getActivity().getSharedPreferences("CoolPreferences", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putStringSet("key", set);

                        editor.apply();



                    }





                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());



                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");



            }

            //image fetch


            return null;
        }

        @Override
        public void onPostExecute(Void a) {
            super.onPostExecute(a);

            SharedPreferences settings = getActivity().getSharedPreferences("CoolPreferences", 0);
            Set<String> set = settings.getStringSet("key", null);

            System.out.println(set);


            final String[] array = set.toArray(new String[0]);


            //dynamisches erstellen der buttons
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 500);
            params.setMargins(10,15,10,0);

            LinearLayout lineartest = (LinearLayout)getView().findViewById(R.id.lineartest);
            //TODO: je nach bildanzahl ändern! Party_fix
            ImageButton[] btn = new ImageButton[2];


            final BitmapFactory.Options options = new BitmapFactory.Options();


            for (int i =0; i < array.length; i++) {

                btn[i] = new ImageButton(getActivity());

                btn[i].setLayoutParams(params);
                btn[i].setBackgroundColor(Color.DKGRAY);
                btn[i].setId(i);
                btn[i].setScaleType(ImageView.ScaleType.FIT_XY);

                byte[] decodeString = Base64.decode(array[i].toString(), Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);



                btn[i].setImageBitmap(bm);






                lineartest.addView(btn[i]);


                //link buttons mit der neuen Activity
                btn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (v.getId()) {
                            case 1:
                                String was1 = "Lass es abgehen im Hiltl";
                                Intent intent1 = new Intent(getActivity(), Party.class);
                                intent1.putExtra("Wo", "Hiltl");
                                intent1.putExtra("Was", was1);
                                intent1.putExtra("Wann","Samstags");
                                intent1.putExtra("Specials", "Ballöne, Konfetti");
                                intent1.putExtra("int", 0);
                                intent1.putExtra("url", "http://10.0.2.2/webservice/goodlife_bilder.php");

                                startActivity(intent1);
                                break;
                            case 0:
                                String was = "Party wie im Paradise";
                                Intent intent = new Intent(getActivity(), Party.class);
                                intent.putExtra("Wo", "Aura");
                                intent.putExtra("Was", was);
                                intent.putExtra("Wann","Samstags");
                                intent.putExtra("Specials", "Ballöne, Konfetti");
                                intent.putExtra("int", 1);
                                intent.putExtra("url", "http://10.0.2.2/webservice/paradise_bilder.php");


                                startActivity(intent);
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), Party.class));
                                break;
                            default:
                                startActivity(new Intent(getActivity(), Party.class));
                                break;


                        }

                        }


                });


            }
        }
    }












    public static Events_Fragment newInstance(String text) {

        Events_Fragment f = new Events_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }





}

