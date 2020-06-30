package college_project.dreamtravels;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;
import college_project.dreamtravels.Util.TimePicker;

public class TripBooking extends AppCompatActivity implements View.OnClickListener {

    EditText etEventBookingToDate, etEventBookingFromDate, etEventBookingVehicleType,
            etEventBookingPickUp, etEventBookingEventDropOff, etEventBookingPassengers,
            etEventBookingEventLuggage, etEventBookingReq;
    Button btEventBookingBookNow;
    RadioButton rbEventBookingOneWay, rbEventBookingReturn;
    LinearLayout llTravellingDetailToDate;

    List<String> LuggageArr;
    List<String> PassengerArr;
    List<String> VehicleArr;
    List<String> PlaceArr;
    Calendar mCalendar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rbEventBookingOneWay = (RadioButton) findViewById(R.id.rbEventBookingOneWay);
        rbEventBookingReturn = (RadioButton) findViewById(R.id.rbEventBookingReturn);
        btEventBookingBookNow = (Button) findViewById(R.id.btEventBookingBookNow);
        etEventBookingFromDate = (EditText) findViewById(R.id.etEventBookingFromDate);
        etEventBookingToDate = (EditText) findViewById(R.id.etEventBookingToDate);
        etEventBookingVehicleType = (EditText) findViewById(R.id.etEventBookingVehicleType);
        etEventBookingPickUp = (EditText) findViewById(R.id.etEventBookingPickUp);
        etEventBookingEventDropOff = (EditText) findViewById(R.id.etEventBookingEventDropOff);
        etEventBookingPassengers = (EditText) findViewById(R.id.etEventBookingPassengers);
        etEventBookingEventLuggage = (EditText) findViewById(R.id.etEventBookingEventLuggage);
        etEventBookingReq = (EditText) findViewById(R.id.etEventBookingReq);
        llTravellingDetailToDate = (LinearLayout) findViewById(R.id.llTravellingDetailToDate);

        btEventBookingBookNow.setOnClickListener(this);
        etEventBookingFromDate.setOnClickListener(this);
        etEventBookingToDate.setOnClickListener(this);
//        etEventBookingEventLuggage.setOnClickListener(this);
//        etEventBookingPassengers.setOnClickListener(this);
        etEventBookingPickUp.setOnClickListener(this);
        etEventBookingVehicleType.setOnClickListener(this);
        etEventBookingEventDropOff.setOnClickListener(this);
        rbEventBookingReturn.setOnClickListener(this);
        rbEventBookingOneWay.setOnClickListener(this);
        mCalendar = Calendar.getInstance();

        LuggageArr = new ArrayList<>();
        PassengerArr = new ArrayList<>();
        VehicleArr = new ArrayList<>();
        PlaceArr = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            LuggageArr.add(i + "");
            PassengerArr.add(i + "");
        }

        VehicleArr.add("Toyota Innova Crysta");
        VehicleArr.add("Hyundai Xcent");
        VehicleArr.add("Maruti Alto 800");
        VehicleArr.add("Maruti Suzuki Swift");
        VehicleArr.add("Maruti Suzuki Ciaz");

        PlaceArr.add("Ahmedabad");
        PlaceArr.add("Rajkot");
        PlaceArr.add("Veraval");
        PlaceArr.add("Keshod");
        PlaceArr.add("Junagath");

        etEventBookingFromDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis())));
        etEventBookingToDate.setText(new SimpleDateFormat("hh:mm aa").format(new Date(System.currentTimeMillis())));
        etEventBookingEventLuggage.setText(LuggageArr.get(0));
        etEventBookingPassengers.setText(PassengerArr.get(0));
        etEventBookingVehicleType.setText(VehicleArr.get(0));
        etEventBookingPickUp.setText(PlaceArr.get(0));
        etEventBookingEventDropOff.setText(PlaceArr.get(1));

    }

    @Override
    public void onClick(View v) {
        CommonUtil.closeKeyboard(TripBooking.this);
        switch (v.getId()) {
            case R.id.btEventBookingBookNow:

                if (CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingPickUp))
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.event_bookin_pick));
                else if (CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingEventDropOff))
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.event_bookin_drop));
                else if (CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingFromDate))
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.enterFromDate));
                else if (rbEventBookingReturn.isChecked() && CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingToDate))
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.enterToDate));
                else if (CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingVehicleType))
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.selectVehicle));
                else if (CommonUtil.checkConditionsEditTextIsEmpty(etEventBookingPassengers) || Integer.valueOf(etEventBookingPassengers.getText().toString().trim()) < 1)
                    CommonUtil.byToastMessage(TripBooking.this, getResources().getString(R.string.enterValidPassengerCount));
                else
                    submit();
                break;

            case R.id.etEventBookingFromDate:
                datePicker(true);
                etEventBookingToDate.setText("");
                break;
            case R.id.etEventBookingToDate:
