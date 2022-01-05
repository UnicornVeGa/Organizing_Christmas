package com.example.organizingchristmas.ui_organizador.cronograma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.organizingchristmas.R;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ListAdapter extends ArrayAdapter<Evento> {

    public ListAdapter(Context context, ArrayList<Evento> lista_eventos){
        super(context, R.layout.renglon_actividad_cronograma,lista_eventos);
    }

    @NonNull
    @Override
    public View getView(int posicion, @Nullable View convetView, @NonNull ViewGroup parent){
        Evento evento = getItem(posicion);

        if(convetView==null){
            convetView= LayoutInflater.from(getContext()).inflate(R.layout.renglon_actividad_cronograma,parent,false);
        }

        TextView hora=convetView.findViewById(R.id.hora_tv);
        TextView actividad=convetView.findViewById(R.id.actividad_tv);
        TextView organiz=convetView.findViewById(R.id.organizador_tv);

        hora.setText(evento.getHora());
        actividad.setText(evento.getDescripcion());
        organiz.setText(evento.getOrganizador());

        return super.getView(posicion,convetView,parent);
    }
}
