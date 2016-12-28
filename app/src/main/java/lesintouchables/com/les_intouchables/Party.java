package lesintouchables.com.les_intouchables;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import layout.Events_Fragment;


public class Party extends AppCompatActivity {


    private TextSwitcher mSwitcher;
    private TextSwitcher mSwitcherTitle;
    Button btnNext;
    Button btnBack;

    private ViewFlipper simpleViewSwitcher;
    Button viewButtonNext;
    Button viewButtonBack;


    String wo;
    String was;
    String wann;
    String specials;


    String textToShow[] = {null, null, null, null};

    // Array of String to Show In TextSwitcher

    String textToShow1[] = {"WO", "WAS", "WANN", "SPECIALS"};


    int messageCount = textToShow.length;
    // to keep current Index of text
    int currentIndex = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);


        wo = getIntent().getExtras().getString("Wo");
        was = getIntent().getExtras().getString("Was");
        wann = getIntent().getExtras().getString("Wann");
        specials = getIntent().getExtras().getString("Specials");


        String[] test = {wo, was, wann, specials};


        textToShow = test;

        setContent();
       new get_goodlife_bilder().execute();


    }

    public void setContent() {
        btnNext = (Button) findViewById(R.id.btnnext);
        btnBack = (Button) findViewById(R.id.btnback);
        mSwitcher = (TextSwitcher) findViewById(R.id.Textswitcher);
        mSwitcherTitle = (TextSwitcher) findViewById(R.id.TextswitcherTitle);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        mSwitcherTitle.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(Party.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(20);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(Party.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(16);
                myText.setTextColor(Color.WHITE);
                return myText;

            }
        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Animation in1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out1 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        // set the animation type of textSwitcher
        mSwitcher.setInAnimation(in1);
        mSwitcher.setOutAnimation(out1);
        mSwitcherTitle.setInAnimation(in);
        mSwitcherTitle.setOutAnimation(out);


        //set default View
        mSwitcher.setText(textToShow[currentIndex]);
        mSwitcherTitle.setText(textToShow1[currentIndex]);


        // ClickListener for NEXT button
        // When clicked on Button TextSwitcher will switch between texts
        // The current Text will go OUT and next text will come in with specified animation
        btnNext.setText("-->");
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentIndex++;
                // If index reaches maximum reset it
                if (currentIndex == messageCount)
                    currentIndex = 0;
                mSwitcher.setText(textToShow[currentIndex]);
                mSwitcherTitle.setText(textToShow1[currentIndex]);

            }
        });

        btnBack.setText("<--");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;

                if (currentIndex == -1)
                    currentIndex = 3;
                mSwitcher.setText(textToShow[currentIndex]);
                mSwitcherTitle.setText(textToShow1[currentIndex]);
            }
        });


        //Imageswitcher

        SharedPreferences settings = getSharedPreferences("CoolPreferences", 0);
        Set<String> set = settings.getStringSet("key", null);

        System.out.println(set);


        final String[] array = set.toArray(new String[0]);




        int titelindex = getIntent().getExtras().getInt("int");
        //set titleImage
        byte[] decodeString = Base64.decode(array[titelindex].toString(), Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

        ImageView titel = (ImageView) findViewById(R.id.club);
        titel.setScaleType(ImageView.ScaleType.FIT_XY);
        titel.setImageBitmap(bm);


    }


    public class get_goodlife_bilder extends AsyncTask<Void, Void, Void> {

        private String TAG = Party.class.getSimpleName();

        private ProgressDialog pDialog;
        private String url = getIntent().getExtras().getString("url");




        ArrayList<HashMap<String, ?>> contactList1;
        String[] a = {"result", "bitmapArray"};

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
                    JSONArray goodlife_bilder = jsonObj.getJSONArray("result");


                    // looping durch ganze Tabelleneinträge
                    for (int i = 0; i < goodlife_bilder.length(); i++) {
                        JSONObject c = goodlife_bilder.getJSONObject(0);
                        JSONObject c1 = goodlife_bilder.getJSONObject(1);

                        //TODO: je nach bildanzahl ändern! Party_fix

                        JSONObject c2 = goodlife_bilder.getJSONObject(2);
                        JSONObject c3 = goodlife_bilder.getJSONObject(3);
                        //JSONObject c4 = goodlife_bilder.getJSONObject(4);



                        String image = c.getString("Image");
                        String image1 = c1.getString("Image");

                        //TODO: je nach bildanzahl ändern! Party_fix
                        String image2 = c2.getString("Image");
                        String image3 = c3.getString("Image");
                        //String image4 = c4.getString("Image");



                        // decode imagestring

                        Set<String> set = new HashSet<String>();
                        set.add(image);
                        set.add(image1);

                        //TODO: je nach bildanzahl ändern! Party_fix

                        set.add(image2);
                        set.add(image3);
                        //set.add(image4);



                        SharedPreferences bilder = getSharedPreferences("bilder", 0);
                        SharedPreferences.Editor editor = bilder.edit();
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

            SharedPreferences bilder = getSharedPreferences("bilder", 0);
            Set<String> set = bilder.getStringSet("key", null);

            System.out.println(set);


            final String[] array = set.toArray(new String[0]);


            viewButtonNext = (Button) findViewById(R.id.viewbtnnext);
            viewButtonBack = (Button) findViewById(R.id.viewbtnback);


            simpleViewSwitcher = (ViewFlipper) findViewById(R.id.ViewSwitcher);


            //TODO: wechsle die Images
            ImageView[] imageView = new ImageView[array.length];
            for (int i = 0; i < array.length; i++) {
                imageView[i] = new ImageView(getApplicationContext());
                imageView[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView[i].setLayoutParams(new ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));


                byte[] decodeString = Base64.decode(array[i].toString(), Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                imageView[i].setImageBitmap(bm);
                simpleViewSwitcher.addView(imageView[i]);


            }


            // Declare in and out animations and load them using AnimationUtils class
            Animation in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
            Animation in1 = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
            Animation out1 = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

            // set the animation type to ViewSwitcher
            simpleViewSwitcher.setInAnimation(in1);
            simpleViewSwitcher.setOutAnimation(out1);


            viewButtonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simpleViewSwitcher.showNext();
                }
            });

            viewButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simpleViewSwitcher.showPrevious();
                }
            });


        }


    }
}
















