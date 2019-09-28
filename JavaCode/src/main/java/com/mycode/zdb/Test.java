package com.mycode.zdb;

import limax.xmlconfig.Service;
import limax.zdb.Procedure;

import javax.management.relation.RoleInfo;

/**
 * @author jiangzhen
 * @date 2019/5/10 13:46
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Service.addRunAfterEngineStartTask(() -> {
			Procedure.call(() -> {

				for (int i = 0; i < 1000; ++i) {
					if (i == 2) {
						int x = 10 / 0;
					}
				}

				return true;
			});
        });

        Service.run(Object.class.getResource("/zdb_config.xml").getPath());
    }
}
