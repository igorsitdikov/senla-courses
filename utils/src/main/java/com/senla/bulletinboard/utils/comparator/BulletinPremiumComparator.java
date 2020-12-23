package com.senla.bulletinboard.utils.comparator;

import com.senla.bulletinboard.entity.BulletinEntity;

import java.util.Comparator;

public class BulletinPremiumComparator implements Comparator<BulletinEntity> {

    @Override
    public int compare(BulletinEntity bulletinFirst, BulletinEntity bulletinSecond) {
        return bulletinFirst.getSeller().getPremium().compareTo(bulletinSecond.getSeller().getPremium());
    }
}
