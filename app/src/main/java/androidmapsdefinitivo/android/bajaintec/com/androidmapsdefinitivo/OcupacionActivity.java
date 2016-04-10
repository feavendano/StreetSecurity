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

public class OcupacionActivity extends AppCompatActivity {


    private Button btnGuardar;
    private RadioButton rButtonIP, rButtonT, rButtonE, rButtonSO;
    private String correo, contrasena, usuario, ocupacion, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocupacion);

        btnGuardar = (Button) findViewById(R.id.btnGuardarV);
        rButtonIP = (RadioButton) findViewById(R.id.rButtonIP);
        rButtonT = (RadioButton) findViewById(R.id.rButtonT);
        rButtonE = (RadioButton) findViewById(R.id.rButtonE);
        rButtonSO = (RadioButton) findViewById(R.id.rButtonSO);

        correo = getIntent().getStringExtra("correo");
        usuario = getIntent().getStringExtra("usuario");
        contrasena = getIntent().getStringExtra("contrasena");
        sexo = getIntent().getStringExtra("sexo");
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rButtonIP.isChecked()){
                    ocupacion="Trabajador";
                }else if(rButtonE.isChecked()){
                    ocupacion = "Independiente/Profesionista";
                }else if(rButtonSO.isChecked()){
                    ocupacion = "Estudiante";
                }else if(rButtonT.isChecked()){
                    ocupacion = "Sin ocupacion";
                }
                System.out.println(correo);
                Intent intent = new Intent(OcupacionActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }


}
