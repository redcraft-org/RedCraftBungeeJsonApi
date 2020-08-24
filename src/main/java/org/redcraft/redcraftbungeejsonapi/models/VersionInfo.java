package org.redcraft.redcraftbungeejsonapi.models;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VersionInfo {
    public String serverSoftware;
    public String mainVersion;
    public ArrayList<String> supportedVersions;

    public VersionInfo(String serverSoftware, String mainVersion, ArrayList<String> supportedVersions) {
        this.serverSoftware = serverSoftware;
        this.mainVersion = mainVersion;
        this.supportedVersions = supportedVersions;
    }

    public String getVersionsJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}