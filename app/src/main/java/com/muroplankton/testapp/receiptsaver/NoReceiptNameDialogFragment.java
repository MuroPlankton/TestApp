package com.muroplankton.testapp.receiptsaver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.muroplankton.testapp.R;

public class NoReceiptNameDialogFragment extends DialogFragment {
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoReceiptNameDialogFragment.NoticeDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.receipt_name_missing_title);
        builder.setMessage(R.string.receipt_name_missing_message);
        builder.setPositiveButton(R.string.receipt_name_missing_positive, (dialog, which) ->
                listener.onDialogPositiveClick(NoReceiptNameDialogFragment.this));
        builder.setNegativeButton(R.string.receipt_name_missing_negative, (dialog, which) ->
                listener.onDialogNegativeClick(NoReceiptNameDialogFragment.this));
        return builder.create();
    }
}
