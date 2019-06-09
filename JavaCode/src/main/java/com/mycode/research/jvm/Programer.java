package com.mycode.research.jvm;

/**
 * @author jiangzhen
 */
public class Programer extends People
{
	private String company;

	public Programer(String name, int age, String company)
	{
		super(name, age);
		this.company = company;
	}

	public String getCompany()
	{
		return company;
	}
}
