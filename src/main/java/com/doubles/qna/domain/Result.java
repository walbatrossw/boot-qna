package com.doubles.qna.domain;

public class Result {
    // 권한 유효성 판단
    private boolean valid;
    // 에러메시지
    private String errorMsg;

    private Result(boolean valid, String errorMsg) {
        this.valid = valid;
        this.errorMsg = errorMsg;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String errorMsg) {
        return new Result(false, errorMsg);
    }
}
