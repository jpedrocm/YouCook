package br.youcook.fragments.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.youcook.R;
import br.youcook.objects.Recipe;

public class RecipeAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<Recipe> itens;

    public RecipeAdapter(LayoutInflater inflater, ArrayList<Recipe> itens)
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

    public Recipe getItem(int position)
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
        Recipe item = itens.get(position);
        if(view!=null){
            return view;
        }
        view = mInflater.inflate(R.layout.item_recipe, null);

        Recipe atual = itens.get(position);
        drawerView(view, atual);

        return view;
    }

    public void drawerView(View v, Recipe atual){
        TextView tv_titulo = (TextView) v.findViewById(R.id.tv_title);
        TextView tv_chef = (TextView) v.findViewById(R.id.tv_chef);
        TextView tv_time = (TextView) v.findViewById(R.id.tv_time);
        TextView tv_favoritos = (TextView) v.findViewById(R.id.tv_favorite_times);

        ImageView img_has_photo = (ImageView) v.findViewById(R.id.img_has_photo);
        ImageView img_dificuldade = (ImageView) v.findViewById(R.id.img_difficulty);

        if(!atual.getBs()[1]){
            img_has_photo.setVisibility(View.INVISIBLE);
        }
        String dif = atual.getDifficulty();

        if(dif.equals("easy")){
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_easy);
        } else if(dif.equals("hard")){
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_hard);
        } else {
            img_dificuldade.setBackgroundResource(R.mipmap.ic_difficulty_medium);
        }

        tv_titulo.setText(atual.getTitle());
        tv_chef.setText(atual.getChef());
        if(atual.getTime() < 2)
            tv_favoritos.setText(atual.getTime() + " minuto");
        else
            tv_time.setText(atual.getTime() + " minutos");

        if(atual.getStars()==1)
            tv_favoritos.setText(atual.getStars() + " vez");
        else
            tv_favoritos.setText(atual.getStars() + " vezes");
    }
}