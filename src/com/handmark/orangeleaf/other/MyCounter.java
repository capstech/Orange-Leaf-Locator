package com.handmark.orangeleaf.other;

import android.os.CountDownTimer;
import com.handmark.orangeleaf.twitterActivity;


public class MyCounter extends CountDownTimer{
	
	public MyCounter(long millisInFuture, long countDownInterval) 
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			/* Make new one, to I can force end it */
			twitterActivity tweetAct =  new twitterActivity();
			tweetAct.endMe();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			/* todo: unfinished */
			/* never */
			
		}
}