package layout;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lesintouchables.com.les_intouchables.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Empfehlungen extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.empfehlungen, container, false);


            return v;
        }

        public static Empfehlungen newInstance(String text) {

            Empfehlungen f = new Empfehlungen();
            Bundle b = new Bundle();
            b.putString("msg", text);

            f.setArguments(b);

            return f;
        }
    }