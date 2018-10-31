package com.xk.bbs.bean.base;

public enum ResponseCode {

    success(200, "操作成功"),
    password_incorrect(1001, "用户名或密码错误"),
    disactivated_account(1002, "账户未激活"),
    forbidden_account(1003, "账户已禁用"),
    input_username(1004, "请输入账号"),
    input_password(1005, "请输入密码"),
    error(500, "服务器错误");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
