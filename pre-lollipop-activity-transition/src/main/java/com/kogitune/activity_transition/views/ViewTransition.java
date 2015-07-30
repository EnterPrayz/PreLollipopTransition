package com.kogitune.activity_transition.views;

import android.animation.TimeInterpolator;
import android.view.View;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by EnterPrayz on 30.07.15 urecki22@gmailcom.
 */
public class ViewTransition {

    private View from;
    private View to;
    private int duration = 300;
    private TimeInterpolator interpolator;
    private String imagePath;
    private float[] aplha;

    public ViewTransition(View from, View to, int duration, TimeInterpolator interpolator, String imagePath, float[] alpha) {
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.interpolator = interpolator;
        this.imagePath = imagePath;
        this.aplha = alpha;
    }

    public ExitViewTransition start() {
        final MoveData moveData = TransitionAnimation.startViewAnimation(this);
        return new ExitViewTransition(moveData);
    }

    public String getImagePath() {
        return imagePath;
    }

    public View getFrom() {
        return from;
    }

    public View getTo() {
        return to;
    }

    public int getDuration() {
        return duration;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }

    public float[] getAplha() {
        return aplha;
    }

    public static Builder with(View from, View to) {
        return Builder.build(from, to);
    }

    public static class Builder {

        private View from;
        private View to;
        private int duration = 300;
        private String imagePath;
        private TimeInterpolator interpolator;
        private float[] aplha;

        private static Builder build(View from, View to) {
            return new Builder(from, to);
        }


        public Builder(View from, View to) {
            this.from = from;
            this.to = to;
        }

        public Builder withImage(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public Builder withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder aplpha(int from, int to) {
            this.aplha = new float[]{from, to};
            return this;
        }

        public Builder withInterpolator(TimeInterpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        public ViewTransition create() {
            return new ViewTransition(from, to, duration, interpolator, imagePath, aplha);
        }
    }

}
