package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MyVFS {
	public static void main(String[] args) {
		
	}
	
	private File myvfsFile = null;
	private File myvfsRecordFile = null;
	private ModelList ml = null;
	
	private void writeModelList(){
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(myvfsRecordFile);
			bw = new BufferedWriter(fw);
			Model currModel = this.ml.getHead();
			while(currModel != null){
				String record = null;
				if (this.ml.getSize() != 0){
					record = currModel.filename+" "+currModel.off+" "+currModel.len+"\n";
				}else{
					record = "";
				}
				bw.write(record);
				currModel = currModel.next;
			}
		}catch(IOException e){
			e.printStackTrace();
			return;
		}finally{
			try{
				if (bw != null){
					bw.close();
				}
				if (fw != null){
					fw.close();
				}
			}catch(IOException e){
				e.printStackTrace();
				return;
			}
		}
	}

	public void initDiskFileDir(String dirName){
		ml = new ModelList();
		
		File dir = new File(dirName);
		
		if (!dir.exists()){
			//create dirs
			if (!dir.mkdirs()){
				System.out.println("create dir error!");
				return;
			}
		}
		myvfsFile = new File(dirName+"/myvfs");
		myvfsRecordFile = new File(dirName+"/record");
		if (!myvfsFile.exists()){
			try{
				myvfsFile.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
				return;
			}
		}
		if (!myvfsRecordFile.exists()){
			try{
				myvfsRecordFile.createNewFile();
			}catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}else{
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try{
				fis = new FileInputStream(myvfsRecordFile);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				String record = null;
				while((record = br.readLine()) != null){
					String[] v = record.split(" ");
					Model model = new Model(v[0], Integer.valueOf(v[1]), Integer.valueOf(v[2]));
					this.ml.addModel(model);
				}
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					if (br != null){
						br.close();
					}
					if (isr != null){
						isr.close();
					}
					if (fis != null){
						fis.close();
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addDiskFile(String filename){
		File addFile = new File(filename);
		if (!addFile.exists()){
			System.out.println("addDiskFile() error: the file doesn't exist!");
			return;
		}
		
		FileInputStream fis = null;
		RandomAccessFile raf = null;
		
		try{
			//append file to myvfsFile
			fis = new FileInputStream(addFile);
			raf = new RandomAccessFile(myvfsFile, "rw");
			byte[] data = new byte[1024];
			int readlen = 0;
			while((readlen = fis.read(data, 0, 1024)) != -1){
				raf.seek(raf.length());
				raf.write(data, 0, readlen);
			}
		}catch(IOException e){
			e.printStackTrace();
			return;
		}finally{
			//close file stream
			try{	
				if (raf != null){
					raf.close();
				}
				if (fis != null){
					fis.close();
				}
			}catch(IOException e){
				e.printStackTrace();
				return;
			}
		}
		
		int l1 = (int)myvfsFile.length();
		int l2 = (int)addFile.length();
		
		Model model = new Model(filename, (int)(myvfsFile.length()-addFile.length()), (int)(addFile.length()));
		this.ml.addModel(model);
		
		//write model to record file
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(myvfsRecordFile, true);
			bw = new BufferedWriter(fw);
			String record = model.filename+" "+model.off+" "+model.len+"\n";
			bw.write(record);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (bw != null){
					bw.close();
				}
				if (fw != null){
					fw.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void deleteDiskFile(String filename){
		Model model = this.ml.findModel(filename);
		
		if (model == null){
			return;
		}
		
		int vfsFileLen = (int)myvfsFile.length();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try{
			//delete bytes from myvfsFile
			fis = new FileInputStream(myvfsFile);
			byte[] data = new byte[vfsFileLen];
			int n = 0;
			while(n < vfsFileLen){
				if ((vfsFileLen-n) < 1024){
					fis.read(data, n, (vfsFileLen-n));
				}else{
					fis.read(data, n, 1024);
				}
				n += 1024;
			}
			
			int off = model.off;
			int len = model.len;
			
			//only this file in myvfsFile
			if (model.len == vfsFileLen){
				if (fos != null){
					fos.close();
				}
				if (fis != null){
					fis.close();
				}
				myvfsFile.delete();
				myvfsFile.createNewFile();

			}else{
				for (int i = off; i < myvfsFile.length()-len; ++i){
					data[i] = data[i+len];
				}
				vfsFileLen -= len;
				fos = new FileOutputStream(myvfsFile);
				n = 0;
				while(n < vfsFileLen){
					if ((vfsFileLen-n) < 1024){
						fos.write(data, n, vfsFileLen-n);
					}else{
						fos.write(data, n, 1024);
					}
					n += 1024;
				}
			}	
		}catch(IOException e){
			e.printStackTrace();
			return;
		}finally{
			try{
				if (fos != null){
					fos.close();
				}
				if (fis != null){
					fis.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//delete file from record
		this.ml.deleteModel(model);
		writeModelList();
	}

	public void updateDiskFile(String filename){
		Model model = null;
		model = this.ml.findModel(filename);
		if (model == null){
			System.out.println("the file doesn't exist!");
			return;
		}
		
		if (this.compareDiskFile(filename)){
			return;
		}
		
		//read file data
		File file = new File(filename);
		int len = (int)file.length();
		byte[] data = new byte[len];
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			if (fis.read(data, 0, len) != len){
				System.out.println("updateDiskFile error(): read file data error!");
				return;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (fis != null){
					fis.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//read myvfsFile data
		int len2 = (int)myvfsFile.length();
		byte[] data2 = new byte[len2];
		FileInputStream fis2 = null;
		try{
			fis2 = new FileInputStream(myvfsFile);
			if (fis2.read(data2, 0, len2) != len2){
				System.out.println("updateDiskFile error(): read myvfsFile error!");
				return;
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (fis2 != null){
					fis2.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//update data
		int len3 = len2-model.len+len;
		byte[] data3 = new byte[len3];
		int i = 0;
		for (int j = 0; j < model.off; ++j){
			data3[i++] = data2[j];
		}
		for (int j = 0; j < len; ++j){
			data3[i++] = data[j];
		}
		for (int j = model.off+model.len; j < len2; ++j){
			data3[i++] = data2[j];
		}
		
		//write data into myvfs
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(myvfsFile);
			fos.write(data3, 0, len3);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (fos != null){
					fos.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//update model
		this.ml.updateModel(filename, model.off, len);
		writeModelList();
		
	}

	public boolean compareDiskFile(String filename){
		Model model = this.ml.findModel(filename);
		if (model == null){
			return false;
		}
		
		//read file data
		File file = new File(filename);
		int len = (int)file.length();
		
		if (len != model.len){
			return false;
		}
		
		byte[] data = new byte[len];
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			fis.read(data, 0, len);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (fis != null){
					fis.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//read model data
		byte[] data2 = new byte[model.len];
		RandomAccessFile raf = null;
		try{
			raf = new RandomAccessFile(myvfsFile, "rw");
			raf.seek(model.off);
			raf.read(data2, 0, model.len);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if (raf != null){
					raf.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < model.len; ++i){
			if (data[i] != data2[i]){
				return false;
			}
		}
		
		return true;
	}
}
