package com.demo.zejun.repoflux.actions;


public interface TodoActions {

    String TODO_CREATE = "todo-create"; //新建任务。
    String TODO_COMPLETE = "todo-complete"; //完成任务。
    String TODO_DESTROY = "todo-destroy"; //删除任务。

    String TODO_DESTROY_COMPLETED = "todo-destroy-completed"; //删除已完成任务。
    String TODO_TOGGLE_COMPLETE_ALL = "todo-toggle-complete-all"; //反选已完成勾选。

    String TODO_UNDO_COMPLETE = "todo-undo-complete"; //撤销完成。
    String TODO_UNDO_DESTROY = "todo-undo-destroy"; //撤销删除。

    String KET_TEXT = "key-text"; //文本。
    String KEY_ID = "key-id"; //id
}
