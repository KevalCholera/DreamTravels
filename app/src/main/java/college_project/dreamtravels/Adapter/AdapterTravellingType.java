package college_project.dreamtravels.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import college_project.dreamtravels.R;
import college_project.dreamtravels.Util.Constants;

public class AdapterTravellingType extends BaseAdapter {

    private LayoutInflater inflater = null;
    private JSONArray data;

    public AdapterTravellingType(Activity activity, JSONArray arrayPassengersDetail) {
        this.data = arrayPassengersDetail;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length();
    }

    public Object getItem(int position) {
        return data.optJSONObject(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final JSONObject jsonObject = data.optJSONObject(position);

        if (convertView == null)
            view = inflater.inflate(R.layout.element_travelling_type, null);

        final TextView tvElementTravellingTypeName, tvElementTravellingTypeDepartureTime,
                tvElementTravellingTypeTimeAquire, tvElementTravellingTypeReachedTime,
                tvElementTravellingTypeTravelType, tvElementTravellingTypeAmount,
                tvElementTravellingTypeSeat;

        tvElementTravellingTypeName = (TextView) view.findViewById(R.id.tvElementTravellingTypeName);
        tvElementTravellingTypeDepartureTime = (TextView) view.findViewById(R.id.tvElementTravellingTypeDepartureTime);
        tvElementTravellingTypeTimeAquire = (TextView) view.findViewById(R.id.tvElementTravellingTypeTimeAquire);
        tvElementTravellingTypeReachedTime = (TextView) view.findViewById(R.id.tvElementTravellingTypeReachedTime);
        tvElementTravellingTypeTravelType = (TextView) view.findViewById(R.id.tvElementTravellingTypeTravelType);
        tvElementTravellingTypeAmount = (TextView) view.findViewById(R.id.tvElementTravellingTypeAmount);
        tvElementTravellingTypeSeat = (TextView) view.findViewById(R.id.tvElementTravellingTypeSeat);

        tvElementTravellingTypeName.setText(jsonObject.optString(Constants.TRAVELLING_MODE_NAME));
        tvElementTravellingTypeDepartureTime.setText(jsonObject.optString(Constants.TRAVELLING_MODE_DEPARTURE_TIME));
        tvElementTravellingTypeReachedTime.setText(jsonObject.optString(Constants.TRAVELLING_MODE_REACHED_TIME));
        tvElementTravellingTypeTravelType.setText(jsonObject.optString(Constants.TRAVELLING_MODE_TRAVEL_MODE));
        tvElementTravellingTypeSeat.setText(jsonObject.optString(Constants.TRAVELLING_MODE_SEAT));
        tvElementTravellingTypeTimeAquire.setText(jsonObject.optString(Constants.TRAVELLING_MODE_TIME_AQUIRE));
        tvElementTravellingTypeAmount.setText(jsonObject.optString(Constants.TRAVELLING_MODE_AMOUNT));

        return view;
    }
}