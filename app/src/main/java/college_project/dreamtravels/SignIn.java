package college_project.dreamtravels;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    Button btSignInSignIn, btSignInSignUp;
    EditText etSignInUserName, etSignInPassword;
    TextView tvSignInForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btSignInSignIn = (Button) findViewById(R.id.btSignInSignIn);
        btSignInSignUp = (Button) findViewById(R.id.btSignInSignUp);
        etSignInUserName = (EditText) findViewById(R.id.etSignInUserName);
        etSignInPassword = (EditText) findViewById(R.id.etSignInPassword);
        tvSignInForgotPassword = (TextView) findViewById(R.id.tvSignInForgotPassword);

        btSignInSignIn.setOnClickListener(this);
        btSignInSignUp.setOnClickListener(this);
        tvSignInForgotPassword.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusignin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btSignInSignIn:

                if (!CommonUtil.isValidEmail(etSignInUserName.getText().toString()) && TextUtils.isEmpty(etSignInPassword.getText().toString()))
                    CommonUtil.byToastMessage(this, getResources().getString(R.string.enter_fields_below));
                else if (!CommonUtil.isValidEmail(etSignInUserName.getText().toString()))
                    CommonUtil.byToastMessage(this, getResources().getString(R.string.enter_user));
                else if (TextUtils.isEmpty(etSignInPassword.getText().toString()))
                    CommonUtil.byToastMessage(this, getResources().getString(R.string.enter_pass));
                else {
                    CommonUtil.closeKeyboard(SignIn.this);
                    if (!TextUtils.isEmpty(SharedPreferenceUtil.getString(Constants.All_USER_DATA, "")))
                        doLogin();
                    else
                        CommonUtil.byToastMessage(this, "Invalid Credential!!!");
                }
                break;
            case R.id.btSignInSignUp:
                startActivity(new Intent(SignIn.this, SignUp.class));
                null_all();
                break;
        }
    }

    public void null_all() {
        etSignInUserName.setText("");
        etSignInPassword.setText("");
    }

    public void doLogin() {

        try {
            JSONObject objectMain = new JSONObject(SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

            for (int i = 1; i <= objectMain.length(); i++) {
                JSONArray arrayUserData = objectMain.getJSONArray(i + "");

                JSONObject objectUserData = arrayUserData.optJSONObject(0);

                JSONArray arrayUserProfile = objectUserData.getJSONArray(Constants.USER_PROFILE);
                JSONObject objectUserProfile = arrayUserProfile.getJSONObject(0);

                if (objectUserProfile.optString(Constants.USER_EMAIL).equalsIgnoreCase(etSignInUserName.getText().toString().trim()) && objectUserProfile.optString(Constants.USER_PASS).equals(etSignInPassword.getText().toString().trim())) {
                    SharedPreferenceUtil.putValue(Constants.ACCESS_TOKEN, 1);
                    SharedPreferenceUtil.putValue(Constants.CURRENT_USER_ID, objectUserProfile.optString(Constants.USER_ID));
                    SharedPreferenceUtil.save();

                    startActivity(new Intent(this, DashBoard.class));
                    break;
                } else
                    CommonUtil.byToastMessage(this, "Invalid Credential!!!");

                CommonUtil.byLogMessage(this, true + "-->" + objectUserProfile.optString(Constants.USER_EMAIL) + "-->" + etSignInUserName.getText().toString().trim() + "-->" + objectUserProfile.optString(Constants.USER_PASS) + "-->" + etSignInPassword.getText().toString().trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    boolean exit = false;

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