package com.main;

class Model {
	public String filename;
	public int off;
	public int len;
	public Model next = null;
	
	public Model(String filename, int off, int len){
		this.filename = filename;
		this.off = off;
		this.len = len;
	}
}

class ModelList
{
	private Model head = null;
	private Model tail = null;
	private int size = 0;
	
	public ModelList(){
		
	}
	
	public int getSize(){
		return this.size;
	}
	
	public Model getHead(){
		return head;
	}
	
	public Model getTail(){
		return tail;
	}
	
	public void addModel(Model model){
		if (head == null){
			head = model;
			tail = model;
		}else{
			tail.next = model;
			tail = model;
		}
		size++;
	}
	
	public void deleteModel(Model model){
		if (size  <= 0){
			return;
		}
		
		Model currModel = this.head;
		if (this.head.filename.equals(model.filename)){
			while(currModel != null){
				currModel.off -= this.head.len;
				currModel = currModel.next;
			}
			this.head = this.head.next;
		}else{
			currModel = this.head;
			while(currModel.next != null && !currModel.next.filename.equals(model.filename)){
				currModel = currModel.next;
			}
			
			if (currModel == null)
				return;
			
			currModel.next = currModel.next.next;
			currModel = currModel.next;
			while(currModel != null){
				currModel.off -= model.len;
				currModel = currModel.next;
			}
		}
		
		--size;
	}
	
	public Model findModel(String filename){
		Model currModel = this.head;
		while (currModel != null && !currModel.filename.equals(filename)){
			currModel = currModel.next;
		}
		return currModel;
	}
	
	public void updateModel(String filename, int off, int len){
		Model currModel = this.head;
		
		while(currModel != null && !currModel.filename.equals(filename)){
			currModel = currModel.next;
		}
		if (currModel == null){
			return;
		}
		
		int tmpLen = len-currModel.len;
		
		currModel.filename = filename;
		currModel.off = off;
		currModel.len = len;
		currModel = currModel.next;
		while(currModel != null){
			currModel.off += tmpLen;
			currModel = currModel.next;
		}
	}
	
	public void printModel(){
		Model currModel = this.head;
		while(currModel != null){
			System.out.println(currModel.filename+","+currModel.off+","+currModel.len);
			currModel = currModel.next;
		}
	}
}
