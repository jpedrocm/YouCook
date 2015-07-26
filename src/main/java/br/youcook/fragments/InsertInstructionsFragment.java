package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Vector;

import br.youcook.R;
import br.youcook.objects.Instruction;

public class InsertInstructionsFragment extends Fragment implements View.OnClickListener {

    Button btn_photo;
    Button btn_more_instructions;

    android.support.v4.app.FragmentManager fm;

    LinearLayout itens;

    Bundle args;

    int quantidadeInstrucoes;

    Vector<InstructionFragment> vInstructions;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_insert_instructions, container, false);

        fm = getFragmentManager();

        args = getArguments();

        itens = (LinearLayout) rootView.findViewById(R.id.itens_instr);

        btn_photo = (Button) rootView.findViewById(R.id.btn_photo);
        btn_more_instructions = (Button) rootView.findViewById(R.id.btn_more_instr);

        btn_photo.setOnClickListener(this);

        vInstructions = new Vector<>();

        quantidadeInstrucoes = 0;

        initialItem();

        btn_more_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vInstructions.add(new InstructionFragment());

                RelativeLayout rl = new RelativeLayout(getActivity().getApplicationContext());
                rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                rl.setId(quantidadeInstrucoes+50);
                itens.addView(rl, quantidadeInstrucoes);

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(quantidadeInstrucoes+50, vInstructions.elementAt(quantidadeInstrucoes));
                ft.commit();
                quantidadeInstrucoes++;
            }
        });

        return rootView;
    }

    public void initialItem(){
        vInstructions.add(new InstructionFragment());

        RelativeLayout rl = new RelativeLayout(getActivity().getApplicationContext());
        rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        rl.setId(quantidadeInstrucoes+50);
        itens.addView(rl, 0);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(quantidadeInstrucoes+50, vInstructions.elementAt(0));
        ft.commit();
        quantidadeInstrucoes++;
    }

    public void onClick(View v) {
        InsertPhotosFragment newFragment = new InsertPhotosFragment();

        Bundle aux;
        String dur, instrucao;

        ArrayList<Instruction> inst = new ArrayList<>();

        for(int i = 0; i < vInstructions.size(); i++){
            aux = vInstructions.elementAt(i).getBundle();
            dur = aux.getString("dur");
            instrucao = aux.getString("instr");

            if(instrucao.equals("")){
                new AlertDialog.Builder(getActivity()).setTitle("Campo obrigatório!")
                        .setMessage("Preecha a instrucao " + (i + 1) + " por completo.")
                        .setIcon(R.id.alertTitle)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which){}}).show();
                inst.clear();
                return;
            } else if(dur.equals("")){
                dur = "tempo necessario";
            }

            inst.add(new Instruction(instrucao, dur, i));
        }

        args.putParcelableArrayList("inst", inst);
        newFragment.setArguments(args);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fiinstr, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}