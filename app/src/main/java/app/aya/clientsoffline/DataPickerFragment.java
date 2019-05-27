package app.aya.clientsoffline;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by egypt2 on 24-Dec-18.
 */

public class DataPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    String result ;
    AddClients  addClients;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Toast.makeText(getActivity(),year+"-"+month+"-"+day , Toast.LENGTH_SHORT).show();
        result = year+"-"+month+"-"+day ;
        addClients = new AddClients();
        addClients.ls_date=result;

        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
    }


}
