#include <linux/kernel.h>

/*
 * Created by jz on 2019/4/14.
 */

int sys_iam(char * name)
{
    char *chr = "jiangzhen\n";
    printk("%s\n", chr);

    return 0;
}