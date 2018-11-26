package mk.techtree.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;

/**
 * Created by muhammadhumzakhan on 10/26/2017.
 */

public class SpannableHelper {

    public SpannableHelper() {

    }

    SpannableHelper spannableHelper;
    SpannableStringBuilder spannableStringBuilder;

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    String mText;

    public SpannableHelper setText(String text) {
        this.mText = text;
        this.spannableStringBuilder = new SpannableStringBuilder(text);
        return spannableHelper;
    }


    public SpannableHelper setClickable(String text, ClickableSpan clickableSpan) {

        if (spannableStringBuilder.toString().contains(text)) {
            spannableStringBuilder.setSpan(
                    clickableSpan,
                    mText.indexOf(text),
                    mText.indexOf(text) + String.valueOf(text).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        return spannableHelper;
    }

    public void setColorSpan(int bgSpanColor, String text) {
        spannableStringBuilder.setSpan(
                bgSpanColor,
                spannableStringBuilder.length() - text.length(),
                spannableStringBuilder.length() - 1, // Change Some Logic Here
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }


    /***** Set Text Style******/
    /***** Possible Params *****/
    /*StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    StyleSpan boldItalicSpan = new StyleSpan(Typeface.BOLD_ITALIC);
    StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
    UnderlineSpan underlineSpan = new UnderlineSpan();*/
    public void setTextStyle(String text, Object spanType) {
        spannableStringBuilder.setSpan(
                spanType,
                mText.indexOf(text),
                mText.indexOf(text) + String.valueOf(text).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

    }

    public void setImageSpan(Context mContext, int drawable, String text) {
        ImageSpan searchImageSpan = new ImageSpan(mContext, drawable);
        spannableStringBuilder.setSpan(
                searchImageSpan,
                0, 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

    }


}
