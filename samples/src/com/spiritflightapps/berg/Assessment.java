package com.spiritflightapps.berg;

import android.database.Cursor;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by neil on 5/6/14.
 */
public class Assessment {
    String date;  //1e,2e,3e.... with eventual longpress for more info, ?
    HashMap<Integer,String> hashMapAnswersStrings;
    HashMap<Integer, EditText> mEditTextFields; // TOOD: Put in adapter?
    private TextView mTextViewTotal;

    //TODO: Separate UI code from model object.
    public Assessment(Cursor cursor) {
        // get values.
    }

    public Assessment() {
        //TODO: Should we have createdate, last modifydate?
    }




    /**
     * get total of the editboxes, only show if all are filled.
     * @return
     */
    public String getTotalString() {
        String ans="";
        int total = 0;
        for (EditText editText : mEditTextFields.values()) {
            ans = editText.toString().trim();
            if (TextUtils.isDigitsOnly(ans)) {
                total+= Integer.valueOf(ans);
            } else {
                return "";
            }

        }
        if (mEditTextFields.size() < 14) {
            return "";
        }
        return String.valueOf(total); //if it made it through.
    }



    public static ArrayList<Assessment> getDummyAssessments() {

        ArrayList<Assessment> tests= new ArrayList<Assessment>();
        tests.add(getDummyAssessment());
       // tests.add(getDummyAssessment());
      //  tests.add(getDummyAssessment());
      //  tests.add(getDummyAssessment());

        return tests;
    }

    private static Assessment getDummyAssessment() {
        HashMap<Integer,String> answers = new HashMap<Integer, String>();
        for (int i=0; i < 14; i++) {
            answers.put(i, getRandomAnswer());
        }
        Assessment dummy = new Assessment();
        dummy.setHashMapAnswersStrings(answers);
        return dummy;
    }

    /**
     *
     * @return random string from "0" to "4"
     */
    private static String getRandomAnswer() {
        int ans = (int )(Math.random() * 4);
        return String.valueOf(ans);
    }

    private void setHashMapAnswersStrings(HashMap<Integer, String> hashMapAnswersStrings) {
        this.hashMapAnswersStrings = hashMapAnswersStrings;
    }



    /*
     * as the field shows up, add it.
     */
    public void addEditText(final Integer row, final EditText editText) {
        if (mEditTextFields == null) {
            mEditTextFields = new HashMap<Integer, EditText>();
        }
        if (mEditTextFields.containsKey(row)) {
            return;  //don't add twice.
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newAns= editable.toString().trim();
                if (TextUtils.isEmpty(newAns)) {
                    return;
                }
                if (hashMapAnswersStrings.containsKey(row)) {
                    hashMapAnswersStrings.remove(row);
                    hashMapAnswersStrings.put(row,editable.toString().trim());

                }
                if (mTextViewTotal!=null) {
                    mTextViewTotal.setText(getTotalString());

                }
            }
        });
        mEditTextFields.put(row, editText);
    }

    protected TextWatcher answerTextWatcher = new TextWatcher() {
        String oldAnswer;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (TextUtils.isDigitsOnly(charSequence.toString())) {
                oldAnswer = charSequence.toString();
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.i("NJW","oldanswer="+ oldAnswer);
            Log.i("NJW", "new="+editable.toString());
            String newAnswer = editable.toString().trim();
            if (TextUtils.isEmpty(newAnswer)) {
                return;
            }
            if (mTextViewTotal!=null) {
                mTextViewTotal.setText(getTotalString());

            }

        }
    };

    /*
     * if you've already seen the total field then it can be updated.
     */
    private void updateTotal() {
        String total = getTotalString();
        Log.i("NJW", "total=" + total);
        if (mTextViewTotal != null) {

           // mTextViewTotal.setText(getTotalString());
            mTextViewTotal.setText("??");

        } else {
            Log.i("NJW", "no textview to update");
        }
    }

    public void setTotalTextView(TextView textView) {
        if (mTextViewTotal == null) {
            this.mTextViewTotal = textView;
        }
    }

    public static Assessment getNewAssessment() {
        HashMap<Integer,String> answers = new HashMap<Integer, String>();
        for (int i=0; i < 14; i++) {
            answers.put(i, "");
        }
        Assessment dummy = new Assessment();
        dummy.setHashMapAnswersStrings(answers);
        return dummy;    }
}
