package me.vigroid.potato.core.net.callback;

/**
 * Created by vigroid on 10/2/17.
 * what to do when callback is error
 */

public interface IError {

    void onError(int code, String msg);
}
