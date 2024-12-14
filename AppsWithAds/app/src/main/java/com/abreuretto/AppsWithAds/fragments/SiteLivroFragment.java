package com.abreuretto.AppsWithAds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abreuretto.AppsWithAds.R;

public class SiteLivroFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site_livro, container, false);
        return view;
    }
}
