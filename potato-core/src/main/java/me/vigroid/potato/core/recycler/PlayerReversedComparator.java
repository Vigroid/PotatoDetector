package me.vigroid.potato.core.recycler;

import java.util.Comparator;

/**
 * Created by yangv on 10/21/2017.
 */

public class PlayerReversedComparator implements Comparator<PlayerBean>{
    @Override
    public int compare(PlayerBean a, PlayerBean b) {
        return a.ratingNum<b.ratingNum ? -1:a.ratingNum==b.ratingNum ? 0:1;
    }
}
