package com.example.xoomcodechallenge.response;

import java.util.List;

public class CountryResponse {
    private String code;
    private String name;
    private List<DisbursementOption> disbursementOptions;

    public CountryResponse(final String code, final String name, final List<DisbursementOption> disbursementOptions) {
        this.code = code;
        this.name = name;
        this.disbursementOptions = disbursementOptions;
    }

    public List<DisbursementOption> getDisbursementOptions() {
        return disbursementOptions;
    }

    public static class DisbursementOption {
        private String mode;

        private DisbursementOption(final String mode) {
            this.mode = mode;
        }

        public String getMode() {
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
