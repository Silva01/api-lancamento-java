package br.net.silva.daniel.shared.application.interfaces;

public interface IClientParam extends Input, ICpfParam, IAgencyParam {
    String id();
    String name();
    String telephone();
    boolean active();
    IAddressParam address();
}
