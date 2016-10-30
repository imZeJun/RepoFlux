package com.demo.zejun.repoflux.actions;


import com.demo.zejun.repoflux.dispatcher.Dispatcher;
import com.demo.zejun.repoflux.model.Todo;

public class ActionCreator {

    private static ActionCreator instance;
    final Dispatcher dispatcher;

    ActionCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static ActionCreator get(Dispatcher dispatcher) { //ActionCreator和Dispatcher类似，都是单例模式。
        if (instance == null) {
            instance = new ActionCreator(dispatcher);
        }
        return instance;
    }

    public void create(String text) {
        dispatcher.dispatch(TodoActions.TODO_CREATE, TodoActions.KET_TEXT, text);
    }

    public void destroy(long id) {
        dispatcher.dispatch(TodoActions.TODO_DESTROY, TodoActions.KEY_ID, id);
    }

    public void undoDestroy() {
        dispatcher.dispatch(TodoActions.TODO_UNDO_DESTROY);
    }

    public void toggleComplete(Todo todo) {
        long id = todo.getId();
        String actionType = todo.isComplete() ? TodoActions.TODO_UNDO_COMPLETE : TodoActions.TODO_COMPLETE;
        dispatcher.dispatch(actionType, TodoActions.KEY_ID, id);
    }

    public void toggleCompleteAll() {
        dispatcher.dispatch(TodoActions.TODO_TOGGLE_COMPLETE_ALL);
    }

    public void destroyCompleted() {
        dispatcher.dispatch(TodoActions.TODO_DESTROY_COMPLETED);
    }

}
