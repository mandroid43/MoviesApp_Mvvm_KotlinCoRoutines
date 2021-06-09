package com.manapps.mandroid.moviesapp_mvc_ret.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorClass {
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }



}
