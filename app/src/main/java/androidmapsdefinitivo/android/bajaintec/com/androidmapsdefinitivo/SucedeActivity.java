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
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.WebServiceTask;

public class SucedeActivity extends AppCompatActivity {

    private RadioButton opcion1;
    private RadioButton opcion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucede);

        opcion1 = (RadioButton) findViewById(R.id.rButtonSi);
        opcion2 = (RadioButton) findViewById(R.id.rButtonNo);

        Button mSiguiente = (Button) findViewById(R.id.button);
        mSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opcion1.isChecked() && !opcion2.isChecked())
                    Toast.makeText(SucedeActivity.this, "No ha seleccionado opcion", Toast.LENGTH_SHORT).show();
                else if(opcion2.isChecked()) {
                    WebServiceTask task = new WebServiceTask();;
                    task.execute("POST", "0", "primera_localizacion_n", getIntent().getStringExtra("origen_n"),
                            "primera_localizacion_w", getIntent().getStringExtra("origen_w"),
                            "segunda_localizacion_n", getIntent().getStringExtra("destino_n"),
                            "segunda_localizacion_w", getIntent().getStringExtra("destino_w"),
                            "calle", getIntent().getStringExtra("calle"), "calificacion",
                            getIntent().getStringExtra("calificacion"), "idUsuario",
                            getIntent().getStringExtra("idUsuario"), "idMotivo",
                            getIntent().getStringExtra("idMotivo"), "idIncidente", "");
                    finish();
                }else{
                    Intent intent = new Intent(SucedeActivity.this, VivenciaActivity.class);
                    intent.putExtra("idUsuario",getIntent().getStringExtra("idUsuario"));
                    intent.putExtra("calle", getIntent().getStringExtra("calle"));
                    intent.putExtra("origen_n", getIntent().getStringExtra("origen_n"));
                    intent.putExtra("origen_w", getIntent().getStringExtra("origen_w"));
                    intent.putExtra("destino_n", getIntent().getStringExtra("destino_n"));
                    intent.putExtra("destino_w", getIntent().getStringExtra("destino_w"));
                    intent.putExtra("calificacion", getIntent().getStringExtra("calificacion"));
                    intent.putExtra("idMotivo", getIntent().getStringExtra("idMotivo"));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
