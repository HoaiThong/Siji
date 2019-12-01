package net.siji.userView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import net.siji.R;
import net.siji.login.LogOutDAO;
import net.siji.login.SignInActivity;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private ListView listView;
    private View view;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user_view, container, false);
        mActivity.setTitle(mActivity.getString(R.string.profile));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        init();
        return view;
    }

    public void init() {
        listView = view.findViewById(R.id.listView);
        List<Integer> list = getListActionUser();
        listView.setAdapter(new ActionUserListAdapter(view.getContext(), list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                int o = (int) listView.getItemAtPosition(position);
                excute(o);
            }
        });
    }

    private List<Integer> getListActionUser() {
        List<Integer> list = new ArrayList<>();
        list.add(R.string.profile);
        list.add(R.string.user_notification);
        list.add(R.string.logout);
        list.add(R.string.policy);
        return list;
    }

    private void excute(int i) {
        if (i == R.string.logout) {
            LogOutDAO logOutDAO = new LogOutDAO(getActivity());
            boolean exit = logOutDAO.exit();
            if (exit) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
        if (i == R.string.profile) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }
}