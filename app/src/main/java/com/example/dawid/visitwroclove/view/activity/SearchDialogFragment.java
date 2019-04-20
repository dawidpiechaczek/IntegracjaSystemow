package com.example.dawid.visitwroclove.view.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.utils.OnSearchFragmentCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDialogFragment extends DialogFragment {
    @BindView(R.id.fs_ac_search)
    AutoCompleteTextView searchAutoComplete;

    private OnSearchFragmentCallback callback;
    private static List<BaseDTO> listOfPlacesAndEvents = new ArrayList<>();

    public static SearchDialogFragment newInstance(OnSearchFragmentCallback callback, List<BaseDTO> listOfPlacesAndEvents1) {
        SearchDialogFragment noteFragment = new SearchDialogFragment();
        noteFragment.callback = callback;
        listOfPlacesAndEvents = listOfPlacesAndEvents1;
        return noteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, v);
        List<String> names = new ArrayList<>();
        for (BaseDTO baseDTO : listOfPlacesAndEvents) {
            names.add(baseDTO.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, names);
        searchAutoComplete.setAdapter(adapter);
        return v;
    }

    @OnClick(R.id.dialog_cancel)
    public void cancelNote() {
        dismiss();
    }

    @OnClick(R.id.dialog_save)
    public void searchNote() {
        String nameText = searchAutoComplete.getText().toString();
        callback.onSearch(nameText);
        dismiss();
    }
}

