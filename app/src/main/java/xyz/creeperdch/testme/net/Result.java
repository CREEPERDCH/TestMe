package xyz.creeperdch.testme.net;

/**
 * Created by creeper on 2018/9/11
 * Hint:
 * Email: wwwwyn4240@gmail.com
 */
public class Result<E> {

    private boolean error;
    private E results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public E getResults() {
        return results;
    }

    public void setResults(E results) {
        this.results = results;
    }
}