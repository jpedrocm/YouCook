package br.youcook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.youcook.R;

public class IngredientFragment extends Fragment {

    EditText et_qt;
    EditText et_name;
    EditText et_unit;

    double qt;
    String name;
    String unit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        et_qt = (EditText) rootView.findViewById(R.id.et_qt);
        et_name = (EditText) rootView.findViewById(R.id.et_name);
        et_unit = (EditText) rootView.findViewById(R.id.et_unit);

        qt = -1;
        unit = "";
        name = "";

        return rootView;
    }

    public Bundle getBundle(){
        Bundle args = new Bundle();

        name = et_name.getText().toString().trim();
        args.putString("name", name);

        unit = et_unit.getText().toString().trim();
        args.putString("unit", unit);

        String sQt = et_qt.getText().toString().trim();
        if(!sQt.equals("")){
            qt = Double.parseDouble(sQt);
        }
        args.putDouble("qt", qt);

        return args;
    }
}