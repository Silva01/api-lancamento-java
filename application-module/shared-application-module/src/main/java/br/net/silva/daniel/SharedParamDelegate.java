package br.net.silva.daniel;

import br.net.silva.daniel.interfaces.IProcessResponse;

public class SharedParamDelegate implements IProcessResponse {

    private Object param;
    private Object response;

    public void addParam(Object param) {
        this.param = param;
    }

    public void addResponse(Object response) {
        this.response = response;
    }

    public Object getParam() {
        return param;
    }

    @Override
    public Object processResponse() {
        return response;
    }
}
