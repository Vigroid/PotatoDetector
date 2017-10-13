package me.vigroid.potato.core.net.callback;

/**
 * Created by vigroid on 10/2/17.
 * what to do when callback is request
 */

public interface IRequest {

    void onRequestStart();
    void onRequestEnd();
}
