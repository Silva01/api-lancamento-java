package br.net.silva.daniel.repository;

import java.util.List;

public interface BaseRepository<T> extends SaveRepository<T>, FindRepository<T>, DeleteRepository<T> {

}
