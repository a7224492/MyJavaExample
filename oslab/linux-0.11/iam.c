#define __LIBRARY__

#include <unistd.h>

_syscall1(int, iam, const char *, name)

int main(int argc, char **argv)
{
    if (argc < 1) {
        return -1;
    }

    iam("jiangzhen");
}