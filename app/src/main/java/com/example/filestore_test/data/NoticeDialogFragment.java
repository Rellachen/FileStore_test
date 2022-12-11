package com.example.filestore_test.data;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NoticeDialogFragment extends DialogFragment {

    public String title;
    public String message;
    public int type;

    public NoticeDialogFragment(String title, String message, int type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public interface NoticeDialogListener  {
        public void onDialogPositiveClick(DialogFragment dialog, int type);
        public void onDialogNegativeClick(DialogFragment dialog, int type);
    }

    NoticeDialogListener dialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onDialogNegativeClick(NoticeDialogFragment.this, type);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogListener.onDialogPositiveClick(NoticeDialogFragment.this, type);
                    }
                });
        return builder.create();
    }

}
