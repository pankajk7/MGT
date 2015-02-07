package com.mygrouptracker.mygrouptracker.activity;

import java.io.FileOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mygrouptracker.mygrouptracker.R;
import com.mygrouptracker.mygrouptracker.data.DataEngine;
import com.mygrouptracker.mygrouptracker.entity.BackgroundTaskWithoutLoading;
import com.mygrouptracker.mygrouptracker.entity.LoginUser;
import com.mygrouptracker.mygrouptracker.utility.BackgroundNetwork;
import com.mygrouptracker.mygrouptracker.utility.Banners;

import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class BaseClass extends Activity {

	TextView title;
	Button helpButton;
	Button backButton;
	TextView staticBannerTextView;
	TextView dynamicBannerTextView;

	public static ArrayList<Banners> staticBannersArrayList;
	public static ArrayList<Banners> dynamicBannersArrayList;
	Boolean isStatic = true;
	LinearLayout staticLinearLayout;
	LinearLayout dynamicLinearLayout;
	public static int count;
	public static InputStream staticInputStream;
	public static List<InputStream> dynamicInputStream;
	public int currentLink = 0;

	static ArrayList<Drawable> topBannerImages = new ArrayList<Drawable>();
	static ArrayList<Drawable> bottomBannerImages = new ArrayList<Drawable>();

	AnimationDrawable animationDrawableTop;
	AnimationDrawable animationDrawableBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.base_layout);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.window_title);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		title = (TextView) findViewById(R.id.title);
		helpButton = (Button) findViewById(R.id.button_baselayout_help);
		backButton = (Button) findViewById(R.id.btn_baselayout_backbutton);
		staticBannerTextView = (TextView) findViewById(R.id.textview_baseclass_staticad);
		dynamicBannerTextView = (TextView) findViewById(R.id.textview_baseclass_dynamicad);

		staticLinearLayout = (LinearLayout) findViewById(R.id.layout_baseclass_staticlayout);
		dynamicLinearLayout = (LinearLayout) findViewById(R.id.layout_baseclass_dynamiclayout);

		// Drawable d1 = getResources().getDrawable(R.drawable.logo);
		// staticLinearLayout.setBackgroundDrawable(d1);
		// dynamicLinearLayout.setBackgroundDrawable(d1);

		if (staticBannersArrayList == null || dynamicBannersArrayList == null) {
			isStatic = true;
			getStaticBanners();
			getDynamicBanners();
		} else {
			setImagesForAdsAnimation();
			setImagesForAdsAnimationForBottom();
		}

		helpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					String url = "http://mygrouptrackerapp.com/index.html";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				} catch (Exception er) {
					System.out.print("" + er.toString());
				}

			}
		});

		staticLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					// String url = objStaticBanners.link.toString();
					// if (!url.startsWith("http://")
					// && !url.startsWith("https://"))
					// url = "http://" + url;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("http://mygrouptracker.com/img/logo.png"));
					startActivity(browserIntent);
				} catch (Exception e) {
					Log.e("Exception>>>", e.toString());
				}

			}
		});

		dynamicLinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("http://mygrouptracker.com/img/logo.png"));
					// .parse(dynamicBannersArrayList.get(currentLink).link));

					startActivity(browserIntent);
				} catch (Exception ex) {
					Log.e("Exception>>>", ex.toString());
					System.out.print(">>>getBanner_Exception>>" + ex.toString()
							+ ">>>" + ex.getMessage());
				}

			}
		});
	}

	public void getStaticBanners() {
		staticBannersArrayList = new ArrayList<Banners>();
		try {
			new BackgroundTaskWithoutLoading(BaseClass.this) {
				protected Void doInBackground(Void[] params) {
					try {
						DataEngine objDataEngine = new DataEngine(
								BaseClass.this);
						staticBannersArrayList = objDataEngine
								.getBannersList(true);
					} catch (Exception ex) {
						Log.e(">>> Exception at loading banner images >>> ",
								ex.toString());
					}
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					try {
						for (int i = 0; i < staticBannersArrayList.size(); i++) {
							final Banners objStaticBanners = staticBannersArrayList
									.get(i);
							new BackgroundTaskWithoutLoading(BaseClass.this) {
								protected Void doInBackground(Void[] params) {
									try {
										Drawable drawable = Drawable
												.createFromStream(
														((java.io.InputStream) new java.net.URL(
																objStaticBanners.link)
																.getContent()),
														"tempsrc");
										topBannerImages.add(drawable);
									} catch (Exception e) {
										System.out.println(">>> Exception >>> "
												+ e + " >>> Message >>> "
												+ e.getMessage());
									}
									return null;
								};

								@SuppressWarnings("deprecation")
								protected void onPostExecute(Void result) {
									super.onPostExecute(result);

									try {
										if (animationDrawableTop == null) {
											setImagesForAdsAnimation();
										}
									} catch (Exception e) {

									}
								};
							}.execute();
						}
					} catch (Exception ex) {
						Log.e(">>> Exception at loading banner images >>> ",
								ex.toString());
					}
				};
			}.execute();
		} catch (Exception e) {

		}

	}

	public void getDynamicBanners() {
		dynamicBannersArrayList = new ArrayList<Banners>();
		try {
			new BackgroundTaskWithoutLoading(BaseClass.this) {
				protected Void doInBackground(Void[] params) {
					try {
						DataEngine objDataEngine = new DataEngine(
								BaseClass.this);
						dynamicBannersArrayList = objDataEngine
								.getBannersList(false);
					} catch (Exception e) {
						System.out.println(">>> Exception >>> " + e
								+ " >>> Message >>> " + e.getMessage());
					}
					return null;
				};

				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					try {

						new BackgroundTaskWithoutLoading(BaseClass.this) {
							protected Void doInBackground(Void[] params) {
								try {
									for (int i = 0; i < dynamicBannersArrayList
											.size(); i++) {
										final Banners objStaticBanners = dynamicBannersArrayList
												.get(i);
										System.out.println(">>>Link>>>"
												+ objStaticBanners.link);
										Drawable drawable = Drawable
												.createFromStream(
														((java.io.InputStream) new java.net.URL(
																objStaticBanners.link)
																.getContent()),
														"tempsrc" + i);
										bottomBannerImages.add(drawable);
									}
								} catch (Exception e) {
									System.out.println(">>> Exception >>> " + e
											+ " >>> Message >>> "
											+ e.getMessage());
								}
								return null;
							};

							@SuppressWarnings("deprecation")
							protected void onPostExecute(Void result) {
								super.onPostExecute(result);

								try {
									if (animationDrawableBottom == null) {
										setImagesForAdsAnimationForBottom();
									}
								} catch (Exception e) {

								}
							};
						}.execute();

					} catch (Exception ex) {
						Log.e(">>> Exception at loading banner images >>> ",
								ex.toString());
					}
				};
			}.execute();
		} catch (Exception e) {

		}
	}

	void setImagesForAdsAnimation() {
		try {
			animationDrawableTop = null;
			animationDrawableTop = new AnimationDrawable();

			for (int i = 0; i < topBannerImages.size(); i++) {
				// BitmapDrawable frame1 = (BitmapDrawable)
				// topBannerImages.get(i);
				// animationDrawableTop.addFrame(frame1, 1000);
				animationDrawableTop.addFrame(topBannerImages.get(i), 1200);
			}

			animationDrawableTop.setOneShot(false);

			staticLinearLayout.setBackgroundDrawable(animationDrawableTop);

			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					animationDrawableTop.start();

				}
			}, 500);

		} catch (Exception e) {

		}
	}

	void setImagesForAdsAnimationForBottom() {
		// ImageView iv1 = (ImageView) findViewById(R.id.imageView1);

		try {
			animationDrawableBottom = null;
			animationDrawableBottom = new AnimationDrawable();
			final int size = bottomBannerImages.size();

			for (int i = 0; i < bottomBannerImages.size(); i++) {
				animationDrawableBottom.addFrame(bottomBannerImages.get(i),
						1200);
			}

			animationDrawableBottom.setOneShot(false);
			dynamicLinearLayout.setBackgroundDrawable(animationDrawableBottom);

			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					animationDrawableBottom.start();

				}
			}, 500);

		} catch (Exception e) {

		}
	}
}
