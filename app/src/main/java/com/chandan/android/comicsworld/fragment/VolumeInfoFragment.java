package com.chandan.android.comicsworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chandan.android.comicsworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeInfoFragment extends Fragment {


    public VolumeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volume_info, container, false);
    }

}
