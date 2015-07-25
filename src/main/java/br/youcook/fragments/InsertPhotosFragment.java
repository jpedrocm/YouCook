package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import br.youcook.R;
import br.youcook.objects.Ingredient;
import br.youcook.objects.Instruction;

public class InsertPhotosFragment extends Fragment implements View.OnClickListener {

    Button btn_fim;

    ImageView btn_photo1;
    ImageView btn_photo2;

    TextView txt1;
    TextView txt2;

    Bitmap photo_now;

    ParseUser user;

    boolean justClicked;
    boolean temPhotos;

    ArrayList<Ingredient> ingredientes;
    ArrayList<Instruction> instrucoes;

    EditText et_photo1;
    EditText et_photo2;

    android.support.v4.app.FragmentManager fm;

    ConnectivityManager connManager;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    Bundle args, extras;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_insert_photos, container, false);

        btn_photo1 = (ImageView) rootView.findViewById(R.id.img_photo1);
        btn_photo2 = (ImageView) rootView.findViewById(R.id.img_photo2);

        et_photo1 = (EditText) rootView.findViewById(R.id.et_details1);
        et_photo2 = (EditText) rootView.findViewById(R.id.et_details2);

        txt1 = (TextView) rootView.findViewById(R.id.myImageViewText1);
        txt2 = (TextView) rootView.findViewById(R.id.myImageViewText2);

        fm = getFragmentManager();

        connManager = (ConnectivityManager) getActivity().getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        args = getArguments();

        btn_fim = (Button) rootView.findViewById(R.id.btn_finalizar);

        temPhotos = false;

        btn_fim.setOnClickListener(this);

        btn_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justClicked = true;
                dispatchTakePictureIntent();
            }
        });

        btn_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justClicked = false;
                dispatchTakePictureIntent();
            }
        });

        return rootView;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            extras = data.getExtras();
            photo_now = (Bitmap) extras.get("data");
            if(justClicked){
                btn_photo1.setImageBitmap(photo_now);
                photo_now = null;
                txt1.setVisibility(View.INVISIBLE);
            } else {
                btn_photo2.setImageBitmap(photo_now);
                photo_now = null;
                txt2.setVisibility(View.INVISIBLE);
            }
            temPhotos = true;
        }
    }

    public byte[] Bitmap2Byte(Drawable photo){
        Bitmap bitmap = ((BitmapDrawable) photo).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return (stream.toByteArray());
    }

    public void onClick(View v) {

        Drawable p1, p2;

        final ParseObject recipe = new ParseObject("Recipe");
        ParseFile file1, file2;

        String titulo, dificuldade, details1, details2;
        double tempo;
        boolean doce, salgado, vegan, forno;

        instrucoes = args.getParcelableArrayList("inst");
        ingredientes = args.getParcelableArrayList("ings");
        titulo = args.getString("titulo");
        tempo = args.getDouble("tempo");
        salgado = args.getBoolean("salgado");
        doce = args.getBoolean("doce");
        vegan = args.getBoolean("vegan");
        forno = args.getBoolean("forno");
        dificuldade = args.getString("diff");

        user = ParseUser.getCurrentUser();

        if(user==null){
            new AlertDialog.Builder(getActivity()).setTitle("Falha!")
                    .setMessage("Logue no perfil editando seu nome.")
                    .setIcon(R.id.alertTitle)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){}}).show();
            return;
        }

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

        recipe.put("tempo", tempo);
        recipe.put("salgado", salgado);
        recipe.put("doce",doce);
        recipe.put("forno",forno);
        recipe.put("vegan", vegan);
        recipe.put("dificuldade",dificuldade);
        recipe.put("titulo", titulo);
        recipe.put("foto", temPhotos);
        recipe.put("favoritos", 0);

        if(temPhotos) {
            p1 = btn_photo1.getDrawable();
            p2 = btn_photo2.getDrawable();
            details1 = et_photo1.getText().toString().trim();
            details2 = et_photo2.getText().toString().trim();
            recipe.put("details1", details1);
            recipe.put("details2", details2);

            if(p1==null){
                file2 = new ParseFile("foto_receita2.png", Bitmap2Byte(p2));

                recipe.put("d2", file2);
                recipe.put("d1", JSONObject.NULL);

            } else if(p2==null){
                file1 = new ParseFile("foto_receita1.png", Bitmap2Byte(p1));

                recipe.put("d1", file1);
                recipe.put("d2", JSONObject.NULL);

            } else {
                file1 = new ParseFile("foto_receita1.png", Bitmap2Byte(p1));
                file2 = new ParseFile("foto_receita2.png", Bitmap2Byte(p2));

                recipe.put("d1", file1);
                recipe.put("d2", file2);
            }
        } else {
            recipe.put("details1", "");
            recipe.put("details2", "");
            recipe.put("d1", JSONObject.NULL);
            recipe.put("d2", JSONObject.NULL);
        }

        recipe.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    String id = recipe.getObjectId();

                    ArrayList<ParseObject> ig_e_it = new ArrayList<>();

                    for (int i = 0; i < ingredientes.size(); i++) {
                        ParseObject atual = new ParseObject("Ingrediente");
                        atual.put("nome_ingrediente", ingredientes.get(i).getName());
                        atual.put("unidade_ingrediente", ingredientes.get(i).getUnit());
                        atual.put("quantidade_ingrediente", ingredientes.get(i).getQt());
                        atual.put("id_receita", id);
                        ig_e_it.add(atual);
                    }

                    for (int i = 0; i < instrucoes.size(); i++) {
                        ParseObject atual = new ParseObject("Instrucao");
                        atual.put("duracao_instrucao", instrucoes.get(i).getDur());
                        atual.put("nome_instrucao", instrucoes.get(i).getInstrucao());
                        atual.put("id_receita", id);
                        ig_e_it.add(atual);
                    }

                    ParseObject.saveAllInBackground(ig_e_it, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.d("terminou", "true");
                        }
                    });

                    CreateRecipeFragment newFragment = new CreateRecipeFragment();

                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.rl_fcr, newFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }
}