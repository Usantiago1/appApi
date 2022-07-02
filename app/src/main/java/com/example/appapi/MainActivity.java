package com.example.appapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegistrar;
    EditText txt_correo, txt_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_correo = findViewById(R.id.txt_correo);
        txt_contrasenia = findViewById(R.id.txt_contrasenia);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                alienLogin(txt_correo.getText().toString(), txt_contrasenia.getText().toString());
            }
        });

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent vistaRegistro = new Intent(MainActivity.this, addUser.class);
                startActivity(vistaRegistro);
            }
        });

    }

    public boolean validate(){
        boolean retorno = true;

        String password = txt_contrasenia.getText().toString();
        String email = txt_correo.getText().toString();

        if(email.isEmpty() && password.isEmpty()){
            txt_contrasenia.setError("Este campo es requerido");
            txt_correo.setError("Este campo es requerido");
            retorno = false;
        }



        return retorno;
    }



    private void alienLogin (final String correo, final String contrasenia){
        String url = "https://api-alien.herokuapp.com/login";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONTokener jsonObject = new JSONTokener(response);
                String res = response.toString();
                Toast.makeText(MainActivity.this, "Mensaje = "+ res, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        })
        {
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("alien_correo", correo);
                params.put("alien_contrasenia", contrasenia);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(loginRequest);
    }


}