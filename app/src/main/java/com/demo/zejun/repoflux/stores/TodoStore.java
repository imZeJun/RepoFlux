package com.demo.zejun.repoflux.stores;

import com.demo.zejun.repoflux.actions.Action;
import com.demo.zejun.repoflux.actions.TodoActions;
import com.demo.zejun.repoflux.dispatcher.Dispatcher;
import com.demo.zejun.repoflux.model.Todo;
import com.squareup.otto.Subscribe;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TodoStore extends Store {

    private static TodoStore instance;
    private final List<Todo> todos;
    private Todo lastDeleted;

    protected TodoStore(Dispatcher dispatcher) {
        super(dispatcher);
        todos = new ArrayList<>();
    }

    public static TodoStore get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new TodoStore(dispatcher);
        }
        return instance;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public boolean canUndo() {
        return lastDeleted != null;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        long id;
        switch (action.getType()) {

            case TodoActions.TODO_CREATE:
                String text = (String) action.getData().get(TodoActions.KET_TEXT);
                create(text);
                emitStoreChange();
                break;

            case TodoActions.TODO_DESTROY:
                id = (long) action.getData().get(TodoActions.KEY_ID);
                destroy(id);
                emitStoreChange();
                break;

            case TodoActions.TODO_UNDO_DESTROY:
                undoDestroy();
                emitStoreChange();
                break;

            case TodoActions.TODO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(id, true);
                emitStoreChange();
                break;

            case TodoActions.TODO_UNDO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(id, false);
                emitStoreChange();
                break;

            case TodoActions.TODO_DESTROY_COMPLETED:
                destroyCompleted();
                emitStoreChange();
                break;

            case TodoActions.TODO_TOGGLE_COMPLETE_ALL:
                updateCompleteAll();
                emitStoreChange();
                break;

        }
    }

    private void destroyCompleted() {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.isComplete()) {
                iterator.remove();
            }
        }
    }

    private void updateCompleteAll() {
        if (areAllComplete()) {
            updateAllComplete(false);
        } else {
            updateAllComplete(true);
        }
    }

    private boolean areAllComplete() {
        for (Todo todo : todos) {
            if (!todo.isComplete()) {
                return false;
            }
        }
        return true;
    }

    private void updateAllComplete(boolean complete) {
        for (Todo todo : todos) {
            todo.setComplete(complete);
        }
    }

    private void updateComplete(long id, boolean complete) {
        Todo todo = getById(id);
        if (todo != null) {
            todo.setComplete(complete);
        }
    }

    private void undoDestroy() {
        try {
            if (lastDeleted != null) {
                addElement(lastDeleted.clone());
                lastDeleted = null;
            }
        } catch (Exception e) {
            Log.e("TodoStore", "Exception:" + e);
        }
    }

    private void create(String text) {
        long id = System.currentTimeMillis();
        Todo todo = new Todo(id, text);
        addElement(todo);
        Collections.sort(todos);
    }

    private void destroy(long id) {
        try {
            Iterator<Todo> iterator = todos.iterator();
            while (iterator.hasNext()) {
                Todo todo = iterator.next();
                if (todo.getId() == id) {
                    lastDeleted = todo.clone();
                    iterator.remove();
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("TodoStore", "Exception:" + e);
        }
    }

    private Todo getById(long id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    private void addElement(Todo clone) {
        todos.add(clone);
        Collections.sort(todos);
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new TodoStoreChangeEvent();
    }

    public class TodoStoreChangeEvent implements StoreChangeEvent {}
}
