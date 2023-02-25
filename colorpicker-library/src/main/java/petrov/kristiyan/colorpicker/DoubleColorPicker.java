package petrov.kristiyan.colorpicker;

import static petrov.kristiyan.colorpicker.ColorUtils.dip2px;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DoubleColorPicker extends ColorPicker{
    protected String title2;
    protected RecyclerView recyclerView2;

    protected int default_color2;

    private OnChooseDoubleColorListener onChooseDoubleColorListener;

    protected ColorViewAdapter colorViewAdapter2;

    protected ArrayList<ColorPal> colors2;

    public interface OnChooseDoubleColorListener {
        void onChooseColor(int position, int color, int position2, int color2);

        void onCancel();
    }

    public DoubleColorPicker(Activity context) {
        super(context, false);
        dialogViewLayout = LayoutInflater.from(context).inflate(R.layout.double_color_palette_layout, null, false);
        colorpicker_base = dialogViewLayout.findViewById(R.id.colorpicker_base);
        recyclerView = dialogViewLayout.findViewById(R.id.color_palette);
        recyclerView2 = dialogViewLayout.findViewById(R.id.color_palette2);
        buttons_layout = dialogViewLayout.findViewById(R.id.buttons_layout);
        positiveButton = dialogViewLayout.findViewById(R.id.positive);
        negativeButton = dialogViewLayout.findViewById(R.id.negative);

        this.mContext = new WeakReference<>(context);
        this.dismiss = true;
        this.marginColorButtonLeft = this.marginColorButtonTop = this.marginColorButtonRight = this.marginColorButtonBottom = 5;
        this.title = context.getString(R.string.colorpicker_dialog_title);
        this.title2 = context.getString(R.string.colorpicker_dialog_title);
        this.negativeText = context.getString(R.string.colorpicker_dialog_cancel);
        this.positiveText = context.getString(R.string.colorpicker_dialog_ok);
        this.default_color = 0;
        this.default_color2 = 0;
        this.columns = 5;
    }

    @Override
    public ColorPicker setColors(int resId) {
        if (mContext == null)
            return this;

        Context context = mContext.get();
        if (context == null)
            return this;

        ta = context.getResources().obtainTypedArray(resId);
        colors = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            colors.add(new ColorPal(ta.getColor(i, 0), false));
        }
        colors2 = new ArrayList<>();
        for (int i = 0; i < ta.length(); i++) {
            colors2.add(new ColorPal(ta.getColor(i, 0), false));
        }

        return this;
    }

    @Override
    public DoubleColorPicker setColors(ArrayList<String> colorsHexList) {
        colors = new ArrayList<>();
        for (int i = 0; i < colorsHexList.size(); i++) {
            colors.add(new ColorPal(Color.parseColor(colorsHexList.get(i)), false));
        }
        colors2 = new ArrayList<>();
        for (int i = 0; i < colorsHexList.size(); i++) {
            colors2.add(new ColorPal(Color.parseColor(colorsHexList.get(i)), false));
        }
        return this;
    }

    @Override
    public ColorPicker setColors(int... colorsList) {
        colors = new ArrayList<>();
        for (int aColorsList : colorsList) {
            colors.add(new ColorPal(aColorsList, false));
        }
        colors2 = new ArrayList<>();
        for (int aColorsList : colorsList) {
            colors2.add(new ColorPal(aColorsList, false));
        }
        return this;
    }

    @Override
    public void show() {
        super.show();
        Activity context = mContext.get();

//        colors2 = (ArrayList<ColorPal>) colors.clone();
//
//        ta = context.getResources().obtainTypedArray(R.array.default_colors);
//        colors2 = new ArrayList<>();
//        for (int i = 0; i < ta.length(); i++) {
//            colors2.add(new ColorPal(ta.getColor(i, 0), false));
//        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, columns);
        recyclerView2.setLayoutManager(gridLayoutManager);
        if (fastChooser)
            colorViewAdapter2 = new ColorViewAdapter(colors2, onFastChooseColorListener, mDialog);
        else
            colorViewAdapter2 = new ColorViewAdapter(colors2);

        if (fullHeight) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.addRule(RelativeLayout.BELOW, recyclerView.getId());
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            recyclerView2.setLayoutParams(lp);
        }

        recyclerView2.setAdapter(colorViewAdapter2);

        AppCompatTextView titleView2 = dialogViewLayout.findViewById(R.id.title2);
        if (title != null) {
            titleView2.setText(title2);
            titleView2.setPadding(
                    dip2px(paddingTitleLeft, context), dip2px(paddingTitleTop, context),
                    dip2px(paddingTitleRight, context), dip2px(paddingTitleBottom, context));
        }


        if (marginBottom != 0 || marginLeft != 0 || marginRight != 0 || marginTop != 0) {
            colorViewAdapter2.setMargin(marginLeft, marginTop, marginRight, marginBottom);
        }
        if (tickColor != 0) {
            colorViewAdapter2.setTickColor(tickColor);
        }
        if (marginColorButtonBottom != 0 || marginColorButtonLeft != 0 || marginColorButtonRight != 0 || marginColorButtonTop != 0) {
            colorViewAdapter2.setColorButtonMargin(
                    dip2px(marginColorButtonLeft, context), dip2px(marginColorButtonTop, context),
                    dip2px(marginColorButtonRight, context), dip2px(marginColorButtonBottom, context));
        }
        if (colorButtonHeight != 0 || colorButtonWidth != 0) {
            colorViewAdapter2.setColorButtonSize(dip2px(colorButtonWidth, context), dip2px(colorButtonHeight, context));
        }
        if (roundColorButton) {
            setColorButtonDrawable(R.drawable.round_button);
        }
        if (colorButtonDrawable != 0) {
            colorViewAdapter2.setColorButtonDrawable(colorButtonDrawable);
        }

        if (default_color2 != 0) {
            colorViewAdapter2.setDefaultColor(default_color2);
        }

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChooseColorListener != null && !fastChooser)
                    onChooseColorListener.onChooseColor(colorViewAdapter.getColorPosition(), colorViewAdapter.getColorSelected());
                if(onChooseDoubleColorListener != null && !fastChooser)
                    onChooseDoubleColorListener.onChooseColor(colorViewAdapter.getColorPosition(), colorViewAdapter.getColorSelected(), colorViewAdapter2.getColorPosition(), colorViewAdapter2.getColorSelected());
                if (dismiss) {
                    dismissDialog();
                    if (onFastChooseColorListener != null) {
                        onFastChooseColorListener.onCancel();
                    }
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismiss)
                    dismissDialog();
                if (onChooseDoubleColorListener != null)
                    onChooseDoubleColorListener.onCancel();
            }
        });
    }

    public DoubleColorPicker setSecondTitle(String title) {
        this.title2 = title;
        return this;
    }

    public DoubleColorPicker setDefaultDoubleColorButton(int color) {
        this.default_color2 = color;
        return this;
    }

    public DoubleColorPicker setOnChooseDoubleColorListener(OnChooseDoubleColorListener listener) {
        onChooseDoubleColorListener = listener;
        return this;
    }
}
