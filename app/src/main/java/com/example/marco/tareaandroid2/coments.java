package com.example.marco.tareaandroid2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class coments extends AppCompatActivity {

    private final String url = "https://jsonplaceholder.typicode.com/comments";
    RequestQueue requestQueue;

    public static ArrayList<String> array;
    String[] comentarios;

    TextView vistaComent;
    ArrayAdapter adaptador;
    ListView listaPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coments);
        Intent intent = getIntent();
        int idPost = intent.getIntExtra("idPost", 0);

        this.listaPosts = (ListView) findViewById(R.id.lista_comentarios);
        array = new ArrayList<String>();
        this.comentarios = new String[0];
        requestQueue = Volley.newRequestQueue(this);

        this.getSupportActionBar().setTitle(MainActivity.array.get(idPost).getTitulo());

        addComment(MainActivity.array.get(idPost).getCuerpo());

        listarComentarios(idPost);



    }

    private void listarComentarios(final int idPost) {

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String postId= jsonObj.get("postId").toString();
                                    if (idPost + 1 == Integer.parseInt(postId)) {
                                        String idComent = jsonObj.get("id").toString();
                                        String nombre = jsonObj.get("name").toString();
                                        String email = jsonObj.get("email").toString();
                                        String cuerpo = jsonObj.get("body").toString();
                                        addComment(idComent, nombre, email, cuerpo);
                                    }
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                            mostrar();
                        } else {
                            Log.e("Volley", "Invalid JSON Object.");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    private void addComment(String idComent, String nombre, String email, String cuerpo) {
        array.add(nombre + "\r\n\r\n" + cuerpo + "\r\n\r\n" + email);
    }

    private void addComment(String cuerpo) {
        array.add("original Post: \r\n\r\n" + cuerpo);
    }

    private void mostrar(){
        this.comentarios = array.toArray(new String[0]);
        this.adaptador = new ArrayAdapter<String>(this,
                R.layout.comentario, comentarios);
        listaPosts.setAdapter(this.adaptador);
    }
}
