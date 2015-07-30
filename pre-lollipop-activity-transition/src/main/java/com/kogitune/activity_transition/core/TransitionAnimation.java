package com.kogitune.activity_transition.core;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.kogitune.activity_transition.views.ViewTransition;

import java.lang.ref.WeakReference;

/**
 * Created by takam on 2015/05/17.
 */
public class TransitionAnimation {
    private static final String TAG = "Transition";
    public static WeakReference<Bitmap> bitmapCache;

    public static MoveData startAnimation(Context context, final View toView, Bundle transitionBundle, Bundle savedInstanceState, final int duration, final TimeInterpolator interpolator) {
        final TransitionData transitionData = new TransitionData(context, transitionBundle);
        if (transitionData.imageFilePath != null) {
            setImageToView(toView, transitionData.imageFilePath);
        }
        final MoveData moveData = new MoveData();
        moveData.toView = toView;
        moveData.duration = duration;
        if (savedInstanceState == null) {

            ViewTreeObserver observer = toView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    toView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    toView.getLocationOnScreen(screenLocation);
                    moveData.leftDelta = transitionData.thumbnailLeft - screenLocation[0];
                    moveData.topDelta = transitionData.thumbnailTop - screenLocation[1];

                    moveData.widthScale = (float) transitionData.thumbnailWidth / toView.getWidth();
                    moveData.heightScale = (float) transitionData.thumbnailHeight / toView.getHeight();

                    runEnterAnimation(moveData, interpolator, false);

                    return true;
                }
            });
        }
        return moveData;
    }

    public static MoveData startViewAnimation(final ViewTransition transition) {
        final MoveData moveData = new MoveData();
        final View from = transition.getFrom();
        final View toView = transition.getTo();

        if (transition.getAplha() != null) {
            moveData.alpha = transition.getAplha();
        } else {
            moveData.alpha = new float[]{from.getAlpha(), toView.getAlpha()};
        }


        moveData.toView = toView;
        moveData.duration = transition.getDuration();
        toView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                toView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                final Rect r = new Rect();
                from.getParent().getChildVisibleRect(from, r, null);

                final TransitionData transitionData = new TransitionData(r.left, r.top, from.getMeasuredWidth(), from.getMeasuredHeight(), null);
                if (transitionData.imageFilePath != null) {
                    setImageToView(toView, transitionData.imageFilePath);
                }

                int[] screenLocation = new int[2];
                toView.getLocationOnScreen(screenLocation);

                moveData.leftDelta = transitionData.thumbnailLeft - screenLocation[0];
                moveData.topDelta = transitionData.thumbnailTop - screenLocation[1];

                moveData.widthScale = (float) transitionData.thumbnailWidth / toView.getWidth();
                moveData.heightScale = (float) transitionData.thumbnailHeight / toView.getHeight();

                toView.setLeft(from.getLeft());
                toView.setRight(from.getRight());
                toView.setTop(from.getTop());
                toView.setBottom(from.getBottom());
                toView.setAlpha(moveData.alpha[0]);
                runEnterAnimation(moveData, transition.getInterpolator(), true);

            }
        });
        return moveData;
    }

    private static void runEnterAnimation(MoveData moveData, TimeInterpolator interpolator, boolean isView) {
        final View toView = moveData.toView;
        toView.setPivotX(0);
        toView.setPivotY(0);
        toView.setScaleX(moveData.widthScale);
        toView.setScaleY(moveData.heightScale);
        toView.setTranslationX(moveData.leftDelta);
        toView.setTranslationY(moveData.topDelta);
        if (isView) {
            toView.animate()
                    .setDuration(moveData.duration).
                    alpha(moveData.alpha[1]).
                    scaleX(1).scaleY(1).
                    translationX(0).translationY(0).
                    setInterpolator(interpolator);
        } else {
            toView.animate().setDuration(moveData.duration).
                    scaleX(1).scaleY(1).
                    translationX(0).translationY(0).
                    setInterpolator(interpolator);
        }
    }

    private static void setImageToView(View toView, String imageFilePath) {
        Bitmap bitmap;
        if (bitmapCache == null || (bitmap = bitmapCache.get()) == null) {
            // Cant get bitmap by static field
            bitmap = BitmapFactory.decodeFile(imageFilePath);
        } else {
            bitmapCache.clear();
        }
        if (toView instanceof ImageView) {
            final ImageView toImageView = (ImageView) toView;
            toImageView.setImageBitmap(bitmap);
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                toView.setBackground(new BitmapDrawable(toView.getResources(), bitmap));
            } else {
                toView.setBackgroundDrawable(new BitmapDrawable(toView.getResources(), bitmap));
            }
        }
    }

    public static void startExitAnimation(MoveData moveData, boolean isView, final Runnable endAction) {
        View view = moveData.toView;
        int duration = moveData.duration;
        int leftDelta = moveData.leftDelta;
        int topDelta = moveData.topDelta;
        float widthScale = moveData.widthScale;
        float heightScale = moveData.heightScale;
        if (isView) {
            view.setAlpha(moveData.alpha[1]);
            view.animate().setDuration(duration).
                    alpha(moveData.alpha[0]).
                    scaleX(widthScale).scaleY(heightScale).
                    translationX(leftDelta).translationY(topDelta);
        } else {
            view.animate().setDuration(duration).
                    scaleX(widthScale).scaleY(heightScale).
                    translationX(leftDelta).translationY(topDelta);
        }

        if (endAction != null) {
            view.postDelayed(endAction, duration);
        }
    }
}
