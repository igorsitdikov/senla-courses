package com.senla.bulletinboard.utils.comparator;

import com.senla.bulletinboard.entity.BulletinEntity;
import com.senla.bulletinboard.enumerated.PremiumStatus;

import java.util.Comparator;

public class BulletinRatingComparator implements Comparator<BulletinEntity> {

    @Override
    public int compare(BulletinEntity bulletinFirst, BulletinEntity bulletinSecond) {
        final BulletinPremiumComparator bulletinPremiumComparator = new BulletinPremiumComparator();
        final int compare = bulletinPremiumComparator.compare(bulletinFirst, bulletinSecond);
        if (compare != 0) {
            return compare;
        }
        return bulletinSecond.getSeller().getRating().compareTo(bulletinFirst.getSeller().getRating());
    }
}
