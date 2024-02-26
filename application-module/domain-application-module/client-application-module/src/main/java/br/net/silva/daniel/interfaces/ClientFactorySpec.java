package br.net.silva.daniel.interfaces;

public interface ClientFactorySpec {

    interface CpfSpec<R> {
        NameSpec<R> withCpf(String cpf);
    }

    interface NameSpec<R> {
        ActiveSpec<R> withName(String name);
    }

    interface ActiveSpec<R> {
        TelephoneSpec<R> withActive(boolean active);
        BuildSpec<R> andWithActive(boolean active);
    }

    interface TelephoneSpec<R> {
        IdSpec<R> withTelephone(String telephone);
        BuildSpec<R> andWithTelephone(String telephone);
    }

    interface IdSpec<R> {
        BuildSpec<R> andWithId(String id);
    }

    interface BuildSpec<R> extends CpfSpec<R>, NameSpec<R>, ActiveSpec<R>, TelephoneSpec<R>, IdSpec<R> {
        R build();
    }
}
