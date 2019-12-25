package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.model.Comic;

public class PoliceDialog extends DialogFragment {
    private String message = "Loading...";
    private Activity context;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    private Context mContext;
    private Comic comic;

    public PoliceDialog(Activity context) {
        this.context = context;
    }

    public PoliceDialog(Context mContext, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.mContext = mContext;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_summary_comic_dialog, null);
        CardView imgIcon = view.findViewById(R.id.card_img);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_summary = view.findViewById(R.id.tv_summary);
        builder.setPositiveButton(getString(R.string.xac_nhan), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        tv_summary.setText(mContext.getResources().getString(R.string.policy));
        tv_title.setText(mContext.getResources().getString(R.string.content_policy));
        imgIcon.setVisibility(View.GONE);
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
