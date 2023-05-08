package org.example.system.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class ResultData implements Serializable {
    private static final long serialVersionUID = 5247070551076011673L;

    private Integer code;
    private String message;
    private Object data;

    public static ResultData optSuccess() {
        return new ResultData().setCode(200).setMessage("ok");
    }
    public static ResultData optSuccess(Object data) {
        return new ResultData().setCode(200).setMessage("ok").setData(data);
    }

    public static ResultData optFail(String message) {
        return new ResultData().setCode(0).setMessage(message);
    }

    public static ResultData optFail(Integer code, String message) {
        return new ResultData().setCode(code).setMessage(message);
    }
}
