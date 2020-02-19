package com.tech.cloudnausor.ohmytennis.utils;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import java.util.List;

public class MyAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private int myThreshold ;

    public MyAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            threshold = 0;
        }
        myThreshold = threshold;
    }

    @Override
    public boolean enoughToFilter() {
        return getText().length() >= myThreshold;
    }

    @Override
    public int getThreshold() {
        return myThreshold;
    }

}