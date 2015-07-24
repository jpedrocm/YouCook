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
import br.youcook.objects.Ingredient;

public class IngredientAdapter extends BaseAdapter implements View.OnClickListener
{
    private LayoutInflater mInflater;
    private ArrayList<Ingredient> itens;
    CheckBox tem;
    Button escutar;
    TextView tv_ingrediente;
    String texto;

    public IngredientAdapter(LayoutInflater inflater, ArrayList<Ingredient> itens)
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

    public Ingredient getItem(int position)
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
        Ingredient item = itens.get(position);
        if(view!=null){
            return view;
        }
        view = mInflater.inflate(R.layout.use_ingredient, null);

        Ingredient atual = itens.get(position);

        tv_ingrediente = (TextView) view.findViewById(R.id.tv_use_ing);
        escutar = (Button) view.findViewById(R.id.btn_listen_ing);
        tem = (CheckBox) view.findViewById(R.id.box_user_has_ing);
        texto = atual.getQt() + " " + atual.getUnit() + " de " + atual.getName();
        tv_ingrediente.setText(texto);

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