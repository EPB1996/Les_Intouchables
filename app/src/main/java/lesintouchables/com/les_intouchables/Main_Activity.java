package lesintouchables.com.les_intouchables;

import android.app.ActivityGroup;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;

/**
 * Created by root on 29.11.2016.
 */

public class Main_Activity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TabHost TabHostWindow;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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




    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Main_Activity.class));

    }

}


