package college_project.dreamtravels;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Adapter.AdapterPassengersDetail;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class FinalEventDetails extends AppCompatActivity {

    TextView tvFinalEventDetailsFromDate, tvFinalEventDetailsToDate,
            tvFinalEventDetailsPickUp, tvFinalEventDetailsDropOff,
            tvFinalEventDetailsPassengers, tvFinalEventDetailsLuggage,
            tvFinalEventDetailsDurationName, tvFinalEventDetailsTravellingName,
            tvFinalEventDetailsOtherReq, tvElementTravellingTypeName,
            tvElementTravellingTypeDepartureTime, tvElementTravellingTypeTimeAquire,
            tvElementTravellingTypeReachedTime, tvElementTravellingTypeTravelType,
            tvElementTravellingTypeDepartureAmount, tvElementTravellingTypeSeat,
            tvElementHotelBookingName, tvElementHotelBookingTime,
            tvElementHotelBookingRoomType, tvElementHotelBookingAmount,
            tvElementHotelBookingRoomAvailable;
    ListView lvFinalEventDetailsPassengersList;
    Button btFinalEventDetailsAmountSubmit;
    JSONArray arrayTripData;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_event_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvFinalEventDetailsFromDate = (TextView) findViewById(R.id.tvFinalEventDetailsFromDate);
        tvFinalEventDetailsToDate = (TextView) findViewById(R.id.tvFinalEventDetailsToDate);
        tvFinalEventDetailsPickUp = (TextView) findViewById(R.id.tvFinalEventDetailsPickUp);
        tvFinalEventDetailsDropOff = (TextView) findViewById(R.id.tvFinalEventDetailsDropOff);
        tvFinalEventDetailsPassengers = (TextView) findViewById(R.id.tvFinalEventDetailsPassengers);
        tvFinalEventDetailsLuggage = (TextView) findViewById(R.id.tvFinalEventDetailsLuggage);
        tvFinalEventDetailsDurationName = (TextView) findViewById(R.id.tvFinalEventDetailsDurationName);
        tvFinalEventDetailsTravellingName = (TextView) findViewById(R.id.tvFinalEventDetailsTravellingName);
        tvFinalEventDetailsOtherReq = (TextView) findViewById(R.id.tvFinalEventDetailsOtherReq);
        lvFinalEventDetailsPassengersList = (ListView) findViewById(R.id.lvFinalEventDetailsPassengersList);
        btFinalEventDetailsAmountSubmit = (Button) findViewById(R.id.btFinalEventDetailsAmountSubmit);


        tvElementTravellingTypeName = (TextView) findViewById(R.id.tvElementTravellingTypeName);
        tvElementTravellingTypeDepartureTime = (TextView) findViewById(R.id.tvElementTravellingTypeDepartureTime);
        tvElementTravellingTypeTimeAquire = (TextView) findViewById(R.id.tvElementTravellingTypeTimeAquire);
        tvElementTravellingTypeReachedTime = (TextView) findViewById(R.id.tvElementTravellingTypeReachedTime);
        tvElementTravellingTypeTravelType = (TextView) findViewById(R.id.tvElementTravellingTypeTravelType);
        tvElementTravellingTypeDepartureAmount = (TextView) findViewById(R.id.tvElementTravellingTypeAmount);
        tvElementTravellingTypeSeat = (TextView) findViewById(R.id.tvElementTravellingTypeSeat);


        tvElementHotelBookingName = (TextView) findViewById(R.id.tvElementHotelBookingName);
        tvElementHotelBookingTime = (TextView) findViewById(R.id.tvElementHotelBookingTime);
        tvElementHotelBookingRoomType = (TextView) findViewById(R.id.tvElementHotelBookingRoomType);
        tvElementHotelBookingAmount = (TextView) findViewById(R.id.tvElementHotelBookingAmount);
        tvElementHotelBookingRoomAvailable = (TextView) findViewById(R.id.tvElementHotelBookingRoomAvailable);

        bookEvent(getIntent().getBooleanExtra(Constants.EVENT_OR_HISTORY, false));

    }

    public void bookEvent(boolean book) {
        try {
            arrayTripData = new JSONArray(SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));
            JSONObject objectTripData = arrayTripData.optJSONObject(0);
            JSONArray arrayPassengersData = objectTripData.getJSONArray(Constants.LIST_OF_PASSENGERS_DETAIL);

            tvFinalEventDetailsPickUp.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_PICK_UP))));
            tvFinalEventDetailsDropOff.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_DROP_OFF))));
            tvFinalEventDetailsFromDate.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_FROM_DATE))));
            tvFinalEventDetailsPassengers.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_PASSENGERS_count))));
            tvFinalEventDetailsLuggage.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_LUGGAGE))));
            tvFinalEventDetailsDurationName.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_DURATION_TYPE_NAME))));
            tvFinalEventDetailsTravellingName.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.TRAVELLING_MODE_NAME))));

            if (!TextUtils.isEmpty(objectTripData.optString(Constants.EVENT_OTHERS_REQ)))
                tvFinalEventDetailsOtherReq.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_OTHERS_REQ))));
            else
                tvFinalEventDetailsOtherReq.append(Html.fromHtml(CommonUtil.getColoredSpanned("No Requirements")));

            if (!TextUtils.isEmpty(objectTripData.optString(Constants.EVENT_TO_DATE)))
                tvFinalEventDetailsToDate.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectTripData.optString(Constants.EVENT_TO_DATE))));
            else
                tvFinalEventDetailsToDate.setVisibility(View.GONE);

            JSONArray arrayTravellingTypeFromDate = objectTripData.optJSONArray(Constants.TRAVELLING_TYPE_FROM_DATE);
            JSONObject objectTravellingType = arrayTravellingTypeFromDate.optJSONObject(0);

            tvElementTravellingTypeName.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_NAME));
            tvElementTravellingTypeDepartureTime.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_DEPARTURE_TIME));
            tvElementTravellingTypeTimeAquire.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_TIME_AQUIRE));
            tvElementTravellingTypeReachedTime.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_REACHED_TIME));
            tvElementTravellingTypeTravelType.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_TRAVEL_MODE));
            tvElementTravellingTypeDepartureAmount.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_AMOUNT));
            tvElementTravellingTypeSeat.setText(objectTravellingType.optString(Constants.TRAVELLING_MODE_SEAT));
            tvElementTravellingTypeSeat.setVisibility(View.INVISIBLE);

            JSONArray arrayHotelBooking = objectTripData.optJSONArray(Constants.HOTEL_BOOKING);
            JSONObject objectHotelBooking = arrayHotelBooking.optJSONObject(0);

            tvElementHotelBookingName.setText(objectHotelBooking.optString(Constants.HOTEL_NAME));
            tvElementHotelBookingRoomType.setText(objectHotelBooking.optString(Constants.HOTEL_ROOM_TYPE));
            tvElementHotelBookingAmount.setText(objectHotelBooking.optString(Constants.HOTEL_AMOUNT));
            tvElementHotelBookingRoomAvailable.setText(objectHotelBooking.optString(Constants.HOTEL_ROOM_AVAILABILITY));
            tvElementHotelBookingRoomAvailable.setVisibility(View.INVISIBLE);

            lvFinalEventDetailsPassengersList.setAdapter(new AdapterPassengersDetail(this, arrayPassengersData, 1));
            CommonUtil.setListViewHeightBasedOnChildren(lvFinalEventDetailsPassengersList);

            int amountTravel = Integer.valueOf(CommonUtil.removeSymbols(this, tvElementTravellingTypeDepartureAmount.getText().toString().trim()));
            int amountHotel = Integer.valueOf(CommonUtil.removeSymbols(this, tvElementHotelBookingAmount.getText().toString().trim()));
            int passengerCount = Integer.valueOf(CommonUtil.removeSymbols(this, tvFinalEventDetailsPassengers.getText().toString().trim().replace("No. of Passengers ", "").trim()));
            total = (amountHotel + amountTravel) * passengerCount;

            if (book) {
                btFinalEventDetailsAmountSubmit.setText("PAY   ₹" + total);

                btFinalEventDetailsAmountSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submit();
                    }
                });
            } else
                btFinalEventDetailsAmountSubmit.setText("₹" + total);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void submit() {
        JSONObject objectMain = new JSONObject();
        try {
            if (SharedPreferenceUtil.contains(Constants.All_USER_DATA) && !TextUtils.isEmpty(SharedPreferenceUtil.getString(Constants.All_USER_DATA, "")))
                objectMain = new JSONObject(SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

            JSONArray arrayUserData = objectMain.optJSONArray(SharedPreferenceUtil.getString(Constants.CURRENT_USER_ID, ""));
            JSONObject objectUserData = arrayUserData.optJSONObject(0);

            JSONObject objectEventData = new JSONObject();
            JSONArray arrayEventData = new JSONArray();

            if (objectUserData.has(Constants.EVENT_INFO)) {
                arrayEventData = objectUserData.optJSONArray(Constants.EVENT_INFO);
                objectEventData = arrayEventData.optJSONObject(0);
            } else {
                arrayEventData.put(objectEventData);
            }
            arrayTripData.optJSONObject(0).put(Constants.EVENT_AMOUNT, total + "");
            objectEventData.put(SharedPreferenceUtil.getString(Constants.TRIP_EVENT_ID, ""), arrayTripData);
            objectUserData.put(Constants.EVENT_INFO, arrayEventData);

            SharedPreferenceUtil.putValue(Constants.TRIP_BOOKING, "");
            SharedPreferenceUtil.putValue(Constants.All_USER_DATA, objectMain.toString());
            SharedPreferenceUtil.save();

            alertBox();

            CommonUtil.byLogMessage(this, "Final Booking" + SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void goToHomePage() {
        CommonUtil.showProgressDialog(this, "Booking Done!!!");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    CommonUtil.closeKeyboard(FinalEventDetails.this);
                    startActivity(new Intent(FinalEventDetails.this, DashBoard.class));
                    CommonUtil.cancelProgressDialog();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void alertBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FinalEventDetails.this);
        builder.setCancelable(false);
        builder.setTitle("Booked!");
        builder.setMessage("Confirmed your Booking");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                goToHomePage();
            }
        });
        builder.create();
        builder.show();
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
