package com.example.controller;

//和前端交互，属于业务层，在controller包下，前后端通信的协议
// data中存放数据，
// code表明属于增删改查哪种，
// 并且结尾为0为查询失败，结尾为1查询成功
//msg存放返回给用户的信息

public class Result {
    private Object data;
    private Integer code;
    private String msg;

//    造构造方法

    public Result() {
    }

    public Result(Object data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public Result(Object data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

//    tostring重写


    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
