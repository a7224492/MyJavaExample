package com.mycode.minigame;

import java.io.IOException;

/**
 * @author jiangzhen
 */
public class MiniBuildTool
{
	private static final MiniBuildConfig config = new MiniBuildConfig();

	public void main(String[] args)
		throws IOException
	{
		// 执行build命令
		String buildCmd = String.format("%s --path %s --build configPath=%s", config.getAppConfigPath(), config.getMiniRootPath(), config.getWeChatGameConfigPath());
		Runtime.getRuntime().exec(buildCmd);

		// 修改game.json超时时间

		// res打包
	}
}
