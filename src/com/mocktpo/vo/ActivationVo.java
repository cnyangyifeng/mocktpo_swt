package com.mocktpo.vo;

import java.io.Serializable;

public class ActivationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String activationCode;
    private String hardware;

    public ActivationVo(String activationCode, String hardware) {
        this.setActivationCode(activationCode);
        this.setHardware(hardware);
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }
}
