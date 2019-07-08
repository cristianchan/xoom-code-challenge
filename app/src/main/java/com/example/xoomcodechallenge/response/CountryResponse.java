package com.example.xoomcodechallenge.response;

import java.util.List;

public class CountryResponse {

    public List<DisbursementOption> getDisbursementOptions() {
        return disbursementOptions;
    }

    public void setDisbursementOptions(List<DisbursementOption> disbursementOptions) {
        this.disbursementOptions = disbursementOptions;
    }

    private String code;
    private String name;
    private List<DisbursementOption> disbursementOptions;

    public CountryResponse(String code, String name, List<DisbursementOption> disbursementOptions) {
        this.code = code;
        this.name = name;
        this.disbursementOptions = disbursementOptions;
    }

    private static class DisbursementOption{
        private Boolean mode;

        private DisbursementOption(Boolean mode) {
            this.mode = mode;
        }

        public Boolean getMode() {
            return mode;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
