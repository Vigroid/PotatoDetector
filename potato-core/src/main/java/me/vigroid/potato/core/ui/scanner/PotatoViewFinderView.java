package me.vigroid.potato.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;
import me.vigroid.potato.core.R;

/**
 * Created by vigroid on 10/17/17.
 * QR scan related class
 */

public class PotatoViewFinderView extends ViewFinderView{
    public PotatoViewFinderView(Context context) {
        this(context, null);
    }

    public PotatoViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        //square view
        mSquareViewFinder = true;
        mBorderPaint.setColor(ContextCompat.getColor(getContext(), R.color.ok_green));
        mLaserPaint.setColor(Color.YELLOW);
    }
}
