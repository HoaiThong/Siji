package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import net.siji.login.LogOutDAO;
import net.siji.login.SignInActivity;
import net.siji.model.ApiManager;
import net.siji.model.Comic;
import net.siji.sessionApp.SessionManager;

public class SummaryComicDialog extends DialogFragment {
    private String message = "Loading...";
    private Activity context;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    private Context mContext;
    private Comic comic;

    public SummaryComicDialog(Activity context) {
        this.context = context;
    }

    public SummaryComicDialog(Context mContext, FragmentManager fragmentManager, Comic comic) {
        this.fragmentManager = fragmentManager;
        this.comic = comic;
        this.mContext = mContext;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_summary_comic_dialog, null);
        ImageView imgIcon = view.findViewById(R.id.img_icon_comic);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_summary = view.findViewById(R.id.tv_summary);
        builder.setPositiveButton(getString(R.string.exit_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        tv_summary.setText(comic.getSummary());
        tv_title.setText(comic.getName());
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
