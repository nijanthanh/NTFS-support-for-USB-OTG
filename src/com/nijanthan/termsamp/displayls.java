package com.nijanthan.termsamp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class displayls extends Activity {
	
	String xyz()
	{
	try{
	Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "system/bin/sh"});
	Process p1 = Runtime.getRuntime().exec("mount -o rw,remount -t yaffs2 /dev/block/sda1 /system");
	Process p3 = Runtime.getRuntime().exec("mount -o rw,remount -t yaffs2 /dev/block/sda1 /system/bin");
	Process p2 = Runtime.getRuntime().exec("chmod 777 /system/bin");
	
	DataOutputStream stdin = new DataOutputStream(p.getOutputStream());
    
	AssetManager am=this.getAssets();
	InputStream is;
	is = am.open("ntfs-3g.zip");
	
	File f=new File("/sdcard/ntfs-3g.zip");
	  OutputStream out=new FileOutputStream(f);
	  byte buff[]=new byte[2100000];
	  int leng=1;
	  Log.i("a", "hii");
	//while((leng=is.read(buff))>0)
	  leng=is.read(buff,0,2100000);
		Log.i("b", "bye"); 
		Log.i("c",Integer.toString(leng));
		out.write(buff, 0, leng);
		
		 Log.i("d","done");
		
		 unpackZip("/sdcard/","ntfs-3g.zip");
	  out.close();
	  is.close();
	  
	  
	  AssetManager am1=this.getAssets();
		InputStream is1;
		is1 = am1.open("fuse.ko");
		
		File f1=new File("/sdcard/Ntfs/fuse.ko");
		  OutputStream out5=new FileOutputStream(f1);
		  byte buff1[]=new byte[2100000];
		  int leng1=1;
		  Log.i("a", "hii");
		  leng1=is1.read(buff1,0,2100000);
			Log.i("b", "bye"); 
			Log.i("c",Integer.toString(leng1));
			out5.write(buff1, 0, leng1);
		  out5.close();
		  is1.close();
		  
    //Process p3 = Runtime.getRuntime().exec("chmod 777 /sdcard/text2");
	    
    Log.i("e","5");
    //from here all commands are executed with su permissions
	//stdin.writeBytes("cd /system/xbin\n");
	stdin.writeBytes("cat /sdcard/ntfs-3g > /system/bin/ntfs-3g\n");
	
	Log.i("f","6");
	stdin.writeBytes("insmod /sdcard/Ntfs/fuse.ko");
	
	stdin.writeBytes("ntfs-3g /dev/block/sda1 /mnt/sdcard/usbStorage");
	//above commanded is not executed
	Log.i("g","7");
	    
	return "success";

	}
	catch(IOException e)
	{
		e.printStackTrace();
		return "error";
	}
	catch(IndexOutOfBoundsException ie)
	{
		ie.printStackTrace();
		return "error1";
	}
	}
	
	private boolean unpackZip(String path, String zipname)
	{       
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
	         is = new FileInputStream(path + zipname);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;

	         while ((ze = zis.getNextEntry()) != null) 
	         {
	             ByteArrayOutputStream baos = new ByteArrayOutputStream();
	             byte[] buffer = new byte[2100000];
	             int count;

	             String filename = ze.getName();
	             FileOutputStream fout = new FileOutputStream(path + filename);

	             while ((count = zis.read(buffer)) != -1) 
	             {
	                 baos.write(buffer, 0, count);
	                 byte[] bytes = baos.toByteArray();
	                 fout.write(bytes);             
	                 baos.reset();
	             }

	             fout.close();               
	             zis.closeEntry();
	         }

	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return false;
	     }

	    return true;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("-2");
		setContentView(R.layout.displayls);
		TextView tv= (TextView) findViewById(R.id.textView1);
		try {
			System.out.println("-1");
			tv.append("\n");
			//tv.append(getStringFromAssetFile(this));
			//tv.append("\n\n");
			tv.append(xyz());
			//tv.append("\ndot.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
