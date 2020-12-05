package com.example.memesapp1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button nbutton,sbutton;
    ProgressBar progressBar;
    String currentImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=findViewById(R.id.imageView2);
        nbutton=findViewById(R.id.nextbutton);
        sbutton=findViewById(R.id.sharebutton);
        progressBar=findViewById(R.id.progressbar);
        loadmeme();
    }

    private void loadmeme(){

        progressBar.setVisibility(View.VISIBLE);


        String url = "https://meme-api.herokuapp.com/gimme";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {




                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            currentImageUrl=response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Glide.with(MainActivity.this).load(currentImageUrl).listener(new RequestListener<Drawable>() {


                            @Override
                            public boolean onResourceReady(android.graphics.drawable.Drawable resource, Object model, Target<android.graphics.drawable.Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                nbutton.isEnabled();
                                sbutton.isEnabled();

                                return false;
                            }


                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<android.graphics.drawable.Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }



                        }).into(imageView);
                    }




                }, new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
        Mysingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void Sharememe(View view) throws URISyntaxException {
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, currentImageUrl);

        startActivity(Intent.createChooser(intent,"Share this meme using..."));
    }

    public void Nextmeme(View view) {
        loadmeme();
    }

}