package com.tylersuehr.chips;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * Subclass of {@link RecyclerView.Adapter} to adapt the selected chips into views
 * and display an input (EditText) to allow the user to type text in for chips.
 *
 * This adapter should afford the following abilities/features:
 * (1) Allow user to create custom chips, if the options permit it.
 * (2) Allow user to remove any chip by pressing delete on an empty input.
 * (3) Allow the user to see chip details, if the options permit it.
 *
 * This observes changes to {@link ChipDataSource} to update the UI accordingly.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
class ChipsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ChipDataSource.ChangeObserver {
    private static final int CHIP  = 0;
    private static final int INPUT = 1;

    private final ChipDataSource mDataSource;
    private final ChipOptions mOptions;
    private final EditText mEditText;
    private final View holder;
    private EmptyChipsListener listener;


    ChipsAdapter(ChipDataSource dataSource,
                 EditText editText,
                 ChipOptions options, View holder,
                 EmptyChipsListener listener) {
        mDataSource = dataSource;
        mEditText = editText;
        mOptions = options;
        this.holder = holder;
        this.listener = listener;

        // Register an observer on the chip data source
        mDataSource.addChangedObserver(this);
    }

    @Override
    public int getItemViewType(int position) {
        return position < mDataSource
                .getSelectedChips().size() ? CHIP : INPUT;
    }

    @Override
    public int getItemCount() {
        // Plus 1 for the edit text
        return mDataSource.getSelectedChips().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == CHIP
                ? new ChipHolder(new ChipView(parent.getContext()))
                : new RecyclerView.ViewHolder(holder) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Display the chip information on the chip view
        final ChipHolder ch = (ChipHolder)holder;
        ch.chipView.inflateFromChip(mDataSource.getSelectedChip(position));
    }


    @Override
    public void onChipDataSourceChanged() {
        notifyDataSetChanged();
        listener.updateChipsHeader(getItemCount());
    }

    private void showDetailedChipView(ChipView view, Chip chip, final int position) {
        // Get chip view's location
        int[] coord = new int[2];
        view.getLocationInWindow(coord);

        // Create a detailed chip view to show
        final ChipDetailsView detailedChipView = new ChipDetailsView(view.getContext());
        detailedChipView.setChipOptions(mOptions);
        detailedChipView.inflateWithChip(chip);

        // Setup the location in window of the detailed chip
        setDetailedChipViewPosition(detailedChipView, coord);

        // Remove the detailed chip when delete button is pressed
        detailedChipView.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Will trigger notifyDataSetChanged()
                mDataSource.replaceChip(position);
                detailedChipView.fadeOut();
            }
        });
    }

    private void setDetailedChipViewPosition(final ChipDetailsView detailedChipView, int[] coord) {
        // Window width
        final ViewGroup rootView = (ViewGroup)mEditText.getRootView();
        int windowWidth = Utils.getWindowWidth(rootView.getContext());

        // Chip size
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                Utils.dp(300),
                Utils.dp(100)
        );
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        // Show the detailed chip view
        rootView.addView(detailedChipView, lp);
        detailedChipView.fadeIn();
    }

    /**
     * Nested inner-subclass of {@link RecyclerView.ViewHolder} that stores
     * reference to the a chip view.
     */
    private class ChipHolder extends RecyclerView.ViewHolder implements
            ChipView.OnChipClickListener, ChipView.OnChipDeleteListener {
        ChipView chipView;

        ChipHolder(ChipView chipView) {
            super(chipView);
            this.chipView = chipView;
            this.chipView.setChipOptions(mOptions);
            this.chipView.setOnDeleteClicked(this);
            if (mOptions.mShowDetails) {
                this.chipView.setOnChipClicked(this);
            } else {
                this.chipView.setOnChipClicked(null);
            }
        }

        @Override
        public void onChipClicked(ChipView v) {
            final int position = getAdapterPosition();
            if (position > -1) {
                final Chip chip = mDataSource.getSelectedChip(position);
                showDetailedChipView(v, chip, position);
            }
        }

        @Override
        public void onChipDeleted(ChipView v) {
            // Will trigger notifyDataSetChanged()
            final int position = getAdapterPosition();
            if (position > -1){
                mDataSource.replaceChip(position);
            }
        }
    }
}