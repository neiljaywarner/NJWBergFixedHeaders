package com.spiritflightapps.berg;

import android.database.Cursor;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by neil on 5/6/14.
 */
public class Assessment {
    String date;  //1e,2e,3e.... with eventual longpress for more info, ?
    ArrayList<String> answers;
    ArrayList<EditText> mEditTextFields; // TOOD: Put in adapter?

    //TODO: Separate UI code from model object.
    public Assessment(Cursor cursor) {
        // get values.
    }

    public Assessment() {
        //TODO: Should we have createdate, last modifydate?
    }



    /**
     * NOTE: answers are strings but all considered integers b/c input type is integer.
     * @return
     */
    private int getTotal() {
        int total = 0; int numValidAnswers = 0;
        EditText editText;
        String ans;
        for (int i=0; i < answers.size(); i++) {
            editText = this.mEditTextFields.get(i);
            ans = editText.getText().toString().trim();
            if (TextUtils.isDigitsOnly(ans)) {
                total += Integer.valueOf(answers.get(i));
            }
        }
        return total;
    }

    /**
     * get total of the editboxes, only show if all are filled.
     * @return
     */
    public String getTotalString() {
        if (isValidAndCompleteAnswers()) {
            return String.valueOf(getTotal());
        } else {
            return "";
        }
    }

    private boolean isValidAndCompleteAnswers() {
        String ans = "";
        if (mEditTextFields.size() < answers.size()) {
            return false; //haven't scrolled down to see all the fields yet.
        }
        for (EditText editText : mEditTextFields) {
            ans = editText.getText().toString().trim();
            if (!TextUtils.isDigitsOnly(ans)) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Assessment> getDummyAssessments() {

        ArrayList<Assessment> tests= new ArrayList<Assessment>();
        tests.add(getDummyAssessment());
        tests.add(getDummyAssessment());
        tests.add(getDummyAssessment());
        tests.add(getDummyAssessment());

        return tests;
    }

    private static Assessment getDummyAssessment() {
        ArrayList<String> answers = new ArrayList<String>();
        for (int i=0; i < 14; i++) {
            answers.add(getRandomAnswer());
        }
        Assessment dummy = new Assessment();
        dummy.setAnswers(answers);
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

    private void setAnswers(ArrayList<String> answers) {
        this.answers=answers;
    }

    public Assessment(ArrayList<EditText> answers) {
        this.mEditTextFields = answers;
    }

    /*
     * as the field shows up, add it.
     */
    public void addEditText(EditText editText) {
        if (mEditTextFields==null) {
            mEditTextFields = new ArrayList<EditText>();
        }
        mEditTextFields.add(editText);
    }
}
