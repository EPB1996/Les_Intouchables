package layout;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import lesintouchables.com.les_intouchables.Club;
import lesintouchables.com.les_intouchables.Party;
import lesintouchables.com.les_intouchables.R;
import lesintouchables.com.les_intouchables.Startseit_Swipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class Events_Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events_, container, false);

        //Gernerator der Onclicklisteners
        int[] clubs = {R.id.Club1,R.id.Club2,R.id.Club3,R.id.Party1,R.id.Party2,R.id.Party3,R.id.Party4,R.id.Party5,R.id.Party6};
        for (int i=0; i < clubs.length; i++ ){

            //zuordnen der ID's
            ImageButton clubNum = (ImageButton)v.findViewById(clubs[i]);
            //Befehle, die auf ALLEN ID's aufgerufen werden
            //TODO: neue Methode mit Logos
            clubNum.setOnClickListener(lol);
        }


        return v;

    }

    //Switchverzweigung für die verschiedenen Imagebuttons (Club/Party)
    private View.OnClickListener lol = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Club1:
                    startActivity(new Intent(getActivity(), Club.class));
                    break;
                case R.id.Club2:
                    startActivity(new Intent(getActivity(), Club.class));
                    break;
                case R.id.Club3:
                    startActivity(new Intent(getActivity(), Club.class));
                    break;
                case R.id.Party1:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;
                case R.id.Party2:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;
                case R.id.Party3:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;
                case R.id.Party4:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;
                case R.id.Party5:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;
                case R.id.Party6:
                    startActivity(new Intent(getActivity(), Party.class));
                    break;

            }

        }
    };




    public static Events_Fragment newInstance(String text) {

        Events_Fragment f = new Events_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }





}

