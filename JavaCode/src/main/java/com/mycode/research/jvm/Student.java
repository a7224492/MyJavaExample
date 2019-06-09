package com.mycode.research.jvm;

/**
 * @author jiangzhen
 */
public class Student extends People
{
	private String school;

	public Student(String name, int age, String school)
	{
		super(name, age);
		this.school = school;
	}

	public String getSchool()
	{
		return school;
	}
}
