package com.radicalninja.pwntdns;

import com.sun.istack.internal.Nullable;

public class StringUtils {

    public static boolean isEmpty(@Nullable final String string) {
        return (null == string || string.isEmpty());
    }

}
