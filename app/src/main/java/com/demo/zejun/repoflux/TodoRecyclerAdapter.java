package com.demo.zejun.repoflux;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.demo.zejun.repoflux.actions.ActionCreator;
import com.demo.zejun.repoflux.model.Todo;
import java.util.ArrayList;
import java.util.List;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>{

    private static ActionCreator actionCreator;
    private List<Todo> todos;

    public TodoRecyclerAdapter(ActionCreator actionCreator) {
        todos = new ArrayList<>();
        TodoRecyclerAdapter.actionCreator = actionCreator;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setItems(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView todoText;
        public CheckBox todoCheck;
        public Button todoDelete;

        public ViewHolder(View v) {
            super(v);
            todoText = (TextView) v.findViewById(R.id.row_text);
            todoCheck = (CheckBox) v.findViewById(R.id.row_checkbox);
            todoDelete = (Button) v.findViewById(R.id.row_delete);
        }

        public void bindView(final Todo todo) {
            if (todo.isComplete()) {
                SpannableString spannableString = new SpannableString(todo.getText());
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 0);
                todoText.setText(spannableString);
            } else {
                todoText.setText(todo.getText());
            }
            todoCheck.setChecked(todo.isComplete());
            todoCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //是否完成。
                    actionCreator.toggleComplete(todo);
                }
            });
            todoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除。
                    actionCreator.destroy(todo.getId());
                }
            });
        }
    }
}
