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
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Entidad.Usuario;

public class RegistroOcupacionActivity extends AppCompatActivity {


    private Button mBtnContinuar;
    private RadioButton mRButtonIP;
    private RadioButton mRButtonT;
    private RadioButton mRButtonE;
    private RadioButton mRButtonSO;
    private Usuario mUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocupacion);
        mUsuario = new Usuario();
        mBtnContinuar = (Button) findViewById(R.id.continuar_ocupacion_btn);
        mRButtonIP = (RadioButton) findViewById(R.id.rButtonIP);
        mRButtonT = (RadioButton) findViewById(R.id.rButtonT);
        mRButtonE = (RadioButton) findViewById(R.id.rButtonE);
        mRButtonSO = (RadioButton) findViewById(R.id.rButtonSO);

        mUsuario.setNombreUsuario(getIntent().getStringExtra("usuario"));
        mUsuario.setContrasena(getIntent().getStringExtra("contrasena"));
        mUsuario.setCorreo(getIntent().getStringExtra("correo"));
        mUsuario.setFecha(getIntent().getStringExtra("fecha"));
        mUsuario.setSexo(getIntent().getStringExtra("sexo"));

        mBtnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validar()) {
                    Intent intent = new Intent(RegistroOcupacionActivity.this, RegistroContactoActivity.class);
                    intent.putExtra("usuario", mUsuario.getNombreUsuario());
                    intent.putExtra("contrasena", mUsuario.getContrasena());
                    intent.putExtra("correo", mUsuario.getCorreo());
                    intent.putExtra("fecha", mUsuario.getFecha());
                    intent.putExtra("sexo", mUsuario.getSexo());
                    if (mRButtonIP.isChecked()) {
                        intent.putExtra("ocupacion", "Trabajador");
                    } else if (mRButtonE.isChecked()) {
                        intent.putExtra("ocupacion", "Independiente/Profesionista");
                    } else if (mRButtonSO.isChecked()) {
                        intent.putExtra("ocupacion", "Estudiante");
                    } else if (mRButtonT.isChecked()) {
                        intent.putExtra("ocupacion", "Sin ocupacion");
                    }
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    private boolean validar(){
        if(!mRButtonIP.isChecked()&&!mRButtonE.isChecked()&&!mRButtonSO.isChecked()&&
                !mRButtonT.isChecked()){
            Toast.makeText(this, R.string.instruccion_ocupacion, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if(data.getBooleanExtra("resultado", false)){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("resultado", true);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
