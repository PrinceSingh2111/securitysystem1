/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.securitysystem.dao;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author BVR
 */
public class IPAddressResolution {

    @SerializedName("last_resolved")
    private String lastResolved;
    @SerializedName("hostname")
    private String hostName;

    /**
     *
     * @return
     */
    public String getLastResolved() {
        return lastResolved;
    }

    /**
     *
     * @return
     */
    public String getHostName() {
        return hostName;
    }

}
