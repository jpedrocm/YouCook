package br.youcook;

import android.app.Application;
import com.parse.Parse;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "5pW4uFuqNL3ncR7BT1XXyNk9bpcgG8wB5jSCcMCs", "xZd6iHqnXc3jfCfpHPk0KbY7VyxbmzOcps5rJ2vS");
    }
} 