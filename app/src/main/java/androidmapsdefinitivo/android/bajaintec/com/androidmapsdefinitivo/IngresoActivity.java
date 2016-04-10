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
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.WebServiceTask;

public class IngresoActivity extends AppCompatActivity {

    private int mIdUsuario;
    private EditText mUser;
    private EditText mPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
        mUser = (EditText) findViewById(R.id.etUser);
        mPass = (EditText) findViewById(R.id.etPass);
        Button ingresoBtn = (Button) findViewById(R.id.btnIniciarSesion);
        mIdUsuario = 0;
        ingresoBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(!validar()){
                    WebServiceTask task = new WebServiceTask();

                    task.execute("POST", "1", "user", mUser.getText().toString(), "pass", mPass.getText().toString());

                    try {
                        String result = task.get();
                        if(result != null && !result.contains("null")){
                            mIdUsuario = Integer.parseInt(result.split("\n")[0]);
                            Intent intent = new Intent(IngresoActivity.this, MapsActivity.class);
                            intent.putExtra("idUsuario", mIdUsuario);
                            Log.d("WEBSERVICE", String.valueOf(mIdUsuario));
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(IngresoActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("WEBSERVICE", result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean validar(){
        if(mUser.getText().toString().equals("")){
            mUser.setHint(R.string.campo_vacio_usuario);
            Toast.makeText(this, R.string.campo_vacio_usuario, Toast.LENGTH_SHORT).show();
            return true;
        }else if(mPass.getText().toString().equals("")){
            mPass.setHint(R.string.campo_vacio_contrasena);
            Toast.makeText(this, R.string.campo_vacio_contrasena, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


}
