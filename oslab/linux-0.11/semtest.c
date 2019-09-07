//
// Created by jiangzhen on 2019/4/24.
//
#include <unistd.h>

_syscall2(int, sem_open, const char *, name, int, value)
_syscall1(int, sem_wait, int, sem)
_syscall1(int, sem_post, int, sem)
_syscall1(int, sem_unlink, int, sem)

int main(int argc, char **argv)
{
    int sem = sem_open("jz", 1);
    sem_wait(sem);
    sem_post(sem);

    return 0;
}