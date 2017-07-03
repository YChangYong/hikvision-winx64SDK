package control;

import com.sun.jna.NativeLong;

import control.HCNetSDK.NET_DVR_JPEGPARA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;

/**
 * Created by wsx on 2017-07-01.
 */
public class ControlFrame extends JFrame {

	private JButton left_bt;
	private JButton right_bt;
	private JButton screenshot_bt;
	private JButton startrecoedvideo_bt;
	private JButton endrecoedvideo_bt;
	private JButton playback_bt;
	private JButton scanAllView_bt;

	private NativeLong lPreviewHandle;
	private HCNetSDK hCNetSDK;
	private NativeLong lUserID;
	private Stitcher stitcher;

	private boolean bRecordVideo;

	public ControlFrame(NativeLong lPreviewHandle, HCNetSDK hCNetSDK, NativeLong lUserID, Stitcher stitcher) {
		this.lPreviewHandle = lPreviewHandle;
		this.hCNetSDK = hCNetSDK;
		this.lUserID = lUserID;
		this.stitcher = stitcher;
		bRecordVideo = false;
		initComponents();
	}

	private void initComponents() {
		left_bt = new JButton("左");
		right_bt = new JButton("右");
		screenshot_bt = new JButton("抓图");
		startrecoedvideo_bt = new JButton("开始录像");
		endrecoedvideo_bt = new JButton("结束录像");
		playback_bt = new JButton("回放");
		scanAllView_bt = new JButton("扫描全景");

		left_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PTZControlAll(lPreviewHandle, HCNetSDK.PAN_LEFT, 0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				PTZControlAll(lPreviewHandle, HCNetSDK.PAN_LEFT, 1);
			}
		});

		right_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PTZControlAll(lPreviewHandle, HCNetSDK.PAN_RIGHT, 0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				PTZControlAll(lPreviewHandle, HCNetSDK.PAN_RIGHT, 1);
			}
		});

		screenshot_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CapturePicture("./pictures/screenshot/");
			}
		});

		startrecoedvideo_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				startRecodeVideo();
			}
		});

		endrecoedvideo_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				endRecodeVideo();
			}
		});

		scanAllView_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				scanAllView();
			}
		});

		playback_bt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				searchFile();
			}
		});

		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new GridLayout(4, 2));
		add(left_bt);
		add(startrecoedvideo_bt);
		add(right_bt);
		add(endrecoedvideo_bt);
		add(screenshot_bt);
		add(playback_bt);
		add(scanAllView_bt);
		setVisible(true);
	}

	/**
	 * 控制转的函数
	 *
	 * @param lRealHandle
	 *            预览句柄
	 * @param iPTZCommand
	 *            操作命令
	 * @param iStop
	 *            是否停止这个命令
	 */
	public void PTZControlAll(NativeLong lRealHandle, int iPTZCommand, int iStop) {
		int iSpeed = 0;
		if (lRealHandle.intValue() >= 0) {
			boolean ret;
			if (iSpeed >= 1)// 有速度的ptz
			{
				ret = hCNetSDK.NET_DVR_PTZControlWithSpeed(lRealHandle, iPTZCommand, iStop, iSpeed);
				if (!ret) {
					System.out.println("云台控制失败");
					return;
				}
			} else// 速度为默认时
			{
				ret = hCNetSDK.NET_DVR_PTZControl(lRealHandle, iPTZCommand, iStop);
				if (!ret) {
					System.out.println("云台控制失败");
					return;
				}
			}
		}
	}

	/**
	 * 抓图bmp到工程根目录下的pictures文件夹中
	 */
	public void CapturePicture(String path) {
		NET_DVR_JPEGPARA jpegArea = new NET_DVR_JPEGPARA();
		jpegArea.wPicSize = 0xff;
		jpegArea.wPicQuality = 0;

		String sPicName = path + System.currentTimeMillis() + ".jpg";

		if (hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, new NativeLong(1), jpegArea, sPicName)) {
			System.out.println("抓图:" + sPicName);
		} else {
			System.out.println("抓图失败");
			return;
		}

		// String sPicName = "./pictures/" + System.currentTimeMillis() +
		// ".bmp";
		// if (hCNetSDK.NET_DVR_CapturePicture(lPreviewHandle, sPicName)) {
		// System.out.println("抓图:" + sPicName);
		// } else {
		// System.out.println("抓图失败");
		// return;
		// }
	}

	public void startRecodeVideo() {
		if (bRecordVideo != false)
			return;

		String videoname = "./videos/" + String.valueOf(System.currentTimeMillis()) + ".mp4";
		boolean isvideobegin = hCNetSDK.NET_DVR_SaveRealData(lUserID, videoname);
		if (isvideobegin == false) {
			System.out.println("开始录像失败");
			return;
		}
		System.out.println("开始录像");
		bRecordVideo = true;
	}

	public void endRecodeVideo() {
		if (bRecordVideo != true)
			return;

		boolean isvideoend = hCNetSDK.NET_DVR_StopSaveRealData(lUserID);
		if (isvideoend == false) {
			System.out.println("结束录像失败");
			return;
		}
		System.out.println("结束录像");
		bRecordVideo = false;
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}
	
	public void scanAllView_up() {
		

		for (int i = 0; i < 6; i++) {
			CapturePicture("./pictures/up/");

			Rotate(lPreviewHandle, HCNetSDK.PAN_LEFT, 1000, 4);

			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void scanAllView_down()
	{
		for (int i = 0; i < 6; i++) {
			CapturePicture("./pictures/down/");

			Rotate(lPreviewHandle, HCNetSDK.PAN_RIGHT, 1000, 4);

			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void scanAllView()
	{
		File result = new File("./result.jpg");
		if(result.exists())
			result.delete();
		
		File file = new File("./pictures");
		if (file.exists())
			deleteDir(file);
		file.mkdirs();
		
		File file_up = new File("./pictures/up");
		file_up.mkdirs();
		
		File file_down = new File("./pictures/down");
		file_down.mkdirs();
		
		try {
			
			Rotate(lPreviewHandle, HCNetSDK.TILT_UP, 1500, 3);
			Thread.sleep(3000);
			scanAllView_up();
			Rotate(lPreviewHandle, HCNetSDK.TILT_DOWN, 800, 3);
			Thread.sleep(2300);
			scanAllView_down();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stitcherPic();
		
	}
	
	/**
	 * 持续转多长时间
	 * @param lRealHandle
	 * @param iPTZCommand
	 * @param time_ms
	 * @param ispeed
	 */
	public void Rotate(NativeLong lRealHandle, int iPTZCommand, long time_ms, int ispeed)
	{
		hCNetSDK.NET_DVR_PTZControlWithSpeed(lRealHandle, iPTZCommand, 0, ispeed);

		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				PTZControlAll(lRealHandle, iPTZCommand, 1);
			}
		}, time_ms);
	}

	public String searchFile() {
		JFileChooser jfc = new JFileChooser(new File("./videos/"));

		jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				if (f.getName().endsWith(".mp4") || f.isDirectory()) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return "*.mp4";
			}
		});

		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "选择");

		File file = jfc.getSelectedFile();
		if (file.isDirectory()) {
			System.out.println("文件夹:" + file.getAbsolutePath());
		} else if (file.isFile()) {
			System.out.println("文件:" + file.getAbsolutePath());
		}
		System.out.println(jfc.getSelectedFile().getName());
		return null;
	}
	
	 public void stitcherPic(){
	    	File file_up = new File("./pictures/up");
	    	File[] files_up = file_up.listFiles();
	    	
	    	File file_down = new File("./pictures/up");
	    	File[] files_down = file_down.listFiles();
	    	
	    	int len = files_up.length + files_down.length;
	    	if(len < 2)
	    	{
	    		System.out.println("图片太少");
	    		return;
	    	}
	    	
	    	String[] strs = new String[len];
	    	for(int i=1; i<=len/2; i++)
	    	{
	    		strs[i*2-2] = (files_up[i-1]).getAbsolutePath();
	    		strs[i*2-1] = (files_down[i-1]).getAbsolutePath();
	    		System.out.println(strs[i*2-2] + "------" + strs[i*2-1]);
	    	}
	    	stitcher.stichimg_from_path_to_path(strs,strs.length,"./result.jpg");
	    }

}
