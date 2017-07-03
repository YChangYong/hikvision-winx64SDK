package control;

import com.sun.jna.*;

public interface Stitcher extends Library{
	Stitcher INSTANCE  = (Stitcher)Native.loadLibrary("stitcher_win_lib_project",Stitcher.class);  
	public int stichimg_from_path(String[] src_path,int size);
	public int stichimg_from_path_to_path(String[] src_path,int size,String dst_path);
}
