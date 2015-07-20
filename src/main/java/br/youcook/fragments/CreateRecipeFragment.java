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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import br.youcook.R;

public class CreateRecipeFragment extends Fragment implements View.OnClickListener {

    private boolean sweet;
    private boolean salt;
    private boolean vegan;
    private boolean forno;
    private String difficult;
    private String titulo;
    private double time;

    EditText et_titulo;
    EditText et_time;

    Button btn_less_difficult;
    Button btn_more_difficult;
    Button btn_ingredientes;

    ImageView im_difficulty_create;

    CheckBox box_sweet;
    CheckBox box_salt;
    CheckBox box_vegan;
    CheckBox box_forno;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        et_titulo = (EditText) rootView.findViewById(R.id.et_titulo_create);
        et_time = (EditText) rootView.findViewById(R.id.et_time_create);

        btn_less_difficult = (Button) rootView.findViewById(R.id.btn_less_difficult_create);
        btn_more_difficult = (Button) rootView.findViewById(R.id.btn_more_difficult_create);
        btn_ingredientes = (Button) rootView.findViewById(R.id.btn_ingredientes_create);

        im_difficulty_create = (ImageView) rootView.findViewById(R.id.im_difficulty_create);

        box_sweet = (CheckBox) rootView.findViewById(R.id.box_sweet_create);
        box_salt = (CheckBox) rootView.findViewById(R.id.box_salt_create);
        box_vegan = (CheckBox) rootView.findViewById(R.id.box_vegetarian_create);
        box_forno = (CheckBox) rootView.findViewById(R.id.box_forno_create);

        sweet = box_sweet.isChecked();
        salt = box_salt.isChecked();
        vegan = box_vegan.isChecked();
        forno = box_forno.isChecked();

        difficult = "easy";

        btn_less_difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficult.equals("medium")){
                    difficult = "easy";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_easy);
                } else if(difficult.equals("hard")){
                    difficult = "medium";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_medium);
                }

            }
        });

        btn_more_difficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficult.equals("easy")){
                    difficult = "medium";
                    im_difficulty_create.setImageResource(R.mipmap.ic_difficulty_medium);
                } else if(difficult.equals("medium")){
                    difficult = "hard";
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

        btn_ingredientes.setOnClickListener(this);

        return rootView;
    }

    public void onClick(View v) {
        InsertIngredientsFragment newFragment = new InsertIngredientsFragment();

        Bundle args = new Bundle();

        if(et_titulo.getText().toString().trim().equals("")){
            new AlertDialog.Builder(getActivity()).setTitle("Campo obrigatório!").setMessage("Preecha o título.")
                    .setIcon(R.id.alertTitle)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which){}}).show();
            return;
        }

        if(et_time.getText().toString().trim().equals("")){
            new AlertDialog.Builder(getActivity()).setTitle("Campo obrigatório!").setMessage("Preecha o tempo de preparo.")
                    .setIcon(R.id.alertTitle)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which){}}).show();
            return;
        }

        titulo = et_titulo.getText().toString().trim();
        String sTime = et_time.getText().toString().trim();
        time = Double.parseDouble(sTime);

        args.putString("titulo", titulo);
        args.putDouble("tempo", time);
        args.putBoolean("salgado", salt);
        args.putBoolean("doce", sweet);
        args.putBoolean("vegan", vegan);
        args.putString("diff", difficult);
        args.putBoolean("forno", forno);

        newFragment.setArguments(args);

        android.support.v4.app.FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_fcr, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}