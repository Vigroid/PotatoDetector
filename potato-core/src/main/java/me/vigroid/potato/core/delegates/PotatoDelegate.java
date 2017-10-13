package me.vigroid.potato.core.delegates;

/**
 * Created by vigroid on 10/12/17.
 * A skeleton delegate class
 */

public abstract class PotatoDelegate extends PermissionCheckerDelegate{
    public <T extends PotatoDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
