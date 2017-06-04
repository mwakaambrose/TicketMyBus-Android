package biz.kanamo.ambrose.ticketmybus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

import static com.digits.sdk.android.Digits.getActiveSession;

public class Login extends AppCompatActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "fXoIMqLMVZJOiGyxvybjlLgAs";
    private static final String TWITTER_SECRET = "Kyu91kWXxdnuXgldqWbWaZUhoSwp1saAi7z4WH2hPEpzucDk9U";


    private static final String TAG = "LoginInActivity";
    private static final int RC_SIGN_IN = 9001;

    DigitsSession digitSession;

    EditText user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        user_name = (EditText) findViewById(R.id.user_name);


        digitSession = getActiveSession();

        if (digitSession != null){
            String phone = digitSession.getPhoneNumber();
            if (phone.contains("+2567")){
                startActivity(new Intent(this, Home.class));
                finish();
            }
        }

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                // save the user name to a prefference.
                String username = user_name.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("ticketmybus", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.commit();
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
                }

            @Override
            public void failure(DigitsException exception) {
                Toast.makeText(Login.this, "Phone Number verification failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    private void signIn() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
