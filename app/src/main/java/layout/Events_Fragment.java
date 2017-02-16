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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import static android.R.attr.layout_centerHorizontal;
import static android.R.attr.layout_centerVertical;
import static android.R.attr.width;
import static android.support.v7.mediarouter.R.attr.height;

/**
 * A simple {@link Fragment} subclass.
 */
public class Events_Fragment extends Fragment {

    private String TAG = Events_Fragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private String url = "https://edev.york.im/partys_fix.php";
    private String url1 = "https://edev.york.im/partys_info.php";

    ArrayList<HashMap<String, ?>> contactList1;
    String[] a = {"result", "bitmapArray"};


    List<String> finalWas;
    List<String> finalWo;
    List<String> finalWann;
    List<String> finalSpecials;
    public static List<String> set;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_, container, false);


        new GetData().execute();
        finalWas= new ArrayList<String>();
        finalWo = new ArrayList<String>();
        finalWann= new ArrayList<String>();
        finalSpecials= new ArrayList<String>();
        set = new ArrayList<String>();


        finalWo.clear();
        finalWann.clear();
        finalWas.clear();
        finalSpecials.clear();



        return v;



    }


    public class GetData extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        public Void doInBackground(Bitmap... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            String jsonStr1 = sh.makeServiceCall(url1);


            Log.e(TAG, "Response from url: " + jsonStr);
            Log.e(TAG, "Response from url: " + jsonStr1);



            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray les_intouchables = jsonObj.getJSONArray("result");


                    // looping durch ganze Tabelleneinträge
                    for (int i = 0; i < les_intouchables.length(); i++) {
                        JSONObject c = les_intouchables.getJSONObject(i);

                        String image = c.getString("Image");

                        set.add(image);



                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");


            }


            //TODO: fetch INfos

            if (jsonStr1 != null) {
                try {
                    JSONObject jsonObj1 = new JSONObject(jsonStr1);

                    // Getting JSON Array node
                    JSONArray les_intouchables1 = jsonObj1.getJSONArray("result1");


                    // looping durch ganze Tabelleneinträge
                    for (int i = 0; i < les_intouchables1.length(); i++) {
                        JSONObject a = les_intouchables1.getJSONObject(i);





                        //TODO: get "Wo" value


                        String wo1 = a.getString("Wo");

                        //TODO: get "Was" value


                        String was1 = a.getString("Was");

                        //TODO: get "Wann" value



                        String wann1 = a.getString("Wann");
                        //TODO: get "Specials" value



                        String specials1 = a.getString("Specials");



                        finalWo.add(wo1);



                        finalWas.add(was1);


                        finalWann.add(wann1);


                        finalSpecials.add(specials1);



                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");


            }













            return null;
        }


        @Override
        public void onPostExecute(Void a) {
            super.onPostExecute(a);


            System.out.println(set);
            System.out.println(finalWo);
            System.out.println(finalWas);
            System.out.println(finalWann);
            System.out.println(finalSpecials);






            final String[] array = set.toArray(new String[0]);
            final String[] Was = finalWas.toArray(new String[0]);
            final String[] Wo = finalWo.toArray(new String[0]);
            final String[] Wann = finalWann.toArray(new String[0]);
            final String[] Specials = finalSpecials.toArray(new String[0]);



            //dynamisches erstellen der buttons
            //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1250,750);
            params.setLayoutDirection(layout_centerVertical);
            params.setMargins(10, 5, 10, 0);



            LinearLayout lineartest = (LinearLayout) getView().findViewById(R.id.lineartest);
            lineartest.setGravity(Gravity.CENTER);
            //TODO: je nach bildanzahl ändern! Party_fix

            ImageButton[] btn = new ImageButton[array.length];

            for (int i = 0; i < array.length; i++) {



                btn[i] = new ImageButton(getActivity());

                btn[i].setLayoutParams(params);
                btn[i].setBackgroundColor(Color.WHITE);
                btn[i].setId(i);
                btn[i].setScaleType(ImageView.ScaleType.FIT_XY);


                byte[] decodeString = Base64.decode(array[i].toString(), Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


                btn[i].setImageBitmap(bm);


                lineartest.addView(btn[i]);




                //TODO:link buttons mit der neuen Activity(manuell)!!
                btn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        switch (v.getId()){
                            case 0:
                                String was = Was[0];
                                Intent intent = new Intent(getActivity(), Party.class);
                                intent.putExtra("Wo", Wo[0]);
                                intent.putExtra("Was", Was[0]);
                                intent.putExtra("Wann", Wann[0]);
                                intent.putExtra("Specials", Specials[0]);
                                intent.putExtra("int", 0);
                                intent.putExtra("url", "https://edev.york.im/goodlife_bilder.php");
                                startActivity(intent);


                                break;
                            case 1:
                                String was1 = Was[1];
                                Intent intent1 = new Intent(getActivity(), Party.class);
                                intent1.putExtra("Wo", Wo[1]);
                                intent1.putExtra("Was", Was[1]);
                                intent1.putExtra("Wann", Wann[1]);
                                intent1.putExtra("Specials", Specials[1]);
                                intent1.putExtra("int", 1);
                                intent1.putExtra("url", "https://edev.york.im/paradise_bilder.php");
                                startActivity(intent1);


                                break;
                            case 2:
                                String was2 = Was[2];
                                Intent intent2 = new Intent(getActivity(), Party.class);
                                intent2.putExtra("Wo", Wo[2]);
                                intent2.putExtra("Was", Was[2]);
                                intent2.putExtra("Wann", Wann[2]);
                                intent2.putExtra("Specials", Specials[2]);
                                intent2.putExtra("int", 2);
                                intent2.putExtra("url", "https://edev.york.im/goodlife_bilder.php");
                                startActivity(intent2);
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



