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

public class SeleccionIngresoActivity extends AppCompatActivity {

    private Button mIngreso;
    private Button mRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_ingreso);
        inicializacionComponentes();
    }

    private void inicializacionComponentes(){

        mIngreso = (Button) findViewById(R.id.ingresar);
        mIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SeleccionIngresoActivity.this, IngresoActivity.class);
                Intent intent = new Intent(SeleccionIngresoActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });

        mRegistrar = (Button) findViewById(R.id.crear_cuenta);
        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionIngresoActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

    }


}
