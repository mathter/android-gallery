package biz.ostw.android.gallery.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Movie;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;

public class MovieDrawable extends Drawable {

    private Movie movie;

    private int wigth;

    private int height;

    private long start;

    private long duration;

    public MovieDrawable(InputStream is) {
        try {
            this.movie = Movie.decodeStream(is);

            this.wigth = this.movie.width();
            this.height = this.movie.height();
            this.start = 0;
            this.duration = this.movie.duration();
        } catch (Exception e) {

        }
    }

    public MovieDrawable(Context context, int resId) {
        final InputStream is = context.getResources().openRawResource(resId);
        try {
            this.movie = Movie.decodeStream(is);

            this.wigth = this.movie.width();
            this.height = this.movie.height();
            this.start = 0;
            this.duration = this.movie.duration();
        } catch (Exception e) {

        }
    }

    @Override
    public int getIntrinsicWidth() {
        return this.wigth;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.height;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        final long now = SystemClock.uptimeMillis();
        if (this.start == 0) {
            this.start = now;
        }

        final int relTime = (int) ((now - this.start) % this.duration);
        this.movie.setTime(relTime);
        this.movie.draw(canvas, 0, 0);

        this.invalidateSelf();
    }


    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
