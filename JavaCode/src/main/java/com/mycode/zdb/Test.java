package com.mycode.zdb;

import limax.xmlconfig.Service;
import limax.zdb.Procedure;
import table.Role_info;
import xbean.RoleInfo;

/**
 * @author jiangzhen
 * @date 2019/5/10 13:46
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Service.addRunAfterEngineStartTask(() -> {
			Procedure.call(() -> {
				Role_info.insert(321313);

				for (int i = 0; i < 1000; ++i) {
					if (i == 2) {
						int x = 10 / 0;
					}

					RoleInfo roleInfo = Role_info.update(i + 1);
					if (roleInfo == null) {
						roleInfo = Role_info.insert(i + 1);
					}

					roleInfo.setCardCount(100);
					roleInfo.setAccountId(100);
					roleInfo.setNickname("jiangzhen" + i);
				}

				return true;
			});
        });

        Service.run(Object.class.getResource("/zdb_config.xml").getPath());
    }
}
