package com.android.bignerdranch.nerdlaucher.Fragments;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bignerdranch.nerdlaucher.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NerdLauncherFragment extends Fragment {

    RecyclerView recyclerView;

    public NerdLauncherFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NerdLauncherFragment newInstance() {
        NerdLauncherFragment fragment = new NerdLauncherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_nerd_launcher, container, false);
        recyclerView = v.findViewById(R.id.launcherview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupActivities();
        return  v;
    }

    public void setupActivities(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> mResolveInfos = getActivity().getPackageManager().queryIntentActivities(intent,0);
        Collections.sort(mResolveInfos, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(pm).toString(),o2.loadLabel(pm).toString());
            }
        });
        NerdAdapter nerdAdapter = new NerdAdapter(mResolveInfos);
        recyclerView.setAdapter(nerdAdapter);

    }

    class NerdHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        ResolveInfo mResolveInfo;

        public NerdHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
            mTextView.setOnClickListener(this);
        }

        public void bindName(ResolveInfo resolveInfo){
            mResolveInfo = resolveInfo;
            mTextView.setText(mResolveInfo.loadLabel(getActivity().getPackageManager()).toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            intent.setClassName(activityInfo.applicationInfo.packageName,mResolveInfo.activityInfo.name);
            startActivity(intent);
        }
    }

    class NerdAdapter extends RecyclerView.Adapter<NerdHolder>{

        List<ResolveInfo> mActivities;

        public NerdAdapter(List<ResolveInfo> activities){
            mActivities = activities;
        }

        @Override
        public NerdHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new NerdHolder(v);
        }

        @Override
        public void onBindViewHolder(NerdHolder holder, int position) {
                holder.bindName(mActivities.get(position));
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }

}
