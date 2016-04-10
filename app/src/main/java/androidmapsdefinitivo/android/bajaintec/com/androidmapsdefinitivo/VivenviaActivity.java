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

public class VivenviaActivity extends AppCompatActivity {

    private RadioButton opcion1;
    private RadioButton opcion2;
    private RadioButton opcion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivenvia);

        opcion1 = (RadioButton) findViewById(R.id.rButtonRobo);
        opcion2 = (RadioButton) findViewById(R.id.rButtonVio);
        opcion3 = (RadioButton) findViewById(R.id.rButtonDrog);

        Button mSiguiente = (Button) findViewById(R.id.btnGuardarV);
        mSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idIncidente = 0;
                if(opcion1.isChecked())
                    idIncidente = 1;
                else if(opcion2.isChecked())
                    idIncidente = 2;
                else if(opcion3.isChecked())
                    idIncidente = 3;
                else
                    Toast.makeText(VivenviaActivity.this, "No ha seleccionado opcion", Toast.LENGTH_SHORT).show();

                if(idIncidente != 0){
                    WebServiceTask task = new WebServiceTask();;
                    task.execute("POST", "0", "primera_localizacion_n", getIntent().getStringExtra("origen_n"),
                            "primera_localizacion_w", getIntent().getStringExtra("origen_w"),
                            "segunda_localizacion_n", getIntent().getStringExtra("destino_n"),
                            "segunda_localizacion_w", getIntent().getStringExtra("destino_w"),
                            "calle", getIntent().getStringExtra("calle"), "calificacion",
                            getIntent().getStringExtra("calificacion"), "idUsuario",
                            getIntent().getStringExtra("idUsuario"), "idMotivo",
                            getIntent().getStringExtra("idMotivo"), "idIncidente",
                            String.valueOf(idIncidente));
                    finish();
                }
            }
        });
    }
}
