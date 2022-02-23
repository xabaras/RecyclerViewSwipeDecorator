package it.xabaras.android.recyclerview.swipedecorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;

/**
 * A simple utility class to add a background and/or an icon while swiping a RecyclerView item left or right.
 */

public class RecyclerViewSwipeDecorator {
    private Canvas canvas;
    private RecyclerView recyclerView;
    private RecyclerView.ViewHolder viewHolder;
    private float dX;
    private float dY;
    private int actionState;
    private boolean isCurrentlyActive;

    private int swipeLeftBackgroundColor;
    private int swipeLeftActionIconId;
    private Integer swipeLeftActionIconTint;

    private int swipeRightBackgroundColor;
    private int swipeRightActionIconId;
    private Integer swipeRightActionIconTint;

    private int iconHorizontalMargin;

    private String mSwipeLeftText;
    private float mSwipeLeftTextSize = 14;
    private int mSwipeLeftTextUnit = TypedValue.COMPLEX_UNIT_SP;
    private int mSwipeLeftTextColor = Color.DKGRAY;
    private Typeface mSwipeLeftTypeface = Typeface.SANS_SERIF;

    private String mSwipeRightText;
    private float mSwipeRightTextSize = 14;
    private int mSwipeRightTextUnit = TypedValue.COMPLEX_UNIT_SP;
    private int mSwipeRightTextColor = Color.DKGRAY;
    private Typeface mSwipeRightTypeface = Typeface.SANS_SERIF;

    private float mSwipeLeftCornerRadius;
    private float mSwipeRightCornerRadius;

    private int[] mSwipeLeftPadding;
    private int[] mSwipeRightPadding;

