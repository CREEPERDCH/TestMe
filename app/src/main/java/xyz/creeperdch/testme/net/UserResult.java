package xyz.creeperdch.testme.net;

/**
 * Created by creeper on 2018/9/19
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
public class UserResult<T> {

    private int ret;
    private T data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
