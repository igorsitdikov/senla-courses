package com.senla.bulletinboard.utils.comparator;

import com.senla.bulletinboard.entity.BulletinEntity;

import java.util.Comparator;

public class BulletinRatingAndDateTimeComparator implements Comparator<BulletinEntity> {

    @Override
    public int compare(BulletinEntity bulletinFirst, BulletinEntity bulletinSecond) {
        final BulletinRatingComparator bulletinRatingComparator = new BulletinRatingComparator();
        int compare = bulletinRatingComparator.compare(bulletinFirst, bulletinSecond);
        if (compare != 0) {
            return compare;
        }
        return bulletinSecond.getCreatedAt().compareTo(bulletinFirst.getCreatedAt());
    }
}
