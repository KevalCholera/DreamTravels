package college_project.dreamtravels.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import college_project.dreamtravels.PassengersDetail;
import college_project.dreamtravels.R;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class AdapterPassengersDetail extends BaseAdapter {

    private LayoutInflater inflater = null;
    private JSONArray data;
    private Activity activity;
    private int removeIcon = 0;

    public AdapterPassengersDetail(Activity activity, JSONArray arrayPassengersDetail, int i) {
        this.data = arrayPassengersDetail;
        this.activity = activity;
        removeIcon = i;
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
            view = inflater.inflate(R.layout.element_passengers_detail, null);

        final TextView tvElementListOfPassengersFullName, tvElementListOfPassengersAge,
                tvElementListOfPassengersIndex, tvElementListOfPassengersContact, tvElementListOfPassengersEmail;
        ImageView ivElementListOfPassengersCancel;

        tvElementListOfPassengersFullName = (TextView) view.findViewById(R.id.tvElementListOfPassengersFullName);
        tvElementListOfPassengersAge = (TextView) view.findViewById(R.id.tvElementListOfPassengersAge);
        tvElementListOfPassengersIndex = (TextView) view.findViewById(R.id.tvElementListOfPassengersIndex);
        tvElementListOfPassengersContact = (TextView) view.findViewById(R.id.tvElementListOfPassengersContact);
        tvElementListOfPassengersEmail = (TextView) view.findViewById(R.id.tvElementListOfPassengersEmail);
        ivElementListOfPassengersCancel = (ImageView) view.findViewById(R.id.ivElementListOfPassengersCancel);

        tvElementListOfPassengersFullName.setText(jsonObject.optString(Constants.EVENT_PASSENGER_FIRST_NAME) + " " + jsonObject.optString(Constants.EVENT_PASSENGER_LAST_NAME));
        tvElementListOfPassengersAge.setText(jsonObject.optString(Constants.EVENT_PASSENGER_AGE));
        tvElementListOfPassengersContact.setText(jsonObject.optString(Constants.EVENT_PASSENGER_CONTACT_NUMBER));
        tvElementListOfPassengersEmail.setText(jsonObject.optString(Constants.EVENT_PASSENGER_EMAIL));
        tvElementListOfPassengersIndex.setText(Integer.valueOf(position) + 1 + "");

        if (TextUtils.isEmpty(tvElementListOfPassengersEmail.getText().toString()))
            tvElementListOfPassengersEmail.setVisibility(View.GONE);

        if (removeIcon == 1)
            ivElementListOfPassengersCancel.setVisibility(View.GONE);

        ivElementListOfPassengersCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertBox(position, tvElementListOfPassengersFullName.getText().toString().trim());

            }
        });

        return view;
    }

    private void alertBox(final int position, String passengerName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setTitle("Delete!");
        builder.setMessage("Are you sure to delete details of \"" + passengerName + "\"  ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {

                CommonUtil.showProgressDialog(activity, "Deleting Passengers Detail...");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data = CommonUtil.remove(position, data);
                        dialog.dismiss();
                        CommonUtil.cancelProgressDialog();

                        PassengersDetail.arrayPassengersDetail.remove(position);
                        PassengersDetail.setData(activity, PassengersDetail.arrayPassengersDetail);
                    }
                }, 1000);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

}