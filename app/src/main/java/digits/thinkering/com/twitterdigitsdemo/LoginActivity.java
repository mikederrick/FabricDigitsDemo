package digits.thinkering.com.twitterdigitsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsOAuthSigning;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    private AuthCallback authCallback;

    private static final String DIGITS_CONSUMER_KEY = "CONSUMER_KEY";

    private static final String DIGITS_CONSUMER_SECRET = "CONSUMER_SECRET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(DIGITS_CONSUMER_KEY, DIGITS_CONSUMER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                handleDigitsLoginSuccess(session);
            }

            @Override
            public void failure(DigitsException exception) { }
        };

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setCallback(authCallback);
    }

    private void handleDigitsLoginSuccess(DigitsSession session) {
        TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken = (TwitterAuthToken) session.getAuthToken();
        DigitsOAuthSigning oauthSigning = new DigitsOAuthSigning(authConfig, authToken);
        Map<String, String> oAuthEchoHeadersForVerifyCredentials = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();
        // Add all map entries to login request as POST parameters
    }

    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}
