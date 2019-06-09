package com.mycode.minigame;

/**
 * @author jiangzhen
 */
public class MiniBuildConfig
{
	private String miniRootPath = "F:\\QuanGuoProject\\miniclient";
	private String targetVersion = "1.2.11";
	private String cocosCreatorAppPath = "F:\\CocosCreator\\cocoscreator\\CocosCreator.exe";
	private String weChatGameConfigPath = miniRootPath + "\\build\\wechatgame\\game.json";
	private String clientJsonDict = "";
	private String buildFolderPath = "";
	private String buildBackFolderPath = "";
	private String resFolderPath = miniRootPath + "\\build\\wechatgame\\res";
	private String ftpUploadPath = "";
	private String publicType = "";
	private String appConfigPath = "";

	public String getMiniRootPath()
	{
		return miniRootPath;
	}

	public void setMiniRootPath(String miniRootPath)
	{
		this.miniRootPath = miniRootPath;
	}

	public String getTargetVersion()
	{
		return targetVersion;
	}

	public void setTargetVersion(String targetVersion)
	{
		this.targetVersion = targetVersion;
	}

	public String getCocosCreatorAppPath()
	{
		return cocosCreatorAppPath;
	}

	public void setCocosCreatorAppPath(String cocosCreatorAppPath)
	{
		this.cocosCreatorAppPath = cocosCreatorAppPath;
	}

	public String getAppConfigPath()
	{
		return appConfigPath;
	}

	public void setAppConfigPath(String appConfigPath)
	{
		this.appConfigPath = appConfigPath;
	}

	public String getWeChatGameConfigPath()
	{
		return weChatGameConfigPath;
	}

	public void setWeChatGameConfigPath(String weChatGameConfigPath)
	{
		this.weChatGameConfigPath = weChatGameConfigPath;
	}

	public String getClientJsonDict()
	{
		return clientJsonDict;
	}

	public void setClientJsonDict(String clientJsonDict)
	{
		this.clientJsonDict = clientJsonDict;
	}

	public String getBuildFolderPath()
	{
		return buildFolderPath;
	}

	public void setBuildFolderPath(String buildFolderPath)
	{
		this.buildFolderPath = buildFolderPath;
	}

	public String getBuildBackFolderPath()
	{
		return buildBackFolderPath;
	}

	public void setBuildBackFolderPath(String buildBackFolderPath)
	{
		this.buildBackFolderPath = buildBackFolderPath;
	}

	public String getResFolderPath()
	{
		return resFolderPath;
	}

	public void setResFolderPath(String resFolderPath)
	{
		this.resFolderPath = resFolderPath;
	}

	public String getFtpUploadPath()
	{
		return ftpUploadPath;
	}

	public void setFtpUploadPath(String ftpUploadPath)
	{
		this.ftpUploadPath = ftpUploadPath;
	}

	public String getPublicType()
	{
		return publicType;
	}

	public void setPublicType(String publicType)
	{
		this.publicType = publicType;
	}
}
