package com.fee.xitemdecoration;

import android.content.Context;
import android.util.TypedValue;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2018/9/12<br>
 * Time: 18:06<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
public class PxUtil {

    public static int dp2Px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

}
