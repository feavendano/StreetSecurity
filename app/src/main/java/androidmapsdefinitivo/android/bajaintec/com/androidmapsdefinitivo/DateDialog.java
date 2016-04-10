package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by angel on 3/30/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText mTxtDate;

    public DateDialog(View view){
        mTxtDate = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle saveInstance){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){

        String date = year+"/"+(month+1)+"/"+day;
        /*String date = day+"/"+(month+1)+"/"+year;*/
        mTxtDate.setText(date);
    }

}
