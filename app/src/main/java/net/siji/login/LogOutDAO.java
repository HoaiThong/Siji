package net.siji.login;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import net.siji.R;


public class LogOutDAO {
    Context mContext;
    Activity mActivity;

    public LogOutDAO(Activity activity) {
        mActivity=activity;
    }

    public boolean exit() {
        boolean f = true;
        signOutGoogle();
        logOutFacebook();
        if (isLoggedInGoogle()) f = false;
        if (isLoggedInFaceBook()) f = false;
        return f;
    }

    public void signOutGoogle() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mActivity.getString(R.string.default_web_client_id))
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(mActivity,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
                    }
                });
    }

    public void logOutFacebook() {
        LoginManager.getInstance().logOut();
    }


    public boolean isLoggedInFaceBook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public boolean isLoggedInGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mActivity);
        return account != null;
    }


}
