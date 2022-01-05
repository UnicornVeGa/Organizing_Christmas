package com.example.organizingchristmas.ui_organizador.cronograma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.organizingchristmas.R;

import java.util.ArrayList;

public class editor_cronograma extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_cronograma);

        listView=findViewById(R.id.listView_actividades);

        //Crear lista de eventos
        ArrayList<Evento> lista_eventos=new ArrayList<>();


    }
}