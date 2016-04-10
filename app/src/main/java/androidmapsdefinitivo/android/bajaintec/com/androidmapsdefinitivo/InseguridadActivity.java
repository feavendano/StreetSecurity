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

public class InseguridadActivity extends AppCompatActivity {

    private RadioButton opcion1;
    private RadioButton opcion2;
    private RadioButton opcion3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseguridad);

        opcion1 = (RadioButton) findViewById(R.id.rButtonT);
        opcion2 = (RadioButton) findViewById(R.id.rButtonIP);
        opcion3 = (RadioButton) findViewById(R.id.rButtonE);

        Button mSiguiente = (Button) findViewById(R.id.btnGuardarV);
        mSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idMotivo = 0;
                if(opcion1.isChecked())
                    idMotivo = 1;
                else if(opcion2.isChecked())
                    idMotivo = 2;
                else if(opcion3.isChecked())
                    idMotivo = 3;
                else
                    Toast.makeText(InseguridadActivity.this, "No ha seleccionado opcion", Toast.LENGTH_SHORT).show();

                if(idMotivo != 0){
                    Intent intent = new Intent(InseguridadActivity.this, SucedeActivity.class);
                    intent.putExtra("idUsuario",getIntent().getStringExtra("idUsuario"));
                    intent.putExtra("calle", getIntent().getStringExtra("calle"));
                    intent.putExtra("origen_n", getIntent().getStringExtra("origen_n"));
                    intent.putExtra("origen_w", getIntent().getStringExtra("origen_w"));
                    intent.putExtra("destino_n", getIntent().getStringExtra("destino_n"));
                    intent.putExtra("destino_w", getIntent().getStringExtra("destino_w"));
                    intent.putExtra("calificacion", getIntent().getStringExtra("calificacion"));
                    intent.putExtra("idMotivo", String.valueOf(idMotivo));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
