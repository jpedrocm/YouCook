package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.youcook.R;
import br.youcook.fragments.adapter.IngredientAdapter;
import br.youcook.objects.Ingredient;
import br.youcook.objects.Instruction;

public class UseIngredientsFragment extends Fragment implements View.OnClickListener {

    Button btn_usar_instrucoes;

    ListView lv_ings;

    android.support.v4.app.FragmentManager fm;

    ConnectivityManager connManager;

    ArrayList<Ingredient> ingredients;

    int temIngrediente;

    String id;

    LayoutInflater inflater;

    Bundle args;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_have_ingredients, container, false);
        this.inflater = inflater;
        lv_ings = (ListView) rootView.findViewById(R.id.lv_use_ings);
        args = getArguments();
        ingredients = args.getParcelableArrayList("ingredientes");
        lv_ings.setAdapter(new IngredientAdapter(inflater, ingredients));

        temIngrediente = 0;

        id = args.getString("id_receita");

        fm = getFragmentManager();

        connManager = (ConnectivityManager) getActivity().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        btn_usar_instrucoes = (Button) rootView.findViewById(R.id.btn_use_instructions);

        btn_usar_instrucoes.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {

        NetworkInfo net = connManager.getActiveNetworkInfo();

        if(net == null || !net.isConnected()){
            new AlertDialog.Builder(getActivity()).setTitle("Falha!")
                    .setMessage("Ligue sua internet.")
                    .setIcon(R.id.alertTitle)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){}}).show();
            return;
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Instrucao");
        query.whereEqualTo("id_receita", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    String instrucao, duration;

                    Bundle bundle = new Bundle();

                    ArrayList<Instruction> instructions = new ArrayList<>();

                    for (ParseObject listElement : list) {
                        instrucao = listElement.getString("nome_instrucao");
                        duration = listElement.getString("duracao_instrucao");
                        instructions.add(new Instruction(instrucao, duration));
                    }

                    bundle.putParcelableArrayList("instrucoes", instructions);

                    UseInstructionsFragment newFragment = new UseInstructionsFragment();
                    FragmentTransaction ft = fm.beginTransaction();
                    newFragment.setArguments(bundle);
                    ft.replace(R.id.rl_fuing, newFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }
}