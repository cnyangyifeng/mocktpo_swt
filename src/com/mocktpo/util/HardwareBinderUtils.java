package com.mocktpo.util;

import com.verhas.licensor.HardwareBinder;

public class HardwareBinderUtils {

    private HardwareBinderUtils() {
    }

    public static String uuid() {
        HardwareBinder binder = new HardwareBinder();
        binder.ignoreHostName();
        binder.ignoreNetwork();
        try {
            return binder.getMachineIdString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
