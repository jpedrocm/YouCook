package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.youcook.R;
import br.youcook.objects.Recipe;

public class SearchRecipeFragment extends Fragment implements View.OnClickListener {

    boolean vegan;
    boolean salt;
    boolean sweet;
    boolean forno;
    boolean foto;

    CheckBox box_vegan;
    CheckBox box_salt;
    CheckBox box_sweet;
    CheckBox box_forno;
    CheckBox box_foto;

    Button btn_less_difficult;
    Button btn_more_difficult;
    Button btn_pesquisar;

    ImageView im_difficulty_create;

    ArrayList<Recipe> recipes;

    android.support.v4.app.FragmentManager fm;

    EditText et_time;

    double minutos;

    String dificuldade;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_search_recipe, container, false);

        et_time = (EditText) rootView.findViewById(R.id.et_time_search);

        btn_less_difficult = (Button) rootView.findViewById(R.id.btn_less_difficult_search);
        btn_more_difficult = (Button) rootView.findViewById(R.id.btn_more_difficult_search);
        btn_pesquisar = (Button) rootView.findViewById(R.id.btn_pesquisar_search);

        im_difficulty_create = (ImageView) rootView.findViewById(R.id.im_difficulty_search);

        box_sweet = (CheckBox) rootView.findViewById(R.id.box_sweet_search);
        box_salt = (CheckBox) rootView.findViewById(R.id.box_salt_search);
        box_vegan = (CheckBox) rootView.findViewById(R.id.box_vegetarian_search);
        box_forno = (CheckBox) rootView.findViewById(R.id.box_forno_search);
        box_foto = (CheckBox) rootView.findViewById(R.id.box_has_photo_search);

        sweet = box_sweet.isChecked();
        salt = box_salt.isChecked();
        vegan = box_vegan.isChecked();
        forno = box_forno.isChecked();
        foto = box_forno.isChecked();

        dificuldade = "easy";
        minutos = -1;
        recipes = new ArrayList<>();

        fm = getFragmentManager();

        btn_pesquisar.setOnClickListener(this);

        btn_less_difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dificuldade.equals("medium")){
                    dificuldade = "easy";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_easy);
                } else if(dificuldade.equals("hard")){
                    dificuldade = "medium";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_medium);
                }

            }
        });

        btn_more_difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dificuldade.equals("easy")){
                    dificuldade = "medium";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_medium);
                } else if(dificuldade.equals("medium")){
                    dificuldade = "hard";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_hard);
                }
            }
        });

        box_sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box_sweet.setChecked(box_sweet.isChecked());
                sweet = box_sweet.isChecked();
            }
        });

        box_forno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box_forno.setChecked(box_forno.isChecked());
                forno = box_forno.isChecked();
            }
        });

        box_salt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box_salt.setChecked(box_salt.isChecked());
                salt = box_salt.isChecked();
            }
        });

        box_vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box_vegan.setChecked(box_vegan.isChecked());
                vegan = box_vegan.isChecked();
            }
        });

        box_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box_foto.setChecked(box_foto.isChecked());
                foto = box_foto.isChecked();
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Aguarde!");
        builder.setMessage("Buscando dados");
        builder.setCancelable(false);
        final AlertDialog alerta = builder.create();
        alerta.show();

        String sMinutos = et_time.getText().toString().trim();

        if(!sMinutos.equals("")){
            minutos = Double.parseDouble(sMinutos);
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.whereEqualTo("forno", forno);
        query.whereEqualTo("doce", sweet);
        query.whereEqualTo("salgado", salt);
        query.whereEqualTo("vegan", vegan);
        query.whereEqualTo("dificuldade", dificuldade);
        query.orderByDescending("favoritos");
        if(minutos != -1){
            query.whereLessThanOrEqualTo("tempo", minutos);
        }
        if (foto) {
            query.whereEqualTo("foto", true);
        }

        final Bundle args = new Bundle();
        final boolean[] c = new boolean[1];

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + list.size() + " scores");
                    Recipe aux;

                    c[0] = list.size() == 0;

                    boolean doce, salgado, foto, forno, vegan;
                    String titulo, dificuldade, id, chef;
                    double tempo, stars;

                    for (ParseObject listElement : list) {
                        doce = listElement.getBoolean("doce");
                        salgado = listElement.getBoolean("salgado");
                        foto = listElement.getBoolean("foto");
                        forno = listElement.getBoolean("forno");
                        vegan = listElement.getBoolean("vegan");
                        titulo = listElement.getString("titulo");
                        dificuldade = listElement.getString("dificuldade");
                        tempo = listElement.getDouble("tempo");
                        stars = listElement.getDouble("favoritos");
                        id = listElement.getObjectId();
                        chef =  listElement.getString("chefe");

                        aux = new Recipe(id, titulo, dificuldade, chef, tempo,
                                (int) stars, foto, salgado, doce, forno, vegan);
                        recipes.add(aux);
                    }

                    if (!c[0]){
                        args.putParcelableArrayList("receitas", recipes);
                        SearchResultsFragment newFragment = new SearchResultsFragment();
                        FragmentTransaction ft = fm.beginTransaction();
                        Log.d("quantidade de itens",""+recipes.size());
                        newFragment.setArguments(args);

                        ft.replace(R.id.rl_fsr, newFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        alerta.cancel();
                    } else {
                        alerta.cancel();
                        new AlertDialog.Builder(getActivity()).setTitle("Ops!")
                                .setMessage("Nao houve resultados.")
                                .setIcon(R.id.alertTitle)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){}}).show();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}