package com.handmark.orangeleaf.other;


import android.os.CountDownTimer;

import com.handmark.orangeleaf.storeActivity;


public class MyCounterStore extends CountDownTimer{
	
	public MyCounterStore(long millisInFuture, long countDownInterval) 
		{
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			/* Make new one, to I can force end it */
			storeActivity storeAct =  new storeActivity();
			storeAct.endMe();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			/* todo: unfinished */
			/* never */
			
		}
}