package com.example.dawid.visitwroclove.view.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.enums.Categories;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.utils.OnSaveFragmentCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaveDialogFragment extends DialogFragment {
    @BindView(R.id.fs_et_notes)
    EditText name;
    @BindView(R.id.fs_et_description) EditText description;

    private OnSaveFragmentCallback callback;
    static RouteDTO route= null;

    public static SaveDialogFragment newInstance(OnSaveFragmentCallback callback, RouteDTO route1) {
        SaveDialogFragment noteFragment = new SaveDialogFragment();
        noteFragment.callback = callback;
        route = route1;
        return noteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_save, container, false);
        ButterKnife.bind(this, v);
        if(route!=null){
            name.setText(route.getName());
            description.setText(route.getDescription());
        }
        return v;
    }

    @OnClick(R.id.dialog_cancel)
    public void cancelNote() {
        dismiss();
    }

    @OnClick(R.id.dialog_save)
    public void saveNote() {
        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        String type = Categories.FAVOURITE.getValue();

        callback.onSave(nameText, descriptionText, type);
        dismiss();
    }
}
