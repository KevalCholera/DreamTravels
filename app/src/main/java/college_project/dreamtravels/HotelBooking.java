package college_project.dreamtravels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Adapter.AdapterHotelBooking;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class HotelBooking extends AppCompatActivity {

    String[] hotelName = {"Taj Lake Palace", "Hotel Samode Palace",
            "Taj Rambaugh Palace", "Oberoi Grand Kolkata", "Hotel Ratan Vilas",
            "The imperial Hotel"};

    String[] roomType = {"2X Delux AC", "3X Delux AC", "2X Non AC",
            "1X Small", "4X Super Delux AC", "Exclusive Room"};

    String[] roomLeft = {"20 Rooms Available", "12 Rooms Available",
            "5 Rooms Available", "4 Rooms Available", "35 Rooms Available",
            "No Rooms Available"};

    String[] amount = {"₹ 5400", "₹ 14000", "₹ 4589", "₹ 1252", "₹ 7500", "₹ 1566"};

    ListView lvHotelBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvHotelBooking = (ListView) findViewById(R.id.lvHotelBooking);

        final JSONArray arrayHotel = new JSONArray();

        try {
            for (int i = 0; i < hotelName.length; i++) {
                JSONObject objectBus = new JSONObject();

                objectBus.put(Constants.HOTEL_NAME, hotelName[i]);
                objectBus.put(Constants.HOTEL_ROOM_TYPE, roomType[i]);
                objectBus.put(Constants.HOTEL_ROOM_AVAILABILITY, roomLeft[i]);
                objectBus.put(Constants.HOTEL_AMOUNT, amount[i]);

                arrayHotel.put(objectBus);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        lvHotelBooking = (ListView) findViewById(R.id.lvHotelBooking);
        lvHotelBooking.setAdapter(new AdapterHotelBooking(this, arrayHotel));

        lvHotelBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    JSONObject objectAddToData = arrayHotel.optJSONObject(position);
                    JSONArray arrayAddToData = new JSONArray();
                    arrayAddToData.put(objectAddToData);


                    JSONArray arrayTripDetail = new JSONArray(SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));
                    JSONObject objectTripDetail = arrayTripDetail.optJSONObject(0);
                    objectTripDetail.put(Constants.HOTEL_BOOKING, arrayAddToData);

                    SharedPreferenceUtil.putValue(Constants.TRIP_BOOKING, arrayTripDetail.toString());
                    SharedPreferenceUtil.save();

                    CommonUtil.byLogMessage(HotelBooking.this, "Hotel Booking" + SharedPreferenceUtil.getString(Constants.TRIP_BOOKING, ""));

                    reloadMove();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void reloadMove() {
        CommonUtil.showProgressDialog(this, "Fetching Trip Details...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    CommonUtil.closeKeyboard(HotelBooking.this);
                    startActivity(new Intent(HotelBooking.this, FinalEventDetails.class).putExtra(Constants.EVENT_OR_HISTORY, true));
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