    private RecyclerViewSwipeDecorator() {
        swipeLeftBackgroundColor = 0;
        swipeRightBackgroundColor = 0;
        swipeLeftActionIconId = 0;
        swipeRightActionIconId = 0;
        swipeLeftActionIconTint = null;
        swipeRightActionIconTint = null;
        mSwipeLeftCornerRadius = 0;
        mSwipeRightCornerRadius = 0;
        mSwipeLeftPadding = new int[]{0, 0, 0};
        mSwipeRightPadding = new int[]{0, 0, 0};
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
     *
     * @deprecated in RecyclerViewSwipeDecorator 1.2.2
     */
    @Deprecated
    public RecyclerViewSwipeDecorator(Context context, Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        this(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * Create a @RecyclerViewSwipeDecorator
     * @param canvas The canvas which RecyclerView is drawing its children
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to
     * @param viewHolder The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
     * @param dX The amount of horizontal displacement caused by user's action
     * @param dY The amount of vertical displacement caused by user's action
     * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
     * @param isCurrentlyActive True if this view is currently being controlled by the user or false it is simply animating back to its original state
     */
    public RecyclerViewSwipeDecorator(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        this();
        this.canvas = canvas;
        this.recyclerView = recyclerView;
        this.viewHolder = viewHolder;
        this.dX = dX;
        this.dY = dY;
        this.actionState = actionState;
        this.isCurrentlyActive = isCurrentlyActive;
        this.iconHorizontalMargin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, recyclerView.getContext().getResources().getDisplayMetrics());
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
     * Set the tint color for either (left/right) action icons
     * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
     */
    public void setActionIconTint(int color) {
        this.setSwipeLeftActionIconTint(color);
        this.setSwipeRightActionIconTint(color);
    }

    /**
     * Set the background corner radius for either (left/right) swipe directions
     * @param unit @TypedValue the unit to convert from
     * @param size the radius value
     */
    public void setCornerRadius(int unit, float size) {
        this.setSwipeLeftCornerRadius(unit, size);
        this.setSwipeRightCornerRadius(unit, size);
    }

    /**
     * Set the background padding for either (left/right) swipe directions
     * @param unit @TypedValue the unit to convert from
     * @param top the top padding value
     * @param side the side padding value
     * @param bottom the bottom padding value
     */
    public void setPadding(int unit, float top, float side, float bottom) {
        this.setSwipeLeftPadding(unit, top, side, bottom);
        this.setSwipeRightPadding(unit, top, side, bottom);
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
     * Set the tint color for action icon drawn while swiping left
     * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
     */
    public void setSwipeLeftActionIconTint(int color) {
        swipeLeftActionIconTint = color;
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
     * Set the tint color for action icon drawn while swiping right
     * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
     */
    public void setSwipeRightActionIconTint(int color) {
        swipeRightActionIconTint = color;
    }

    /**
     * Set the label shown when swiping right
     * @param label a String
     */
    public void setSwipeRightLabel(String label) {
        mSwipeRightText = label;
    }

    /**
     * Set the size of the text shown when swiping right
     * @param unit TypedValue (default is COMPLEX_UNIT_SP)
     * @param size the size value
     */
    public void setSwipeRightTextSize(int unit, float size) {
        mSwipeRightTextUnit = unit;
        mSwipeRightTextSize = size;
    }

    /**
     * Set the color of the text shown when swiping right
     * @param color the color to be set
     */
    public void setSwipeRightTextColor(int color) {
        mSwipeRightTextColor = color;
    }

    /**
     * Set the Typeface of the text shown when swiping right
     * @param typeface the Typeface to be set
     */
    public void setSwipeRightTypeface(Typeface typeface) {
        mSwipeRightTypeface = typeface;
    }

    /**
     * Set the horizontal margin of the icon in DPs (default is 16dp)
     * @param iconHorizontalMargin the margin in pixels
     *
     * @deprecated in RecyclerViewSwipeDecorator 1.2, use {@link #setIconHorizontalMargin(int, int)} instead.
     */
    @Deprecated
    public void setIconHorizontalMargin(int iconHorizontalMargin) {
        setIconHorizontalMargin(TypedValue.COMPLEX_UNIT_DIP, iconHorizontalMargin);
    }

    /**
     * Set the horizontal margin of the icon in the given unit (default is 16dp)
     * @param unit TypedValue
     * @param iconHorizontalMargin the margin in the given unit
     */
    public void setIconHorizontalMargin(int unit, int iconHorizontalMargin) {
        this.iconHorizontalMargin = (int)TypedValue.applyDimension(unit, iconHorizontalMargin, recyclerView.getContext().getResources().getDisplayMetrics());
    }

    /**
     * Set the label shown when swiping left
     * @param label a String
     */
    public void setSwipeLeftLabel(String label) {
        mSwipeLeftText = label;
    }

    /**
     * Set the size of the text shown when swiping left
     * @param unit TypedValue (default is COMPLEX_UNIT_SP)
     * @param size the size value
     */
    public void setSwipeLeftTextSize(int unit, float size) {
        mSwipeLeftTextUnit = unit;
        mSwipeLeftTextSize = size;
    }

    /**
     * Set the color of the text shown when swiping left
     * @param color the color to be set
     */
    public void setSwipeLeftTextColor(int color) {
        mSwipeLeftTextColor = color;
    }

    /**
     * Set the Typeface of the text shown when swiping left
     * @param typeface the Typeface to be set
     */
    public void setSwipeLeftTypeface(Typeface typeface) {
        mSwipeLeftTypeface = typeface;
    }

    /**
     * Set the background corner radius for left swipe direction
     * @param unit @TypedValue the unit to convert from
     * @param size the radius value
     */
    public void setSwipeLeftCornerRadius(int unit, float size) {
        mSwipeLeftCornerRadius = TypedValue.applyDimension(unit, size, recyclerView.getContext().getResources().getDisplayMetrics());
    }

    /**
     * Set the background corner radius for right swipe direction
     * @param unit @TypedValue the unit to convert from
     * @param size the radius value
     */
    public void setSwipeRightCornerRadius(int unit, float size) {
        mSwipeRightCornerRadius = TypedValue.applyDimension(unit, size, recyclerView.getContext().getResources().getDisplayMetrics());
    }

    /**
     * Set the background padding for left swipe direction
     * @param unit @TypedValue the unit to convert from
     * @param top the top padding value
     * @param right the right padding value
     * @param bottom the bottom padding value
     */
    public void setSwipeLeftPadding(int unit, float top, float right, float bottom) {
        mSwipeLeftPadding = new int[] {
                (int)TypedValue.applyDimension(unit, top, recyclerView.getContext().getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(unit, right, recyclerView.getContext().getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(unit, bottom, recyclerView.getContext().getResources().getDisplayMetrics())
        };
    }

    /**
     * Set the background padding for right swipe direction
     * @param unit @TypedValue the unit to convert from
     * @param top the top padding value
     * @param left the left padding value
     * @param bottom the bottom padding value
     */
    public void setSwipeRightPadding(int unit, float top, float left, float bottom) {
        mSwipeRightPadding = new int[] {
                (int)TypedValue.applyDimension(unit, top, recyclerView.getContext().getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(unit, left, recyclerView.getContext().getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(unit, bottom, recyclerView.getContext().getResources().getDisplayMetrics())
        };
    }

    /**
     * Decorate the RecyclerView item with the chosen backgrounds and icons
     */
    public void decorate() {
        try {
            if ( actionState != ItemTouchHelper.ACTION_STATE_SWIPE ) return;

            if ( dX > 0 ) {
                // Swiping Right
                canvas.clipRect(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop(), viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom());
                if ( swipeRightBackgroundColor != 0 ) {
                    if ( mSwipeRightCornerRadius != 0 ) {
                        final GradientDrawable background = new GradientDrawable();
                        background.setColor(swipeRightBackgroundColor);
                        background.setBounds(viewHolder.itemView.getLeft() + mSwipeRightPadding[1], viewHolder.itemView.getTop() + mSwipeRightPadding[0], viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom() - mSwipeRightPadding[2]);
                        background.setCornerRadii(new float[]{mSwipeLeftCornerRadius, mSwipeLeftCornerRadius, 0, 0, 0, 0, mSwipeLeftCornerRadius, mSwipeLeftCornerRadius});
                        background.draw(canvas);
                    } else {
                        final ColorDrawable background = new ColorDrawable(swipeRightBackgroundColor);
                        background.setBounds(viewHolder.itemView.getLeft() + mSwipeRightPadding[1], viewHolder.itemView.getTop() + mSwipeRightPadding[0], viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom() - mSwipeRightPadding[2]);
                        background.draw(canvas);
                    }
                }

                int iconSize = 0;
                if ( swipeRightActionIconId != 0 && dX > iconHorizontalMargin ) {
                    Drawable icon = ContextCompat.getDrawable(recyclerView.getContext(), swipeRightActionIconId);
                    if ( icon != null ) {
                        iconSize = icon.getIntrinsicHeight();
                        int halfIcon = iconSize / 2;
                        int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                        icon.setBounds(viewHolder.itemView.getLeft() + iconHorizontalMargin + mSwipeRightPadding[1], top, viewHolder.itemView.getLeft() + iconHorizontalMargin + mSwipeRightPadding[1] + icon.getIntrinsicWidth(), top + icon.getIntrinsicHeight());
                        if (swipeRightActionIconTint != null)
                            icon.setColorFilter(swipeRightActionIconTint, PorterDuff.Mode.SRC_IN);
                        icon.draw(canvas);
                    }
                }

                if ( mSwipeRightText != null && mSwipeRightText.length() > 0 && dX > iconHorizontalMargin + iconSize) {
                    TextPaint textPaint = new TextPaint();
                    textPaint.setAntiAlias(true);
                    textPaint.setTextSize(TypedValue.applyDimension(mSwipeRightTextUnit, mSwipeRightTextSize, recyclerView.getContext().getResources().getDisplayMetrics()));
                    textPaint.setColor(mSwipeRightTextColor);
                    textPaint.setTypeface(mSwipeRightTypeface);

                    int textTop = (int) (viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2.0) + textPaint.getTextSize()/2);
                    canvas.drawText(mSwipeRightText, viewHolder.itemView.getLeft() + iconHorizontalMargin  + mSwipeRightPadding[1] + iconSize + (iconSize > 0 ? iconHorizontalMargin/2 : 0), textTop,textPaint);
                }

            } else if ( dX < 0 ) {
                // Swiping Left
                canvas.clipRect(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
                if ( swipeLeftBackgroundColor != 0 ) {
                    if ( mSwipeLeftCornerRadius != 0 ) {
                        final GradientDrawable background = new GradientDrawable();
                        background.setColor(swipeLeftBackgroundColor);
                        background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop() + mSwipeLeftPadding[0], viewHolder.itemView.getRight() - mSwipeLeftPadding[1], viewHolder.itemView.getBottom() - mSwipeLeftPadding[2]);
                        background.setCornerRadii(new float[]{0, 0, mSwipeLeftCornerRadius, mSwipeLeftCornerRadius, mSwipeLeftCornerRadius, mSwipeLeftCornerRadius, 0, 0});
                        background.draw(canvas);
                    } else {
                        final ColorDrawable background = new ColorDrawable(swipeLeftBackgroundColor);
                        background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop() + mSwipeLeftPadding[0], viewHolder.itemView.getRight() - mSwipeLeftPadding[1], viewHolder.itemView.getBottom() - mSwipeLeftPadding[2]);
                        background.draw(canvas);
                    }
                }

                int iconSize = 0;
                int imgLeft = viewHolder.itemView.getRight();
                if ( swipeLeftActionIconId != 0 && dX < - iconHorizontalMargin ) {
                    Drawable icon = ContextCompat.getDrawable(recyclerView.getContext(), swipeLeftActionIconId);
                    if ( icon != null ) {
                        iconSize = icon.getIntrinsicHeight();
                        int halfIcon = iconSize / 2;
                        int top = viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2 - halfIcon);
                        imgLeft = viewHolder.itemView.getRight() - iconHorizontalMargin - mSwipeLeftPadding[1] - halfIcon * 2;
                        icon.setBounds(imgLeft, top, viewHolder.itemView.getRight() - iconHorizontalMargin - mSwipeLeftPadding[1], top + icon.getIntrinsicHeight());
                        if (swipeLeftActionIconTint != null)
                            icon.setColorFilter(swipeLeftActionIconTint, PorterDuff.Mode.SRC_IN);
                        icon.draw(canvas);
                    }
                }

                if ( mSwipeLeftText != null && mSwipeLeftText.length() > 0 && dX < - iconHorizontalMargin - mSwipeLeftPadding[1] - iconSize ) {
                    TextPaint textPaint = new TextPaint();
                    textPaint.setAntiAlias(true);
                    textPaint.setTextSize(TypedValue.applyDimension(mSwipeLeftTextUnit, mSwipeLeftTextSize, recyclerView.getContext().getResources().getDisplayMetrics()));
                    textPaint.setColor(mSwipeLeftTextColor);
                    textPaint.setTypeface(mSwipeLeftTypeface);

                    float width = textPaint.measureText(mSwipeLeftText);
                    int textTop = (int) (viewHolder.itemView.getTop() + ((viewHolder.itemView.getBottom() - viewHolder.itemView.getTop()) / 2.0) + textPaint.getTextSize() / 2);
                    canvas.drawText(mSwipeLeftText, imgLeft - width - ( imgLeft == viewHolder.itemView.getRight() ? iconHorizontalMargin : iconHorizontalMargin/2 ), textTop, textPaint);
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
         *
         * @deprecated in RecyclerViewSwipeDecorator 1.2.2
         */
        @Deprecated
        public Builder(Context context, Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            this(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        /**
         * Create a builder for a RecyclerViewsSwipeDecorator
         * @param canvas The canvas which RecyclerView is drawing its children
         * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to
         * @param viewHolder The ViewHolder which is being interacted by the User or it was interacted and simply animating to its original position
         * @param dX The amount of horizontal displacement caused by user's action
         * @param dY The amount of vertical displacement caused by user's action
         * @param actionState The type of interaction on the View. Is either ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
         * @param isCurrentlyActive True if this view is currently being controlled by the user or false it is simply animating back to its original state
         */
        public Builder(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            mDecorator = new RecyclerViewSwipeDecorator(
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
         * Add a background color to both swiping directions
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
         * Set the tint color for either (left/right) action icons
         * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setActionIconTint(int color) {
            mDecorator.setActionIconTint(color);
            return this;
        }

        /**
         * Add a corner radius to swipe background for both swipe directions
         * @param unit @TypedValue the unit to convert from
         * @param size the corner radius in the given unit
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addCornerRadius(int unit, int size) {
            mDecorator.setCornerRadius(unit, size);
            return this;
        }

        /**
         * Add padding to the swipe background for both swipe directions
         * @param unit @TypedValue the unit to convert from
         * @param top the top padding value
         * @param side the side padding value
         * @param bottom the bottom padding value
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addPadding(int unit, float top, float side, float bottom) {
            mDecorator.setPadding(unit, top, side, bottom);
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
         * Set the tint color for action icon shown while swiping right
         * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeRightActionIconTint(int color) {
            mDecorator.setSwipeRightActionIconTint(color);
            return this;
        }

        /**
         * Add a label to be shown while swiping right
         * @param label The string to be shown as label
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeRightLabel(String label) {
            mDecorator.setSwipeRightLabel(label);
            return this;
        }

        /**
         * Set the color of the label to be shown while swiping right
         * @param color the color to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeRightLabelColor(int color) {
            mDecorator.setSwipeRightTextColor(color);
            return this;
        }

        /**
         * Set the size of the label to be shown while swiping right
         * @param unit the unit to convert from
         * @param size the size to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeRightLabelTextSize(int unit, float size) {
            mDecorator.setSwipeRightTextSize(unit, size);
            return this;
        }

        /**
         * Set the Typeface of the label to be shown while swiping right
         * @param typeface the Typeface to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeRightLabelTypeface(Typeface typeface) {
            mDecorator.setSwipeRightTypeface(typeface);
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
         * Set the tint color for action icon shown while swiping left
         * @param color a color in ARGB format (e.g. 0xFF0000FF for blue)
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeLeftActionIconTint(int color) {
            mDecorator.setSwipeLeftActionIconTint(color);
            return this;
        }

        /**
         * Add a label to be shown while swiping left
         * @param label The string to be shown as label
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeLeftLabel(String label) {
            mDecorator.setSwipeLeftLabel(label);
            return this;
        }

        /**
         * Set the color of the label to be shown while swiping left
         * @param color the color to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeLeftLabelColor(int color) {
            mDecorator.setSwipeLeftTextColor(color);
            return this;
        }

        /**
         * Set the size of the label to be shown while swiping left
         * @param unit the unit to convert from
         * @param size the size to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeLeftLabelTextSize(int unit, float size) {
            mDecorator.setSwipeLeftTextSize(unit, size);
            return this;
        }

        /**
         * Set the Typeface of the label to be shown while swiping left
         * @param typeface the Typeface to be set
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setSwipeLeftLabelTypeface(Typeface typeface) {
            mDecorator.setSwipeLeftTypeface(typeface);
            return this;
        }

        /**
         * Set the horizontal margin of the icon in DPs (default is 16dp)
         * @param pixels margin in pixels
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         *
         * @deprecated in RecyclerViewSwipeDecorator 1.2, use {@link #setIconHorizontalMargin(int, int)} instead.
         */
        @Deprecated
        public Builder setIconHorizontalMargin(int pixels) {
            mDecorator.setIconHorizontalMargin(pixels);
            return this;
        }

        /**
         * Set the horizontal margin of the icon in the given unit (default is 16dp)
         * @param unit @TypedValue the unit to convert from
         * @param iconHorizontalMargin the margin in the given unit
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder setIconHorizontalMargin(int unit, int iconHorizontalMargin) {
            mDecorator.setIconHorizontalMargin(unit, iconHorizontalMargin);
            return this;
        }

        /**
         * Set the background corner radius for left swipe direction
         * @param unit @TypedValue the unit to convert from
         * @param size the radius value
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeLeftCornerRadius(int unit, float size) {
            mDecorator.setSwipeLeftCornerRadius(unit, size);
            return this;
        }

        /**
         * Set the background corner radius for right swipe direction
         * @param unit @TypedValue the unit to convert from
         * @param size the radius value
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeRightCornerRadius(int unit, float size) {
            mDecorator.setSwipeRightCornerRadius(unit, size);
            return this;
        }

        /**
         * Add padding to the swipe background for left swipe direction
         * @param unit @TypedValue the unit to convert from
         * @param top the top padding value
         * @param right the right padding value
         * @param bottom the bottom padding value
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeLeftPadding(int unit, float top, float right, float bottom) {
            mDecorator.setSwipeLeftPadding(unit, top, right, bottom);
            return this;
        }

        /**
         * Add padding to the swipe background for right swipe direction
         * @param unit @TypedValue the unit to convert from
         * @param top the top padding value
         * @param left the left padding value
         * @param bottom the bottom padding value
         *
         * @return This instance of @RecyclerViewSwipeDecorator.Builder
         */
        public Builder addSwipeRightPadding(int unit, float top, float left, float bottom) {
            mDecorator.setSwipeRightPadding(unit, top, left, bottom);
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
