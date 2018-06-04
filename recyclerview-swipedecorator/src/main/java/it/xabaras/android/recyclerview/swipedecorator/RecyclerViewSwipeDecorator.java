package it.xabaras.android.recyclerview.swipedecorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;

/**
 * A simple utility class to add a background and/or an icon while swiping a RecyclerView item left or right.
 */

public class RecyclerViewSwipeDecorator {
    private Context context;
    private Canvas canvas;
    private RecyclerView recyclerView;
    private RecyclerView.ViewHolder viewHolder;
    private float dX;
    private float dY;
    private int actionState;
    private boolean isCurrentlyActive;

    private int swipeLeftBackgroundColor;
    private int swipeLeftActionIconId;

    private int swipeRightBackgroundColor;
    private int swipeRightActionIconId;

    private int iconHorizontalMargin;

    private RecyclerViewSwipeDecorator() {
        swipeLeftBackgroundColor = 0;
        swipeRightBackgroundColor = 0;
        swipeLeftActionIconId = 0;
        swipeRightActionIconId = 0;
    }

    /**
     * Create a @RecyclerViewSwipeDecorator
     * @param context A valid Context object for the RecyclerView
     * @param canvas The canvas which RecyclerView is drawing its children
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to
     * @param viewHolder The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
     * @param dX The amount of horizontal displacement caused by user's action
     * @param dY The amount of vertical displacement caused by user's action
     * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
     * @param isCurrentlyActive True if this view is currently being controlled by the user or false it is simply animating back to its original state
     */
    public RecyclerViewSwipeDecorator(Context context, Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        this();
        this.context = context;
        this.canvas = canvas;
        this.recyclerView = recyclerView;
        this.viewHolder = viewHolder;
        this.dX = dX;
        this.dY = dY;
        this.actionState = actionState;
        this.isCurrentlyActive = isCurrentlyActive;
        this.iconHorizontalMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
    }

    /**
     * Set the background color for either (left/right) swipe directions
     * @param backgroundColor The resource id of the background color to be set
     */
    public void setBackgroundColor(int backgroundColor) {
        this.swipeLeftBackgroundColor = backgroundColor;
        this.swipeRightBackgroundColor = backgroundColor;
    }

    /**
     * Set the action icon for either (left/right) swipe directions
     * @param actionIconId The resource id of the icon to be set
     */
    public void setActionIconId(int actionIconId) {
        this.swipeLeftActionIconId = actionIconId;
        this.swipeRightActionIconId = actionIconId;
    }

    /**
     * Set the background color for left swipe direction
     * @param swipeLeftBackgroundColor The resource id of the background color to be set
     */
    public void setSwipeLeftBackgroundColor(int swipeLeftBackgroundColor) {
        this.swipeLeftBackgroundColor = swipeLeftBackgroundColor;
    }

    /**
     * Set the action icon for left swipe direction
     * @param swipeLeftActionIconId The resource id of the icon to be set
     */
    public void setSwipeLeftActionIconId(int swipeLeftActionIconId) {
        this.swipeLeftActionIconId = swipeLeftActionIconId;
    }

    /**
     * Set the background color for right swipe direction
     * @param swipeRightBackgroundColor The resource id of the background color to be set
     */
    public void setSwipeRightBackgroundColor(int swipeRightBackgroundColor) {
        this.swipeRightBackgroundColor = swipeRightBackgroundColor;
    }

    /**
     * Set the action icon for right swipe direction
     * @param swipeRightActionIconId The resource id of the icon to be set
     */
    public void setSwipeRightActionIconId(int swipeRightActionIconId) {
        this.swipeRightActionIconId = swipeRightActionIconId;
    }

    /**
     * Set the horizontal margin of the icon (default is 16dp)
     * @param iconHorizontalMargin the margin in pixels
     */
    public void setIconHorizontalMargin(int iconHorizontalMargin) {
        this.iconHorizontalMargin = iconHorizontalMargin;
    }

