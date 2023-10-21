package com.example.afusesc.tastyready;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class TabUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_user);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(usuario.getDisplayName());
        TextView coreo = findViewById(R.id.correo);
        coreo.setText(usuario.getEmail());
        TextView prove = findViewById(R.id.proveedor);
        prove.setText(usuario.getProviderId());
        TextView telf = findViewById(R.id.telefono);
        telf.setText(usuario.getPhoneNumber());
        TextView Uid = findViewById(R.id.uid);
        Uid.setText(usuario.getUid());

        // Inicialización Volley (Hacer solo una vez en Singleton o Applicaction)
        RequestQueue colaPeticiones = Volley.newRequestQueue(this); ImageLoader lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() { private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10); public void putBitmap(String url, Bitmap bitmap) {cache.put(url, bitmap); } public Bitmap getBitmap(String url) { return cache.get(url); } }); Uri urlImagen = usuario.getPhotoUrl(); if (urlImagen != null) { NetworkImageView foto = (NetworkImageView) findViewById(R.id.imagen); foto.setImageUrl(urlImagen.toString(), lectorImagenes); }

    }
    public void cerrarSesion(View view) {
        AuthUI.getInstance().signOut(getApplicationContext()) .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override public void onComplete(@NonNull Task<Void> task) {
                Intent i = new Intent( getApplicationContext (),LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); startActivity(i); finish(); } }); }


}