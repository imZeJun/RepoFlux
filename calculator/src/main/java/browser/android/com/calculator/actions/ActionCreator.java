package browser.android.com.calculator.actions;

import browser.android.com.calculator.dispatcher.Dispatcher;

public class ActionCreator {

    private static ActionCreator sInstance;
    private Dispatcher mDispatcher;

    public static ActionCreator getInstance(Dispatcher dispatcher) {
        if (sInstance == null) {
            sInstance = new ActionCreator(dispatcher);
        }
        return sInstance;
    }

    private ActionCreator(Dispatcher dispatcher) {
        mDispatcher = dispatcher;
    }

    public void add(int leftNum, int rightNum) {
        if (mDispatcher != null) {
            mDispatcher.dispatch(CalculatorActions.ADD, CalculatorActions.KEY_LEFT_NUM, leftNum, CalculatorActions.KEY_RIGHT_NUM, rightNum);
        }
    }

    public void minus(int leftNum, int rightNum) {
        if (mDispatcher != null) {
            mDispatcher.dispatch(CalculatorActions.MINUS, CalculatorActions.KEY_LEFT_NUM, leftNum, CalculatorActions.KEY_RIGHT_NUM, rightNum);
        }
    }
}
