package stitcher;


import com.sun.jna.Library;
import com.sun.jna.Native;

public interface StitcherTest extends Library{
	StitcherTest instanceDll  = (StitcherTest)Native.loadLibrary("stitcher_win_lib_project",StitcherTest.class);  
	public int stichimg_from_path(String[] src_path,int size);
	public int stichimg_from_path_to_path(String[] src_path,int size,String dst_path);
}
