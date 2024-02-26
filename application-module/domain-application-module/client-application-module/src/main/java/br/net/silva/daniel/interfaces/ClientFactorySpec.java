package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.value_object.output.AddressOutput;

public interface ClientFactorySpec {

    interface CpfSpec<R> {
        NameSpec<R> withCpf(String cpf);
    }

    interface NameSpec<R> {
        ActiveSpec<R> withName(String name);
    }

    interface ActiveSpec<R> {
        AddressSpec<R> withActive(boolean active);
        BuildSpec<R> andWithActive(boolean active);
    }

    interface AddressSpec<R> {
        TelephoneSpec<R> withAddress(AddressOutput address);
    }

    interface TelephoneSpec<R> {
        IdSpec<R> withTelephone(String telephone);
        BuildSpec<R> andWithTelephone(String telephone);
    }

    interface IdSpec<R> {
        BuildSpec<R> andWithId(String id);
    }

    interface BuildSpec<R> extends CpfSpec<R>, NameSpec<R>, ActiveSpec<R>, TelephoneSpec<R>, IdSpec<R>, AddressSpec<R> {
        R build();
    }
}
