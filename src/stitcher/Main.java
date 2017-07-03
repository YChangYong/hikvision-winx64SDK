package stitcher;

import java.util.Scanner;

import java.lang.String;

public class Main {  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
    	
    	Scanner in = new Scanner(System.in);
		System.out.print("Please input the number of row-picture:");
		int n = in.nextInt();
		in.close();
		
		String path[] = new String[n*2];
		for (int i = 1; i <= n; i++)
		{
			path[i*2-2] = String.format(".\\images\\7-43\\img (%d).jpg",i);
			path[i*2-1] = String.format(".\\images\\5-44\\img (%d).jpg", i);
		}
		
		// Check if have enough images
		int size = n*2;
		if (size < 2)
		{
			System.out.println("We need more pictures!");
			return;
		}
		//save result image to the local path
		//Stitcher.instanceDll.stichimg_from_path(path,size);
		//save result image to the custom path
		StitcherTest.instanceDll.stichimg_from_path_to_path(path,size,"./result.jpg");
    }  
  
} 