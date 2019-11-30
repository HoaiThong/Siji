package net.siji.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.AndroidException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import net.siji.MainActivity;
import net.siji.R;
import net.siji.dao.NetworkChangeReceiver;
import net.siji.dao.NetworkUtil;
import net.siji.fcm.FcmUtil;
import net.siji.imageSliderViewPager.IndicatorView;
import net.siji.imageSliderViewPager.PagesLessException;
import net.siji.model.Customer;
import net.siji.splashScreenView.SplashScreenActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    LoginButton fbLoginBtn;
    FcmUtil fcmUtil;
    Customer customer;
    SignInDAO signInDAO;
    NetworkUtil networkUtil;
    NetworkChangeReceiver receiver;
    Button facebook_btn;
    Button google_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        facebook_btn = findViewById(R.id.ibtn_facebook);
        google_btn = findViewById(R.id.ibtn_google);
        facebook_btn.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        fcmUtil = new FcmUtil(this);
        fcmUtil.accessToken();
        receiver = new NetworkChangeReceiver();
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        signInButton = (SignInButton) findViewById(R.id.btnLoginGg);
        signInButton.setOnClickListener(this);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn = (LoginButton) findViewById(R.id.btnLoginFb);
        fbLoginBtn.setOnClickListener(this);
        fbLoginBtn.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        fbLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "======Facebook login success======");
//                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
//                Toast.makeText(getApplicationContext(), "Login Facebook success.", Toast.LENGTH_SHORT).show();
                signInDAO = new SignInDAO(SignInActivity.this);
                signInDAO.sendRegistrationFacebookToServe();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(getApplicationContext(), "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });
        printKeyHash(this);

//        TextView btn_signin_phone = (TextView) findViewById(R.id.btnLoginPhone);
//        btn_signin_phone.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isLoggedInFaceBook() || isLoggedInGoogle()) {
            redirect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null) {
            // Sometimes the Fragment onDestroy() unregisters the observer before calling below code
            // See <a>http://stackoverflow.com/questions/6165070/receiver-not-registered-exception-error</a>
            try {
                this.unregisterReceiver(receiver);
                receiver = null;
            } catch (IllegalArgumentException e) {
                // Check wether we are in debug mode
                e.printStackTrace();
            }
        }
//        unregisterReceiver(receiver);
    }

    public void redirect() {
        Intent intent = new Intent(SignInActivity.this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_google:
                signInButton.performClick();
                signIn();
                break;
            case R.id.ibtn_facebook:
                fbLoginBtn.performClick();
                loginFaceBook();
                break;
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
                signInDAO = new SignInDAO(SignInActivity.this);
                signInDAO.sendRegistrationGoogleToServe(account);
//                redirect();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Google sign in failed!",Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public boolean isLoggedInGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        return account != null;
    }

//    public boolean isLoggedInPhone() {
//        return AccountKit.getCurrentAccessToken() != null;
//    }

    public void loginFaceBook() {
        Collection<String> permissionsFaceBook = Arrays.asList("public_profile", "user_friends", "email", "user_link", "user_age_range"
                , "user_birthday", "user_gender", "user_friends"
                , "user_location", "user_likes", "user_hometown"
                , "user_posts");
        LoginManager.getInstance().logInWithReadPermissions(this, permissionsFaceBook);
    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

}
