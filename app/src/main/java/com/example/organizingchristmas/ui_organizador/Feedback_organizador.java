package com.example.organizingchristmas.ui_organizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizingchristmas.ui_invitado.Feedback_invitado;

import com.example.organizingchristmas.R;

public class Feedback_organizador extends AppCompatActivity {

    Button boton_regresar;
    TextView calificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_organizador);

        calificacion=findViewById(R.id.calificacion_tv);

        calificacion.setText("Tu promedio fue de "+Feedback_invitado.total_acumulado);

        boton_regresar=findViewById(R.id.regresar_bt);

        boton_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Feedback_organizador.this, "Regresando", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}