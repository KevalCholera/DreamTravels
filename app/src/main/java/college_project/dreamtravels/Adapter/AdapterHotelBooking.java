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

public class AdapterHotelBooking extends BaseAdapter {

    private LayoutInflater inflater = null;
    private JSONArray data;

    public AdapterHotelBooking(Activity activity, JSONArray arrayPassengersDetail) {
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
            view = inflater.inflate(R.layout.element_hotel_booking, null);

        final TextView tvElementHotelBookingName, tvElementHotelBookingTime,
                tvElementHotelBookingRoomType, tvElementHotelBookingAmount,
                tvElementHotelBookingRoomAvailable;

        tvElementHotelBookingName = (TextView) view.findViewById(R.id.tvElementHotelBookingName);
        tvElementHotelBookingTime = (TextView) view.findViewById(R.id.tvElementHotelBookingTime);
        tvElementHotelBookingRoomType = (TextView) view.findViewById(R.id.tvElementHotelBookingRoomType);
        tvElementHotelBookingAmount = (TextView) view.findViewById(R.id.tvElementHotelBookingAmount);
        tvElementHotelBookingRoomAvailable = (TextView) view.findViewById(R.id.tvElementHotelBookingRoomAvailable);

        tvElementHotelBookingName.setText(jsonObject.optString(Constants.HOTEL_NAME));
        tvElementHotelBookingRoomType.setText(jsonObject.optString(Constants.HOTEL_ROOM_TYPE));
        tvElementHotelBookingRoomAvailable.setText(jsonObject.optString(Constants.HOTEL_ROOM_AVAILABILITY));
        tvElementHotelBookingAmount.setText(jsonObject.optString(Constants.HOTEL_AMOUNT));

        return view;
    }
}