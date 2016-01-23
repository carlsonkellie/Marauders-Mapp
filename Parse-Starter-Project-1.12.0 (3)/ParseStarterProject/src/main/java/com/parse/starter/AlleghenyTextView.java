package com.parse.starter;
import android.widget.TextView;
import android.graphics.Typeface;
import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by kelliecarlson on 1/23/16.
 */
public class AlleghenyTextView extends TextView {

    public AlleghenyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Allegheny.ttf"));
    }
}