    /**
     * Decorate the RecyclerView item with the chosen backgrounds and icons
     */
    public void decorate() {
        try {
            if ( actionState != ItemTouchHelper.ACTION_STATE_SWIPE ) return;

            if ( dX > 0 ) {
                // Swiping Right
                if ( swipeRightBackgroundColor != 0 ) {
                    final ColorDrawable background = new ColorDrawable(swipeRightBackgroundColor);
                    background.setBounds(0, viewHolder.itemView.getTop(), viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom());
                    background.draw(canvas);
                }

                if ( swipeRightActionIconId != 0 ) {
                    Drawable icon = ContextCompat.getDrawable(context, swipeRightActionIconId);
                    int halfIcon = icon.getIntrinsicHeight() / 2;
                    int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                    icon.setBounds(iconHorizontalMargin, top, iconHorizontalMargin + icon.getIntrinsicWidth(), top + icon.getIntrinsicHeight());
                    icon.draw(canvas);
                }
            } else if ( dX < 0 ) {
                // Swiping Left
                if ( swipeLeftBackgroundColor != 0 ) {
                    final ColorDrawable background = new ColorDrawable(swipeLeftBackgroundColor);
                    background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
                    background.draw(canvas);
                }

                if ( swipeLeftActionIconId != 0 ) {
                    Drawable icon = ContextCompat.getDrawable(context, swipeLeftActionIconId);
                    int halfIcon = icon.getIntrinsicHeight() / 2;
                    int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                    icon.setBounds(viewHolder.itemView.getRight() - iconHorizontalMargin - halfIcon * 2, top, viewHolder.itemView.getRight() - iconHorizontalMargin, top + icon.getIntrinsicHeight());
                    icon.draw(canvas);
                }
            }
        } catch(Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    /**
     * A Builder for the RecyclerViewSwipeDecorator class
     */
    public static class Builder {
        private RecyclerViewSwipeDecorator mDecorator;

        /**
         * Create a builder for a RecyclerViewsSwipeDecorator
         * @param context A valid Context object for the RecyclerView
         * @param canvas The canvas which RecyclerView is drawing its children
         * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to
         * @param viewHolder The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
         * @param dX The amount of horizontal displacement caused by user's action
         * @param dY The amount of vertical displacement caused by user's action
         * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
         * @param isCurrentlyActive True if this view is currently being controlled by the user or false it is simply animating back to its original state
         */
        public Builder(Context context , Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            mDecorator = new RecyclerViewSwipeDecorator(
                    context,
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
            );
        }

        /**
         * Adds a background color to both swiping directions
         * @param color A single color value in the form 0xAARRGGBB
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addBackgroundColor(int color) {
            mDecorator.setBackgroundColor(color);
            return this;
        }

        /**
         * Add an action icon to both swiping directions
         * @param drawableId The resource id of the icon to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addActionIcon(int drawableId) {
            mDecorator.setActionIconId(drawableId);
            return this;
        }

        /**
         * Add a background color while swiping right
         * @param color A single color value in the form 0xAARRGGBB
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeRightBackgroundColor(int color) {
            mDecorator.setSwipeRightBackgroundColor(color);
            return this;
        }

        /**
         * Add an action icon while swiping right
         * @param drawableId The resource id of the icon to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeRightActionIcon(int drawableId) {
            mDecorator.setSwipeRightActionIconId(drawableId);
            return this;
        }

        /**
         * Adds a background color while swiping left
         * @param color A single color value in the form 0xAARRGGBB
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeLeftBackgroundColor(int color) {
            mDecorator.setSwipeLeftBackgroundColor(color);
            return this;
        }

        /**
         * Add an action icon while swiping left
         * @param drawableId The resource id of the icon to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeLeftActionIcon(int drawableId) {
            mDecorator.setSwipeLeftActionIconId(drawableId);
            return this;
        }

        /**
         * Set icon horizontal margin (default is 16dp)
         * @param pixels margin in pixels
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setIconHorizontalMargin(int pixels) {
            mDecorator.setIconHorizontalMargin(pixels);
            return this;
        }

        /**
         * Create a RecyclerViewSwipeDecorator
         * @return The created @RecyclerViewSwipeDecorator
         */
        public RecyclerViewSwipeDecorator create() {
            return mDecorator;
        }
    }
}
