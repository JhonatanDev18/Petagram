package com.crystal.petagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fabMiFab;
    SwipeRefreshLayout sfiMiIndicadorRefresh;
    TextView tvHolaMundo;
    ListView lstMiLista;

    boolean isPlanetas;
    String[] personas;
    String[] planetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializar();
        eventos();
    }

    private void inicializar() {
        fabMiFab = findViewById(R.id.fabMiFab);
        sfiMiIndicadorRefresh = (SwipeRefreshLayout) findViewById(R.id.sfiMiIndicadorRefresh);
        lstMiLista = (ListView) findViewById(R.id.lstMiLista);

        planetas = getResources().getStringArray(R.array.planetas);
        lstMiLista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, planetas));
        isPlanetas = true;
        tvHolaMundo = findViewById(R.id.tvHolaMundo);
    }

    private void eventos() {
        fabMiFab.setOnClickListener(this);
        sfiMiIndicadorRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refrescandoContenido();
            }
        });
        lstMiLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isPlanetas){
                    tvHolaMundo.setText(planetas[position].toUpperCase());
                }else{
                    tvHolaMundo.setText(personas[position].toUpperCase());
                }
            }
        });
    }

    public void refrescandoContenido(){
        if(isPlanetas){
            personas = getResources().getStringArray(R.array.personas);
            lstMiLista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personas));
            sfiMiIndicadorRefresh.setRefreshing(false);
            isPlanetas = false;
        }else{
            planetas = getResources().getStringArray(R.array.planetas);
            lstMiLista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, planetas));
            sfiMiIndicadorRefresh.setRefreshing(false);
            isPlanetas = true;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fabMiFab){
            Snackbar.make(v, getResources().getString(R.string.mensajeFab), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.mensajeAccionFab), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            refrescandoContenido();
                        }
                    })
                    .show();
        }
    }
}