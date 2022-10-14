package org.example.exception;

public class ReadExcelException extends RuntimeException {
    private String msg;
    public ReadExcelException(String msg){
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
