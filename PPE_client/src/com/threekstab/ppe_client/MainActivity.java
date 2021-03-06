package com.threekstab.ppe_client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private VideoView videoView;
	private WebView wv;
	private String link = "rtsp://192.168.43.64:8554/stream.sdp";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
		/*videoView =(VideoView)findViewById(R.id.videoView1);
		MediaController mediaController= new MediaController(this);
	    mediaController.setAnchorView(videoView);
	    videoView.setMediaController(mediaController);
	    initVideo();*/
		wv = (WebView) findViewById(R.id.webView1);
		initWebBrowser();	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/* test passing argument from other activity */
		String data = "no data received yet";
		/*if(getIntent()!=null)
			if(getIntent().getExtras()!=null)
				data = getIntent().getExtras().getString("keyName");*/
		
		/* toast for test */
		Context context = getApplicationContext();
		CharSequence text = "Activity received : "+data;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void addListenerOnButton(){
		
		final Context context = this;
		
		final Button button = (Button) findViewById(R.id.button1);
		//final Button button2 = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,MenuActivity.class);
				intent.putExtra("keyName","value");
				startActivity(intent);
			}
			
		});
		/*button2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				EditText et = (EditText) findViewById(R.id.editText1);
				MainActivity.this.link = et.getText().toString();
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText(MainActivity.this.link);
				initVideo();
			}
			
		});*/
	}
	
	private void initVideo()
	{
		

	    // Uri uri = 	Uri.parse("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
	     //Uri uri = Uri.parse("rtsp://"+ipServer+":"+port+"/stream.sdp");
	    //Uri uri = Uri.parse("rtp://@:5004");
	    PopupWindow popup = new PopupWindow(this);
	    popup.showAtLocation(new LinearLayout(this), Gravity.BOTTOM, 50, 50);
	    popup.update(50,50,300,80);
	    Uri uri = Uri.parse(link);

	    
	    videoView.setVideoURI(uri);
	    videoView.requestFocus();
	    videoView.start();
	}
	
	private void initWebBrowser(){
		 //getWindow().requestFeature(Window.FEATURE_PROGRESS);

		 wv.getSettings().setJavaScriptEnabled(true);

		 final Activity activity = this;
		 wv.setWebChromeClient(new WebChromeClient() {
		   public void onProgressChanged(WebView view, int progress) {
		     // Activities and WebViews measure progress with different scales.
		     // The progress meter will automatically disappear when we reach 100%
		     activity.setProgress(progress * 1000);
		   }
		 });
		 wv.setWebViewClient(new WebViewClient() {
		   public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		     Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
		   }
		 });

		 //wv.loadUrl(getResources().openRawResource(R.raw.streamexample));
		 //wv.loadData(readTextFromResource(R.raw.streamexamplestandalone), "text/html", "utf-8");
		 
		 String cnt = "<!DOCTYPE html> <html> <head> 	<meta name=\"viewport\" content=\"width=320, initial-scale=1\"/> 	<title>jsmpeg streaming</title> 	<style type=\"text/css\"> 		body { 			background: #333; 			text-align: center; 			margin-top: 10%; 		}	 	</style> </head> <body>		 	<canvas id=\"videoCanvas\" width=\"640\" height=\"480\"> 		<p> 			Please use a browser that supports the Canvas Element, like 			<a href=\"http://www.google.com/chrome\">Chrome</a>, 			<a href=\"http://www.mozilla.com/firefox/\">Firefox</a>, 			<a href=\"http://www.apple.com/safari/\">Safari</a> or Internet Explorer 10 		</p> 	</canvas> 	<script type=\"text/javascript\" > 	(function(e){\"use strict\";var t=function(){return e.requestAnimationFrame||e.webkitRequestAnimationFrame||e.mozRequestAnimationFrame||function(t){e.setTimeout(t,1e3/60)}}();var n=e.jsmpeg=function(e,t){t=t||{};this.benchmark=!!t.benchmark;this.canvas=t.canvas||document.createElement(\"canvas\");this.autoplay=!!t.autoplay;this.loop=!!t.loop;this.externalLoadCallback=t.onload||null;this.externalDecodeCallback=t.ondecodeframe||null;this.bwFilter=t.bwFilter||false;this.customIntraQuantMatrix=new Uint8Array(64);this.customNonIntraQuantMatrix=new Uint8Array(64);this.blockData=new Int32Array(64);this.canvasContext=this.canvas.getContext(\"2d\");if(e instanceof WebSocket){this.client=e;this.client.onopen=this.initSocketClient.bind(this)}else{this.load(e)}};n.prototype.waitForIntraFrame=true;n.prototype.socketBufferSize=512*1024;n.prototype.onlostconnection=null;n.prototype.initSocketClient=function(e){this.buffer=new O(new ArrayBuffer(this.socketBufferSize));this.nextPictureBuffer=new O(new ArrayBuffer(this.socketBufferSize));this.nextPictureBuffer.writePos=0;this.nextPictureBuffer.chunkBegin=0;this.nextPictureBuffer.lastWriteBeforeWrap=0;this.client.binaryType=\"arraybuffer\";this.client.onmessage=this.receiveSocketMessage.bind(this)};n.prototype.decodeSocketHeader=function(e){if(e[0]==r.charCodeAt(0)&&e[1]==r.charCodeAt(1)&&e[2]==r.charCodeAt(2)&&e[3]==r.charCodeAt(3)){this.width=e[4]*256+e[5];this.height=e[6]*256+e[7];this.initBuffers()}};n.prototype.receiveSocketMessage=function(e){var n=new Uint8Array(e.data);if(!this.sequenceStarted){this.decodeSocketHeader(n)}var r=this.buffer;var s=this.nextPictureBuffer;if(s.writePos+n.length>s.length){s.lastWriteBeforeWrap=s.writePos;s.writePos=0;s.index=0}s.bytes.set(n,s.writePos);s.writePos+=n.length;var o=0;while(true){o=s.findNextMPEGStartCode();if(o==O.NOT_FOUND||s.index>>3>s.writePos){s.index=Math.max(s.writePos-3,0)<<3;return}else if(o==C){break}}if(this.waitForIntraFrame){s.advance(10);if(s.getBits(3)==b){this.waitForIntraFrame=false;s.chunkBegin=s.index-13>>3}return}if(!this.currentPictureDecoded){this.decodePicture(i)}var u=s.index>>3;if(u>s.chunkBegin){r.bytes.set(s.bytes.subarray(s.chunkBegin,u));r.writePos=u-s.chunkBegin}else{r.bytes.set(s.bytes.subarray(s.chunkBegin,s.lastWriteBeforeWrap));var a=s.lastWriteBeforeWrap-s.chunkBegin;r.bytes.set(s.bytes.subarray(0,u),a);r.writePos=u+a}r.index=0;s.chunkBegin=u;this.currentPictureDecoded=false;t(this.scheduleDecoding.bind(this),this.canvas)};n.prototype.scheduleDecoding=function(){this.decodePicture();this.currentPictureDecoded=true};n.prototype.isRecording=false;n.prototype.recorderWaitForIntraFrame=false;n.prototype.recordedFrames=0;n.prototype.recordedSize=0;n.prototype.didStartRecordingCallback=null;n.prototype.recordBuffers=[];n.prototype.canRecord=function(){return this.client&&this.client.readyState==this.client.OPEN};n.prototype.startRecording=function(e){if(!this.canRecord()){return}this.discardRecordBuffers();this.isRecording=true;this.recorderWaitForIntraFrame=true;this.didStartRecordingCallback=e||null;this.recordedFrames=0;this.recordedSize=0;var t=this.width>>4,n=(this.width&15)<<4|this.height>>8,r=this.height&255;this.recordBuffers.push(new Uint8Array([0,0,1,179,t,n,r,19,255,255,225,88,0,0,1,184,0,8,0,0,0,0,1,0]))};n.prototype.recordFrameFromCurrentBuffer=function(){if(!this.isRecording){return}if(this.recorderWaitForIntraFrame){if(this.pictureCodingType!=b){return}this.recorderWaitForIntraFrame=false;if(this.didStartRecordingCallback){this.didStartRecordingCallback(this)}}this.recordedFrames++;this.recordedSize+=this.buffer.writePos;this.recordBuffers.push(new Uint8Array(this.buffer.bytes.subarray(0,this.buffer.writePos)))};n.prototype.discardRecordBuffers=function(){this.recordBuffers=[];this.recordedFrames=0};n.prototype.stopRecording=function(){var e=new Blob(this.recordBuffers,{type:\"video/mpeg\"});this.discardRecordBuffers();this.isRecording=false;return e};n.prototype.load=function(e){this.url=e;var t=new XMLHttpRequest;var n=this;t.onreadystatechange=function(){if(t.readyState==t.DONE&&t.status==200){n.loadCallback(t.response)}};t.onprogress=this.updateLoader.bind(this);t.open(\"GET\",e);t.responseType=\"arraybuffer\";t.send()};n.prototype.updateLoader=function(e){var t=e.loaded/e.total,n=this.canvas.width,r=this.canvas.height,i=this.canvasContext;i.fillStyle=\"#222\";i.fillRect(0,0,n,r);i.fillStyle=\"#fff\";i.fillRect(0,r-r*t,n,r*t)};n.prototype.loadCallback=function(e){var t=Date.now();this.buffer=new O(e);this.findStartCode(x);this.firstSequenceHeader=this.buffer.index;this.decodeSequenceHeader();this.nextFrame();if(this.autoplay){this.play()}if(this.externalLoadCallback){this.externalLoadCallback(this)}};n.prototype.play=function(e){if(this.playing){return}this.targetTime=Date.now();this.playing=true;this.scheduleNextFrame()};n.prototype.pause=function(e){this.playing=false};n.prototype.stop=function(e){if(this.buffer){this.buffer.index=this.firstSequenceHeader}this.playing=false;if(this.client){this.client.close();this.client=null}};n.prototype.readCode=function(e){var t=0;do{t=e[t+this.buffer.getBits(1)]}while(t>=0&&e[t]!=0);return e[t+2]};n.prototype.findStartCode=function(e){var t=0;while(true){t=this.buffer.findNextMPEGStartCode();if(t==e||t==O.NOT_FOUND){return t}}return O.NOT_FOUND};n.prototype.fillArray=function(e,t){for(var n=0,r=e.length;n<r;n++){e[n]=t}};n.prototype.pictureRate=30;n.prototype.lateTime=0;n.prototype.firstSequenceHeader=0;n.prototype.targetTime=0;n.prototype.nextFrame=function(){if(!this.buffer){return}while(true){var e=this.buffer.findNextMPEGStartCode();if(e==x){this.decodeSequenceHeader()}else if(e==C){if(this.playing){this.scheduleNextFrame()}this.decodePicture();return this.canvas}else if(e==O.NOT_FOUND){this.stop();if(this.loop&&this.sequenceStarted){this.play()}return null}else{}}};n.prototype.scheduleNextFrame=function(){this.lateTime=Date.now()-this.targetTime;var e=Math.max(0,1e3/this.pictureRate-this.lateTime);this.targetTime=Date.now()+e;if(this.benchmark){var t=Date.now();if(!this.benchframe){this.benchstart=t;this.benchframe=0}this.benchframe++;var n=t-this.benchstart;if(this.benchframe>=100){this.benchfps=this.benchframe/n*1e3;if(console){console.log(\"frames per second: \"+this.benchfps)}this.benchframe=null}setTimeout(this.nextFrame.bind(this),0)}else if(e<18){this.scheduleAnimation()}else{setTimeout(this.scheduleAnimation.bind(this),e)}};n.prototype.scheduleAnimation=function(){t(this.nextFrame.bind(this),this.canvas)};n.prototype.decodeSequenceHeader=function(){this.width=this.buffer.getBits(12);this.height=this.buffer.getBits(12);this.buffer.advance(4);this.pictureRate=s[this.buffer.getBits(4)];this.buffer.advance(18+1+10+1);this.initBuffers();if(this.buffer.getBits(1)){for(var e=0;e<64;e++){this.customIntraQuantMatrix[o[e]]=this.buffer.getBits(8)}this.intraQuantMatrix=this.customIntraQuantMatrix}if(this.buffer.getBits(1)){for(var e=0;e<64;e++){this.customNonIntraQuantMatrix[o[e]]=this.buffer.getBits(8)}this.nonIntraQuantMatrix=this.customNonIntraQuantMatrix}};n.prototype.initBuffers=function(){this.intraQuantMatrix=u;this.nonIntraQuantMatrix=a;this.mbWidth=this.width+15>>4;this.mbHeight=this.height+15>>4;this.mbSize=this.mbWidth*this.mbHeight;this.codedWidth=this.mbWidth<<4;this.codedHeight=this.mbHeight<<4;this.codedSize=this.codedWidth*this.codedHeight;this.halfWidth=this.mbWidth<<3;this.halfHeight=this.mbHeight<<3;this.quarterSize=this.codedSize>>2;if(this.sequenceStarted){return}this.sequenceStarted=true;var t=e.Uint8ClampedArray||e.Uint8Array;if(!e.Uint8ClampedArray){this.copyBlockToDestination=this.copyBlockToDestinationClamp;this.addBlockToDestination=this.addBlockToDestinationClamp}this.currentY=new t(this.codedSize);this.currentY32=new Uint32Array(this.currentY.buffer);this.currentCr=new t(this.codedSize>>2);this.currentCr32=new Uint32Array(this.currentCr.buffer);this.currentCb=new t(this.codedSize>>2);this.currentCb32=new Uint32Array(this.currentCb.buffer);this.forwardY=new t(this.codedSize);this.forwardY32=new Uint32Array(this.forwardY.buffer);this.forwardCr=new t(this.codedSize>>2);this.forwardCr32=new Uint32Array(this.forwardCr.buffer);this.forwardCb=new t(this.codedSize>>2);this.forwardCb32=new Uint32Array(this.forwardCb.buffer);this.canvas.width=this.width;this.canvas.height=this.height;this.currentRGBA=this.canvasContext.getImageData(0,0,this.width,this.height);if(this.bwFilter){this.currentRGBA32=new Uint32Array(this.currentRGBA.data.buffer)}this.fillArray(this.currentRGBA.data,255)};n.prototype.currentY=null;n.prototype.currentCr=null;n.prototype.currentCb=null;n.prototype.currentRGBA=null;n.prototype.pictureCodingType=0;n.prototype.forwardY=null;n.prototype.forwardCr=null;n.prototype.forwardCb=null;n.prototype.fullPelForward=false;n.prototype.forwardFCode=0;n.prototype.forwardRSize=0;n.prototype.forwardF=0;n.prototype.decodePicture=function(e){this.buffer.advance(10);this.pictureCodingType=this.buffer.getBits(3);this.buffer.advance(16);if(this.pictureCodingType<=0||this.pictureCodingType>=E){return}if(this.pictureCodingType==w){this.fullPelForward=this.buffer.getBits(1);this.forwardFCode=this.buffer.getBits(3);if(this.forwardFCode==0){return}this.forwardRSize=this.forwardFCode-1;this.forwardF=1<<this.forwardRSize}var t=0;do{t=this.buffer.findNextMPEGStartCode()}while(t==k||t==L);while(t>=T&&t<=N){this.decodeSlice(t&255);t=this.buffer.findNextMPEGStartCode()}this.buffer.rewind(32);this.recordFrameFromCurrentBuffer();if(e!=i){if(this.bwFilter){this.YToRGBA()}else{this.YCbCrToRGBA()}this.canvasContext.putImageData(this.currentRGBA,0,0);if(this.externalDecodeCallback){this.externalDecodeCallback(this,this.canvas)}}if(this.pictureCodingType==b||this.pictureCodingType==w){var n=this.forwardY,r=this.forwardY32,s=this.forwardCr,o=this.forwardCr32,u=this.forwardCb,a=this.forwardCb32;this.forwardY=this.currentY;this.forwardY32=this.currentY32;this.forwardCr=this.currentCr;this.forwardCr32=this.currentCr32;this.forwardCb=this.currentCb;this.forwardCb32=this.currentCb32;this.currentY=n;this.currentY32=r;this.currentCr=s;this.currentCr32=o;this.currentCb=u;this.currentCb32=a}};n.prototype.YCbCrToRGBA=function(){var e=this.currentY;var t=this.currentCb;var n=this.currentCr;var r=this.currentRGBA.data;var i=0;var s=this.codedWidth;var o=this.codedWidth+(this.codedWidth-this.width);var u=0;var a=this.halfWidth-(this.width>>1);var f=0;var l=this.width*4;var c=this.width*4;var h=this.width>>1;var p=this.height>>1;var d,v,m,g,y,b;for(var w=0;w<p;w++){for(var E=0;E<h;E++){v=t[u];m=n[u];u++;g=m+(m*103>>8)-179;y=(v*88>>8)-44+(m*183>>8)-91;b=v+(v*198>>8)-227;d=e[i++];r[f]=d+g;r[f+1]=d-y;r[f+2]=d+b;f+=4;d=e[i++];r[f]=d+g;r[f+1]=d-y;r[f+2]=d+b;f+=4;d=e[s++];r[l]=d+g;r[l+1]=d-y;r[l+2]=d+b;l+=4;d=e[s++];r[l]=d+g;r[l+1]=d-y;r[l+2]=d+b;l+=4}i+=o;s+=o;f+=c;l+=c;u+=a}};n.prototype.YToRGBA=function(){var e=this.currentY;var t=this.currentRGBA32;var n=0;var r=this.codedWidth-this.width;var i=0;var s=this.width;var o=this.height;var u;for(var a=0;a<o;a++){for(var f=0;f<s;f++){u=e[n++];t[i++]=4278190080|u<<16|u<<8|u}n+=r}};n.prototype.quantizerScale=0;n.prototype.sliceBegin=false;n.prototype.decodeSlice=function(e){this.sliceBegin=true;this.macroblockAddress=(e-1)*this.mbWidth-1;this.motionFwH=this.motionFwHPrev=0;this.motionFwV=this.motionFwVPrev=0;this.dcPredictorY=128;this.dcPredictorCr=128;this.dcPredictorCb=128;this.quantizerScale=this.buffer.getBits(5);while(this.buffer.getBits(1)){this.buffer.advance(8)}do{this.decodeMacroblock()}while(!this.buffer.nextBytesAreStartCode())};n.prototype.macroblockAddress=0;n.prototype.mbRow=0;n.prototype.mbCol=0;n.prototype.macroblockType=0;n.prototype.macroblockIntra=false;n.prototype.macroblockMotFw=false;n.prototype.motionFwH=0;n.prototype.motionFwV=0;n.prototype.motionFwHPrev=0;n.prototype.motionFwVPrev=0;n.prototype.decodeMacroblock=function(){var e=0,t=this.readCode(l);while(t==34){t=this.readCode(l)}while(t==35){e+=33;t=this.readCode(l)}e+=t;if(this.sliceBegin){this.sliceBegin=false;this.macroblockAddress+=e}else{if(this.macroblockAddress+e>=this.mbSize){return}if(e>1){this.dcPredictorY=128;this.dcPredictorCr=128;this.dcPredictorCb=128;if(this.pictureCodingType==w){this.motionFwH=this.motionFwHPrev=0;this.motionFwV=this.motionFwVPrev=0}}while(e>1){this.macroblockAddress++;this.mbRow=this.macroblockAddress/this.mbWidth|0;this.mbCol=this.macroblockAddress%this.mbWidth;this.copyMacroblock(this.motionFwH,this.motionFwV,this.forwardY,this.forwardCr,this.forwardCb);e--}this.macroblockAddress++}this.mbRow=this.macroblockAddress/this.mbWidth|0;this.mbCol=this.macroblockAddress%this.mbWidth;this.macroblockType=this.readCode(A[this.pictureCodingType]);this.macroblockIntra=this.macroblockType&1;this.macroblockMotFw=this.macroblockType&8;if((this.macroblockType&16)!=0){this.quantizerScale=this.buffer.getBits(5)}if(this.macroblockIntra){this.motionFwH=this.motionFwHPrev=0;this.motionFwV=this.motionFwVPrev=0}else{this.dcPredictorY=128;this.dcPredictorCr=128;this.dcPredictorCb=128;this.decodeMotionVectors();this.copyMacroblock(this.motionFwH,this.motionFwV,this.forwardY,this.forwardCr,this.forwardCb)}var n=(this.macroblockType&2)!=0?this.readCode(d):this.macroblockIntra?63:0;for(var r=0,i=32;r<6;r++){if((n&i)!=0){this.decodeBlock(r)}i>>=1}};n.prototype.decodeMotionVectors=function(){var e,t,n=0;if(this.macroblockMotFw){e=this.readCode(v);if(e!=0&&this.forwardF!=1){n=this.buffer.getBits(this.forwardRSize);t=(Math.abs(e)-1<<this.forwardRSize)+n+1;if(e<0){t=-t}}else{t=e}this.motionFwHPrev+=t;if(this.motionFwHPrev>(this.forwardF<<4)-1){this.motionFwHPrev-=this.forwardF<<5}else if(this.motionFwHPrev<-this.forwardF<<4){this.motionFwHPrev+=this.forwardF<<5}this.motionFwH=this.motionFwHPrev;if(this.fullPelForward){this.motionFwH<<=1}e=this.readCode(v);if(e!=0&&this.forwardF!=1){n=this.buffer.getBits(this.forwardRSize);t=(Math.abs(e)-1<<this.forwardRSize)+n+1;if(e<0){t=-t}}else{t=e}this.motionFwVPrev+=t;if(this.motionFwVPrev>(this.forwardF<<4)-1){this.motionFwVPrev-=this.forwardF<<5}else if(this.motionFwVPrev<-this.forwardF<<4){this.motionFwVPrev+=this.forwardF<<5}this.motionFwV=this.motionFwVPrev;if(this.fullPelForward){this.motionFwV<<=1}}else if(this.pictureCodingType==w){this.motionFwH=this.motionFwHPrev=0;this.motionFwV=this.motionFwVPrev=0}};n.prototype.copyMacroblock=function(e,t,n,r,i){var s,o,u,a,f,l,c,h,p;var d=this.currentY32;var v=this.currentCb32;var m=this.currentCr32;s=this.codedWidth;o=s-16;u=e>>1;a=t>>1;f=(e&1)==1;l=(t&1)==1;c=((this.mbRow<<4)+a)*s+(this.mbCol<<4)+u;h=this.mbRow*s+this.mbCol<<2;p=h+(s<<2);var g,y,b;if(f){if(l){while(h<p){g=n[c]+n[c+s];c++;for(var w=0;w<4;w++){y=n[c]+n[c+s];c++;b=g+y+2>>2&255;g=n[c]+n[c+s];c++;b|=g+y+2<<6&65280;y=n[c]+n[c+s];c++;b|=g+y+2<<14&16711680;g=n[c]+n[c+s];c++;b|=g+y+2<<22&4278190080;d[h++]=b}h+=o>>2;c+=o-1}}else{while(h<p){g=n[c++];for(var w=0;w<4;w++){y=n[c++];b=g+y+1>>1&255;g=n[c++];b|=g+y+1<<7&65280;y=n[c++];b|=g+y+1<<15&16711680;g=n[c++];b|=g+y+1<<23&4278190080;d[h++]=b}h+=o>>2;c+=o-1}}}else{if(l){while(h<p){for(var w=0;w<4;w++){b=n[c]+n[c+s]+1>>1&255;c++;b|=n[c]+n[c+s]+1<<7&65280;c++;b|=n[c]+n[c+s]+1<<15&16711680;c++;b|=n[c]+n[c+s]+1<<23&4278190080;c++;d[h++]=b}h+=o>>2;c+=o}}else{while(h<p){for(var w=0;w<4;w++){b=n[c];c++;b|=n[c]<<8;c++;b|=n[c]<<16;c++;b|=n[c]<<24;c++;d[h++]=b}h+=o>>2;c+=o}}}if(this.bwFilter){return}s=this.halfWidth;o=s-8;u=e/2>>1;a=t/2>>1;f=(e/2&1)==1;l=(t/2&1)==1;c=((this.mbRow<<3)+a)*s+(this.mbCol<<3)+u;h=this.mbRow*s+this.mbCol<<1;p=h+(s<<1);var E,S,x;var T,N,C;if(f){if(l){while(h<p){E=r[c]+r[c+s];T=i[c]+i[c+s];c++;for(var w=0;w<2;w++){S=r[c]+r[c+s];N=i[c]+i[c+s];c++;x=E+S+2>>2&255;C=T+N+2>>2&255;E=r[c]+r[c+s];T=i[c]+i[c+s];c++;x|=E+S+2<<6&65280;C|=T+N+2<<6&65280;S=r[c]+r[c+s];N=i[c]+i[c+s];c++;x|=E+S+2<<14&16711680;C|=T+N+2<<14&16711680;E=r[c]+r[c+s];T=i[c]+i[c+s];c++;x|=E+S+2<<22&4278190080;C|=T+N+2<<22&4278190080;m[h]=x;v[h]=C;h++}h+=o>>2;c+=o-1}}else{while(h<p){E=r[c];T=i[c];c++;for(var w=0;w<2;w++){S=r[c];N=i[c++];x=E+S+1>>1&255;C=T+N+1>>1&255;E=r[c];T=i[c++];x|=E+S+1<<7&65280;C|=T+N+1<<7&65280;S=r[c];N=i[c++];x|=E+S+1<<15&16711680;C|=T+N+1<<15&16711680;E=r[c];T=i[c++];x|=E+S+1<<23&4278190080;C|=T+N+1<<23&4278190080;m[h]=x;v[h]=C;h++}h+=o>>2;c+=o-1}}}else{if(l){while(h<p){for(var w=0;w<2;w++){x=r[c]+r[c+s]+1>>1&255;C=i[c]+i[c+s]+1>>1&255;c++;x|=r[c]+r[c+s]+1<<7&65280;C|=i[c]+i[c+s]+1<<7&65280;c++;x|=r[c]+r[c+s]+1<<15&16711680;C|=i[c]+i[c+s]+1<<15&16711680;c++;x|=r[c]+r[c+s]+1<<23&4278190080;C|=i[c]+i[c+s]+1<<23&4278190080;c++;m[h]=x;v[h]=C;h++}h+=o>>2;c+=o}}else{while(h<p){for(var w=0;w<2;w++){x=r[c];C=i[c];c++;x|=r[c]<<8;C|=i[c]<<8;c++;x|=r[c]<<16;C|=i[c]<<16;c++;x|=r[c]<<24;C|=i[c]<<24;c++;m[h]=x;v[h]=C;h++}h+=o>>2;c+=o}}}};n.prototype.dcPredictorY;n.prototype.dcPredictorCr;n.prototype.dcPredictorCb;n.prototype.blockData=null;n.prototype.decodeBlock=function(e){var t=0,n;this.fillArray(this.blockData,0);if(this.macroblockIntra){var r,i;if(e<4){r=this.dcPredictorY;i=this.readCode(m)}else{r=e==4?this.dcPredictorCr:this.dcPredictorCb;i=this.readCode(g)}if(i>0){var s=this.buffer.getBits(i);if((s&1<<i-1)!=0){this.blockData[0]=r+s}else{this.blockData[0]=r+(-1<<i|s+1)}}else{this.blockData[0]=r}if(e<4){this.dcPredictorY=this.blockData[0]}else if(e==4){this.dcPredictorCr=this.blockData[0]}else{this.dcPredictorCb=this.blockData[0]}this.blockData[0]<<=3+5;n=this.intraQuantMatrix;t=1}else{n=this.nonIntraQuantMatrix}var u=0;while(true){var a=0,l=this.readCode(y);if(l==1&&t>0&&this.buffer.getBits(1)==0){break}if(l==65535){a=this.buffer.getBits(6);u=this.buffer.getBits(8);if(u==0){u=this.buffer.getBits(8)}else if(u==128){u=this.buffer.getBits(8)-256}else if(u>128){u=u-256}}else{a=l>>8;u=l&255;if(this.buffer.getBits(1)){u=-u}}t+=a;var c=o[t];t++;u<<=1;if(!this.macroblockIntra){u+=u<0?-1:1}u=u*this.quantizerScale*n[c]>>4;if((u&1)==0){u-=u>0?1:-1}if(u>2047){u=2047}else if(u<-2048){u=-2048}this.blockData[c]=u*f[c]}if(t==1){this.fillArray(this.blockData,this.blockData[0]+128>>8)}else{this.IDCT()}var h,p,d;if(e<4){h=this.currentY;d=this.codedWidth-8;p=this.mbRow*this.codedWidth+this.mbCol<<4;if((e&1)!=0){p+=8}if((e&2)!=0){p+=this.codedWidth<<3}}else{h=e==4?this.currentCb:this.currentCr;d=(this.codedWidth>>1)-8;p=(this.mbRow*this.codedWidth<<2)+(this.mbCol<<3)}t=0;var v=this.blockData;if(this.macroblockIntra){this.copyBlockToDestination(this.blockData,h,p,d)}else{this.addBlockToDestination(this.blockData,h,p,d)}};n.prototype.copyBlockToDestination=function(e,t,n,r){var i=0;for(var s=0;s<8;s++){for(var o=0;o<8;o++){t[n++]=e[i++]}n+=r}};n.prototype.addBlockToDestination=function(e,t,n,r){var i=0;for(var s=0;s<8;s++){for(var o=0;o<8;o++){t[n++]+=e[i++]}n+=r}};n.prototype.copyBlockToDestinationClamp=function(e,t,n,r){var i=0;for(var s=0;s<8;s++){for(var o=0;o<8;o++){var u=e[i++];t[n++]=u>255?255:u<0?0:u}n+=r}};n.prototype.addBlockToDestinationClamp=function(e,t,n,r){var i=0;for(var s=0;s<8;s++){for(var o=0;o<8;o++){var u=e[i++]+t[n];t[n++]=u>255?255:u<0?0:u}n+=r}};n.prototype.IDCT=function(){var e,t,n,r,i,s,o,u,a,f,l,c,h,p,d,v,m,g,y,b=this.blockData;for(y=0;y<8;++y){e=b[4*8+y];t=b[2*8+y]+b[6*8+y];n=b[5*8+y]-b[3*8+y];s=b[1*8+y]+b[7*8+y];o=b[3*8+y]+b[5*8+y];r=b[1*8+y]-b[7*8+y];i=s+o;u=b[0*8+y];h=(r*473-n*196+128>>8)-i;a=h-((s-o)*362+128>>8);f=u-e;l=((b[2*8+y]-b[6*8+y])*362+128>>8)-t;c=u+e;p=f+l;d=c+t;v=f-l;m=c-t;g=-a-(n*473+r*196+128>>8);b[0*8+y]=i+d;b[1*8+y]=h+p;b[2*8+y]=v-a;b[3*8+y]=m-g;b[4*8+y]=m+g;b[5*8+y]=a+v;b[6*8+y]=p-h;b[7*8+y]=d-i}for(y=0;y<64;y+=8){e=b[4+y];t=b[2+y]+b[6+y];n=b[5+y]-b[3+y];s=b[1+y]+b[7+y];o=b[3+y]+b[5+y];r=b[1+y]-b[7+y];i=s+o;u=b[0+y];h=(r*473-n*196+128>>8)-i;a=h-((s-o)*362+128>>8);f=u-e;l=((b[2+y]-b[6+y])*362+128>>8)-t;c=u+e;p=f+l;d=c+t;v=f-l;m=c-t;g=-a-(n*473+r*196+128>>8);b[0+y]=i+d+128>>8;b[1+y]=h+p+128>>8;b[2+y]=v-a+128>>8;b[3+y]=m-g+128>>8;b[4+y]=m+g+128>>8;b[5+y]=a+v+128>>8;b[6+y]=p-h+128>>8;b[7+y]=d-i+128>>8}};var r=\"jsmp\",i=1,s=[0,23.976,24,25,29.97,30,50,59.94,60,0,0,0,0,0,0,0],o=new Uint8Array([0,1,8,16,9,2,3,10,17,24,32,25,18,11,4,5,12,19,26,33,40,48,41,34,27,20,13,6,7,14,21,28,35,42,49,56,57,50,43,36,29,22,15,23,30,37,44,51,58,59,52,45,38,31,39,46,53,60,61,54,47,55,62,63]),u=new Uint8Array([8,16,19,22,26,27,29,34,16,16,22,24,27,29,34,37,19,22,26,27,29,34,34,38,22,22,26,27,29,34,37,40,22,26,27,29,32,35,40,48,26,27,29,32,35,40,48,58,26,27,29,34,38,46,56,69,27,29,35,38,46,56,69,83]),a=new Uint8Array([16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16]),f=new Uint8Array([32,44,42,38,32,25,17,9,44,62,58,52,44,35,24,12,42,58,55,49,42,33,23,12,38,52,49,44,38,30,20,10,32,44,42,38,32,25,17,9,25,35,33,30,25,20,14,7,17,24,23,20,17,14,9,5,9,12,12,10,9,7,5,2]),l=new Int16Array([1*3,2*3,0,3*3,4*3,0,0,0,1,5*3,6*3,0,7*3,8*3,0,9*3,10*3,0,11*3,12*3,0,0,0,3,0,0,2,13*3,14*3,0,15*3,16*3,0,0,0,5,0,0,4,17*3,18*3,0,19*3,20*3,0,0,0,7,0,0,6,21*3,22*3,0,23*3,24*3,0,25*3,26*3,0,27*3,28*3,0,-1,29*3,0,-1,30*3,0,31*3,32*3,0,33*3,34*3,0,35*3,36*3,0,37*3,38*3,0,0,0,9,0,0,8,39*3,40*3,0,41*3,42*3,0,43*3,44*3,0,45*3,46*3,0,0,0,15,0,0,14,0,0,13,0,0,12,0,0,11,0,0,10,47*3,-1,0,-1,48*3,0,49*3,50*3,0,51*3,52*3,0,53*3,54*3,0,55*3,56*3,0,57*3,58*3,0,59*3,60*3,0,61*3,-1,0,-1,62*3,0,63*3,64*3,0,65*3,66*3,0,67*3,68*3,0,69*3,70*3,0,71*3,72*3,0,73*3,74*3,0,0,0,21,0,0,20,0,0,19,0,0,18,0,0,17,0,0,16,0,0,35,0,0,34,0,0,33,0,0,32,0,0,31,0,0,30,0,0,29,0,0,28,0,0,27,0,0,26,0,0,25,0,0,24,0,0,23,0,0,22]),c=new Int8Array([1*3,2*3,0,-1,3*3,0,0,0,1,0,0,17]),h=new Int8Array([1*3,2*3,0,3*3,4*3,0,0,0,10,5*3,6*3,0,0,0,2,7*3,8*3,0,0,0,8,9*3,10*3,0,11*3,12*3,0,-1,13*3,0,0,0,18,0,0,26,0,0,1,0,0,17]),p=new Int8Array([1*3,2*3,0,3*3,5*3,0,4*3,6*3,0,8*3,7*3,0,0,0,12,9*3,10*3,0,0,0,14,13*3,14*3,0,12*3,11*3,0,0,0,4,0,0,6,18*3,16*3,0,15*3,17*3,0,0,0,8,0,0,10,-1,19*3,0,0,0,1,20*3,21*3,0,0,0,30,0,0,17,0,0,22,0,0,26]),d=new Int16Array([2*3,1*3,0,3*3,6*3,0,4*3,5*3,0,8*3,11*3,0,12*3,13*3,0,9*3,7*3,0,10*3,14*3,0,20*3,19*3,0,18*3,16*3,0,23*3,17*3,0,27*3,25*3,0,21*3,28*3,0,15*3,22*3,0,24*3,26*3,0,0,0,60,35*3,40*3,0,44*3,48*3,0,38*3,36*3,0,42*3,47*3,0,29*3,31*3,0,39*3,32*3,0,0,0,32,45*3,46*3,0,33*3,41*3,0,43*3,34*3,0,0,0,4,30*3,37*3,0,0,0,8,0,0,16,0,0,44,50*3,56*3,0,0,0,28,0,0,52,0,0,62,61*3,59*3,0,52*3,60*3,0,0,0,1,55*3,54*3,0,0,0,61,0,0,56,57*3,58*3,0,0,0,2,0,0,40,51*3,62*3,0,0,0,48,64*3,63*3,0,49*3,53*3,0,0,0,20,0,0,12,80*3,83*3,0,0,0,63,77*3,75*3,0,65*3,73*3,0,84*3,66*3,0,0,0,24,0,0,36,0,0,3,69*3,87*3,0,81*3,79*3,0,68*3,71*3,0,70*3,78*3,0,67*3,76*3,0,72*3,74*3,0,86*3,85*3,0,88*3,82*3,0,-1,94*3,0,95*3,97*3,0,0,0,33,0,0,9,106*3,110*3,0,102*3,116*3,0,0,0,5,0,0,10,93*3,89*3,0,0,0,6,0,0,18,0,0,17,0,0,34,113*3,119*3,0,103*3,104*3,0,90*3,92*3,0,109*3,107*3,0,117*3,118*3,0,101*3,99*3,0,98*3,96*3,0,100*3,91*3,0,114*3,115*3,0,105*3,108*3,0,112*3,111*3,0,121*3,125*3,0,0,0,41,0,0,14,0,0,21,124*3,122*3,0,120*3,123*3,0,0,0,11,0,0,19,0,0,7,0,0,35,0,0,13,0,0,50,0,0,49,0,0,58,0,0,37,0,0,25,0,0,45,0,0,57,0,0,26,0,0,29,0,0,38,0,0,53,0,0,23,0,0,43,0,0,46,0,0,42,0,0,22,0,0,54,0,0,51,0,0,15,0,0,30,0,0,39,0,0,47,0,0,55,0,0,27,0,0,59,0,0,31]),v=new Int16Array([1*3,2*3,0,4*3,3*3,0,0,0,0,6*3,5*3,0,8*3,7*3,0,0,0,-1,0,0,1,9*3,10*3,0,12*3,11*3,0,0,0,2,0,0,-2,14*3,15*3,0,16*3,13*3,0,20*3,18*3,0,0,0,3,0,0,-3,17*3,19*3,0,-1,23*3,0,27*3,25*3,0,26*3,21*3,0,24*3,22*3,0,32*3,28*3,0,29*3,31*3,0,-1,33*3,0,36*3,35*3,0,0,0,-4,30*3,34*3,0,0,0,4,0,0,-7,0,0,5,37*3,41*3,0,0,0,-5,0,0,7,38*3,40*3,0,42*3,39*3,0,0,0,-6,0,0,6,51*3,54*3,0,50*3,49*3,0,45*3,46*3,0,52*3,47*3,0,43*3,53*3,0,44*3,48*3,0,0,0,10,0,0,9,0,0,8,0,0,-8,57*3,66*3,0,0,0,-9,60*3,64*3,0,56*3,61*3,0,55*3,62*3,0,58*3,63*3,0,0,0,-10,59*3,65*3,0,0,0,12,0,0,16,0,0,13,0,0,14,0,0,11,0,0,15,0,0,-16,0,0,-12,0,0,-14,0,0,-15,0,0,-11,0,0,-13]),m=new Int8Array([2*3,1*3,0,6*3,5*3,0,3*3,4*3,0,0,0,1,0,0,2,9*3,8*3,0,7*3,10*3,0,0,0,0,12*3,11*3,0,0,0,4,0,0,3,13*3,14*3,0,0,0,5,0,0,6,16*3,15*3,0,17*3,-1,0,0,0,7,0,0,8]),g=new Int8Array([2*3,1*3,0,4*3,3*3,0,6*3,5*3,0,8*3,7*3,0,0,0,2,0,0,1,0,0,0,10*3,9*3,0,0,0,3,12*3,11*3,0,0,0,4,14*3,13*3,0,0,0,5,16*3,15*3,0,0,0,6,17*3,-1,0,0,0,7,0,0,8]),y=new Int32Array([1*3,2*3,0,4*3,3*3,0,0,0,1,7*3,8*3,0,6*3,5*3,0,13*3,9*3,0,11*3,10*3,0,14*3,12*3,0,0,0,257,20*3,22*3,0,18*3,21*3,0,16*3,19*3,0,0,0,513,17*3,15*3,0,0,0,2,0,0,3,27*3,25*3,0,29*3,31*3,0,24*3,26*3,0,32*3,30*3,0,0,0,1025,23*3,28*3,0,0,0,769,0,0,258,0,0,1793,0,0,65535,0,0,1537,37*3,36*3,0,0,0,1281,35*3,34*3,0,39*3,38*3,0,33*3,42*3,0,40*3,41*3,0,52*3,50*3,0,54*3,53*3,0,48*3,49*3,0,43*3,45*3,0,46*3,44*3,0,0,0,2049,0,0,4,0,0,514,0,0,2305,51*3,47*3,0,55*3,57*3,0,60*3,56*3,0,59*3,58*3,0,61*3,62*3,0,0,0,2561,0,0,3329,0,0,6,0,0,259,0,0,5,0,0,770,0,0,2817,0,0,3073,76*3,75*3,0,67*3,70*3,0,73*3,71*3,0,78*3,74*3,0,72*3,77*3,0,69*3,64*3,0,68*3,63*3,0,66*3,65*3,0,81*3,87*3,0,91*3,80*3,0,82*3,79*3,0,83*3,86*3,0,93*3,92*3,0,84*3,85*3,0,90*3,94*3,0,88*3,89*3,0,0,0,515,0,0,260,0,0,7,0,0,1026,0,0,1282,0,0,4097,0,0,3841,0,0,3585,105*3,107*3,0,111*3,114*3,0,104*3,97*3,0,125*3,119*3,0,96*3,98*3,0,-1,123*3,0,95*3,101*3,0,106*3,121*3,0,99*3,102*3,0,113*3,103*3,0,112*3,116*3,0,110*3,100*3,0,124*3,115*3,0,117*3,122*3,0,109*3,118*3,0,120*3,108*3,0,127*3,136*3,0,139*3,140*3,0,130*3,126*3,0,145*3,146*3,0,128*3,129*3,0,0,0,2050,132*3,134*3,0,155*3,154*3,0,0,0,8,137*3,133*3,0,143*3,144*3,0,151*3,138*3,0,142*3,141*3,0,0,0,10,0,0,9,0,0,11,0,0,5377,0,0,1538,0,0,771,0,0,5121,0,0,1794,0,0,4353,0,0,4609,0,0,4865,148*3,152*3,0,0,0,1027,153*3,150*3,0,0,0,261,131*3,135*3,0,0,0,516,149*3,147*3,0,172*3,173*3,0,162*3,158*3,0,170*3,161*3,0,168*3,166*3,0,157*3,179*3,0,169*3,167*3,0,174*3,171*3,0,178*3,177*3,0,156*3,159*3,0,164*3,165*3,0,183*3,182*3,0,175*3,176*3,0,0,0,263,0,0,2562,0,0,2306,0,0,5633,0,0,5889,0,0,6401,0,0,6145,0,0,1283,0,0,772,0,0,13,0,0,12,0,0,14,0,0,15,0,0,517,0,0,6657,0,0,262,180*3,181*3,0,160*3,163*3,0,196*3,199*3,0,0,0,27,203*3,185*3,0,202*3,201*3,0,0,0,19,0,0,22,197*3,207*3,0,0,0,18,191*3,192*3,0,188*3,190*3,0,0,0,20,184*3,194*3,0,0,0,21,186*3,193*3,0,0,0,23,204*3,198*3,0,0,0,25,0,0,24,200*3,205*3,0,0,0,31,0,0,30,0,0,28,0,0,29,0,0,26,0,0,17,0,0,16,189*3,206*3,0,187*3,195*3,0,218*3,211*3,0,0,0,37,215*3,216*3,0,0,0,36,210*3,212*3,0,0,0,34,213*3,209*3,0,221*3,222*3,0,219*3,208*3,0,217*3,214*3,0,223*3,220*3,0,0,0,35,0,0,267,0,0,40,0,0,268,0,0,266,0,0,32,0,0,264,0,0,265,0,0,38,0,0,269,0,0,270,0,0,33,0,0,39,0,0,7937,0,0,6913,0,0,7681,0,0,4098,0,0,7425,0,0,7169,0,0,271,0,0,274,0,0,273,0,0,272,0,0,1539,0,0,2818,0,0,3586,0,0,3330,0,0,3074,0,0,3842]),b=1,w=2,E=3,S=4,x=179,T=1,N=175,C=0,k=181,L=178;var A=[null,c,h,p];var O=function(e){this.bytes=new Uint8Array(e);this.length=this.bytes.length;this.writePos=this.bytes.length;this.index=0};O.NOT_FOUND=-1;O.prototype.findNextMPEGStartCode=function(){for(var e=this.index+7>>3;e<this.writePos;e++){if(this.bytes[e]==0&&this.bytes[e+1]==0&&this.bytes[e+2]==1){this.index=e+4<<3;return this.bytes[e+3]}}this.index=this.writePos<<3;return O.NOT_FOUND};O.prototype.nextBytesAreStartCode=function(){var e=this.index+7>>3;return e>=this.writePos||this.bytes[e]==0&&this.bytes[e+1]==0&&this.bytes[e+2]==1};O.prototype.nextBits=function(e){var t=this.index>>3,n=8-this.index%8;if(n>=e){return this.bytes[t]>>n-e&255>>8-e}var r=(this.index+e)%8,i=this.index+e-1>>3,s=this.bytes[t]&255>>8-n;for(t++;t<i;t++){s<<=8;s|=this.bytes[t]}if(r>0){s<<=r;s|=this.bytes[t]>>8-r}else{s<<=8;s|=this.bytes[t]}return s};O.prototype.getBits=function(e){var t=this.nextBits(e);this.index+=e;return t};O.prototype.advance=function(e){return this.index+=e};O.prototype.rewind=function(e){return this.index-=e}})(window) 	</script> 	<script type=\"text/javascript\">  		var canvas = document.getElementById('videoCanvas'); 		var ctx = canvas.getContext('2d'); 		ctx.fillStyle = '#444'; 		ctx.fillText('Loading...', canvas.width/2-30, canvas.height/3);  		var client = new WebSocket( 'ws://192.168.0.10:8084/' ); 		var player = new jsmpeg(client, {canvas:canvas}); 	</script> </body> </html> ";
		 wv.loadData(cnt, "text/html", null);
		 //wv.loadUrl("http://google.com/");
	}
	
	private String readTextFromResource(int resourceID)
	{
	    InputStream raw = getResources().openRawResource(resourceID);
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    int i;
	    try
	    {
	        i = raw.read();
	        while (i != -1)
	        {
	            stream.write(i);
	            i = raw.read();
	        }
	        raw.close();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return stream.toString();
	}



}
