package net.siji.epubWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

public class EpubWebView extends WebView {

    Context context;
    private boolean bAllowScroll = true;

    public EpubWebView(Context context) {
        super(context);
        this.context = context;

    }
    public EpubWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getContentWidth()
    {
        return this.computeHorizontalScrollRange();
    }
    public int getTotalHeight()
    {
        return this.computeVerticalScrollRange();
    }
}