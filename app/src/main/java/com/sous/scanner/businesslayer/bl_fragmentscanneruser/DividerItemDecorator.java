package com.sous.scanner.businesslayer.bl_fragmentscanneruser;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecorator extends RecyclerView.ItemDecoration{



    private int minHeight;
    public DividerItemDecorator( int minHeight) {
        this.minHeight = minHeight;
    }

















    @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int itemCount = state.getItemCount();
            int lastPosition = itemCount - 1;
            int itemPosition = parent.getChildAdapterPosition(view);
            int layoutPosition = parent.getChildLayoutPosition(view);

            // If this view isnt on screen then do nothing
            if (layoutPosition != RecyclerView.NO_POSITION && itemPosition == lastPosition) {

                // NOTE: view.getHeight() doesn't always return the correct height, even if the layout is given a fixed height in the XML
                int childHeight = view.getHeight();

                int totalChildHeight = childHeight * itemCount;



                    outRect.bottom = minHeight - 500;

            }
        }
    }


