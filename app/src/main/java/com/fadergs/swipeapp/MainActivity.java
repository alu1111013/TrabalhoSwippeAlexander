package com.fadergs.swipeapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.pro.adalto.quiztouch.OnSwipeTouchListener;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout tela;
    TextView tvSwipe;
    TextView tvSim;
    TextView tvNao;
    Button button;

    int contador;


    String[] perguntass = {"A fórmula da agua é H2O?", "Guaiba é um rio?", "Porto Alegre é a capítal do RS?", "Inter é Azul?"};
    String[] gabaritoss = {"sim", "nao", "sim", "nao"};
    String[] respostass = {null, null, null, null};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tela = findViewById(R.id.tela);
        tvSim = findViewById(R.id.tvSim);
        tvNao = findViewById(R.id.tvNao);
        tvSwipe = findViewById(R.id.tvSwipe);

        contador = -1;


        tela.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeBottom() {
                if (contador >= 0) {
                    super.onSwipeBottom();
                    respostass[contador] = "nao";
                    tvNao.setText("NÃO");
                    tvSim.setText("");

                }
                checarSeTodasPerguntasForamRespondidas(respostass);
            }

            @Override
            public void onSwipeTop() {
                if (contador >= 0) {
                    super.onSwipeTop();
                    respostass[contador] = "sim";
                    tvSim.setText("SIM");
                    tvNao.setText("");
                }
                checarSeTodasPerguntasForamRespondidas(respostass);

            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                tvNao.setText("");
                tvSim.setText("");

                if (contador <= 0) {
                    contador = perguntass.length - 1;
                } else {
                    contador--;
                }
                tvSwipe.setText(perguntass[contador]);
            }

            @Override
            public void onSwipeRight() {
                tvNao.setText("");
                tvSim.setText("");
                super.onSwipeRight();
                if (contador == perguntass.length - 1 || contador < 0) {
                    contador = 0;
                } else {
                    contador++;

                }
                tvSwipe.setText(perguntass[contador]);
            }
        });

    }

    int retornaQuantosAcertos(String[] respostas, String[] gabaritoss) {
        int total = 0;
        for (int i = 0; i < respostas.length; i++) {
            if (respostas[i].equals(gabaritoss[i])) {
                total++;
            }
        }
        return total;
    }

    void checarSeTodasPerguntasForamRespondidas(String[] respostas) {
        boolean todasRespondidas = true;
        for (String resposta : respostas) {
            if (resposta == null) {
                todasRespondidas = false;
            }
        }
        if (todasRespondidas) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("PONTUAÇÃO");
            alerta.setMessage("Você acertou " + retornaQuantosAcertos(respostass, gabaritoss) + " de " + gabaritoss.length);
            alerta.setNeutralButton("OK", null);
            alerta.show();
        }

    }

}
