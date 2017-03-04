package lesintouchables.com.les_intouchables;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
import java.util.Map;

import static lesintouchables.com.les_intouchables.ImageHelper.getRoundedCornerBitmap;


public class Next extends AppCompatActivity {

    private String TAG = Next.class.getSimpleName();
    Bitmap bm;
    Target target;

    private ProgressDialog pDialog;
    ListView lv;


    // URL to get contacts JSON
    private String url = "https://edev.york.im/test.php";
    ArrayList<HashMap<String, ?>> contactList;


    String[] a = {"result", "bitmapArray"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);


        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);


        new GetContacts().execute();

        getButtons();


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


                        Bitmap test = getBitmapFromURL(image);


                        Bitmap test1 = getRoundedCornerBitmap(test, 0);


                        HashMap<String, Object> result = new HashMap<>();

                        // adding each child node to HashMap key => value
                        result.put("Name", name);
                        result.put("Date", date);
                        result.put("Beschreibung", beschreibung);
                        //result.put("Image",getBitmapFromURL(image));
                        result.put("Image", test1);

                        contactList.add(result);


                        // tmp hash map for single contact


                        // adding contact to contact list


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

    private void getButtons() {
        Button guestlist = (Button) findViewById(R.id.Guestlist);
        guestlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(), PopUp_Guestlist.class));

            }
        });

        Button lounges = (Button) findViewById(R.id.Lounges);
        lounges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), pop_up_lounges.class));

            }
        });


    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Main_Activity.class));

    }









    public class CustomList extends SimpleAdapter {
        List<? extends Map<String, ?>> map; // if fails to compile, replace with List<HashMap<String, Object>> map
        String[] from;
        int layout;
        int[] to;
        Context context;
        LayoutInflater mInflater;

        public Bitmap e_tielbild;


        public CustomList(Next context, List<? extends Map<String, ?>> data, // if fails to compile, do the same replacement as above on this line
                          int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            layout = resource;
            map = data;
            this.from = from;
            this.to = to;
            this.context = context;


        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return this.createViewFromResource(position, convertView, parent, layout);


        }

        private View createViewFromResource(int position, View convertView,
                                            ViewGroup parent, int resource) {
            final View v;
            if (convertView == null) {
                v = mInflater.inflate(resource, parent, false);
            } else {
                v = convertView;
            }


            this.bindView(position, v);



            final RelativeLayout test = (RelativeLayout) v.findViewById(R.id.PartyShare);
            test.setClickable(true);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(v.getContext(), test, Gravity.CENTER);


                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.pop_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.one:
                                    share(v);


                                    break;
                                default:
                                    Toast.makeText(v.getContext(), "Nope", Toast.LENGTH_LONG).show();
                                    break;
                            }


                            return true;
                        }
                    });

                    popup.show(); //showing popup menu
                }
            }); //closing the setOnClickListener method


            return v;
        }


        private void share(View v) {

            final ShareDialog shareDialog = new ShareDialog(Next.this);

            ImageView titelbild = (ImageView) v.findViewById(R.id.Titelbild_1);
            TextView titel = (TextView) v.findViewById(R.id.Endlezz_Info);
            TextView beschreibung = (TextView) v.findViewById(R.id.Endlezz_Info1);
            TextView datum = (TextView) v.findViewById(R.id.Endlezz_Info2);


            e_tielbild = ((BitmapDrawable) titelbild.getDrawable()).getBitmap();
            String e_titel = titel.getText().toString();
            String e_beschreibung = beschreibung.getText().toString();
            String e_datum = datum.getText().toString();


            if (ShareDialog.canShow(ShareLinkContent.class)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(e_tielbild)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setContentTitle(e_titel + " " + e_datum)
                        .setContentDescription(e_beschreibung)
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag("#LesIntouchables")
                                .build())
                        .setContentUrl(Uri.parse("https://edev.york.im/App_Bilder/"+e_titel+".png"))
                        .setImageUrl(Uri.parse("https://edev.york.im/App_Bilder/"+e_titel+".png"))
                        .build();

                shareDialog.show(shareLinkContent);
            }
        }


        private void bindView(int position, View view) {
            final Map dataSet = map.get(position);
            if (dataSet == null) {
                return;
            }

            final ViewBinder binder = super.getViewBinder();
            final int count = to.length;

            for (int i = 0; i < count; i++) {
                final View v = view.findViewById(to[i]);
                if (v != null) {
                    final Object data = dataSet.get(from[i]);
                    String text = data == null ? "" : data.toString();
                    if (text == null) {
                        text = "";
                    }

                    boolean bound = false;
                    if (binder != null) {
                        bound = binder.setViewValue(v, data, text);
                    }

                    if (!bound) {
                        if (v instanceof Checkable) {
                            if (data instanceof Boolean) {
                                ((Checkable) v).setChecked((Boolean) data);
                            } else if (v instanceof TextView) {
                                // Note: keep the instanceof TextView check at the bottom of these
                                // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                                setViewText((TextView) v, text);
                            } else {
                                throw new IllegalStateException(v.getClass().getName() +
                                        " should be bound to a Boolean, not a " +
                                        (data == null ? "<unknown type>" : data.getClass()));
                            }
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else if (v instanceof ImageView) {
                            if (data instanceof Integer) {
                                setViewImage((ImageView) v, (Integer) data);
                            } else if (data instanceof Bitmap) {
                                setViewImage((ImageView) v, (Bitmap) data);
                            } else {
                                setViewImage((ImageView) v, text);
                            }
                        } else {
                            throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                    " view that can be bounds by this SimpleAdapter");
                        }
                    }
                }
            }
        }


        private void setViewImage(ImageView v, Bitmap bmp) {
            v.setImageBitmap(bmp);
        }


    }
}



















