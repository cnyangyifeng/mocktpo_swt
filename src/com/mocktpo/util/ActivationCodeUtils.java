package com.mocktpo.util;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.mocktpo.util.constants.ResourceConstants;
import com.mocktpo.vo.RequireActivationVo;
import com.verhas.licensor.License;

public class ActivationCodeUtils {

    protected static final Logger logger = LogManager.getLogger();

    public static final int NETWORK_FAILURE = 0;
    public static final int EMAIL_HARDWARE_OK = 1;
    public static final int REGISTERED_EMAIL_NOT_FOUND = 2;
    public static final int REGISTERED_HARDWARE_UNMATCHED = 3;

    private static final String REMOTE_SERVICE = "http://cnmengma.com:8080/cmp/api/licenses/require";

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

    public static boolean isLicensed(String acc) {
        String path = ActivationCodeUtils.class.getResource(ResourceConstants.CONFIG_DIR).getPath();
        try {
            final License license;
            final String pubring = URLDecoder.decode(path + ResourceConstants.PUBLIC_KEY_FILE, "utf-8");
            if ((license = new License()).loadKeyRing(pubring, null).setLicenseEncoded(acc).isVerified()) {
                String actual = license.getFeature("hardware");
                logger.info("actual hardware: " + actual);
                String expected = HardwareBinderUtils.uuid();
                logger.info("expected hardware: " + expected);
                if (null != actual && actual.equals(expected)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
