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
import br.youcook.fragments.adapter.RecipeAdapter;
import br.youcook.objects.Recipe;

public class SearchResultsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button btn_nova_pesquisa;

    ListView lv_recipes;

    android.support.v4.app.FragmentManager fm;

    ArrayList<Recipe> recipes;

    LayoutInflater inflater;

    Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        this.inflater = inflater;
        lv_recipes = (ListView) rootView.findViewById(R.id.lv_search_results);
        args = getArguments();
        recipes = args.getParcelableArrayList("receitas");
        lv_recipes.setOnItemClickListener(this);
        lv_recipes.setAdapter(new RecipeAdapter(inflater ,recipes));

        fm = getFragmentManager();

        btn_nova_pesquisa = (Button) rootView.findViewById(R.id.btn_nova_pesquisa);

        btn_nova_pesquisa.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        SearchRecipeFragment newFragment = new SearchRecipeFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fsresult, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(recipes != null && recipes.size() > 0){
            Bundle next = new Bundle();
            Recipe aux = recipes.get(position);
            next.putParcelable("receita", aux);

            RecipeFragment newFragment = new RecipeFragment();

            FragmentTransaction ft = fm.beginTransaction();
            newFragment.setArguments(next);
            ft.replace(R.id.rl_fur, newFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}