package browser.android.com.calculator.stores;

import com.squareup.otto.Subscribe;
import browser.android.com.calculator.actions.Action;
import browser.android.com.calculator.dispatcher.Dispatcher;

public class CalStore extends Store {

    public CalStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    @Subscribe
    public void onCalFinished(Action action) {}

    @Override
    public StoreChangeEvent changeEvent() {
        return new CalStoreChangeEvent();
    }

    public class CalStoreChangeEvent implements StoreChangeEvent {};
}
