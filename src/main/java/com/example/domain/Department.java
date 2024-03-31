package com.example.domain;

public enum Department {
    Neurology(0, "神经科"),
    Dentistry(1, "牙科"),
    Cardiology(2, "心脏病学"),
    Pediatrics(3, "小儿科"),
    Pulmonology(4, "肺病学"),
    Ophthalmology(5, "眼科学"),
    Diagnostics(6, "诊断"),
    Traumatology(7, "创伤学");

    private int code;
    private String name;

    private Department(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code) {
        for (Department gss : Department.values()) {
            if (gss.getCode() == code) {
                return gss.getName();
            }
        }
        return "未知";
    }

    public static int getCodeByName(String name) {
        for (Department gss : Department.values()) {
            if (gss.getName().equals(name)) {
                return gss.getCode();
            }
        }
        return -1;
    }


    public static Department getEnumByCode(int code) {
        for (Department gss : Department.values()) {
            if (gss.getCode() == code) {
                return gss;
            }
        }
        return null;
    }

    /**
     * valueOf.
     *
     * @param ordinal int
     * @return OperatorType
     */
    public static Department valueOf(int ordinal) {
        Department[] values = Department.values();
        if (ordinal >= values.length) {
            return null;
        } else {
            return values[ordinal];
        }
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
