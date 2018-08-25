package com.worldsills.tspmobile;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;

public class TextViewMelo extends TextView {
    public TextViewMelo(Context context) {
        super(context);
        addFont(context);
    }

    public TextViewMelo(Context context, AttributeSet attrs) {
        super(context, attrs);
        addFont(context);

    }

    public TextViewMelo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addFont(context);
    }

    public void addFont(Context context){
        Typeface typeface=getFont(context,"Signatra.ttf");
    }


    private HashMap<String, Typeface> fontCache=new HashMap<>();
    public Typeface getFont(Context context, String nomFont){
        Typeface typeface1=fontCache.get(nomFont);

        if (typeface1==null){
            typeface1=Typeface.createFromAsset(context.getAssets(),nomFont);

            fontCache.put(nomFont,typeface1);
        }
        return typeface1;
    }
}
