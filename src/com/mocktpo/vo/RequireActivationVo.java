package com.mocktpo.vo;

import java.io.Serializable;

public class RequireActivationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String hardware;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }
}
