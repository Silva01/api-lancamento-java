package br.net.silva.daniel;

public class SharedParamDelegate<P, R> {

    private P param;
    private R response;

    public void addParam(P param) {
        this.param = param;
    }

    public void addResponse(R response) {
        this.response = response;
    }

    public P getParam() {
        return param;
    }

    public R getResponse() {
        return response;
    }
}
