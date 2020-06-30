package college_project.dreamtravels;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.qqtheme.framework.picker.OptionPicker;
import college_project.dreamtravels.Util.CommonUtil;
import college_project.dreamtravels.Util.Constants;

public class SecurityQuestion extends AppCompatActivity implements View.OnClickListener {
    EditText etSecurityQuestion1, etSecurityAnswer1, etSecurityQuestion2, etSecurityAnswer2;
    CheckBox cbSecurityFromOrganization, cbSecurityFromPrivacyPolicy, cbSecurityAboutTaxinearu;
    Button btSecuritySave;
    TextView tvSecurityTermsConditions, tvSecurityPrivacyPolicy;
    CoordinatorLayout clSecurityQuestion;
    ImageView btSecurityBack;
    int tvQuestionClicked = 0;
    String[] stringArr = {"What was your childhood nickname?", "In what city or town was your first school"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);

        etSecurityQuestion1 = (EditText) findViewById(R.id.etSecurityQuestion1);
        etSecurityAnswer1 = (EditText) findViewById(R.id.etSecurityAnswer1);
        etSecurityQuestion2 = (EditText) findViewById(R.id.etSecurityQuestion2);
        etSecurityAnswer2 = (EditText) findViewById(R.id.etSecurityAnswer2);
        cbSecurityFromOrganization = (CheckBox) findViewById(R.id.cbSecurityFromOrganization);
        cbSecurityFromPrivacyPolicy = (CheckBox) findViewById(R.id.cbSecurityFromPrivacyPolicy);
        cbSecurityAboutTaxinearu = (CheckBox) findViewById(R.id.cbSecurityAboutTaxinearu);
        btSecuritySave = (Button) findViewById(R.id.btSecuritySave);
        tvSecurityTermsConditions = (TextView) findViewById(R.id.tvSecurityTermsConditions);
        tvSecurityPrivacyPolicy = (TextView) findViewById(R.id.tvSecurityPrivacyPolicy);
        clSecurityQuestion = (CoordinatorLayout) findViewById(R.id.clSecurityQuestion);
        btSecurityBack = (ImageView) findViewById(R.id.btSecurityBack);

        etSecurityQuestion1.setText(stringArr[0]);
        etSecurityQuestion2.setText(stringArr[1]);

        etSecurityQuestion1.setOnClickListener(this);
        etSecurityQuestion2.setOnClickListener(this);
        btSecuritySave.setOnClickListener(this);
        btSecurityBack.setOnClickListener(this);
        tvSecurityPrivacyPolicy.setOnClickListener(this);
        tvSecurityTermsConditions.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSecuritySave:
                if (TextUtils.isEmpty(etSecurityQuestion1.getText().toString().trim()) || TextUtils.isEmpty(etSecurityQuestion2.getText().toString().trim()) || TextUtils.isEmpty(etSecurityAnswer1.getText().toString().trim()) || TextUtils.isEmpty(etSecurityAnswer2.getText().toString()))
                    CommonUtil.byToastMessage(SecurityQuestion.this, getResources().getString(R.string.enter_fields_below));
                else if (!cbSecurityFromPrivacyPolicy.isChecked())
                    CommonUtil.byToastMessage(SecurityQuestion.this, getResources().getString(R.string.check_term));
                else {
                    CommonUtil.closeKeyboard(SecurityQuestion.this);
                    doSignUp();
                }
                break;
            case R.id.btSecurityBack:
                finish();
                CommonUtil.closeKeyboard(this);
                break;
            case R.id.etSecurityQuestion1:
                CommonUtil.closeKeyboard(this);
                tvQuestionClicked = 1;
                selectSecurityQuestions();
                break;
            case R.id.etSecurityQuestion2:
                CommonUtil.closeKeyboard(this);
                tvQuestionClicked = 2;
                selectSecurityQuestions();
                break;
            case R.id.tvSecurityTermsConditions:
                startActivity(new Intent(this, AboutUs.class));
                break;
            case R.id.tvSecurityPrivacyPolicy:
                startActivity(new Intent(this, AboutUs.class).putExtra("privacy", "privacy"));
                break;
        }
    }

    private void doSignUp() {
        try {

            JSONObject objectUserProfile = new JSONObject(getIntent().getStringExtra("objectSignUpData"));
            String userId = CommonUtil.indexId(Constants.USER_ID);

            objectUserProfile.put(Constants.USER_ID, userId)
                    .put(Constants.SEQ_QUESTION1, etSecurityQuestion1.getText().toString().trim())
                    .put(Constants.SEQ_QUESTION2, etSecurityQuestion2.getText().toString().trim())
                    .put(Constants.SEQ_ANSWER1, etSecurityAnswer1.getText().toString().trim())
                    .put(Constants.SEQ_ANSWER2, etSecurityAnswer2.getText().toString().trim());


            JSONObject objectMain = new JSONObject();

            if (SharedPreferenceUtil.contains(Constants.All_USER_DATA) && !TextUtils.isEmpty(SharedPreferenceUtil.getString(Constants.All_USER_DATA, "")))
                objectMain = new JSONObject(SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

            JSONArray arrayUserProfile = new JSONArray();
            arrayUserProfile.put(objectUserProfile);

            JSONObject objectUserData = new JSONObject();
            objectUserData.put(Constants.USER_PROFILE, arrayUserProfile);

            JSONArray arrayUserData = new JSONArray();
            arrayUserData.put(objectUserData);

            objectMain.put(userId, arrayUserData);

            SharedPreferenceUtil.putValue(Constants.ACCESS_TOKEN, 1);
            SharedPreferenceUtil.putValue(Constants.CURRENT_USER_ID, userId);
            SharedPreferenceUtil.putValue(Constants.All_USER_DATA, objectMain.toString().trim());
            SharedPreferenceUtil.save();

            CommonUtil.byLogMessage(this, SharedPreferenceUtil.getString(Constants.All_USER_DATA, ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        reloadMove();
    }

    public void reloadMove() {
        CommonUtil.showProgressDialog(this, "Creating Account...");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    CommonUtil.closeKeyboard(SecurityQuestion.this);
                    startActivity(new Intent(SecurityQuestion.this, DashBoard.class));
                    CommonUtil.cancelProgressDialog();

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void selectSecurityQuestions() {
        try {

            OptionPicker picker = new OptionPicker(SecurityQuestion.this, stringArr);
            picker.setTextSize(15);

            picker.setTitleText("Select Question");
            picker.setTitleTextColor(ActivityCompat.getColor(SecurityQuestion.this, R.color.white));
            picker.setTopBackgroundColor(ActivityCompat.getColor(SecurityQuestion.this, R.color.colorAccent));
            picker.setTopLineColor(ActivityCompat.getColor(SecurityQuestion.this, R.color.colorAccent));
            picker.setCancelTextColor(ActivityCompat.getColor(SecurityQuestion.this, R.color.white));
            picker.setSubmitTextColor(ActivityCompat.getColor(SecurityQuestion.this, R.color.white));
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(String option) {
                    if (tvQuestionClicked == 1) {
                        etSecurityQuestion1.setText(option);
                        etSecurityQuestion1.setTag("1");
                    } else if (tvQuestionClicked == 2) {
                        etSecurityQuestion2.setText(option);
                        etSecurityQuestion2.setTag("2");
                    }
                }
            });
            picker.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}