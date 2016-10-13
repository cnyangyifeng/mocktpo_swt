package com.mocktpo.config;

import com.alibaba.fastjson.annotation.JSONField;

public class Settings {

    @JSONField(name = "database_available")
    private boolean databaseAvailable;

    @JSONField(name = "license_available")
    private boolean licenseAvailable;

    @JSONField(name = "network_available")
    private boolean networkAvailable;

    public boolean isDatabaseAvailable() {
        return databaseAvailable;
    }

    public void setDatabaseAvailable(boolean databaseAvailable) {
        this.databaseAvailable = databaseAvailable;
    }

    public boolean isLicenseAvailable() {
        return licenseAvailable;
    }

    public void setLicenseAvailable(boolean licenseAvailable) {
        this.licenseAvailable = licenseAvailable;
    }

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }

    @Override
    public String toString() {
        return "{" + "databaseAvailable:" + this.databaseAvailable + ",licenseAvailable:" + this.licenseAvailable + ",networkAvailable:" + this.networkAvailable + "}";
    }
}
