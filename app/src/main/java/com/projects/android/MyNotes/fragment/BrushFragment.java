package com.projects.android.MyNotes.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.projects.android.MyNotes.R;
import com.projects.android.MyNotes.helper.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;

public class BrushFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private BrushFragment.BrushFragmentListener listener;
    LineColorPicker picker;
    SeekBar seekBarSize, seekBarOpacity;

    public void setListener(BrushFragment.BrushFragmentListener listener) {
        this.listener = listener;
    }

    public BrushFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brush, container, false);

        ButterKnife.bind(this, view);
        seekBarSize=view.findViewById(R.id.seekbar_size);
        seekBarOpacity=view.findViewById(R.id.seekbar_opacity);

        seekBarSize.setOnSeekBarChangeListener(this);
        seekBarOpacity.setOnSeekBarChangeListener(this);

        picker = view.findViewById(R.id.color);
        int[] array = view.getResources().getIntArray(R.array.color);
        picker.setColors(array);
        picker.setSelectedColor(2);
   /*     picker.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int c) {
                int colorCode = picker.getColor();
                editor.mPhotoEditor.setBrushColor(colorCode);
            }
        });
*/
        view.findViewById(R.id.brush_radio);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (listener != null) {

            if (seekBar.getId() == R.id.seekbar_size) {
                // brightness values are b/w -100 to +100
                listener.onSizeChanged(progress - 100);
            }

            if (seekBar.getId() == R.id.seekbar_opacity) {
                // converting int value to float
                // contrast values are b/w 1.0f - 3.0f
                // progress = progress > 10 ? progress : 10;
                progress += 10;
                float floatVal = .10f * progress;
                listener.onOpacityChanged(floatVal);
            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (listener != null)
            listener.onBrushStarted();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null)
            listener.onBrushCompleted();
    }

    public void resetControls() {
        seekBarSize.setProgress(100);
        seekBarOpacity.setProgress(0);
    }

    public interface BrushFragmentListener {
        void onSizeChanged(int brightness);

        void onOpacityChanged(float saturation);

        void onColorChanged(float contrast);

        void onBrush();

        void onEraser();

        void onBrushStarted();

        void onBrushCompleted();
    }
}

