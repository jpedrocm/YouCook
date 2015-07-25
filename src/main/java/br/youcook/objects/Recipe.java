package br.youcook.objects;

import android.os.Parcel;
import android.os.Parcelable;

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
        arg0.writeBooleanArray(bs);
    }
}