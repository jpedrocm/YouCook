package br.youcook.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    String unit;
    String name;
    double qt;

    public Ingredient(String unit, String name, double qt){
        this.unit = unit;
        this.name = name;
        this.qt = qt;
    }

    public Ingredient(Parcel in){
        this.unit =in.readString();
        this.name = in.readString();
        this.qt =in.readDouble();
    }

    public double getQt() {
        return qt;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeDouble(qt);
        arg0.writeString(unit);
        arg0.writeString(name);
    }
}