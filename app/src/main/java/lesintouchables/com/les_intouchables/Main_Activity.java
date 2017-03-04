package lesintouchables.com.les_intouchables;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static android.R.color.holo_orange_light;
import static com.google.android.gms.wearable.DataMap.TAG;
import static lesintouchables.com.les_intouchables.ImageHelper.getRoundedCornerBitmap;

/**
 * Created by root on 29.11.2016.
 */

public class Main_Activity extends ActivityGroup implements NavigationView.OnNavigationItemSelectedListener  {

    ActionBarDrawerToggle toggle;
    View headerview;

    protected DrawerLayout drawer;
    protected Toolbar toolbar;
    CallbackManager callbackManager;
    ImageView profilePictureView;
    String userId;
    private Target mTarget;
    public static String username_vorname;
    public static String username_nachname;
    ImageView profil_raw;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final TabHost TabHostWindow;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        TabHostWindow = (TabHost) findViewById(android.R.id.tabhost);


        //create TabSpec
        TabHost.TabSpec Tab1 = TabHostWindow.newTabSpec("STARTSEITE");
        TabHost.TabSpec Tab2 = TabHostWindow.newTabSpec("NEXT");


        //set indicators auf die Tabs
        Tab1.setIndicator("STARTSEITE");
        Tab2.setIndicator("NEXT");









        //TODO: linke mit Activities
        Tab1.setContent(new Intent(this, Startseit_Swipe.class));
        Tab2.setContent(new Intent(this, Next.class));



        //adden der Tabbs zum Tabhost
        TabHostWindow.setup(this.getLocalActivityManager());
        TabHostWindow.addTab(Tab1);
        TabHostWindow.addTab(Tab2);



            TextView tv = (TextView) TabHostWindow.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);


        TextView tx = (TextView) TabHostWindow.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tx.setTextColor(getResources().getColor(holo_orange_light));




        TabHostWindow.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabColor(TabHostWindow);
            }
        });

        headerview = navigationView.getHeaderView(0);

        //facebook();
        Button login= (Button)headerview.findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook();
            }
        });


        Button logout= (Button)headerview.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                setdefault();
            }
        });


        setdefault();


    }

    public void setTabColor(TabHost tabhost) {

        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
                TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(Color.WHITE);


        }

        TextView tv = (TextView) tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor((getResources().getColor(holo_orange_light)));// selected
    }
    private void setdefault(){
        profil_raw = (ImageView) headerview.findViewById(R.id.fb_profil);
        profil_raw.setImageResource(R.drawable.les_intouchable);
        user = (TextView)headerview.findViewById(R.id.User);
        user.setText("User");
        username_vorname = null;
        username_nachname = null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

            if(id == R.id.logout_button) {

                        LoginManager.getInstance().logOut();
                        profil_raw = (ImageView) findViewById(R.id.fb_profil);
                        profil_raw.setImageResource(android.R.color.transparent);
                        Toast.makeText(this,"hallo",Toast.LENGTH_SHORT).show();
                    }





        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        int[] ids = {R.id.Startseite, R.id.Next, R.id.Verlosung, R.id.Newsletter, R.id.Verlosung, R.id.Friendlist, R.id.Lounges_nav, R.id.Kontakt};



            switch (id) {
                case R.id.Verlosung:
                    startActivity(new Intent(this, Main_Activity.class));
                    break;
                case R.id.Friendlist:
                    startActivity(new Intent(this, Main_Activity.class));
                    break;
                case R.id.Newsletter:
                    startActivity(new Intent(this, Main_Activity.class));
                    break;
                case R.id.Lounges_nav:
                    startActivity(new Intent(this, Main_Activity.class));
                    break;
                case R.id.Kontakt:
                    startActivity(new Intent(this, Kontakt.class));
                    break;
                default:  startActivity(new Intent(this, Main_Activity.class));
                    break;

            }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void facebook(){
        Button loginButton = (Button) findViewById(R.id.login_button);



        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)  {
                // App code
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                userId = Profile.getCurrentProfile().getId();

                username_vorname = Profile.getCurrentProfile().getFirstName();
                username_nachname = Profile.getCurrentProfile().getLastName();

                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name");
                //request.setParameters(parameters);
                //request.executeAsync();

                getFacebookProfilePicture(userId);



            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel triggered");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i(TAG, "onError triggered");

            }
        });












    }
    private void getFacebookProfilePicture(String UserId){

       String url =  "https://graph.facebook.com/" + UserId+ "/picture?type=large";
        loadImage(this,url);
        setName();
    }

    private void setName(){
        user = (TextView)findViewById(R.id.User);
        user.setText(username_vorname + " " + username_nachname);
    }


        void loadImage(Context context, String url) {

            profil_raw = (ImageView) findViewById(R.id.fb_profil);


            mTarget = new Target() {
                @Override
                public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){

                    Bitmap profil= getRoundedCornerBitmap(bitmap, 0);
                    profil_raw.setImageBitmap(profil);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.with(context)
                    .load(url)
                    .into(mTarget);
        }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}




