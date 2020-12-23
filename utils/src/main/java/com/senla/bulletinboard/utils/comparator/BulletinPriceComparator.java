package com.senla.bulletinboard.utils.comparator;

import com.senla.bulletinboard.entity.BulletinEntity;

import java.util.Comparator;

public class BulletinPriceComparator implements Comparator<BulletinEntity> {

    @Override
    public int compare(BulletinEntity bulletinFirst, BulletinEntity bulletinSecond) {
        final BulletinRatingComparator bulletinRatingComparator = new BulletinRatingComparator();
        int compare = bulletinRatingComparator.compare(bulletinFirst, bulletinSecond);
        if (compare != 0) {
            return compare;
        }
        return bulletinSecond.getPrice().compareTo(bulletinFirst.getPrice());
    }
}
