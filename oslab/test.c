#include <stdio.h>

int i = 0x12345678;

int main()
{
    int local = 0x321;

    printf("The logical/virtual address of i is 0x%08x\nlocal is 0x%08x\n", &i, &local);
    fflush(stdout);

    while (i)
        ;

    return 0;
}

