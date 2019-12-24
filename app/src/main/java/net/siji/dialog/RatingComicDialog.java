package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.inforView.RatingComicAsyncTask;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;

public class RatingComicDialog extends DialogFragment {
    private String message = "Loading...";
    private Activity context;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    private Context mContext;
    private Comic comic;
    private String idCustomer;
    private String fcmtoken;
    private String API_URL_RATING_STAR = "";

    public RatingComicDialog(Activity context) {
        this.context = context;
    }

    public RatingComicDialog(Context mContext, FragmentManager fragmentManager, Comic comic) {
        this.fragmentManager = fragmentManager;
        this.comic = comic;
        this.mContext = mContext;
        SessionManager sessionManager = new SessionManager(mContext);
        idCustomer = sessionManager.getReaded("idUser");
        fcmtoken = sessionManager.getReaded("tokenfcm");
        API_URL_RATING_STAR = new ApiManager().API_URL_RATING_STAR;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_rating_comic_dialog, null);
        ImageView imgIcon = view.findViewById(R.id.img_dialog);
        Button cancelBtn = view.findViewById(R.id.btn_cancel);
        final RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        TextView tv_title = view.findViewById(R.id.tv_title_comic);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        tv_title.setText(comic.getName());
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                String rate = String.valueOf(rating);
                String idComic=String.valueOf(comic.getId());
                new RatingComicAsyncTask().execute(idCustomer, idComic, rate, fcmtoken, API_URL_RATING_STAR);
                Toast.makeText(mContext,rate,Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        Glide.with(mContext)
                .load(comic.getIconUrl())
                .into(imgIcon);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        return builder.create();
    }

    public void show() {
        this.show(fragmentManager, "dialog");
        this.setCancelable(false);
    }
}
