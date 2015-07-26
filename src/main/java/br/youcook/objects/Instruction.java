package br.youcook.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Instruction implements Parcelable {

    String dur;
    String instrucao;
    int posicao;

    public Instruction(String instrucao, String dur, int posicao){
        this.dur = dur;
        this.instrucao = instrucao;
        this.posicao = posicao;
    }

    public Instruction(Parcel in){
        this.instrucao =in.readString();
        this.dur = in.readString();
        this.posicao = in.readInt();
    }

    public String getDur() {
        return dur;
    }

    public String getInstrucao() {
        return instrucao;
    }

    public int getPosicao() {
        return posicao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        arg0.writeString(instrucao);
        arg0.writeString(dur);
        arg0.writeInt(posicao);
    }
}