package com.bigdicegames.carswithguns.core.modes.loading;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;
import static forplay.core.ForPlay.log;

import java.util.ArrayList;

import com.bigdicegames.carswithguns.core.engine.AbstractCompletionCallback;
import com.bigdicegames.carswithguns.core.engine.CompletionCallback;
import com.bigdicegames.carswithguns.core.engine.GameMode;
import com.bigdicegames.carswithguns.core.engine.ModeMgr;

import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.ResourceCallback;

public class ModeLoading implements GameMode {
	private static final float MINIMUM_LIFETIME = 1.0f;
	private ArrayList<String> assetNameList;
	private int loadedCount;
	private int errorCount;
	private float elapsedDuration;
	private Image titleImage;
	private ImageLayer titleLayer;
	private CompletionCallback completionCallback;
	private boolean imageLoaded;
	private float fractionLoaded;

	public ModeLoading(ArrayList<String> assetNames) {
		this.assetNameList = assetNames;
		imageLoaded = false;
		
		titleImage = assetManager().getImage("images/title.png");
		titleImage.addCallback(new ResourceCallback<Image>() {

			@Override
			public void done(Image resource) {
				imageLoaded = true;
			}

			@Override
			public void error(Throwable err) {
				// TODO Auto-generated method stub
				
			}});
		
		titleLayer = graphics().createImageLayer(titleImage);
	}

	@Override
	public void onUpdate(float deltaSeconds) {
		elapsedDuration += deltaSeconds;
		maybeFinish();
	}

	@Override
	public void onPushed() {
		graphics().rootLayer().add(titleLayer);
		loadedCount = 0;
		errorCount = 0;
		elapsedDuration = 0.0f;
		
		for (String assetName : assetNameList) {
			Image image = assetManager().getImage(assetName);
			
			image.addCallback(new ResourceCallback<Image>() {

				@Override
				  public void done(Image image) {
				    loadedCount += 1;
				    
				    maybeFinish();
				  }

			      @Override
			      public void error(Throwable err) {
			        log().error("Error loading image!", err);
			        errorCount += 1;
			        
			        maybeFinish();
			      }
			    });
			
		}
	}

	protected void maybeFinish() {
		fractionLoaded = (float)loadedCount / assetNameList.size();
		
		if ((loadedCount + errorCount == assetNameList.size()) &&
			(elapsedDuration > MINIMUM_LIFETIME)) {
			completionCallback.onComplete();
		}
	}

	@Override
	public void onPopped() {
		graphics().rootLayer().remove(titleLayer);
	}

	@Override
	public void onPointerStart(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPointerEnd(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMove(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseWheelScroll(float velocity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(float alpha) {
		// TODO(davelecompte): make this update with the fraction loaded
		if (!imageLoaded) {
			
		}
	}

	@Override
	public void onKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void setCallback(CompletionCallback callback) {
		this.completionCallback = callback;
	}

}
