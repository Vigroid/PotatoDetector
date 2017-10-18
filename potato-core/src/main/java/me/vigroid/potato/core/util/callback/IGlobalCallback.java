package me.vigroid.potato.core.util.callback;

import android.support.annotation.Nullable;

/**
 * From IMOOC project - FastEC
 * Created by 傅令杰
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
