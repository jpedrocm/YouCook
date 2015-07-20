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
import br.youcook.objects.Ingredient;

public class InsertIngredientsFragment extends Fragment implements View.OnClickListener {

    Button btn_instructions;
    Button btn_more_ingredients;

    android.support.v4.app.FragmentManager fm;

    LinearLayout itens;

    Bundle args;

    int quantidadeIngredientes;

    Vector<IngredientFragment> vIngredients;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_insert_ingredients, container, false);

        fm = getFragmentManager();

        args = getArguments();

        itens = (LinearLayout) rootView.findViewById(R.id.itens_ing);

        btn_instructions = (Button) rootView.findViewById(R.id.btn_instructions);
        btn_more_ingredients = (Button) rootView.findViewById(R.id.btn_more_ingredient);

        btn_instructions.setOnClickListener(this);

        vIngredients = new Vector<>();

        quantidadeIngredientes = 0;

        initialItem();

        btn_more_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vIngredients.add(new IngredientFragment());

                RelativeLayout rl = new RelativeLayout(getActivity().getApplicationContext());
                rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                rl.setId(quantidadeIngredientes+1);

                itens.addView(rl, quantidadeIngredientes);

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(quantidadeIngredientes+1, vIngredients.elementAt(quantidadeIngredientes));
                ft.commit();
                quantidadeIngredientes++;
            }
        });

        return rootView;
    }

    public void initialItem(){
        vIngredients.add(new IngredientFragment());

        RelativeLayout rl = new RelativeLayout(getActivity().getApplicationContext());
        rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        rl.setId(quantidadeIngredientes+1);
        itens.addView(rl, 0);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(quantidadeIngredientes+1, vIngredients.elementAt(0));
        ft.commit();
        quantidadeIngredientes++;
    }

    public void onClick(View v) {
        InsertInstructionsFragment newFragment = new InsertInstructionsFragment();

        Bundle aux;
        String unit, name;
        double qt;

        ArrayList<Ingredient> ings = new ArrayList<>();

        for(int i = 0; i < vIngredients.size(); i++){
            aux = vIngredients.elementAt(i).getBundle();
            unit = aux.getString("unit");
            name = aux.getString("name");
            qt = aux.getDouble("qt");

            if(name.equals("") || unit.equals("") || qt <=0){
                new AlertDialog.Builder(getActivity()).setTitle("Campo obrigatório!")
                        .setMessage("Preecha o ingrediente " + (i + 1) + " por completo.")
                        .setIcon(R.id.alertTitle)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which){}}).show();
                ings.clear();
                return;
            }

            ings.add(new Ingredient(unit, name, qt));
        }

        args.putParcelableArrayList("ings", ings);
        newFragment.setArguments(args);

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fiing, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}