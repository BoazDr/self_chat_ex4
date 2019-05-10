package com.boaz.dragonski.mychat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecycleViewUtils {
    static class RowHolder extends RecyclerView.ViewHolder{
        public TextView row_text_view;
        public RowHolder(@NonNull View itemView) {
            super(itemView);
            row_text_view = itemView.findViewById(R.id.row_text);
        }
    }

    static class RowCallBack extends DiffUtil.ItemCallback<OneMessage>{
        @Override
        public boolean areContentsTheSame(@NonNull OneMessage oneMessage, @NonNull OneMessage t1) {
            return oneMessage.equals(t1);
        }
        @Override
        public boolean areItemsTheSame(@NonNull OneMessage oneMessage, @NonNull OneMessage t1) {
            return oneMessage.equals(t1);
        }
    }

    static class RowAdapter extends ListAdapter<OneMessage, RowHolder> {
        private Messages messages;
        private Context context;

        protected RowAdapter(Messages messages, Context context) {
            super(new RowCallBack());
            this.messages = messages;
            this.context = context;
        }

        @NonNull
        @Override
        public RowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            View row_view = LayoutInflater.from(context).inflate(R.layout.single_row, viewGroup, false);
            return new RowHolder(row_view);
        }

        @Override
        public void onBindViewHolder(@NonNull RowHolder rowHolder, int i) {
            String curRow = getItem(i).getContent();
            rowHolder.row_text_view.setText(curRow);
            final OneMessage message = getItem(i);

            final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            messages.removeMessage(message);
                            notifyDataSetChanged();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE: //No button clicked
                            break;
                    }
                }
            };

            rowHolder.row_text_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Delete the message?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener)
                            .show();
                    return false;
                }
            });
        }
    }

}
