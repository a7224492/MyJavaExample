#include <stdio.h>

int i = 0x12345678;
int i2 = 0x13445;

int main()
{
    int local = 0x321;
    int local2 = 0x1114;

    printf("The logical/virtual address of i is 0x%08x\ni2 is 0x%08x\nlocal is 0x%08x\nlocal2 is 0x%08x\n", &i, &i2, &local, &local2);
    fflush(stdout);

    while (i)
        ;

    return 0;
}

