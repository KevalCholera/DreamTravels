package college_project.dreamtravels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Adapter.AdapterHistoryOfTripAndTravels;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class HistoryOfTripAndTravels extends AppCompatActivity {

    ListView lvHistoryOfTripAndTravels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_trip_and_travels);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvHistoryOfTripAndTravels = (ListView) findViewById(R.id.lvHistoryOfTripAndTravels);

        try {
            JSONObject objectMain = new JSONObject();

            if (SharedPreferenceUtil.contains(Constants.All_USER_DATA) && !TextUtils.isEmpty(SharedPreferenceUtil.getString(Constants.All_USER_DATA, "")))
                objectMain = new JSONObject(SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

            JSONArray arrayUserData = objectMain.optJSONArray(SharedPreferenceUtil.getString(Constants.CURRENT_USER_ID, ""));
            JSONObject objectUserData = arrayUserData.optJSONObject(0);

            if (objectUserData.has(Constants.EVENT_INFO)) {
                JSONArray arrayEventData = objectUserData.optJSONArray(Constants.EVENT_INFO);
                JSONObject objectEventDataList = arrayEventData.optJSONObject(0);

                lvHistoryOfTripAndTravels.setAdapter(new AdapterHistoryOfTripAndTravels(this, objectEventDataList));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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