package br.youcook.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {

    String dur;
    String instrucao;

    public Instruction(String instrucao, String dur){
        this.dur = dur;
        this.instrucao = instrucao;
    }

    public Instruction(Parcel in){
        this.instrucao =in.readString();
        this.dur = in.readString();
    }

    public String getDur() {
        return dur;
    }

    public String getInstrucao() {
        return instrucao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(instrucao);
        arg0.writeString(dur);
    }
}