#include <mydefines.h>

/*
 * Created by jz on 2019/4/14.
 */

int sys_iam(char * name)
{
    char * chr = "jiangzhen\n";
    fprintk(0, chr);
    fprintk(1, chr);
    fprintk(2, chr);

    /* 测试一下是否能在进程日志问价总输出信息 */
    RECORD_N(1);
    RECORD_J(1);
    RECORD_R(1);
    RECORD_W(1);
    RECORD_E(1);
}
