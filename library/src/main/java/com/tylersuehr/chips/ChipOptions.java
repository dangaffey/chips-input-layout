package com.tylersuehr.chips;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * Contains all the mutable properties for this library.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
final class ChipOptions {
    /* Properties pertaining to ChipsEditText */
    ColorStateList mTextColorHint;
    ColorStateList mTextColor;
    CharSequence mHint;

    /* Properties pertaining to ChipView */
    Drawable mChipDeleteIcon;
    ColorStateList mChipDeleteIconColor;
    ColorStateList mChipBackgroundColor;
    ColorStateList mChipTextColor;
    ColorStateList mChipColor;
    float mDeleteIconAlpha;
    boolean mShowAvatar;
    boolean mShowDetails;
    boolean mShowDelete;

    /* Properties pertaining to ChipDetailsView */
    ColorStateList mDetailsChipDeleteIconColor;
    ColorStateList mDetailsChipBackgroundColor;
    ColorStateList mDetailsChipTextColor;

    /* Properties pertaining to FilterableRecyclerView */
    ColorStateList mFilterableListBackgroundColor;
    ColorStateList mFilterableListTextColor;
    float mFilterableListElevation;

    /* Properties pertaining to the ChipsInputLayout itself */
    Typeface mTypeface = Typeface.DEFAULT;
    boolean mAllowCustomChips;
    boolean mHideKeyboardOnChipClick;
    int mMaxRows;



    @NonNull
    ChipImageRenderer mImageRenderer;


    ChipOptions(Context c, AttributeSet attrs) {
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.ChipsInputLayout);

        // Setup the properties for the ChipView
        mShowDetails = a.getBoolean(R.styleable.ChipsInputLayout_chip_showDetails, true);
        mShowAvatar = a.getBoolean(R.styleable.ChipsInputLayout_chip_showAvatar, true);
        mShowDelete = a.getBoolean(R.styleable.ChipsInputLayout_chip_showDelete, true);
        mChipDeleteIcon = a.getDrawable(R.styleable.ChipsInputLayout_chip_deleteIcon);
        mChipDeleteIconColor = a.getColorStateList(R.styleable.ChipsInputLayout_chip_deleteIconColor);
        mChipBackgroundColor = a.getColorStateList(R.styleable.ChipsInputLayout_chip_backgroundColor);
        mChipTextColor = a.getColorStateList(R.styleable.ChipsInputLayout_chip_textColor);
        mChipColor = a.getColorStateList(R.styleable.ChipsInputLayout_chipColor);
        mDeleteIconAlpha = a.getFloat(R.styleable.ChipsInputLayout_delete_icon_alpha, .53f);


        // Setup the properties for the DetailedChipView
        mDetailsChipDeleteIconColor = a.getColorStateList(R.styleable.ChipsInputLayout_details_deleteIconColor);
        mDetailsChipBackgroundColor = a.getColorStateList(R.styleable.ChipsInputLayout_chip_backgroundColor);
        mDetailsChipTextColor = a.getColorStateList(R.styleable.ChipsInputLayout_details_textColor);

        // Setup the properties for the FilterableRecyclerView
        mFilterableListElevation = a.getDimension(R.styleable.ChipsInputLayout_filter_elevation, 8.0f);
        mFilterableListBackgroundColor = a.getColorStateList(R.styleable.ChipsInputLayout_filter_backgroundColor);
        mFilterableListTextColor = a.getColorStateList(R.styleable.ChipsInputLayout_filter_textColor);

        // Setup the properties for the ChipsInput itself
        mAllowCustomChips = a.getBoolean(R.styleable.ChipsInputLayout_allowCustomChips, true);
        mHideKeyboardOnChipClick = a.getBoolean(R.styleable.ChipsInputLayout_hideKeyboardOnChipClick, true);
        mMaxRows = a.getInt(R.styleable.ChipsInputLayout_maxRows, 3);

        a.recycle();

        mImageRenderer = new DefaultImageRenderer();
    }
}