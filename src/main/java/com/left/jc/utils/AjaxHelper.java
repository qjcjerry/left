package com.left.jc.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HzQ on 15/12/21.
 */
public class AjaxHelper {

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";

    public enum Status {
        Success(1),
        Error(0),
        Command(2),
        Unknown(-1);

        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }

        public static Status getStatus(int value) {
            Status[] values = Status.values();
            for (Status status : values) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            return Unknown;
        }
    }

    public enum Command {
        Redirect("redirect"),
        Unknown("");

        private final String value;

        public String getValue() {
            return value;
        }

        Command(String value) {
            this.value = value;
        }

        public static Command getCommand(String value) {
            Command[] values = Command.values();
            for (Command command : values) {
                if (command.getValue() == value) {
                    return command;
                }
            }
            return Unknown;
        }
    }

    private Map<String, Object> objectMap;

    public AjaxHelper() {
        this.objectMap = new HashMap<>(3);
    }

    public AjaxHelper(Status status, Object data, String message) {
        this();
        this.setStatus(status);
        this.setMessage(message);
        this.setData(data);
    }

    public void setStatus(Status status) {
        this.objectMap.put(STATUS, status.getValue());
    }

    public Status getStatus() {
        return Status.getStatus((int) this.objectMap.get(STATUS));
    }


    public void setMessage(String message) {
        this.objectMap.put(MESSAGE, message);
    }

    public String getMessage() {
        return (String) this.objectMap.get(MESSAGE);
    }

    public void setData(Object data) {
        this.objectMap.put(DATA, data);
    }

    public Object getData() {
        return this.objectMap.get(DATA);
    }

    public Map getJson() {
        return this.objectMap;
    }

    public static Map sendData(Object data) {
        AjaxHelper ajaxHelper = new AjaxHelper(Status.Success, data, null);
        return ajaxHelper.getJson();
    }

    public static Map sendMessage(String message) {
        AjaxHelper ajaxHelper = new AjaxHelper(Status.Error, null, message);
        return ajaxHelper.getJson();
    }

    @Deprecated
    public static Map sendCommand(Command command, Object data) {
        AjaxHelper ajaxHelper = new AjaxHelper(Status.Command, data, command.getValue());
        return ajaxHelper.getJson();
    }
}
