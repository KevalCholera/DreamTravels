package college_project.dreamtravels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Adapter.AdapterPassengersDetail;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class PassengersDetail extends AppCompatActivity {

    static EditText etListOfPassengerFirstName, etListOfPassengerLastName,
            etListOfPassengersContact, etListOfPassengersAge, etListOfPassengersEmail;
    static Button btListOfPassengersSubmit, btListOfPassengersAddPassenger;
    static ListView lvListOfPassengersList;
    public static JSONArray arrayPassengersDetail;
    static JSONObject objectIntent;
    static LinearLayout llListOfPassengersMainList;
    static RadioButton rbListOfPassengersBus, rbListOfPassengersTrain, rbListOfPassengersFlight, rbListOfPassengersCar;
    RadioGroup rgListOfPassengersTravellingMode;
    static TextView tvListOfPassengersTravelMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etListOfPassengerFirstName = (EditText) findViewById(R.id.etListOfPassengerFirstName);
        etListOfPassengerLastName = (EditText) findViewById(R.id.etListOfPassengerLastName);
        etListOfPassengersContact = (EditText) findViewById(R.id.etListOfPassengersContact);
        etListOfPassengersAge = (EditText) findViewById(R.id.etListOfPassengersAge);
        etListOfPassengersEmail = (EditText) findViewById(R.id.etListOfPassengersEmail);
        btListOfPassengersSubmit = (Button) findViewById(R.id.btListOfPassengersSubmit);
        btListOfPassengersAddPassenger = (Button) findViewById(R.id.btListOfPassengersAddPassenger);
        lvListOfPassengersList = (ListView) findViewById(R.id.lvListOfPassengersList);
        llListOfPassengersMainList = (LinearLayout) findViewById(R.id.llListOfPassengersMainList);
        rbListOfPassengersCar = (RadioButton) findViewById(R.id.rbListOfPassengersCar);
        rbListOfPassengersBus = (RadioButton) findViewById(R.id.rbListOfPassengersBus);
        rbListOfPassengersTrain = (RadioButton) findViewById(R.id.rbListOfPassengersTrain);
        rbListOfPassengersFlight = (RadioButton) findViewById(R.id.rbListOfPassengersFlight);
        rgListOfPassengersTravellingMode = (RadioGroup) findViewById(R.id.rgListOfPassengersTravellingMode);
        tvListOfPassengersTravelMode = (TextView) findViewById(R.id.tvListOfPassengersTravelMode);

        btListOfPassengersSubmit.setVisibility(View.GONE);
        rbListOfPassengersCar.setVisibility(View.GONE);
        rbListOfPassengersBus.setVisibility(View.GONE);
        rbListOfPassengersTrain.setVisibility(View.GONE);
        rbListOfPassengersFlight.setVisibility(View.GONE);
        tvListOfPassengersTravelMode.setVisibility(View.GONE);

        arrayPassengersDetail = new JSONArray();
        objectIntent = new JSONObject();
        try {

            JSONArray arrayIntent = new JSONArray(SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));

            objectIntent = arrayIntent.optJSONObject(0);
            btListOfPassengersAddPassenger.setText("Add Passengers   (" + objectIntent.optString(Constants.EVENT_PASSENGERS_count) + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btListOfPassengersAddPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (CommonUtil.checkConditionsEditTextIsEmpty(etListOfPassengerFirstName))
                        CommonUtil.byToastMessage(PassengersDetail.this, getString(R.string.enter_first_name));
                    else if (CommonUtil.checkConditionsEditTextIsEmpty(etListOfPassengerLastName))
                        CommonUtil.byToastMessage(PassengersDetail.this, getString(R.string.enter_last_name));
                    else if (CommonUtil.checkConditionsEditTextIsEmpty(etListOfPassengersContact) || etListOfPassengersContact.getText().toString().trim().length() < 10)
                        CommonUtil.byToastMessage(PassengersDetail.this, getString(R.string.enter_contact_no));
                    else if (CommonUtil.checkConditionsEditTextIsEmpty(etListOfPassengersAge) || Integer.valueOf(etListOfPassengersAge.getText().toString().trim()) > 125)
                        CommonUtil.byToastMessage(PassengersDetail.this, getString(R.string.enter_valid_age));
                    else if (!CommonUtil.checkConditionsEditTextIsEmpty(etListOfPassengersEmail) && !CommonUtil.isValidEmail(etListOfPassengersEmail.getText().toString().trim()))
                        CommonUtil.byToastMessage(PassengersDetail.this, getString(R.string.enter_valid_email_id));
                    else {

                        JSONObject objectPassengersDetail = new JSONObject();

                        objectPassengersDetail.put(Constants.EVENT_PASSENGER_FIRST_NAME, etListOfPassengerFirstName.getText().toString().trim());
                        objectPassengersDetail.put(Constants.EVENT_PASSENGER_LAST_NAME, etListOfPassengerLastName.getText().toString().trim());
                        objectPassengersDetail.put(Constants.EVENT_PASSENGER_AGE, etListOfPassengersAge.getText().toString().trim());
                        objectPassengersDetail.put(Constants.EVENT_PASSENGER_CONTACT_NUMBER, etListOfPassengersContact.getText().toString().trim());
                        objectPassengersDetail.put(Constants.EVENT_PASSENGER_EMAIL, TextUtils.isEmpty(etListOfPassengersEmail.getText().toString().trim()) ? "" : etListOfPassengersEmail.getText().toString().trim());

                        arrayPassengersDetail.put(objectPassengersDetail);
                        setData(PassengersDetail.this, arrayPassengersDetail);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btListOfPassengersSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject objectCreateEvent = objectIntent;
                try {

                    int radioButtonID = rgListOfPassengersTravellingMode.getCheckedRadioButtonId();
                    View radioButton = rgListOfPassengersTravellingMode.findViewById(radioButtonID);
                    int idx = rgListOfPassengersTravellingMode.indexOfChild(radioButton);
                    String value = ((RadioButton) findViewById(radioButtonID)).getText().toString();
                    String eventId = CommonUtil.indexId(Constants.EVENT_ID + "-->" + SharedPreferenceUtil.getString(Constants.CURRENT_USER_ID, ""));

                    SharedPreferenceUtil.putValue(Constants.TRIP_EVENT_ID, eventId);
                    SharedPreferenceUtil.save();

                    objectCreateEvent.put(Constants.EVENT_ID, eventId);
                    objectCreateEvent.put(Constants.TRAVELLING_TYPE_ID, (idx + 1) + "");
                    objectCreateEvent.put(Constants.TRAVELLING_TYPE_NAME, value);
                    objectCreateEvent.put(Constants.LIST_OF_PASSENGERS_DETAIL, arrayPassengersDetail);
                    submit(objectCreateEvent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setData(Activity activity, JSONArray arrayPassengersDetail) {
        lvListOfPassengersList.setAdapter(new AdapterPassengersDetail(activity, arrayPassengersDetail, 0));
        CommonUtil.setListViewHeightBasedOnChildren(lvListOfPassengersList);

        int count = (Integer.valueOf(objectIntent.optString(Constants.EVENT_PASSENGERS_count)) - lvListOfPassengersList.getCount());
        btListOfPassengersAddPassenger.setText("Add Passengers   (" + (count) + ")");

        if (count == 0) {
            btListOfPassengersAddPassenger.setVisibility(View.GONE);
            llListOfPassengersMainList.setVisibility(View.GONE);
            btListOfPassengersSubmit.setVisibility(View.VISIBLE);
            rbListOfPassengersFlight.setVisibility(View.VISIBLE);
            rbListOfPassengersCar.setVisibility(View.VISIBLE);
            rbListOfPassengersTrain.setVisibility(View.VISIBLE);
            rbListOfPassengersBus.setVisibility(View.VISIBLE);
            tvListOfPassengersTravelMode.setVisibility(View.VISIBLE);
        } else {
            btListOfPassengersAddPassenger.setVisibility(View.VISIBLE);
            llListOfPassengersMainList.setVisibility(View.VISIBLE);
            btListOfPassengersSubmit.setVisibility(View.GONE);
            rbListOfPassengersFlight.setVisibility(View.GONE);
            rbListOfPassengersCar.setVisibility(View.GONE);
            rbListOfPassengersTrain.setVisibility(View.GONE);
            rbListOfPassengersBus.setVisibility(View.GONE);
            tvListOfPassengersTravelMode.setVisibility(View.GONE);
        }

        etListOfPassengerFirstName.setText("");
        etListOfPassengerLastName.setText("");
        etListOfPassengersContact.setText("");
        etListOfPassengersEmail.setText("");
        etListOfPassengersAge.setText("");
    }

    public void submit(JSONObject objectUserEventInfo) {

        JSONArray arrayTripDetail = new JSONArray();
        arrayTripDetail.put(objectUserEventInfo);

        SharedPreferenceUtil.putValue(Constants.TRIP_BOOKING, arrayTripDetail.toString());
        SharedPreferenceUtil.save();

        CommonUtil.byLogMessage(this, "Passengers Detail" + SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));

        reloadMove();
    }

    public void reloadMove() {
        CommonUtil.showProgressDialog(this, "Adding Passengers...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    CommonUtil.closeKeyboard(PassengersDetail.this);

                    if (rbListOfPassengersBus.isChecked())
                        startActivity(new Intent(PassengersDetail.this, TravellingType.class).putExtra(Constants.TRAVEL_TYPE, Constants.BUS));
                    else if (rbListOfPassengersTrain.isChecked())
                        startActivity(new Intent(PassengersDetail.this, TravellingType.class).putExtra(Constants.TRAVEL_TYPE, Constants.TRAIN));
                    else if (rbListOfPassengersFlight.isChecked())
                        startActivity(new Intent(PassengersDetail.this, TravellingType.class).putExtra(Constants.TRAVEL_TYPE, Constants.FLIGHT));
                    else
                        startActivity(new Intent(PassengersDetail.this, TravellingType.class).putExtra(Constants.TRAVEL_TYPE, Constants.CAR));

                    CommonUtil.cancelProgressDialog();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
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