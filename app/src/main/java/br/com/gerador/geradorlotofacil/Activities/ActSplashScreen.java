package br.com.gerador.geradorlotofacil.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.gerador.geradorlotofacil.R;

public class ActSplashScreen extends AppCompatActivity implements Runnable {
    /* Variáveis */
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash_screen);

        handler.postDelayed(this, 3000);
    }

    @Override
    public void run() {
        startActivity(new Intent(this, ActSorteio.class));
        finish();
    }
}
