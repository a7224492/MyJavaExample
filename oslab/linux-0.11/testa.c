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
    for (i = 0; i < 10; ++i) {
        shm[i] = 30 - i;
    }

    printf("shm[5] = %d\n", shm[5]);

    shmid2 = shmget(101, sizeof(int) * 10);
    shm2 = (int *)shmat(shmid2);
    for (i = 0; i < 5; ++i) {
        shm2[i] = 100 - i * 10;
    }

    printf("shm2[2] = %d\n", shm2[2]);
    printf("shm[5] = %d\n", shm[5]);

    sleep(10);
    printf("testa finish...");
    return 0;
}