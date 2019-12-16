package com.trangdv.swipelayoutme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ItemHolder> {
    private static final String TAG = "LeftAdapter";
    private List<String> items;
    private int lastPositionOpened = -1;
    private SwipeListener swipeListener;

    LeftAdapter(List<String> items, SwipeListener swipeListener) {
        this.items = items;
        this.swipeListener = swipeListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
                return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_layout_item_0, viewGroup, false));
            default:
                return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_layout_item_0, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder itemHolder, int position) {
        itemHolder.parentLayoutItem0.close();
        itemHolder.dragItem.setText(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void remove(Context context, int position) {
        Toast.makeText(context, "removed item " + position, Toast.LENGTH_SHORT).show();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView dragItem;
        ImageView rightView;
        SwipeLayout swipeLayout;
        SwipeLayout parentLayoutItem0;

        ItemHolder(@NonNull final View itemView) {
            super(itemView);
            dragItem = itemView.findViewById(R.id.drag_item);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            rightView = itemView.findViewById(R.id.right_view);

            parentLayoutItem0 = (SwipeLayout) itemView.findViewById(R.id.parentLayoutItem0);

            if (rightView != null) {
                rightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAdapterPosition() != NO_POSITION) {
                            remove(itemView.getContext(), getAdapterPosition());
                        }
                    }
                });
            }


            parentLayoutItem0.setOnActionsListener(new SwipeLayout.SwipeActionsListener() {
                @Override
                public void onOpen(int direction, boolean isContinuous) {
                    if (lastPositionOpened != -1 && lastPositionOpened != getAdapterPosition()) {
                        swipeListener.onCloseLastSwipe(lastPositionOpened);
                    }
                    lastPositionOpened = getAdapterPosition();
                    parentLayoutItem0.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            parentLayoutItem0.close();
                            lastPositionOpened = -1;
                        }
                    });

                }

                @Override
                public void onClose() {
                    parentLayoutItem0.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickItem(itemView.getContext(), getAdapterPosition());
                        }
                    });
                }

            });

            parentLayoutItem0.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem(itemView.getContext(), getAdapterPosition());
                }
            });

        }
    }

    private void clickItem(Context context, int adapterPosition) {
        Toast.makeText(context, "clicked item " + adapterPosition, Toast.LENGTH_SHORT).show();
    }


    public interface SwipeListener {
        void onCloseLastSwipe(int position);
    }
}
