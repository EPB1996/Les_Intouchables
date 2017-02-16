package layout;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lesintouchables.com.les_intouchables.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Team extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team, container, false);


        setText(v);

        return v;
    }

    //set Text
    public void setText(View v) {












        int[] idtext2 = {R.id.Team,R.id.Text1_1,R.id.Text1_2,R.id.Text1_3,R.id.Text2_1,R.id.Text2_2,R.id.Text2_3,R.id.Text3_1,R.id.Text3_2,R.id.Text3_3,R.id.allgemein_1_1,R.id.allgemein_1_2,R.id.allgemein_1_3,
                            R.id.Text4_1,R.id.Text4_2,R.id.Text4_3};

        for (int i = 0; i < idtext2.length; i++) {
            TextView text2 = (TextView) v.findViewById(idtext2[i]);

            //allgeimeine Einstellungen des Textes text2:
            text2.setTextColor(Color.GRAY);
            text2.setTextSize(10);


            switch (idtext2[i]){
                case R.id.Team:
                    text2.setText("Team");
                    text2.setAllCaps(true);
                    text2.setTextColor(Color.BLACK);
                    break;
                case R.id.allgemein_1_1:
                    text2.setText(R.string.Kontakt);
                    break;
                case R.id.allgemein_1_2:
                    text2.setText(R.string.Telefon);
                    break;
                case R.id.allgemein_1_3:
                    text2.setText(R.string.Beschreibung);
                    break;
                case R.id.Text1_1:
                    text2.setText(R.string.Sahin_Name);
                    break;
                case R.id.Text2_1:
                    text2.setText(R.string.Umit_Name);
                    break;
                case R.id.Text3_1:
                    text2.setText(R.string.Serhat_Name);
                    break;
                case R.id.Text1_2:
                    text2.setText(R.string.Enterprise);
                    break;
                case R.id.Text2_2:
                    text2.setText(R.string.Enterprise);
                    break;
                case R.id.Text3_2:
                    text2.setText(R.string.Enterprise);
                    break;
                case R.id.Text1_3:
                    text2.setText(R.string.Sahin_Text);
                    break;
                case R.id.Text2_3:
                    text2.setText(R.string.Umit_Text);
                    break;
                case R.id.Text3_3:
                    text2.setText(R.string.Serhat_Text);
                    break;
                case R.id.Text4_1:
                    text2.setText(R.string.Etienne_Name);
                    break;
                case R.id.Text4_2:
                    text2.setText(R.string.Etinne_Enterprise);
                    break;
                case R.id.Text4_3:
                    text2.setText(R.string.Etienne_Text);
                    break;
            }

        }
    }



    public static Team newInstance(String text) {

        Team f = new Team();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}