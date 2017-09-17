package com.mocktpo.orm.domain;

public class ActivationCode {

    private String email;
    private String content;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "{\nemail:" + this.getEmail() + ",\ncontent:\n" + this.getContent() + "\n}";
    }
}
