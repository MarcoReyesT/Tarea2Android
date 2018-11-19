package com.example.marco.tareaandroid2;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] titulos;

    public static ArrayList<Post> array;
    ArrayAdapter adaptador;

    private final String url = "https://jsonplaceholder.typicode.com/posts";
    RequestQueue requestQueue;
    //EditText filtro;
    Button btnBuscar;
    ListView listaPosts;
    public Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setTitle("Lista de Posts");
        //this.filtro = (EditText) findViewById(R.id.text_filtro);
        this.btnBuscar = (Button) findViewById(R.id.boton_buscar);
        this.listaPosts = (ListView) findViewById(R.id.lista_posts);
        array = new ArrayList<Post>();
        this.titulos = new String[0];
        mostrar();

        requestQueue = Volley.newRequestQueue(this);
        listaPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                String selItem = (String) listaPosts.getItemAtPosition(position); //
                //String value = selItem.toString(); //getter method
                mostrarComentarios(v, position);

            }
        });
    }

    private void listarPosts(String filtro) {

        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObj = response.getJSONObject(i);
                                    String titulo = jsonObj.get("title").toString();
                                    String cuerpo = jsonObj.get("body").toString();
                                    String idUsuario = jsonObj.get("userId").toString();
                                    String idPost = jsonObj.get("id").toString();
                                    addPost(idPost, titulo, cuerpo, idUsuario);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                            mostrar();
                        } else {
                            limpiar();
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

    private void limpiar() {
        //this.elementos = new String[1];
        //this.elementos[0] = "Nada que mostrar...";
    }

    private void addPost(String idPostStr, String titulo, String cuerpo, String idUsuarioStr) {
        int idPost = Integer.parseInt(idPostStr);
        int idUsuario = Integer.parseInt(idUsuarioStr);
        Post p = new Post(idPost, titulo, cuerpo, idUsuario);
        array.add(p);
    }

    public void buscarPosts(View v) {
        listarPosts("filtro");
    }

    private void mostrar(){
        this.titulos = getTitulos();
        this.adaptador = new ArrayAdapter<String>(this,
                R.layout.activity_elemento, titulos);
        listaPosts.setAdapter(this.adaptador);
    }

    public String[] getTitulos(){
        //String[] titles = {"asddww", "asdaWWW"};
        String[] titles = new String[this.array.size()];
        for (int i = 0; i < titles.length; i++){
            titles[i] = array.get(i).getTitulo();
        }
        return titles;
    }

    public void mostrarComentarios(View v, int p) {
        Intent intent = new Intent(this, coments.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        intent.putExtra("idPost", p);
        startActivity(intent);
    }

}
