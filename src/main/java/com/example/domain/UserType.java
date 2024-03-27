package com.example.domain;

public enum UserType {
    common(0,"患者"),
    doctor(1,"医生"),
    admin(2,"管理员");

    private int code;
    private String name;

    private UserType(int code,String name){
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(int code){
        for(UserType gss : UserType.values()){
            if(gss.getCode()==code){
                return gss.getName();
            }
        }
        return "未知";
    }


    public static UserType getEnumByCode(int code) {
        for (UserType gss : UserType.values()) {
            if (gss.getCode()==code) {
                return gss;
            }
        }
        return null;
    }

    /**
     * valueOf.
     * @param ordinal int
     * @return OperatorType
     */
    public static UserType valueOf(int ordinal) {
        UserType[] values = UserType.values();
        if (ordinal >= values.length) {
            return null;
        }
        else {
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
