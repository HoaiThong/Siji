package net.siji.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import net.siji.R;
import net.siji.readView.NotificationBugDialog;

public class LoadingDialog extends DialogFragment {
    private String message="Loading...";
    private Activity context;
    private View view;
    private TextView msg_tv;
    private FragmentManager fragmentManager;
    public LoadingDialog(Activity context) {
        this.context = context;
    }

    public LoadingDialog(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_loading, null);
        msg_tv = (TextView) view.findViewById(R.id.tv_msg_dialog);
        msg_tv.setText(message);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        return builder.create();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void show(){
        this.show(fragmentManager,"dialog");
    }
}
