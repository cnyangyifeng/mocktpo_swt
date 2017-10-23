package com.mocktpo.util;

import com.mocktpo.util.constants.RC;
import com.verhas.licensor.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;

public class ActivationUtils {

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private ActivationUtils() {
    }

    public static boolean isLicensed(String codeText) {
        boolean licensed = false;
        String path = ActivationUtils.class.getResource(RC.CONFIG_DIR).getPath();
        try {
            final License lic;
            final String pubring = URLDecoder.decode(path + RC.PUBRING_FILE, "utf-8");
            if ((lic = new License()).loadKeyRing(pubring, null).setLicenseEncoded(codeText).isVerified()) {
                String licensedHardware = lic.getFeature("hardware");
                String hardware = HardwareBinderUtils.uuid();
                if (hardware != null && hardware.equals(licensedHardware)) {
                    licensed = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return licensed;
    }
}
