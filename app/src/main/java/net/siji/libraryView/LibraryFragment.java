package net.siji.libraryView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import net.siji.R;
import net.siji.userView.ActionUserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment
        extends Fragment {
    private ListView listView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user_view, container, false);
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



}
