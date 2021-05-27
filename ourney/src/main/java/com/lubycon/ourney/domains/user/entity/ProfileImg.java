package com.lubycon.ourney.domains.user.entity;

import java.util.Random;

public enum ProfileImg {
    hamster,
    dog,
    rabbit,
    unicorn,
    fox,
    bear,
    panda,
    tiger;

    public static ProfileImg getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
