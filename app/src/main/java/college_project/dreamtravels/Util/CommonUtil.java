package college_project.dreamtravels.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import college_project.dreamtravels.R;


public class CommonUtil {

    private static ProgressDialog pDialog;
    private static AlertDialog alert;

    public static void showProgressDialog(Context activity, String msg) {
        activity.setTheme(R.style.New_Style);
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
        activity.setTheme(R.style.AppTheme);
    }

    public static void cancelProgressDialog() {
        if (pDialog != null)
            pDialog.cancel();
    }

    public static boolean isValidEmail(CharSequence strEmail) {
        return !TextUtils.isEmpty(strEmail) && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static boolean isLegalPassword(String pass) {

        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", Pattern.DOTALL);
        Matcher m = p.matcher(pass);

        return m.find();
    }

    public static boolean isSpecialChar(String pass) {

        Pattern p = Pattern.compile("[&@!#+]", Pattern.DOTALL);
        Matcher m = p.matcher(pass);

        return m.find();
    }

    public static void byToastMessage(Activity activity, String msg) {
//        activity.setTheme(R.style.New_Style);
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
//        activity.setTheme(R.style.AppTheme);
    }

    public static void byLogMessage(Activity activity, String msg) {
//        activity.setTheme(R.style.New_Style);
        Log.i("DATA", msg);
//        activity.setTheme(R.style.AppTheme);
    }

    public static InputFilter textFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence c, int arg1, int arg2, Spanned arg3, int arg4, int arg5) {
            StringBuilder sbText = new StringBuilder(c);
            String text = sbText.toString();
            if (text.contains(" ")) {
                return "";
            }
            return c;
        }
    };

    static public void closeKeyboard(Activity a) {
        try {
//            View view = a.getCurrentFocus();
//            if (view != null) {
            if (isKeyboardVisible(a)) {
                InputMethodManager imm = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isKeyboardVisible(Activity activity) {
        ///This method is based on the one described at http://stackoverflow.com/questions/4745988/how-do-i-detect-if-software-keyboard-is-visible-on-android-device
        Rect r = new Rect();
        View contentView = activity.findViewById(android.R.id.content);
        contentView.getWindowVisibleDisplayFrame(r);
        int screenHeight = contentView.getRootView().getHeight();

        int keypadHeight = screenHeight - r.bottom;

        return
                (keypadHeight > screenHeight * 0.15);
    }

    public static String getDecimal(Double x) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        String s = formatter.format(x);
        return s;
    }

    public static String dateFormat(long date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public static String timeFormat(long time) {
        return new SimpleDateFormat("hh:mm aa").format(time);
    }

    public static boolean dateEqualCompare(String fromCompare, String toCompare) {

        long selectDate = convertStringToDate(fromCompare).getTime();
        long systemDate = convertStringToDate(toCompare).getTime();

        return selectDate == systemDate;
    }

    public static Date convertStringToDate(String date) {

        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date convertStringToDateTime(String date) {

        try {
            return new SimpleDateFormat("dd-MM-yyyy hh:mm aa").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String removeSymbols(Activity activity, String amount) {
        return amount.replace(activity.getResources().getString(R.string.currency_india), "")
                .replace("+", "")
                .replace("-", "")
                .replace("=", "").trim();
    }

    public static boolean timeCompare(String fromCompare, String toCompare) {

        long selectTime = 0;
        long systemTime = 0;

        try {
            selectTime = new SimpleDateFormat("hh:mm aa").parse(fromCompare).getTime();
            systemTime = new SimpleDateFormat("hh:mm aa").parse(toCompare).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return selectTime >= systemTime;
    }

    public static String indexId(String constantsString) {

        if (!TextUtils.isEmpty(SharedPreferenceUtil.getString(constantsString, "")))
            SharedPreferenceUtil.putValue(constantsString, (Integer.valueOf(SharedPreferenceUtil.getString(constantsString, "")) + 1) + "");
        else
            SharedPreferenceUtil.putValue(constantsString, "1");

        SharedPreferenceUtil.save();
        return SharedPreferenceUtil.getString(constantsString, "");

    }

    private static List<JSONObject> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<JSONObject> result = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            final JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    public static JSONArray remove(final int idx, final JSONArray from) {
        final List<JSONObject> objs = asList(from);
        objs.remove(idx);
        final JSONArray ja = new JSONArray();
        for (final JSONObject obj : objs) {
            ja.put(obj);
        }
        return ja;
    }

    public static boolean checkConditionsEditTextIsEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static String getColoredSpanned(String text) {
        return "<font color=#000000>" + text + "</font>";
    }
}