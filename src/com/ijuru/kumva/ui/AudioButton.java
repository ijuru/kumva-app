/**
 * Copyright 2011 Rowan Seymour
 * 
 * This file is part of Kumva.
 *
 * Kumva is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kumva is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kumva. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ijuru.kumva.ui;

import com.ijuru.kumva.R;
import com.ijuru.kumva.util.Dialogs;
import com.ijuru.kumva.util.Utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

/**
 * Custom button for playing definition audio clips
 */
public class AudioButton extends Button implements View.OnClickListener {

	private String audioURL;
	private Drawable playIcon, pauseIcon;
	private AnimationDrawable animLoading;
	private MediaPlayer player;
	
	/**
	 * Creates a new audio button
	 * @param context the context
	 * @param attrs the attributes
	 */
	public AudioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		playIcon = getResources().getDrawable(R.drawable.ic_listen);
		pauseIcon = getResources().getDrawable(R.drawable.ic_pause);
		animLoading = (AnimationDrawable)getResources().getDrawable(R.drawable.am_loading);
		
		setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		setCompoundDrawablePadding(Utils.pixels(getContext(), 4));
		setCompoundDrawablesWithIntrinsicBounds(playIcon, null, null, null);
		
		setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// If audio is playing then this is a pause button
		if (player.isPlaying()) {
			player.stop();
			setCompoundDrawablesWithIntrinsicBounds(playIcon, null, null, null);
		}
		else {
			// Set animated throbber
			setCompoundDrawablesWithIntrinsicBounds(animLoading, null, null, null);
			setEnabled(false);
					
			// Start animation
			animLoading.setOneShot(false);
			animLoading.start();
					
			try {
				// Reset player and connect to audio URL
				player.reset();
				player.setDataSource(audioURL);
				player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {	
					@Override
					public void onPrepared(MediaPlayer arg0) {
						// Switch to pause icon
						animLoading.stop();
						setEnabled(true);
						setCompoundDrawablesWithIntrinsicBounds(pauseIcon, null, null, null);
						
						// Play audio
						player.start();
					}
				});
				player.setOnErrorListener(new MediaPlayer.OnErrorListener() {		
					@Override
					public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
						animLoading.stop();
						setEnabled(true);
						setCompoundDrawablesWithIntrinsicBounds(playIcon, null, null, null);
						Dialogs.toast(AudioButton.this.getContext(), getContext().getString(R.string.err_communicationfailed));
						return true;
					}
				});
				player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {	
					@Override
					public void onCompletion(MediaPlayer arg0) {
						setCompoundDrawablesWithIntrinsicBounds(playIcon, null, null, null);
					}
				});
				player.prepareAsync();
			} catch (Exception e) {
				Dialogs.toast(AudioButton.this.getContext(), getContext().getString(R.string.err_communicationfailed));
			}
		}
	}
	
	/**
	 * Sets the media player
	 * @param player the media player
	 */
	public void setMediaPlayer(MediaPlayer player) {
		this.player = player;
	}
	
	/**
	 * Gets the audio URL string
	 * @return the audio URL string
	 */
	public String getAudioURL() {
		return audioURL;
	}
	
	/**
	 * Sets the audio URL string
	 * @param audioURL the audio URL string
	 */
	public void setAudioURL(String audioURL) {
		this.audioURL = audioURL;
	}
}
