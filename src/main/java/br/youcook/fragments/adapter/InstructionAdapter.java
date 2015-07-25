package br.youcook.fragments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import br.youcook.R;
import br.youcook.objects.Instruction;

public class InstructionAdapter extends BaseAdapter implements View.OnClickListener
{
    private LayoutInflater mInflater;
    private ArrayList<Instruction> itens;
    CheckBox tem;
    Button escutar;
    TextView tv_instrucao;
    String texto;

    public InstructionAdapter(LayoutInflater inflater, ArrayList<Instruction> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = inflater;
    }

    public int getCount()
    {
        return itens.size();
    }

    public Instruction getItem(int position)
    {
        return itens.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }
    @Override

    public View getView(int position, View view, ViewGroup parent)
    {
        Instruction item = itens.get(position);
        if(view!=null){
            return view;
        }
        view = mInflater.inflate(R.layout.use_instruction, null);

        Instruction atual = itens.get(position);

        tv_instrucao = (TextView) view.findViewById(R.id.tv_use_inst);
        escutar = (Button) view.findViewById(R.id.btn_listen_inst);
        tem = (CheckBox) view.findViewById(R.id.box_user_has_inst);
        texto = atual.getInstrucao() + " por " + atual.getDur();
        tv_instrucao.setText(texto);

        escutar.setOnClickListener(this);

        tem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tem.isChecked()){
                    tem.setChecked(false);
                } else {
                    tem.setChecked(true);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        // falar text
    }
}