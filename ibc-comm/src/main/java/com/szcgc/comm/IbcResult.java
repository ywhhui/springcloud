package com.szcgc.comm;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author liaohong
 * @create 2020/8/21 15:17
 */
@Data
@NoArgsConstructor
public class IbcResult<T> {

    private static final IbcResult<?> _OK = new IbcResult(null, true);

    public static <T> IbcResult<T> OK() {
        @SuppressWarnings("unchecked")
        IbcResult<T> t = (IbcResult<T>) _OK;
        return t;
    }

    private T t;
    private boolean ok;

    public T getValue() {
        return t;
    }

    public void setValue(T t) {
        this.t = t;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    private IbcResult(T t, boolean ok) {
        this.t = t;
        this.ok = ok;
    }

    public static <T> IbcResult<T> ok(T t) {
        return new IbcResult<T>(t, true);
    }

    public static <T> IbcResult<T> error(T t) {
        return new IbcResult<T>(t, false);
    }
}

