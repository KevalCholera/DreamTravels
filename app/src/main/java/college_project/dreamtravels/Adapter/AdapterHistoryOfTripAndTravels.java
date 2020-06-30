package college_project.dreamtravels.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import college_project.dreamtravels.FinalEventDetails;
import college_project.dreamtravels.R;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class AdapterHistoryOfTripAndTravels extends BaseAdapter {

    private LayoutInflater inflater = null;
    private JSONObject data;
    private Activity activity;

    public AdapterHistoryOfTripAndTravels(Activity activity, JSONObject data) {
        this.data = data;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length();
    }

    public Object getItem(int position) {
        return data.optJSONArray((position + 1) + "");
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final JSONArray arrayEventData = data.optJSONArray((position + 1) + "");

        CommonUtil.byLogMessage(activity, "ID==>" + (position + 1) + "");
        CommonUtil.byLogMessage(activity, "Array" + arrayEventData.toString());
        CommonUtil.byLogMessage(activity, "Object" + arrayEventData.optJSONObject(0).toString());

        final JSONObject objectEventData = arrayEventData.optJSONObject(0);

        if (convertView == null)
            view = inflater.inflate(R.layout.element_history_of_trip_and_travels, null);

        final TextView tvElementHistoryOfTripAndTravelsFromDate, tvElementHistoryOfTripAndTravelsToDate,
                tvElementHistoryOfTripAndTravelsPickUp, tvElementHistoryOfTripAndTravelsDropOff,
                tvElementHistoryOfTripAndTravelsTravelMode, tvElementHistoryOfTripAndTravelsHotelName,
                tvElementHistoryOfTripAndTravelsAmount, tvElementHistoryOfTripAndTravelsEventCount;

        tvElementHistoryOfTripAndTravelsFromDate = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsFromDate);
        tvElementHistoryOfTripAndTravelsToDate = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsToDate);
        tvElementHistoryOfTripAndTravelsPickUp = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsPickUp);
        tvElementHistoryOfTripAndTravelsDropOff = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsDropOff);
        tvElementHistoryOfTripAndTravelsTravelMode = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsTravelMode);
        tvElementHistoryOfTripAndTravelsHotelName = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsHotelName);
        tvElementHistoryOfTripAndTravelsAmount = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsAmount);
        tvElementHistoryOfTripAndTravelsEventCount = (TextView) view.findViewById(R.id.tvElementHistoryOfTripAndTravelsEventCount);

        tvElementHistoryOfTripAndTravelsFromDate.setText(activity.getString(R.string.date_of_journey_n));
        tvElementHistoryOfTripAndTravelsToDate.setText(activity.getString(R.string.date_of_return_n));
        tvElementHistoryOfTripAndTravelsPickUp.setText(activity.getString(R.string.pickup_n));
        tvElementHistoryOfTripAndTravelsTravelMode.setText(activity.getString(R.string.traveling_mode_n));
        tvElementHistoryOfTripAndTravelsDropOff.setText(activity.getString(R.string.dropoff_n));
//        tvElementHistoryOfTripAndTravelsHotelName.append(jsonObject.optString(Constants.HOTEL_NAME));
        tvElementHistoryOfTripAndTravelsAmount.setText(activity.getString(R.string.amount_));
        tvElementHistoryOfTripAndTravelsEventCount.setText((position + 1) + "");

        tvElementHistoryOfTripAndTravelsFromDate.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.EVENT_FROM_DATE))));
        tvElementHistoryOfTripAndTravelsToDate.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.EVENT_TO_DATE))));
        tvElementHistoryOfTripAndTravelsPickUp.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.EVENT_PICK_UP))));
        tvElementHistoryOfTripAndTravelsTravelMode.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.TRAVELLING_TYPE_NAME))));
        tvElementHistoryOfTripAndTravelsDropOff.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.EVENT_DROP_OFF))));
//        tvElementHistoryOfTripAndTravelsHotelName.append(jsonObject.optString(Constants.HOTEL_NAME));
        tvElementHistoryOfTripAndTravelsAmount.append(Html.fromHtml(CommonUtil.getColoredSpanned(objectEventData.optString(Constants.EVENT_AMOUNT))));

        if (TextUtils.isEmpty(objectEventData.optString(Constants.EVENT_TO_DATE)))
            tvElementHistoryOfTripAndTravelsToDate.setText("");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtil.putValue(Constants.TRIP_BOOKING, data.optString(tvElementHistoryOfTripAndTravelsEventCount.getText().toString()));
                SharedPreferenceUtil.save();
                activity.startActivity(new Intent(activity, FinalEventDetails.class).putExtra(Constants.EVENT_OR_HISTORY, false));
            }
        });

        return view;
    }
}