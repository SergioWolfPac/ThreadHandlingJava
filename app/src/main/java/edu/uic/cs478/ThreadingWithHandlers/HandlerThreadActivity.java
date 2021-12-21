/**********************************************
 * Sergio Ruelas                              *
 * sruela2@uic.edu                            *
 *                                            *
 * August 2021                                *
 * University of Illinois at Chicago          *
 * CS 478                                     *
 * Project 4                                  *
 *                                            *
 *  Too much time was spent trying to         *
 *   dynamically add buttons programmatically *
 *   in the end hard writing all 100 buttons  *
 *   was the chosen solution and much         *
 *   of the logic implementation is incomplete*
 *   Currently, guess by guess works          *
 *     but no UI elements are updated         *
 *     Continuous does not work and only      *
 *     toasts where the gopher is             *
 *                                            *
 *      message handlers are missing as well  *
 *********************************************/


package edu.uic.cs478.ThreadingWithHandlers;

// import edu.uic.cs478.YourName.R;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HandlerThreadActivity extends Activity  {

	private final Handler mHandler = new Handler(Looper.getMainLooper()) ; // Handler is created by UI Thread and associated with it
	private ImageView mImageView ;
	private ProgressBar mProgressBar ;
	private Bitmap mBitmap ;
	private final Boolean mBitmapLock = false ;
	int move = 1; //used to keep track of switching threads
	int t1Guess = 0;
	int t2Guess;
	int rNum;



	GridView mBoardView;
	Button b2;
	//Button boardButtons[] = new Button[100];


	public HandlerThreadActivity() {
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Button mButton1 = findViewById(R.id.button1x);
		Button mButton2 = findViewById(R.id.button2y);

		try {
			mBoardView = findViewById(R.id.boardLayout);
		}
		catch(Exception e) {
			Log.e("findViewError", "Null see notes in code");
			/* Attempts were made to add buttons here but null was being returned by
			findViewId for the gridView named boardView in the xml files
			no other solution was found. Attempted to use onPostCreate but documentation is not
			well written for this function and was not being called throughout the program/ever
			 */
		}
		//generate random gopher location
		Random r = new Random();
		rNum = r.nextInt(99); //number between 0 and 99
		Log.e("Gopher is at location: ", Integer.toString(rNum));


        
        mButton1.setOnClickListener(new View.OnClickListener() { //this is the guess by guess thread
        	       public void onClick(View v) {

        	    	        Thread t1 = new Thread(new ReadPageRunnable()) ;   	
        	    	        t1.start();
        	    	        //checkForWin(t1Guess);
					   		//move++;
        	    	        Thread t2 = new Thread(new ReadPageRunnable2());
        	    	        t2.start();
        	    	       // move++;
        	    	        //checkForWin(t2Guess);
        	       }
        });
        
        mButton2.setOnClickListener(new View.OnClickListener() { //this is the continuous thread
 	           public void onClick(View v) {
 	    	           Toast.makeText(HandlerThreadActivity.this, "Gopher is at location " + Integer.toString(rNum), Toast.LENGTH_SHORT).show() ;
 	          }
        });

    }

    public boolean checkForWin(int guess){

		return guess == rNum;
	}


	// This code runs in background thread in parallel with UI thread
    public class ReadPageRunnable implements Runnable  {	

		public void run() {
			//note odd and first move goes to Thread t1
			// even goes to Thread t2
			synchronized(this) {
				Log.e("move is ", Integer.toString(move));
				if (move % 2 != 0) { //odd t1 move
					//thread 1 will implement an incremental approach going up then starting back at 0 once at 99
					mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(HandlerThreadActivity.this, "This is thread 1 move", Toast.LENGTH_SHORT).show();
							t1Guess++;
							Log.e("t1 guess is", Integer.toString(t1Guess));
							if(t1Guess > 99){
								Log.e("Unexpected thread 1 guess value", "value exceeds 99");
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(checkForWin(t1Guess)){
								Log.e("Win found", "t1");
							}
							//move++;
						}
					}); //end mHandler.post
				} else { //t2 move
					//thread 2 will implement a fully random approach
					mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(HandlerThreadActivity.this, "This is thread 2 move", Toast.LENGTH_SHORT).show();
							Random r = new Random();
							t2Guess = r.nextInt(99);
							Log.e("t2 guess is", Integer.toString(t2Guess));
							if(t2Guess > 99){
								Log.e("Unexpected thread 2 guess value", "value exceeds 99");
							}

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(checkForWin(t2Guess)){
								Log.e("Win found", "t2");

							}
							//move++;
						}
					}); //end mHandler.post

				}
				//move++;
			}//end synchronization lock

			move++;
		}
		//move++;
	}//end runnable

	// This code runs in background thread in parallel with UI thread
	public class ReadPageRunnable2 implements Runnable  {

		public void run() {
			//note odd and first move goes to Thread t1
			// even goes to Thread t2
			synchronized(this) {
				Log.e("move is ", Integer.toString(move));
				if (move % 2 != 0) { //odd t1 move
					//thread 1 will implement an incremental approach going up then starting back at 0 once at 99
					mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(HandlerThreadActivity.this, "This is thread 1 move", Toast.LENGTH_SHORT).show();
							t1Guess++;
							Log.e("t1 guess is", Integer.toString(t1Guess));
							if(t1Guess > 99){
								Log.e("Unexpected thread 1 guess value", "value exceeds 99");
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(checkForWin(t1Guess)){
								Log.e("Win found", "t1");
							}
								//move++;
						}
					}); //end mHandler.post
				} else { //t2 move
					//thread 2 will implement a fully random approach
					mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(HandlerThreadActivity.this, "This is thread 2 move", Toast.LENGTH_SHORT).show();
							Random r = new Random();
							t2Guess = r.nextInt(99);
							Log.e("t2 guess is", Integer.toString(t2Guess));
							if(t2Guess > 99){
								Log.e("Unexpected thread 2 guess value", "value exceeds 99");
							}

							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(checkForWin(t2Guess)){
								Log.e("Win found", "t2");

							}
								//move++;
						}
					}); //end mHandler.post

				}
					//move++;
			}//end synchronization lock
			move++;
		}
	//	move++;
	}//end runnable

}

    

