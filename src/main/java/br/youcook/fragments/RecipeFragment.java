package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import br.youcook.R;
import br.youcook.objects.Recipe;

public class RecipeFragment extends Fragment implements View.OnClickListener {

    Bundle args;

    TextView tv_sal;
    TextView tv_doce;
    TextView tv_vegan;
    TextView tv_forno;
    TextView tv_chef;
    TextView tv_titulo;

    boolean favorito;

    ParseUser user;

    ImageView img_dificuldade;
    ImageView img_favoritar;

    FragmentManager fm;

    ConnectivityManager connManager;

    Button btn_preparar;

    Recipe receita;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        tv_doce = (TextView) rootView.findViewById(R.id.doce_use);
        tv_sal = (TextView) rootView.findViewById(R.id.sal_use);
        tv_forno = (TextView) rootView.findViewById(R.id.forno_use);
        tv_vegan = (TextView) rootView.findViewById(R.id.vegan_use);
        tv_chef = (TextView) rootView.findViewById(R.id.tv_chef_use);
        tv_titulo = (TextView) rootView.findViewById(R.id.tv_use_title);

        img_dificuldade = (ImageView) rootView.findViewById(R.id.img_use_dificuldade);
        img_favoritar = (ImageView) rootView.findViewById(R.id.img_favoritar_use);
        btn_preparar = (Button) rootView.findViewById(R.id.btn_preparar_use);

        args = getArguments();

        fm = getFragmentManager();

        connManager = (ConnectivityManager) getActivity().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        receita = args.getParcelable("receita");

        user = ParseUser.getCurrentUser();

        img_favoritar.setBackgroundResource(R.mipmap.ic_unmarked_star);
        favorito = false;

        tv_chef.setText("Por: "+ receita.getChef());
        tv_titulo.setText(receita.getTitle());

        if(receita.getBs()[4]){
            tv_doce.setText("Eh doce");
        } else {
            tv_doce.setText("Nao eh doce");
        }

        if(receita.getBs()[3]){
            tv_sal.setText("Eh salgado");
        } else {
            tv_sal.setText("Nao eh salgado");
        }

        if(receita.getBs()[2]){
            tv_vegan.setText("Eh vegan");
        } else {
            tv_vegan.setText("Nao eh vegan");
        }

        if(receita.getBs()[0]){
            tv_forno.setText("Usa forno");
        } else {
            tv_forno.setText("Nao usa forno");
        }

        if(receita.getDifficulty().equals("hard")){
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_hard);
        } else if(receita.getDifficulty().equals("easy")){
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_easy);
        } else {
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_medium);
        }


        img_favoritar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!favorito) {
                    img_favoritar.setBackgroundResource(R.mipmap.ic_marked_star);
                    favorito = true;
                } else {
                    img_favoritar.setBackgroundResource(R.mipmap.ic_unmarked_star);
                    favorito = false;
                }
            }
        });

        btn_preparar.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("id", receita.getId());

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

        UseIngredientsFragment newFragment = new UseIngredientsFragment();
        FragmentTransaction ft = fm.beginTransaction();

        newFragment.setArguments(bundle);
        ft.replace(R.id.rl_fur, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}