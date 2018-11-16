package com.example.marco.tareaandroid2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    String url = "https://jsonplaceholder.typicode.com/posts";
    RequestQueue requestQueue;
    EditText filtro;
    Button btnBuscar;
    TextView listaPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.filtro = (EditText) findViewById(R.id.text_filtro);
        this.btnBuscar = (Button) findViewById(R.id.boton_buscar);
        this.listaPosts = (TextView) findViewById(R.id.lista_posts);
        this.listaPosts.setMovementMethod(new ScrollingMovementMethod());
        this.listaPosts.setText("Nada que mostrar...");
        requestQueue = Volley.newRequestQueue(this);
    }

    private void listarPosts(String asd) {

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
                                    addToRepoList(titulo, cuerpo);
                                } catch (JSONException e) {
                                    Log.e("Volley", "Invalid JSON Object.");
                                }

                            }
                        } else {
                            setRepoListText("No existen posts");
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setRepoListText("Error while calling REST API");
                        Log.e("Volley", error.toString());
                    }
                }
        );
        requestQueue.add(arrReq);
    }

    private void limpiar() {
        this.listaPosts.setText("Nada que mostrar");
    }

    private void addToRepoList(String repoName, String lastUpdated) {
        String strRow = repoName + " / " + lastUpdated;
        String currentText = listaPosts.getText().toString();
        this.listaPosts.setText(currentText + "\n\n" + strRow);
    }

    private void setRepoListText(String str) {
        this.listaPosts.setText(str);
    }

    public void buscarPosts(View v) {
        limpiar();
        listarPosts(filtro.getText().toString());
    }
}
