package com.mocktpo.orm.domain;

public class LicenseCode {

    private String content;

    public LicenseCode(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
