#define __LIBRARY__
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <linux/kernel.h>

#define MAX_NAME_LENGTH 23

const char kernel_name[MAX_NAME_LENGTH];

int sys_iam(const char * name)
{
    printk(name);
    int nameLen = strlen(name);
    if (nameLen > MAX_NAME_LENGTH) {
        return -EINVAL;
    }

    strcpy(kernel_name, name);
    return nameLen;
}
