package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Control.WebServiceTask;
import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Entidad.Contacto;
import androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Entidad.Usuario;

public class RegistroContactoActivity extends AppCompatActivity {

    private ListView mLista;
    private MyCustomAdapter mDataAdapter;
    private Button mBtnGuardar;
    private Usuario mUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_contacto);
        mBtnGuardar = (Button) findViewById(R.id.btnGuardarV);
        mUsuario = new Usuario();
        mUsuario.setNombreUsuario(getIntent().getStringExtra("usuario"));
        mUsuario.setContrasena(getIntent().getStringExtra("contrasena"));
        mUsuario.setCorreo(getIntent().getStringExtra("correo"));
        mUsuario.setFecha(getIntent().getStringExtra("fecha"));
        mUsuario.setSexo(getIntent().getStringExtra("sexo"));
        mUsuario.setOcupacion(getIntent().getStringExtra("ocupacion"));
        mostrarLista();
        checkButtonClick();

    }

    private void mostrarLista() {
        Cursor cursor = null;
        mLista = (ListView) findViewById(R.id.lista_contactos);

        ArrayList<Contacto> listaItems = new ArrayList<Contacto>();
        try {
            cursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            //int contactIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
            int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            //int photoIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
            cursor.moveToFirst();
            do {
                //String idContact = cursor.getString(contactIdIdx);
                Contacto contacto = new Contacto(cursor.getString(nameIdx), cursor.getString(phoneNumberIdx), false);
                listaItems.add(contacto);
            }
            while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        mDataAdapter = new MyCustomAdapter(this, R.layout.contacto_info, listaItems);
        //ArrayAdapter<String> adapter =
        //        new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listaItems);
        mLista.setAdapter(mDataAdapter);
    }

    public class MyCustomAdapter extends ArrayAdapter<Contacto> {
        private ArrayList<Contacto> mContactoList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Contacto> contactoList) {
            super(context, textViewResourceId, contactoList);
            this.mContactoList = new ArrayList<Contacto>();
            this.mContactoList.addAll(contactoList);
        }

        private class ViewHolder {
            TextView mNombre;
            TextView mNumero;
            CheckBox mSelected;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.contacto_info, null);
                holder = new ViewHolder();
                holder.mNombre = (TextView) convertView.findViewById(R.id.Nombre);
                holder.mNumero = (TextView) convertView.findViewById(R.id.numero);
                holder.mSelected = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.mSelected.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Contacto contacto = (Contacto) cb.getTag();
                        contacto.setSelected(cb.isChecked());
                    }
                });
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            Contacto contacto = mContactoList.get(position);
            holder.mNombre.setText(contacto.getNombre());
            holder.mNumero.setText(contacto.getNumero());
            holder.mSelected.setChecked(contacto.isSelected());
            holder.mSelected.setTag(contacto);

            return convertView;

        }
    }


        private void checkButtonClick() {

            mBtnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    StringBuffer responseText = new StringBuffer();
                    responseText.append("The following were selected...\n");

                    ArrayList<Contacto> contactoList = mDataAdapter.mContactoList;
                    for (int i = 0; i < contactoList.size(); i++) {
                        Contacto contacto = contactoList.get(i);
                        if (contacto.isSelected()) {
                            responseText.append("\n" + contacto.getNombre());
                        }
                    }
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    finish();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroContactoActivity.this);

                    builder.setTitle("Confirmar");
                    builder.setMessage(R.string.guardar_info);

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            WebServiceTask task = new WebServiceTask();

                            //Log.d("WEBSERVICE", String.valueOf(getIntent().getIntExtra("idUsuario", 0)));
                            task.execute("POST", "2",
                                    "user", mUsuario.getNombreUsuario(),
                                    "pass", mUsuario.getContrasena(),
                                    "gender", mUsuario.getSexo(),
                                    "occupation", mUsuario.getOcupacion(),
                                    "email", mUsuario.getCorreo(),
                                    "birthday", mUsuario.getFecha());

                            try {
                                String result = task.get();
                                if(result != null && result.contains("ok")){
                                    Intent returnIntent = new Intent();
                                    returnIntent.putExtra("resultado", true);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }else{
                                    Toast.makeText(RegistroContactoActivity.this, result, Toast.LENGTH_SHORT).show();
                                }
                                Log.d("WEBSERVICE", result);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }


                        }

                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }


}
