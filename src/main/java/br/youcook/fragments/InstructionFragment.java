package br.youcook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.youcook.R;

public class InstructionFragment extends Fragment {

    EditText et_instr;
    EditText et_duration;

    String dur;
    String instr;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);

        et_duration = (EditText) rootView.findViewById(R.id.et_duration);
        et_instr = (EditText) rootView.findViewById(R.id.et_instr);

        instr = "";
        dur = "";

        return rootView;
    }

    public Bundle getBundle(){
        Bundle args = new Bundle();

        dur = et_duration.getText().toString().trim();
        args.putString("dur", dur);

        instr = et_instr.getText().toString().trim();
        args.putString("instr", instr);

        return args;
    }
}