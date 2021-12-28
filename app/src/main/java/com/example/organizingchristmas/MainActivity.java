package com.example.organizingchristmas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



/*public class MainActivity extends AppCompatActivity {
    EditText email, contrasena;
    Button iniciarSesion_bt,irARegistrarse_bt;
    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.registrar_email_et);
        contrasena=findViewById(R.id.registrar_contrasena_et);
        iniciarSesion_bt=findViewById(R.id.iniciar_sesion_bt);
        irARegistrarse_bt=findViewById(R.id.registrarse_bt);


        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = mFireBaseAuth.getCurrentUser();
                if (fuser != null) {
                    Toast.makeText(MainActivity.this, "Has ingresado con éxito!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Por favor, regístrate", Toast.LENGTH_SHORT).show();
                }
            }
        };

        iniciarSesion_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string=email.getText().toString();
                String contrasena_string=contrasena.getText().toString();

                if(email_string.isEmpty()){
                    email.setError("Por favor ingresa tu correo");
                    email.requestFocus();
                }else if(contrasena_string.isEmpty()){
                    contrasena.setError("Por favor ingresa tu contraseña");
                    contrasena.requestFocus();
                }else if(email_string.isEmpty()&&contrasena_string.isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor llena los campos", Toast.LENGTH_LONG).show();
                }else if(!(email_string.isEmpty()&&contrasena_string.isEmpty())){
                    mFireBaseAuth.signInWithEmailAndPassword(email_string,contrasena_string).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Error al ingresar, inténtalo de nuevo", Toast.LENGTH_LONG).show();
                            }else{
                                Intent intentHome=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intentHome);
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Ocurrió un error", Toast.LENGTH_LONG).show();
                }
            }
        });

        irARegistrarse_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrarseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mFireBaseAuth.addAuthStateListener(mAuthStateListener);

    }
}*/


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, contrasena;
    Button irARegistrarse_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email=findViewById(R.id.email_et);
        contrasena=findViewById(R.id.contrasena_et);
        irARegistrarse_bt=findViewById(R.id.registrarse_bt);

        irARegistrarse_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void iniciarSesion(View view){
        String email_string=email.getText().toString();
        String contrasena_string=contrasena.getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(email_string, contrasena_string).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(MainActivity.this, "Usuario ingresado :).", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication falló.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public  void irAregistrar(View view){
        Intent intent=new Intent(MainActivity.this,RegistroActivity.class);
        startActivity(intent);
    }*/
}