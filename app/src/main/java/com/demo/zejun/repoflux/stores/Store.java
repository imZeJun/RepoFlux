package com.demo.zejun.repoflux.stores;

import com.demo.zejun.repoflux.actions.Action;
import com.demo.zejun.repoflux.dispatcher.Dispatcher;

public abstract class Store {

    final Dispatcher dispatcher;

    protected Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    void emitStoreChange() {
        dispatcher.emitChange(changeEvent());
    }

    abstract StoreChangeEvent changeEvent();

    public abstract void onAction(Action action);

    public interface StoreChangeEvent {}
}
