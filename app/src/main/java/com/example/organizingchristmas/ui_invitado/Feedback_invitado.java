package com.example.organizingchristmas.ui_invitado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.organizingchristmas.HomeActivity;
import com.example.organizingchristmas.MainActivity;
import com.example.organizingchristmas.R;

import com.example.organizingchristmas.ui_organizador.Feedback_organizador;

public class Feedback_invitado extends AppCompatActivity {
    public static float total_acumulado;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_invitado);

        ratingBar=findViewById(R.id.barraDeEstrellas);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(Feedback_invitado.this, "Votaste con "+rating, Toast.LENGTH_SHORT).show();
                total_acumulado+=rating;

                Intent intent = new Intent(Feedback_invitado.this, Feedback_organizador.class);
                startActivity(intent);
            }
        });

    }
}