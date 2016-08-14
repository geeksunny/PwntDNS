package com.radicalninja.pwntdns;

import javax.annotation.Nullable;

public class StringUtils {

    public static boolean isEmpty(@Nullable final String string) {
        return (null == string || string.isEmpty());
    }

}
