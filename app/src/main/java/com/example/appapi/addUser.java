package com.example.appapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addUser extends AppCompatActivity {

    EditText txtusuario, txtnombre, txtap, txtam, txtcorreo, txtcontrasenia, txtnac, txtstatus;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        txtusuario = findViewById(R.id.txt_usuario);
        txtnombre = findViewById(R.id. txt_nombre);
        txtap = findViewById(R.id.txt_pa);
        txtam = findViewById(R.id.txt_ma);
        txtcorreo = findViewById(R.id.txt_email);
        txtcontrasenia = findViewById(R.id.txt_contrasenia);
        txtnac = findViewById(R.id.txt_nac);
        txtstatus = findViewById(R.id.txt_status);

        registrar = findViewById(R.id.btnRegistrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAdd();
                alien_add(txtusuario.getText().toString(), txtnombre.getText().toString(), txtap.getText().toString(), txtam.getText().toString(), txtcorreo.getText().toString(), txtcontrasenia.getText().toString(), txtnac.getText().toString(), txtstatus.getText().toString());

            }
        });
    }

    public boolean validateAdd(){
        boolean retorno = true;

        String user = txtusuario.getText().toString();
        String name = txtnombre.getText().toString();
        String ap = txtap.getText().toString();
        String am = txtap.getText().toString();
        String email = txtcorreo.getText().toString();
        String password = txtcontrasenia.getText().toString();
        String nac = txtnac.getText().toString();
        String status = txtstatus.getText().toString();

        if (user.isEmpty() && name.isEmpty() && ap.isEmpty() && am.isEmpty() && email.isEmpty() && password.isEmpty() && nac.isEmpty() && status.isEmpty()){
            String message = "Este campo es requerido";
            txtnombre.setError(message);
            txtusuario.setError(message);
            txtap.setError(message);
            txtam.setError(message);
            txtcorreo.setError(message);
            txtstatus.setError(message);
            txtnac.setError(message);
            txtcontrasenia.setError(message);

            retorno = false;
        }



        return retorno;
    }

    private void alien_add(final String usuario, final String nombre, final String ap, final String am, final String correo, final String contrasenia, final String nac, final String status){
        String url = "https://api-alien.herokuapp.com/aliens";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(addUser.this, "Datos Guardados ="+ jsonObject.getString("Registro"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        })
        {
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("alien_usuario", usuario);
                params.put("alien_nombre", nombre);
                params.put("alien_ap", ap);
                params.put("alien_am",am);
                params.put("alien_correo", correo);
                params.put("alien_contrasenia", contrasenia);
                params.put("alien_nac", nac);
                params.put("alien_status", status);
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);

    }
}