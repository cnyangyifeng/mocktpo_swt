package com.mocktpo.orm.domain;

import java.util.Date;

public class ActivationCode {

    private String email;
    private String content;
    private Date dateCreated;
    private Date dateUpdated;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public String toString() {
        return "{\nemail:" + this.getEmail() + ",\ncontent:\n" + this.getContent() + ",\ndateCreated:" + this.getDateCreated() + ",\ndateUpdated:" + this.getDateUpdated() + "\n}";
    }
}
