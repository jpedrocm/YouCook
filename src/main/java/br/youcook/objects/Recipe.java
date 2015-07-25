package br.youcook.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.youcook.R;


public class Recipe implements Parcelable {

    int stars;
    boolean use_oven;
    boolean has_photo;
    boolean salt;
    boolean sweet;
    boolean vegan;
    boolean[] bs;
    double time;
    String id;
    String title;
    String difficulty;
    String chef;
    ArrayList<Ingredient> ings;
    ArrayList<Instruction> inst;

    public Recipe(String id,String title, String difficulty, String chef, double time,
                  int stars, ArrayList<Ingredient> ings, ArrayList<Instruction> inst, boolean foto,
                    boolean sal, boolean doce, boolean forno, boolean vegan){
        this.title = title;
        this.difficulty = difficulty;
        this.chef = chef;
        this.time = time;
        this.id=id;
        this.stars = stars;
        this.ings = ings;
        this.inst = inst;
        this.bs = new boolean[5];
        this.use_oven = forno;
        this.bs[0] = forno;
        this.has_photo = foto;
        this.bs[1] = foto;
        this.vegan = vegan;
        this.bs[2] = vegan;
        this.salt = sal;
        this.bs[3] = sal;
        this.sweet = doce;
        this.bs[4] = doce;
        this.id = id;
    }

    public Recipe(String id,String title, String difficulty, String chef, double time,
                  int stars, boolean foto, boolean sal, boolean doce,
                  boolean forno, boolean vegan){
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.chef = chef;
        this.time = time;
        this.stars = stars;
        this.id = id;
        this.bs = new boolean[5];
        this.use_oven = forno;
        this.bs[0] = forno;
        this.has_photo = foto;
        this.bs[1] = foto;
        this.vegan = vegan;
        this.bs[2] = vegan;
        this.salt = sal;
        this.bs[3] = sal;
        this.sweet = doce;
        this.bs[4] = doce;
    }

    public Recipe(Parcel in){
        this.title = in.readString();
        this.difficulty = in.readString();
        this.chef = in.readString();
        this.time = in.readDouble();
        this.stars = in.readInt();
        in.readBooleanArray(bs);
        in.readList(inst, null);
        in.readList(ings, null);
    }

    public ArrayList<Ingredient> getIngs() {
        return ings;
    }

    public ArrayList<Instruction> getInst() {
        return inst;
    }

    public boolean[] getBs() {
        return bs;
    }

    public int getStars() {
        return stars;
    }

    public String getChef() {
        return chef;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public double getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeInt(stars);
        arg0.writeDouble(time);
        arg0.writeString(chef);
        arg0.writeString(difficulty);
        arg0.writeString(title);
        arg0.writeList(ings);
        arg0.writeList(inst);
        arg0.writeBooleanArray(bs);
    }
}