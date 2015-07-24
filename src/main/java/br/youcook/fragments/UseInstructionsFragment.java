package br.youcook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.youcook.R;
import br.youcook.fragments.adapter.InstructionAdapter;
import br.youcook.objects.Instruction;

public class UseInstructionsFragment extends Fragment implements View.OnClickListener {

    Button btn_fim_uso;

    ListView lv_inst;

    android.support.v4.app.FragmentManager fm;

    ArrayList<Instruction> instructions;
    int temInstrucao;

    LayoutInflater inflater;

    Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_have_instructions, container, false);
        this.inflater = inflater;
        lv_inst = (ListView) rootView.findViewById(R.id.lv_use_inst);
        args = getArguments();
        lv_inst.setAdapter(new InstructionAdapter(inflater, instructions));

        temInstrucao = 0;

        fm = getFragmentManager();

        btn_fim_uso = (Button) rootView.findViewById(R.id.btn_use_instructions);

        btn_fim_uso.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        UseNoRecipeFragment newFragment = new UseNoRecipeFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fuinst, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}