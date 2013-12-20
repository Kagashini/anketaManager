package ru.develop.anketamanager.utils;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;

public class GifMovieView extends View {

    private Movie mMovie;

    private long mMoviestart;
    
    private FrameLayout parent; 

   
    int parent_width;
    int parent_height;
    @SuppressLint("NewApi")
	View.OnLayoutChangeListener event=
    new View.OnLayoutChangeListener() {
		
		@Override
		public void onLayoutChange(View v, int left, int top, int right,
				int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) 
		{
			parent_width = parent.getWidth();
			parent_height = parent.getHeight();
		}
	};
    
	public GifMovieView(InputStream stream,FrameLayout p_) {
        super(p_.getContext());      
        parent = p_;
        mMovie = Movie.decodeStream(stream);
        parent.addOnLayoutChangeListener(event);
    }
	
	boolean _visible;
	public boolean getVisible()
	{
		return  _visible;
	}

	public void setVisible(boolean value)
	{
		_visible = value;
	}
	
    @Override
    protected void onDraw(Canvas canvas) {
    	
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        if(!_visible) 
        	return;
        final long now = SystemClock.uptimeMillis();

        if (mMoviestart == 0) { 
            mMoviestart = now;
        }
        
        final int relTime = (int)((now - mMoviestart) % mMovie.duration());        
        mMovie.setTime(relTime);
        mMovie.draw(canvas, (parent_width-mMovie.width())/2, (parent_height-mMovie.height())/2);
        //mMovie.draw(canvas,10, 10);
        this.invalidate();
    }
}