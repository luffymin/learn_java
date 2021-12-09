package com.example.udp.proto.common.util;

import org.apache.commons.lang3.RandomUtils;
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceNumberUtil {
    public static int getSequenceNo() {
        return sequenceId.incrementAndGet();
    }

    private final static AtomicInteger sequenceId = new AtomicInteger(RandomUtils.nextInt());
}