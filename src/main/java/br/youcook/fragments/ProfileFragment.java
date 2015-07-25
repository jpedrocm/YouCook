package br.youcook.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.SignUpCallback;
import br.youcook.R;

public class ProfileFragment extends Fragment {

    Button user_favorites;
    Button user_recipes;

    ImageView user_photo;

    TextView txt0;
    TextView user_hobby;

    EditText et_user_age;
    EditText et_user_name;

    ParseUser user;

    Bundle extras;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        user_favorites = (Button) rootView.findViewById(R.id.button_user_favorites);
        user_recipes = (Button) rootView.findViewById(R.id.button_user_recipes);

        et_user_age = (EditText) rootView.findViewById(R.id.user_age);
        et_user_name = (EditText) rootView.findViewById(R.id.user_name);

        user_photo = (ImageView) rootView.findViewById(R.id.img_photo0);

        user_hobby = (TextView) rootView.findViewById(R.id.user_hobby);

        txt0 = (TextView) rootView.findViewById(R.id.myImageViewText0);

        user = new ParseUser();

        et_user_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final boolean[] b = new boolean[1];
                b[0] = false;
                if (actionId == EditorInfo.IME_ACTION_DONE && !et_user_name.getText().toString().trim().equals("")) {

                    new AlertDialog.Builder(getActivity()).setTitle("Completando")
                            .setMessage("Terminou seu nome?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    user.setUsername(et_user_name.getText().toString().trim());
                                    user.setPassword(et_user_name.getText().toString().trim());

                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(com.parse.ParseException e) {
                                            if (e == null) {
                                                et_user_name.setFocusable(false);
                                                et_user_name.setEnabled(false);
                                                b[0] = true;
                                            }
                                        }
                                    });
                                }})
                            .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }}).show();
                }
                return b[0];
            }
        });

        et_user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !et_user_name.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(getActivity()).setTitle("Completando")
                            .setMessage("Terminou seu nome?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    user.setUsername(et_user_name.getText().toString().trim());
                                    user.setPassword(et_user_name.getText().toString().trim());

                                    user.signUpInBackground(new SignUpCallback() {
                                        @Override
                                        public void done(com.parse.ParseException e) {
                                            if (e == null) {
                                                et_user_name.setFocusable(false);
                                                et_user_name.setEnabled(false);
                                            }
                                        }
                                    });
                                }})
                            .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }}).show();
                }
            }
        });

        user_hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("Escolha")
                        .setMessage("Voce e um chef?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user_hobby.setText("Chef");
                            }
                        })
                        .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user_hobby.setText("Hobby");
                            }
                        }).show();
            }
        });

        user_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pega receitas favoritas
            }
        });

        user_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pega receitas proprias
            }
        });

        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            Bitmap photo_now = (Bitmap) extras.get("data");
            user_photo.setImageBitmap(photo_now);
            txt0.setVisibility(View.INVISIBLE);
        }
    }
}