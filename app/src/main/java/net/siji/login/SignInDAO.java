package net.siji.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.siji.MainActivity;
import net.siji.R;
import net.siji.model.Customer;
import net.siji.sessionApp.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class SignInDAO {
    private OkHttpUtil okHttpUtil;
    private Activity mActivity;
    private Customer customer;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";
    private Gson gson;
    private String url = "http://127.0.0.1/ServerKoniki/view/signin.php";
    private ProgressBar mProgressBar;
    private SessionManager sessionManager;

    public SignInDAO(Activity mActivity) {
        this.mActivity = mActivity;
        gson = getGson();
        okHttpUtil = new OkHttpUtil();
        sessionManager=new SessionManager(mActivity);
    }

    public Gson getGson() {
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();
        return gson;
    }

    public String getPackageData(String jsonCustomer, String label) {
        String data = "{\"message\":\"" + label + "\"," + "\"customer\":" + jsonCustomer + "}";
        Log.d("DATA :", data);
        return data;
    }

    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public boolean isLoggedInGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mActivity);
        return account != null;
    }


    public void loginFaceBook() {
        Collection<String> permissionsFaceBook = Arrays.asList("public_profile", "user_friends", "email", "user_link", "user_age_range"
                , "user_birthday", "user_gender", "user_friends"
                , "user_location", "user_likes", "user_hometown"
                , "user_posts");
        LoginManager.getInstance().logInWithReadPermissions(mActivity, permissionsFaceBook);
    }

    public void sendRegistrationFacebookToServe() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        getFbInfo(token);
                        // Log and toast
                        String msg = mActivity.getString(R.string.msg_token_fmt, token);
                        Log.d("token id :", token);
//                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendRegistrationGoogleToServe(final GoogleSignInAccount acc) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        getCustomerFirebaseAuthWithGoogle(acc, token);
                        // Log and toast
                        String msg = mActivity.getString(R.string.msg_token_fmt, token);
                        Log.d("token id :", token);
//                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void getCustomerFirebaseAuthWithGoogle(final GoogleSignInAccount acct, String tokenFcm) {
        mAuth = FirebaseAuth.getInstance();
        customer = new Customer();
        customer.setIdTokenFcm(tokenFcm);
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getIdToken());
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getEmail() + "---" + acct.getDisplayName());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "firebase:" + user.getUid());
//                            Log.d(TAG, "firebase:" + user.getIdToken(true).getResult().getToken());
                            if (user.getUid() != null) customer.setIdGoogle(user.getUid());
                            if (user.getDisplayName() != null)
                                customer.setNameGoogle(user.getDisplayName());
                            if (user.getEmail() != (null)) customer.setEmailGoogle(user.getEmail());
                            if (user.getPhoneNumber() != (null))
                                customer.setPhone(user.getPhoneNumber());

                            String json = gson.toJson(customer);
                            sessionManager.setReading("user",json);
                            String data = getPackageData(json, "Google");
                            Log.d("======Gson======", json);
                            OkHttpHandler httpHandler = new OkHttpHandler(mActivity);
                            boolean bool = false;
                            try {
                                bool = httpHandler.execute(data).get();

                            } catch (ExecutionException e) {
                                bool = false;
                                Log.d("======Gson======", e.toString());
                            } catch (InterruptedException e) {
                                bool = false;
                                Log.d("======Gson======", e.toString());
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    public void getFbInfo(String tokenFcm) {
        customer = new Customer();
        customer.setIdTokenFcm(tokenFcm);
        if (AccessToken.getCurrentAccessToken() != null) {
            AccessToken access_token = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject object, GraphResponse response) {
                            if (object != null) {

                                String name = object.optString(mActivity.getString(R.string.name));
                                String id = object.optString(mActivity.getString(R.string.id));
                                String email = object.optString(mActivity.getString(R.string.email));
                                String link = object.optString(mActivity.getString(R.string.link));
                                URL imageURL = extractFacebookIcon(id);
                                customer.setIdFacebook(id);
                                customer.setEmailFacebook(email);
                                customer.setLinkFacebook(link);
                                customer.setNameFaceBook(name);

                                Log.d("name: ", object.toString());
                                Log.d("id: ", id);
                                Log.d("email: ", email);
                                Log.d("link: ", link);
                                Log.d("imageURL: ", imageURL.toString());

                                String json = gson.toJson(customer);
                                Log.d("======Gson======", json);
                                sessionManager.setReading("user",json);
                                String data = getPackageData(json, "Facebook");
                                Log.d("======Gson======", json);
                                OkHttpHandler httpHandler = new OkHttpHandler(mActivity);
                                boolean bool = false;

                                try {
                                    bool = httpHandler.execute(data).get();

                                } catch (ExecutionException e) {
                                    bool = false;
                                    Log.d("======Gson======", e.toString());
                                } catch (InterruptedException e) {
                                    bool = false;
                                    Log.d("======Gson======", e.toString());
                                }


                            }
                        }
                    });
            GraphRequest request1 = GraphRequest.newMyFriendsRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONArrayCallback() {
                        @Override
                        public void onCompleted(
                                JSONArray jsonArray,
                                GraphResponse response) {
                            if (jsonArray != null) {
                                Log.d("jsonArray: ", jsonArray.toString());
                            }
                        }
                    });


            Bundle parameters = new Bundle();
            parameters.putString(mActivity.getString(R.string.fields), mActivity.getString(R.string.fields_name));
            request.setParameters(parameters);
            request.executeAsync();
            request1.executeAsync();
        }

    }

    public URL extractFacebookIcon(String id) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL imageURL = new URL("http://graph.facebook.com/" + id
                    + "/picture?type=large");
            return imageURL;
        } catch (Throwable e) {
            return null;
        }
    }

//    public void sendRegistrationPhoneToServe() {
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//                        getInfoAccountKit(token);
//                        // Log and toast
//                        String msg = mActivity.getString(R.string.msg_token_fmt, token);
//                        Log.d("token id :", token);
////                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//    public void getInfoAccountKit(String tokenFcm) {
//        customer = new Customer();
//        customer.setIdTokenFcm(tokenFcm);
//        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
//            @Override
//            public void onSuccess(final Account account) {
//                // Get Account Kit ID
//
//                // Get phone number
//                PhoneNumber phoneNumber = account.getPhoneNumber();
//                if (phoneNumber != null) {
//                    String phoneNumberString = phoneNumber.toString();
//                    customer.setPhone(phoneNumberString);
//                }
//                if (account.getId() != null) {
//                    String accountKitId = account.getId();
//                    customer.setIdAccoutKit(accountKitId);
//                }
//                if (account.getEmail() != null) {
//                    String email = account.getEmail();
//                    customer.setEmailAccoutKit(email);
//                }
//                // Get email
//
//                String json = gson.toJson(customer);
//                sessionManager.setReading("user",json);
//                String data = getPackageData(json, "Phone");
//                Log.d("======Gson======", json);
//                OkHttpHandler httpHandler = new OkHttpHandler(mActivity);
//
//                boolean bool = false;
//                try {
//                    bool = httpHandler.execute(data).get();
//
//                } catch (ExecutionException e) {
//                    bool = false;
//                    Log.d("======Gson======", e.toString());
//                } catch (InterruptedException e) {
//                    bool = false;
//                    Log.d("======Gson======", e.toString());
//                }
//
//
//            }
//
//            @Override
//            public void onError(final AccountKitError error) {
//                // Handle Error
//            }
//        });
//    }
//    public boolean isLoggedInPhone() {
//        return AccountKit.getCurrentAccessToken() != null;
//    }

    public void redirectUI() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
