package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo;

/**
 * Copyright(C) <2016> <BajaInTec>
 * This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.WebServiceTask;

public class SeguridadActivity extends AppCompatActivity {
    private Button mAtras;
    private Button mSiguiente;

    private RadioButton mInseguroCB;
    private RadioButton mpeligrosoCB;
    private RadioButton mseguroCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguridad);

        inicializarGraficos();

    }

    private void inicializarGraficos(){

        mInseguroCB = (RadioButton) findViewById(R.id.inseguro_radio);
        mpeligrosoCB = (RadioButton) findViewById(R.id.peligroso_radio);
        mseguroCB = (RadioButton) findViewById(R.id.seguro_radio);

        mAtras = (Button) findViewById(R.id.anterior);
        mAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSiguiente = (Button) findViewById(R.id.siguiente);
        mSiguiente .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idUsuario = getIntent().getIntExtra("idUsuario",0);
                if(mInseguroCB.isChecked() || mpeligrosoCB.isChecked()){
                    Intent intent = new Intent(SeguridadActivity.this, InseguridadActivity.class);
                    intent.putExtra("idUsuario",String.valueOf(idUsuario));
                    intent.putExtra("calle", getIntent().getStringExtra("calle"));
                    intent.putExtra("origen_n", getIntent().getStringExtra("origen_n"));
                    intent.putExtra("origen_w", getIntent().getStringExtra("origen_w"));
                    intent.putExtra("destino_n", getIntent().getStringExtra("destino_n"));
                    intent.putExtra("destino_w", getIntent().getStringExtra("destino_w"));
                    if(mInseguroCB.isChecked()){
                        intent.putExtra("calificacion","2");
                    }else{
                        intent.putExtra("calificacion", "1");
                    }
                    startActivity(intent);
                    finish();
                }else if(mseguroCB.isChecked()){
                    WebServiceTask task = new WebServiceTask();
                    Log.d("WEBSERVICE", String.valueOf(getIntent().getIntExtra("idUsuario", 0)));
                    task.execute("POST", "0", "primera_localizacion_n", getIntent().getStringExtra("origen_n"),
                            "primera_localizacion_w", getIntent().getStringExtra("origen_w"),
                            "segunda_localizacion_n", getIntent().getStringExtra("destino_n"),
                            "segunda_localizacion_w", getIntent().getStringExtra("destino_w"),
                            "calle", getIntent().getStringExtra("calle"), "calificacion",
                            "3", "idUsuario", String.valueOf(idUsuario),
                            "idMotivo", "", "idIncidente", "");
                    finish();
                }else{
                    Toast.makeText(SeguridadActivity.this, "No ha seleccionado calificacion", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