//                TimePicker(etEventBookingFromDate.getText().toString().trim());
                if (!TextUtils.isEmpty(etEventBookingFromDate.getText().toString().trim()))
                    datePicker(false);
                else
                    CommonUtil.byToastMessage(this, "First Select From Date");
                break;

            case R.id.rbEventBookingOneWay:
                llTravellingDetailToDate.setVisibility(View.INVISIBLE);
                etEventBookingToDate.setText("");
                break;

            case R.id.rbEventBookingReturn:
                llTravellingDetailToDate.setVisibility(View.VISIBLE);
                etEventBookingToDate.setText("");
                break;

            case R.id.etEventBookingPassengers:
                selectFromPicker(PassengerArr, etEventBookingPassengers, -1);
                break;

            case R.id.etEventBookingEventLuggage:
                selectFromPicker(LuggageArr, etEventBookingEventLuggage, -1);
                break;

            case R.id.etEventBookingVehicleType:
                selectFromPicker(VehicleArr, etEventBookingVehicleType, -1);
                break;

            case R.id.etEventBookingPickUp:
                selectFromPicker(PlaceArr, etEventBookingPickUp, 0);
                break;

            case R.id.etEventBookingEventDropOff:
                selectFromPicker(PlaceArr, etEventBookingEventDropOff, 1);
                break;

        }
    }

    public void selectFromPicker(List<String> pickerType, final EditText editText, int pickDropClick) {
        try {
            List<String> duplicatedList = new ArrayList<>();

            for (int i = 0; i < pickerType.size(); i++) {
                duplicatedList.add(pickerType.get(i));
            }

            for (int i = 0; i < duplicatedList.size(); i++) {

                if (pickDropClick == 0 && TextUtils.equals(etEventBookingEventDropOff.getText().toString().trim(), duplicatedList.get(i))) {
                    duplicatedList.remove(i);
                    break;
                } else if (pickDropClick == 1 && TextUtils.equals(etEventBookingPickUp.getText().toString().trim(), duplicatedList.get(i))) {
                    duplicatedList.remove(i);
                    break;
                }
            }

            Object[] objNames = duplicatedList.toArray();
            String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

            OptionPicker picker = new OptionPicker(TripBooking.this, strNames);
            picker.setTextSize(15);

            picker.setTitleText("Select Category");
            picker.setTitleTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
            picker.setTopBackgroundColor(ActivityCompat.getColor(TripBooking.this, R.color.colorAccent));
            picker.setTopLineColor(ActivityCompat.getColor(TripBooking.this, R.color.colorAccent));
            picker.setCancelTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
            picker.setSubmitTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(String option) {
                    editText.setText(option);
                }
            });
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit() {

        JSONObject objectUserEventInfo = new JSONObject();
        try {

            if (rbEventBookingReturn.isChecked())
                objectUserEventInfo.put(Constants.EVENT_TO_DATE, etEventBookingToDate.getText().toString().trim());
            else
                objectUserEventInfo.put(Constants.EVENT_TO_DATE, "");

            objectUserEventInfo.put(Constants.EVENT_PASSENGERS_count, etEventBookingPassengers.getText().toString().trim())
                    .put(Constants.EVENT_OTHERS_REQ, etEventBookingReq.getText().toString().trim())
                    .put(Constants.EVENT_VEHICLE_TYPE, etEventBookingVehicleType.getText().toString().trim())
                    .put(Constants.EVENT_LUGGAGE, TextUtils.isEmpty(etEventBookingEventLuggage.getText().toString().trim()) ? 0 : etEventBookingEventLuggage.getText().toString().trim())
                    .put(Constants.EVENT_FROM_DATE, Constants.DATE_FORMAT_ONLY_DATE.format(Constants.DATE_FORMAT_ONLY_DATE.parse(etEventBookingFromDate.getText().toString().trim())))
                    .put(Constants.EVENT_PICK_UP, etEventBookingPickUp.getText().toString().trim())
                    .put(Constants.EVENT_DROP_OFF, etEventBookingEventDropOff.getText().toString().trim())
                    .put(Constants.EVENT_DURATION_TYPE, rbEventBookingOneWay.isChecked() ? "1" : "2")
                    .put(Constants.EVENT_DURATION_TYPE_NAME, rbEventBookingOneWay.isChecked() ? "One Way" : "Return");

            JSONArray arrayTripDetail = new JSONArray();
            arrayTripDetail.put(objectUserEventInfo);

            SharedPreferenceUtil.putValue(Constants.TRIP_BOOKING, arrayTripDetail.toString());
            SharedPreferenceUtil.save();

            CommonUtil.byLogMessage(this, "Trip Booking" + SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));

            nextActivity();

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void nextActivity() {
        CommonUtil.showProgressDialog(this, "Loading...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    CommonUtil.closeKeyboard(TripBooking.this);
                    startActivity(new Intent(TripBooking.this, PassengersDetail.class));
                    CommonUtil.cancelProgressDialog();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void datePicker(final boolean fromDate) {

        DatePickerDialog DatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker DatePicker, int year, int month, int dayOfMonth) {
                mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (fromDate)
                    etEventBookingFromDate.setText(CommonUtil.dateFormat(mCalendar.getTime().getTime()));
                else
                    etEventBookingToDate.setText(CommonUtil.dateFormat(mCalendar.getTime().getTime()));

            }

        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        DatePicker.show();
        DatePicker.setCancelable(false);

        if (fromDate)
            DatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        else
            try {
                DatePicker.getDatePicker().setMinDate(Constants.DATE_FORMAT_ONLY_DATE.parse(etEventBookingFromDate.getText().toString().trim()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    public void TimePicker(final String date) {

        String hour = "1";
        String minute = "1";

        try {
            hour = new SimpleDateFormat("HH").format(new SimpleDateFormat("hh:mm aa").parse(etEventBookingToDate.getText().toString()));
            minute = new SimpleDateFormat("mm").format(new SimpleDateFormat("hh:mm aa").parse(etEventBookingToDate.getText().toString()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimePicker picker = new TimePicker(TripBooking.this, TimePicker.HOUR, Integer.valueOf(hour), Integer.valueOf(minute));
        picker.setLabel("", "");
        picker.setTitleText("Select Time");
        picker.setTitleTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
        picker.setTopBackgroundColor(ActivityCompat.getColor(TripBooking.this, R.color.colorPrimary));
        picker.setTopLineColor(ActivityCompat.getColor(TripBooking.this, R.color.colorPrimary));
        picker.setCancelTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
        picker.setSubmitTextColor(ActivityCompat.getColor(TripBooking.this, R.color.white));
        picker.setTopLineVisible(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String selectedHour, String selectedMinute, String amPm) {

                String time = selectedHour + ":" + selectedMinute + " " + amPm;

                if (CommonUtil.dateEqualCompare(date, CommonUtil.dateFormat(System.currentTimeMillis())))
                    if (CommonUtil.timeCompare(time, CommonUtil.timeFormat(System.currentTimeMillis()))) {
                        etEventBookingToDate.setText(time);
                    } else
                        CommonUtil.byToastMessage(TripBooking.this, "Time has to be less than Current Time");
                else {
                    etEventBookingToDate.setText(time);
                }
            }
        });
        picker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        CommonUtil.closeKeyboard(this);
        super.onBackPressed();
    }

}
