package me.vigroid.potato.core.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * Created by vigroid on 10/9/17.
 * RGB class for our project
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue){
        return new AutoValue_RgbValue(red, green, blue);
    }
}
