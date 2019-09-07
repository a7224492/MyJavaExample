#define __LIBRARY__
#include <unistd.h>
#include <stdio.h>

_syscall2(int, shmget, int, key, int, size)
_syscall1(void*, shmat, int, shmid)

int main(int argc, char **argv)
{
    int shmid, shmid2, i;
    int *shm, *shm2;

    shmid = shmget(100, sizeof(int) * 20);
    shm = (int *)shmat(shmid);

    printf("share memory 1 content is ");
    for (i = 0; i < 10; ++i) {
        printf("%d ", shm[i]);
    }
    printf("\n");

    shmid2 = shmget(101, sizeof(int) * 10);
    shm2 = (int *)shmat(shmid2);

    printf("share memory 2 content is ");
    for (i = 0; i < 5; ++i) {
        printf("%d ", shm2[i]);
    }
    printf("\n");

    return 0;
}