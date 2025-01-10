package org.sangahn.sangahncloudboot.user.exception;

import lombok.Data;

@Data
public class LoginTaskException extends RuntimeException {

    private int status;
    private String msg;

    public LoginTaskException(final int status, final String msg) {
        super(status +"_" + msg);
        this.status = status;
        this.msg = msg;
    }

}