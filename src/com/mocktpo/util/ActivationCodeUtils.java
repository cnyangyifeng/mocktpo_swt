package com.mocktpo.util;

import com.alibaba.fastjson.JSON;
import com.mocktpo.util.constants.RC;
import com.mocktpo.vo.RequireActivationVo;
import com.verhas.licensor.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

public class ActivationCodeUtils {

    /* Constants */

    public static final int NETWORK_FAILURE = 0;
    public static final int EMAIL_HARDWARE_OK = 1;
    public static final int REGISTERED_EMAIL_NOT_FOUND = 2;
    public static final int REGISTERED_HARDWARE_UNMATCHED = 3;
    public static final String REMOTE_SERVICE = "http://cnmengma.com/cmp/api/licenses/require";

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    private ActivationCodeUtils() {
    }

    public static int post(RequireActivationVo vo) {
        try {
            URL url = new URL(REMOTE_SERVICE);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-Type", "application/json");
            c.setRequestProperty("charset", "utf-8");
            c.setDoOutput(true);
            c.connect();
            OutputStreamWriter w = new OutputStreamWriter(c.getOutputStream());
            w.write(JSON.toJSONString(vo));
            w.flush();
            w.close();
            int code = c.getResponseCode();
            String message = c.getResponseMessage();
            logger.info("Http Response Code: " + code + "; message: " + message);
            switch (code) {
                case 200:
                    return EMAIL_HARDWARE_OK;
                case 404:
                    return REGISTERED_EMAIL_NOT_FOUND;
                case 409:
                    return REGISTERED_HARDWARE_UNMATCHED;
                default:
                    return NETWORK_FAILURE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NETWORK_FAILURE;
    }

    public static boolean isLicensed(String email, String ac) {
        boolean licensed = false;
        String path = ActivationCodeUtils.class.getResource(RC.CONFIG_DIR).getPath();
        try {
            final License lic;
            final String pubring = URLDecoder.decode(path + RC.PUBRING_FILE, "utf-8");
            if ((lic = new License()).loadKeyRing(pubring, null).setLicenseEncoded(ac).isVerified()) {
                String licensedEmail = lic.getFeature("email");
                String licensedHardware = lic.getFeature("hardware");
                String hardware = HardwareBinderUtils.uuid();
                if (null != hardware && hardware.equals(licensedHardware) && email.equals(licensedEmail)) {
                    licensed = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return licensed;
    }
}
