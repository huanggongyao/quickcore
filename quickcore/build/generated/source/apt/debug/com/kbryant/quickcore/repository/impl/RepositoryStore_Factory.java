// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.kbryant.quickcore.repository.impl;

import dagger.internal.Factory;
import javax.inject.Provider;
import retrofit2.Retrofit;

public final class RepositoryStore_Factory implements Factory<RepositoryStore> {
  private final Provider<Retrofit> retrofitProvider;

  public RepositoryStore_Factory(Provider<Retrofit> retrofitProvider) {
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RepositoryStore get() {
    return new RepositoryStore(retrofitProvider.get());
  }

  public static Factory<RepositoryStore> create(Provider<Retrofit> retrofitProvider) {
    return new RepositoryStore_Factory(retrofitProvider);
  }
}
