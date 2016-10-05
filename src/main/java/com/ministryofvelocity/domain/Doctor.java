package com.ministryofvelocity.domain;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by diyahm-pl on 10/12/16.
 */
public class Doctor {
    private String providerName;
    private Object[] address;
    private Object[] spokenLanguages;
    private String webPhoto;


    public Doctor(){

    }

    public String getLanguages() {
        String languages = "";
        JSONArray langArray = new JSONArray(spokenLanguages);
        for (int i =0; i < langArray.length();i++){
            languages = languages + langArray.getJSONObject(i).get("language").toString() + " ";
        }
        return languages;
    }

    public String getAddress() {
        String addressStr = "";
        JSONArray addrArray = new JSONArray(address);
        for (int i =0; i < addrArray.length();i++){
            addressStr = addressStr + addrArray.getJSONObject(i).get("addr").toString() + "\n ";
        }
        return addressStr;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getWebPhoto(){
        return webPhoto;
    }


}
