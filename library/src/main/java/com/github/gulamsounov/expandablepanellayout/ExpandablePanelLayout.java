package com.github.gulamsounov.expandablepanellayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

public class ExpandablePanelLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private enum State {COLLAPSE, COLLAPSING, EXPAND, EXPANDING}

    ;
    private static final int DEFAULT_DURATION = 300;
    private static final boolean DEFAULT_EXPANDED = true;
    private float expansion = 0.0f;
    private State currentState = State.COLLAPSE;
    private ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
    private int duration = 300;

    public ExpandablePanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandablePanelLayout);
            boolean expanded = typedArray.getBoolean(R.styleable.ExpandablePanelLayout_epl_expanded, DEFAULT_EXPANDED);
            if (expanded) {
                expansion = 1.0f;
                currentState = State.EXPAND;
            } else {
                expansion = 0.0f;
                currentState = State.COLLAPSE;
            }
            duration = typedArray.getInt(R.styleable.ExpandablePanelLayout_epl_duration, DEFAULT_DURATION);
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
        valueAnimator.setDuration(duration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int newHeight = Math.round(height * expansion);
        int deltaHeight = newHeight - height;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setTranslationY(deltaHeight);

        }

        setMeasuredDimension(width, newHeight);
    }

    public void collapse() {
        if (currentState != State.EXPAND) {
            return;
        }
        valueAnimator.reverse();
    }

    public void expand() {
        if (currentState != State.COLLAPSE) {
            return;
        }
        valueAnimator.start();
    }

    public void toggle() {
        switch (currentState) {
            case COLLAPSE:
                expand();
                break;
            case EXPAND:
                collapse();
                break;
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        expansion = (float) animation.getAnimatedValue();
        requestLayout();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        switch (currentState) {
            case COLLAPSE:
                currentState = State.EXPANDING;
                break;
            case EXPAND:
                currentState = State.COLLAPSING;
                break;
        }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        switch (currentState) {
            case EXPANDING:
                currentState = State.EXPAND;
                break;
            case COLLAPSING:
                currentState = State.COLLAPSE;
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
