package br.youcook.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.youcook.R;
import br.youcook.fragments.adapter.IngredientAdapter;
import br.youcook.fragments.adapter.RecipeAdapter;
import br.youcook.objects.Ingredient;
import br.youcook.objects.Recipe;

public class UseIngredientsFragment extends Fragment implements View.OnClickListener {

    Button btn_usar_instrucoes;

    ListView lv_ings;

    Recipe recipe;

    android.support.v4.app.FragmentManager fm;

    ArrayList<Ingredient> ingredients;
    int temIngrediente;

    LayoutInflater inflater;

    Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_have_ingredients, container, false);
        this.inflater = inflater;
        lv_ings = (ListView) rootView.findViewById(R.id.lv_use_ings);
        args = getArguments();
        lv_ings.setAdapter(new IngredientAdapter(inflater, ingredients));

        temIngrediente = 0;

        fm = getFragmentManager();

        btn_usar_instrucoes = (Button) rootView.findViewById(R.id.btn_use_instructions);

        btn_usar_instrucoes.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        UseInstructionsFragment newFragment = new UseInstructionsFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fuing, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}