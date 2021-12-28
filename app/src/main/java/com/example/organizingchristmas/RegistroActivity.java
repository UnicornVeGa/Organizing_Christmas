package com.example.organizingchristmas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    EditText nombre, apellido, edad, email, contrasena, idEvento, contrasenaOrg;
    RadioButton mujer,hombre,otroGenero,invitado,organizador, crearEvento, unirmeAEvento;
    RadioGroup opcionesOrganiz;
    TextView letreroQuiero,letreroIDEvento,letreroContrasenaOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        nombre=findViewById(R.id.registrar_nombre);
        apellido=findViewById(R.id.registrar_apellido);
        edad=findViewById(R.id.registrar_edad);
        email=findViewById(R.id.registrar_email);
        contrasena=findViewById(R.id.registrar_contrasena);
        idEvento=findViewById(R.id.registrar_IDEvento);
        contrasenaOrg=findViewById(R.id.registrar_contrasenaOrganizador);

        mujer=findViewById(R.id.mujer_rb);
        hombre=findViewById(R.id.hombre_rb);
        otroGenero=findViewById(R.id.otroGenero_rb);

        invitado=findViewById(R.id.invitado_rb);
        organizador=findViewById(R.id.organizador_rb);

        letreroIDEvento=findViewById(R.id.ingresaIDEvento_tv);
        letreroQuiero=findViewById(R.id.quiero_tv);
        letreroContrasenaOrg=findViewById(R.id.ingresaContOrg_tv);

        opcionesOrganiz=findViewById(R.id.opcionesOrganizador_rb);

        crearEvento=findViewById(R.id.crearEvento_rb);
        unirmeAEvento=findViewById(R.id.unirmeAEvento_rb);


        invitado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    letreroIDEvento.setVisibility(View.VISIBLE);
                    idEvento.setVisibility(View.VISIBLE);

                    letreroQuiero.setVisibility(View.GONE);
                    opcionesOrganiz.setVisibility(View.GONE);
                    letreroContrasenaOrg.setVisibility(View.GONE);
                    contrasenaOrg.setVisibility(View.GONE);
                }
            }
        });

        organizador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!crearEvento.isChecked()&&!unirmeAEvento.isChecked()||crearEvento.isChecked()){
                        letreroIDEvento.setVisibility(View.GONE);
                        idEvento.setVisibility(View.GONE);
                    }

                    letreroQuiero.setVisibility(View.VISIBLE);
                    opcionesOrganiz.setVisibility(View.VISIBLE);

                    if(unirmeAEvento.isChecked()){
                        letreroContrasenaOrg.setVisibility(View.VISIBLE);
                        contrasenaOrg.setVisibility(View.VISIBLE);
                    }

                    crearEvento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                letreroIDEvento.setVisibility(View.GONE);
                                idEvento.setVisibility(View.GONE);

                                letreroContrasenaOrg.setVisibility(View.GONE);
                                contrasenaOrg.setVisibility(View.GONE);
                            }
                        }
                    });
                    unirmeAEvento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked) {
                                letreroIDEvento.setVisibility(View.VISIBLE);
                                idEvento.setVisibility(View.VISIBLE);

                                letreroContrasenaOrg.setVisibility(View.VISIBLE);
                                contrasenaOrg.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void validacionRegistro(View view){
        String nombre_st=nombre.getText().toString();
        String apellido_st=apellido.getText().toString();
        String edad_st=edad.getText().toString();
        String email_st=email.getText().toString();
        String contrasena_st=contrasena.getText().toString();

        char genero;

        //boolean crearEvento;

        if(!nombre_st.isEmpty()&&!apellido_st.isEmpty()&&!edad_st.isEmpty()&&!email_st.isEmpty()&&!contrasena_st.isEmpty()){
            if(mujer.isChecked()||hombre.isChecked()||otroGenero.isChecked()){
                if(mujer.isChecked()){
                    genero='M';
                }else if(hombre.isChecked()){
                    genero='H';
                }else{
                    genero='O';
                }
                if(invitado.isChecked()||organizador.isChecked()){
                    if(invitado.isChecked()){
                        validacionInvitado(nombre_st,apellido_st,edad_st,genero,email_st,contrasena_st);
                    }else{
                        validacionOrganizador(nombre_st,apellido_st,edad_st,genero,email_st,contrasena_st);
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Por favor selecciona si eres invitado u organizador :)", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(RegistroActivity.this, "Por favor selecciona un género :)", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegistroActivity.this, "Por favor completa todos los campos para registrarte :)", Toast.LENGTH_SHORT).show();
        }
    }

    private void validacionInvitado(String nombre,String apellido, String edad, char genero, String email,String contrasena){
        String idEvento_st=idEvento.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(!idEvento_st.isEmpty()){

            DocumentReference doc = db.collection("eventos").document(idEvento_st);
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            registrarInvUOrg_conID_Evento(nombre, apellido, edad, genero, email, contrasena, idEvento_st);
                        } else {
                            Toast.makeText(RegistroActivity.this, "No existe el evento, revisa el ID por favor", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistroActivity.this, "Error al consultar base: get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(RegistroActivity.this, "Por favor ingresa el ID del evento :)", Toast.LENGTH_SHORT).show();
        }
    }

    private void validacionOrganizador(String nombre,String apellido, String edad, char genero, String email,String contrasena){
        if(crearEvento.isChecked()){
            registrarOrganizador(nombre, apellido, edad, genero, email, contrasena);
        }else if(unirmeAEvento.isChecked()){
            String idEvento_st=idEvento.getText().toString();
            int contrasenaOrg_int=Integer.parseInt(contrasenaOrg.getText().toString());

            if(!idEvento_st.isEmpty()&&!contrasenaOrg.getText().toString().isEmpty()){
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference doc = db.collection("eventos").document(idEvento_st);
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if(Objects.equals(document.get("contrasenaOrganizador",Integer.class), contrasenaOrg_int)){
                                    //doc.update("contrasenaOrganizador",3456);
                                    registrarInvUOrg_conID_Evento(nombre, apellido, edad, genero, email, contrasena, idEvento_st);
                                }else {
                                    Toast.makeText(RegistroActivity.this, "La contraseña de organizador es incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegistroActivity.this, "No existe el evento, revisa el ID por favor", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistroActivity.this, "Error al consultar base: get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(RegistroActivity.this, "Por favor ingresa el ID del evento :)", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(RegistroActivity.this, "Por favor selecciona una opcion como organizador :)", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrarInvUOrg_conID_Evento(String nombre,String apellido, String edad,char genero,String email,String contrasena,String idEvento){

        mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user=mAuth.getCurrentUser();
                    String uid=user.getUid();
                    HashMap<String,String> tablaDatos=new HashMap();

                    tablaDatos.put("nombre",nombre);
                    tablaDatos.put("apellido",apellido);
                    tablaDatos.put("edad",edad);
                    //tablaDatos.put("genero",genero);
                    tablaDatos.put("email",email);
                    tablaDatos.put("contrasena",contrasena);
                    tablaDatos.put("idEvento",idEvento);

                    //añadir booleano invitado=invitado.isChecked;

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("users").document(uid).set(tablaDatos).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task){
                            if (task.isSuccessful()) {
                                Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito :).", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegistroActivity.this, "Ocurió un error al guardar la información", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarOrganizador(String nombre,String apellido, String edad,char genero,String email,String contrasena){
        mAuth.createUserWithEmailAndPassword(email, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user=mAuth.getCurrentUser();
                    String uid=user.getUid();
                    HashMap<String,String> tablaDatos=new HashMap();

                    tablaDatos.put("nombre",nombre);
                    tablaDatos.put("apellido",apellido);
                    tablaDatos.put("edad",edad);
                    //tablaDatos.put("genero",genero);
                    tablaDatos.put("email",email);
                    tablaDatos.put("contrasena",contrasena);
                    //falta agregar genero y booleano de invitado=false;

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("users").document(uid).set(tablaDatos).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task){
                            if (task.isSuccessful()) {
                                DocumentReference refNuevoEvento=db.collection("eventos").document();//crear el nuevo evento
                                //refNuevoEvento.set("IDEvento":refNuevoEvento.getId());
                                Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito :).", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegistroActivity.this, "Ocurió un error al guardar la información", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

/* Antigua y original activiyRegistrare

public class RegistrarseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.registrar_email_et);
        contrasena=findViewById(R.id.registrar_contrasena_et);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void registrarUsuario(View view){
        String email_string=email.getText().toString();
        String contrasena_string=contrasena.getText().toString();

        mAuth.createUserWithEmailAndPassword(email_string, contrasena_string).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(RegistrarseActivity.this, "Usuario creado :).", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent=new Intent(RegistrarseActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistrarseActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}*/