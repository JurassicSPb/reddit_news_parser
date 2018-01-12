package epam.com.reddit_news_parser.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import epam.com.reddit_news_parser.R;

/**
 * Created by yuri on 1/11/18.
 */

public class ChooseDialog extends DialogFragment {
    private DialogListener dialogListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            dialogListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_choose)
               .setPositiveButton(R.string.dialog_open_news, (dialog, id) -> dialogListener.onDialogPositiveClick(ChooseDialog.this))
               .setNegativeButton(R.string.dialog_share_news, (dialog, id) -> dialogListener.onDialogNegativeClick(ChooseDialog.this));

        return builder.create();
    }

    public interface DialogListener {

        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }
}
