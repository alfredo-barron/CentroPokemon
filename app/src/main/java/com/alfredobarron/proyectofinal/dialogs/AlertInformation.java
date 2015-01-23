package com.alfredobarron.proyectofinal.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alfredobarron.proyectofinal.R;


public class AlertInformation extends DialogFragment {

    public interface Callback {

        void onPositiveButtonClicked();

        void onCancel();

    }

    private static final String TITLE = "argument.title";

    private static final String MESSAGE = "argument.message";

    private static final String POSITIVE_TEXT = "argument.positive.text";

    private static final String NEGATIVE_TEXT = "argument.negative.text";

    private Callback callback;

    public static AlertInformation newInstance(
            final String title,
            final String message,
            final String positiveText,
            final String negativeText) {

        final Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(MESSAGE, message);
        args.putString(POSITIVE_TEXT, positiveText);
        args.putString(NEGATIVE_TEXT, negativeText);

        final AlertInformation alertInformation = new AlertInformation();
        alertInformation.setArguments(args);

        return alertInformation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        String title = null;
        String message = null;
        String positiveText = null;
        String negativeText = null;

        if (getArguments() != null) {
            if (getArguments().containsKey(TITLE)) {
                title = getArguments().getString(TITLE);
            }

            if (getArguments().containsKey(MESSAGE)) {
                message = getArguments().getString(MESSAGE);
            }

            if (getArguments().containsKey(POSITIVE_TEXT)) {
                positiveText = getArguments().getString(POSITIVE_TEXT);
            }
            if (getArguments().containsKey(NEGATIVE_TEXT)) {
                negativeText = getArguments().getString(NEGATIVE_TEXT);
            }

        }

        final DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onPositiveButtonClicked();
                }
            }
        };

        final DialogInterface.OnClickListener negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onCancel();
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setIcon(R.drawable.ic_launcher)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveButtonListener)
                .setNegativeButton(negativeText, negativeButtonListener)
                .create();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
