package br.net.silva.daniel.shared.application.gateway;

import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.List;
import java.util.Optional;

public interface FindApplicationBaseGateway<R> {
    Optional<R> findById(ParamGateway param);
    List<R> findAllBy(ParamGateway param);
}
