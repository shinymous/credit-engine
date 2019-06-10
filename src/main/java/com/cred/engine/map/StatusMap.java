package com.cred.engine.map;

public enum StatusMap {

    APPROVED("Approved"),
    DISAPPROVED("Disapproved");

    private String param;

    StatusMap(String param){this.param = param;}

    public String param() {
        return param;
    }

    public static String get(String key){
        for(StatusMap n : StatusMap.values()){
            if(key.equals(n.name()))
                return n.param();
        }
        return null;
    }
}
