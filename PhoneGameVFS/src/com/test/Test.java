package com.test;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.main.MyVFS;

public class Test {

	public static void main(String[] args) {
		MyVFS myvfs = new MyVFS();
		myvfs.initDiskFileDir("d:/VFS");
//		myvfs.addDiskFile("d:/VFS/a.txt");
//		myvfs.addDiskFile("d:/VFS/b.txt");
//		myvfs.addDiskFile("d:/VFS/c.txt");
//		myvfs.deleteDiskFile("d:/VFS/b.txt");
//		myvfs.updateDiskFile("d:/VFS/a.txt");
		if (myvfs.compareDiskFile("d:/VFS/a.txt")){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
		if (myvfs.compareDiskFile("d:/VFS/b.txt")){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
	}
}
