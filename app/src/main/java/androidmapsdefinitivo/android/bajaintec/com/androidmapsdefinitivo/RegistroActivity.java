package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo;
/**
 * Copyright(C) <2016> <BajaInTec>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity{

    private Button mContinuar;
    private EditText mCorreo;
    private EditText mUsuario;
    private EditText mContrasena;
    private EditText mConfirmarContrasena;
    private EditText mDateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mContinuar = (Button) findViewById(R.id.continuar);
        mCorreo = (EditText) findViewById(R.id.correo_edit);

        mUsuario = (EditText) findViewById(R.id.usuario_edit_text);
        mContrasena = (EditText) findViewById(R.id.contrasena_edit);
        mConfirmarContrasena = (EditText) findViewById(R.id.contrasena_conf_edit);
        mDateDialog = (EditText) findViewById(R.id.fecha_ed);
        mContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validar()) {
                    Intent i = new Intent(RegistroActivity.this, RegistroSexoActivity.class);
                    i.putExtra("usuario", mUsuario.getText().toString());
                    i.putExtra("contrasena", mContrasena.getText().toString());
                    i.putExtra("correo", mCorreo.getText().toString());
                    i.putExtra("fecha",mDateDialog.getText().toString());
                    startActivityForResult(i,1);
                }
            }
        });

        mDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dialog = new DateDialog(v);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });
    }


    private boolean validar() {
        if (mUsuario.getText().toString().equals("")) {
            mUsuario.setHint(R.string.campo_vacio_usuario);
            Toast.makeText(this, R.string.campo_vacio_usuario, Toast.LENGTH_SHORT).show();
            return true;
        } else if (mContrasena.getText().toString().equals("")) {
            mContrasena.setHint(R.string.campo_vacio_contrasena);
            Toast.makeText(this, R.string.campo_vacio_contrasena, Toast.LENGTH_SHORT).show();
            return true;
        } else if (mConfirmarContrasena.getText().toString().equals("")) {
            mConfirmarContrasena.setHint(R.string.campo_vacio_contrasena);
            Toast.makeText(this, R.string.campo_vacio_contrasena, Toast.LENGTH_SHORT).show();
            return true;
        } else if (mConfirmarContrasena.getText().toString().compareTo(mContrasena.getText().toString()) != 0) {
            mContrasena.setText("");
            mConfirmarContrasena.setText("");
            mContrasena.setHint(R.string.contrasena_no_iguales);
            mConfirmarContrasena.setHint(R.string.contrasena_no_iguales);
            Toast.makeText(this, R.string.contrasena_no_iguales, Toast.LENGTH_SHORT).show();
            return true;
        } else if (mCorreo.getText().toString().equals("")) {
            mCorreo.setHint(R.string.campo_vacio_correo);
            Toast.makeText(this, R.string.campo_vacio_correo, Toast.LENGTH_SHORT).show();
            return true;
        }else if(mDateDialog.getText().toString().equals("")){
            mDateDialog.setHint(R.string.campo_vacio_fecha);
            Toast.makeText(this, R.string.campo_vacio_fecha, Toast.LENGTH_SHORT).show();
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
