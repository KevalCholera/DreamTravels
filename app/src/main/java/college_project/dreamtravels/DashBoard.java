package college_project.dreamtravels;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class DashBoard extends AppCompatActivity {

    TextView tvDashboardCustom, tvDashboardPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        tvDashboardCustom = (TextView) findViewById(R.id.tvDashboardCustom);
        tvDashboardPre = (TextView) findViewById(R.id.tvDashboardPre);

        CommonUtil.byLogMessage(this, SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));
        try {
            JSONObject jsonObject = new JSONObject(SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));
            JSONArray jsonArray = jsonObject.optJSONArray(SharedPreferenceUtil.getString(Constants.CURRENT_USER_ID, ""));
            JSONObject jsonObject1 = jsonArray.optJSONObject(0);
            JSONArray jsonArray1 = jsonObject1.optJSONArray(Constants.USER_PROFILE);
            JSONObject jsonObject2 = jsonArray1.optJSONObject(0);
            String fullName = jsonObject2.optString(Constants.USER_FULL_NAME);

            setTitle(fullName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        tvDashboardPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this, TripBooking.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferenceUtil.putValue(Constants.ACCESS_TOKEN, 0);
                startActivity(new Intent(DashBoard.this, SignIn.class));
                finish();
                break;
            case R.id.historyOfTripAndTravels:
                startActivity(new Intent(DashBoard.this, HistoryOfTripAndTravels.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 5000);
        }
    }
}